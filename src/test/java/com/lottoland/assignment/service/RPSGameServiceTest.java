package com.lottoland.assignment.service;

import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.enums.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RPSGameServiceTest {

    @Autowired
    RPSGameService rpsGameService;

    String sessionId1;
    GameRound roundDraw, roundP1, roundP2;

    @BeforeEach
    void initVariables() {

        sessionId1 = "SESSION_ID_1";
        roundDraw = new GameRound(0, 0, GameResult.DRAW);
        roundP1 = new GameRound(0, 2, GameResult.PLAYER_1);
        roundP2 = new GameRound(0, 1, GameResult.PLAYER_2);

    }

    @Test
    void testPlayRound() {
        rpsGameService.playRound(sessionId1);
    }

    @Test
    void testGetRounds() {

        for (int i = 0; i < 5; i++) {
            rpsGameService.playRound(sessionId1);
        }
        Integer sessionRoundCount = rpsGameService.getSessionRoundCount(sessionId1);
        List<GameRound> sessionRounds = rpsGameService.getSessionRounds(sessionId1);
        System.out.println(sessionRoundCount);
        System.out.println(sessionRounds);

    }


}
