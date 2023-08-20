package com.lottoland.assignment.service;

import com.lottoland.assignment.repository.game.GameRepository;
import com.lottoland.assignment.repository.gamestats.GameStatsRepository;
import com.lottoland.assignment.service.simulator.RPSGameSimulator;
import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.dto.GameRoundResponseDTO;
import com.lottoland.assignment.utils.dto.GameStats;
import com.lottoland.assignment.utils.mappers.RPSMapper;
import io.micrometer.common.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.qos.logback.classic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RPSGameService {

    Logger logger = (Logger) LoggerFactory.getLogger(RPSGameService.class);

    private final RPSGameSimulator rpsGameSimulator;
    private final GameRepository gameRepository;
    private final GameStatsRepository gameStatsRepository;

    public RPSGameService(RPSGameSimulator rpsGameSimulator, GameRepository gameRepository, GameStatsRepository gameStatsRepository) {
        this.rpsGameSimulator = rpsGameSimulator;
        this.gameRepository = gameRepository;
        this.gameStatsRepository = gameStatsRepository;
    }

    // get number of rounds in session
    public Integer getSessionRoundCount(String sessionId) {
        var gamesFromSession = this.gameRepository.getGamesFromSession(sessionId);
        if (Objects.isNull(gamesFromSession) || gamesFromSession.isEmpty()) {
            return 0;
        }
        return this.gameRepository.getGamesFromSession(sessionId).size();
    }

    // get all rounds played in session and return them to web DTO
    public List<GameRoundResponseDTO> getSessionRounds(String sessionId) {
        ArrayList<GameRound> gamesFromSession = this.gameRepository.getGamesFromSession(sessionId);
        return RPSMapper.gameRoundToGameRoundResponseDTO(gamesFromSession);
    }

    // begin TRANSACTION here to ensure both repositories (session & global) are coordinated
    @Transactional
    public void playRound(String sessionId) {
        if (null == sessionId || StringUtils.isBlank(sessionId)) {
            throw new IllegalArgumentException("Null or blank parameter in method playRound()");
        }

        GameRound gameRound = rpsGameSimulator.playRPSRound();
        if (null == gameRound) {
            throw new RuntimeException("Game simulation failed");
        }

        try {
            gameRepository.addGameToSession(sessionId, gameRound);
            this.addGameStats(gameRound);
        } catch (Exception e) {
            logger.error("Error saving game in repositories");
        }
    }

    // adds game stats to stats repository
    // @Loggable(LogLevel.DEBUG)
    private boolean addGameStats(GameRound gameRound) {
        gameStatsRepository.addGameToTotal();
        switch (gameRound.gameResult()) {
            case DRAW -> gameStatsRepository.addDraw();
            case PLAYER_1 -> gameStatsRepository.addPlayer1Victory();
            case PLAYER_2 -> gameStatsRepository.addPlayer2Victory();
        }
        return true;
    }

    // gets all stats and returns them in a record
    public GameStats getGlobalGameStats() {
        return new GameStats(
                gameStatsRepository.getTotalGames(),
                gameStatsRepository.getPlayer1Victories(),
                gameStatsRepository.getPlayer2Victories(),
                gameStatsRepository.getDraws()
        );
    }

    // clears session rounds data
    // @Loggable(LogLevel.DEBUG)
    public void clearSessionRounds(String sessionId) {
        gameRepository.clearGameSession(sessionId);
    }
}
