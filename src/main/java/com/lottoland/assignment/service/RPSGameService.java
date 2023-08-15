package com.lottoland.assignment.service;

import com.lottoland.assignment.repository.game.GameRepository;
import com.lottoland.assignment.repository.gamestats.GameStatsRepository;
import com.lottoland.assignment.service.simulator.RPSGameSimulator;
import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.dto.GameStats;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RPSGameService {

    private final RPSGameSimulator rpsGameSimulator;
    private final GameRepository gameRepository;
    private final GameStatsRepository gameStatsRepository;

    public RPSGameService(RPSGameSimulator rpsGameSimulator, GameRepository gameRepository, GameStatsRepository gameStatsRepository) {
        this.rpsGameSimulator = rpsGameSimulator;
        this.gameRepository = gameRepository;
        this.gameStatsRepository = gameStatsRepository;
    }

    // get number of rounds in session
    // @Loggable(LogLevel.INFO)
    public Integer getSessionRoundCount(String sessionId) {
        var gamesFromSession = this.gameRepository.getGamesFromSession(sessionId);
        if (Objects.isNull(gamesFromSession) || gamesFromSession.isEmpty()) {
            return 0;
        }
        return this.gameRepository.getGamesFromSession(sessionId).size();
    }

    // get all rounds played in session
    // @Loggable(LogLevel.INFO)
    public List<GameRound> getSessionRounds(String sessionId) {
        return this.gameRepository.getGamesFromSession(sessionId);
    }

    // begin TRANSACTION here to ensure both repositories are coordinated
    // @Loggable(LogLevel.INFO)
    public void playRound(String sessionId) {
        var gameRound = rpsGameSimulator.playRPSRound();
        gameRepository.addGameToSession(sessionId, gameRound);
        this.addGameStats(gameRound);
    }

    // adds game stats to stats repository
    // @Loggable(LogLevel.DEBUG)
    private void addGameStats(GameRound gameRound) {
        gameStatsRepository.addGameToTotal();
        switch (gameRound.gameResult()) {
            case DRAW -> gameStatsRepository.addDraw();
            case PLAYER_1 -> gameStatsRepository.addPlayer1Victory();
            case PLAYER_2 -> gameStatsRepository.addPlayer2Victory();
        }
    }

    // gets all stats and returns them in a record
    // @Loggable(LogLevel.INFO)
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
