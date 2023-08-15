package com.lottoland.assignment.repository;

import com.lottoland.assignment.repository.game.GameRepository;
import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.enums.GameResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    String sessionId1;
    GameRound roundDraw;
    GameRound roundP1;
    GameRound roundP2;

    @BeforeEach
    void initVariables() {

        sessionId1 = "SESSION_ID_1";
        roundDraw = new GameRound(0, 0, GameResult.DRAW);
        roundP1 = new GameRound(0, 2, GameResult.PLAYER_1);
        roundP2 = new GameRound(0, 1, GameResult.PLAYER_2);

    }

    @Test
    void testAddGameToSession() {

        gameRepository.addGameToSession(sessionId1, roundDraw);
        gameRepository.addGameToSession(sessionId1, roundP1);
        gameRepository.addGameToSession(sessionId1, roundP2);

        var userGames = gameRepository.getGamesFromSession(sessionId1);
        Assertions.assertEquals(3, userGames.size());

        var p1Victories =
                userGames
                        .stream()
                        .filter(game -> game.gameResult().equals(GameResult.PLAYER_1))
                        .collect(Collectors.toList())
                        .size();
        Assertions.assertEquals(1, p1Victories);

        var p2Victories =
                userGames
                        .stream()
                        .filter(game -> game.gameResult().equals(GameResult.PLAYER_2))
                        .collect(Collectors.toList())
                        .size();;
        Assertions.assertEquals(1, p2Victories);

        var draws =
                userGames
                        .stream()
                        .filter(game -> game.gameResult().equals(GameResult.DRAW))
                        .collect(Collectors.toList())
                        .size();
        Assertions.assertEquals(1, draws);

    }


}
