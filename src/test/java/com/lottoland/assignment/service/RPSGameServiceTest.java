package com.lottoland.assignment.service;

import com.lottoland.assignment.repository.game.GameRepository;
import com.lottoland.assignment.repository.gamestats.GameStatsRepository;
import com.lottoland.assignment.service.simulator.RPSGameSimulator;
import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.dto.GameRoundResponseDTO;
import com.lottoland.assignment.utils.enums.GameResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RPSGameServiceTest {

    RPSGameSimulator gameSimulator;
    GameRepository gameRepository;
    GameStatsRepository gameStatsRepository;
    RPSGameService rpsGameService;

    String sessionId1;
    GameRound roundDraw, roundP1, roundP2;

    @BeforeEach
    void setup() {

        gameSimulator = mock(RPSGameSimulator.class);
        gameRepository = mock(GameRepository.class);
        gameStatsRepository = mock(GameStatsRepository.class);

        rpsGameService = new RPSGameService(gameSimulator, gameRepository, gameStatsRepository);

        sessionId1 = "SESSION_ID_1";
        roundDraw = new GameRound(0, 0, GameResult.DRAW);
        roundP1 = new GameRound(0, 2, GameResult.PLAYER_1);
        roundP2 = new GameRound(0, 1, GameResult.PLAYER_2);

    }

    // Test KO due to wrong parameters
    @Test
    @DisplayName("Test KO playRound() - Wrong Parameters")
    void testPlayRoundKOWrongParameters() {

        // Null parameter
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rpsGameService.playRound(null);
        });

        // Blank parameter
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rpsGameService.playRound("");
        });

    }

    // Test KO due to RPSGameSimulator failing to simulate game
    @Test
    @DisplayName("Test KO playRound() - Failed game simulation")
    void testPlayRoundKOSimulationFails() {
        when(gameSimulator.playRPSRound()).thenReturn(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            rpsGameService.playRound(sessionId1);
        });
    }

    // Test KO due to exception in GameRepository failing to save game
    @Test
    @DisplayName("Test KO playRound() - Unable to save in GameRepository")
    @ExtendWith(OutputCaptureExtension.class)
    void testPlayRoundKOGameRepositoryFails(CapturedOutput output) {
        when(gameSimulator.playRPSRound()).thenReturn(new GameRound(1, 1, GameResult.DRAW));

        // GameRepository throws RuntimeException
        doThrow(RuntimeException.class).when(gameRepository).addGameToSession(anyString(), any());

        rpsGameService.playRound(sessionId1);

        String errorLog = output.getOut();
        Assertions.assertTrue(errorLog.contains("Error saving game in repositories"));

    }

    // Test KO due to exception in GameStatsRepository failing to save game
    @Test
    @DisplayName("Test KO playRound() - Unable to save in GameStatsRepository")
    @ExtendWith(OutputCaptureExtension.class)
    void testPlayRoundKOGameStatsRepositoryFails(CapturedOutput output) {
        when(gameSimulator.playRPSRound()).thenReturn(new GameRound(1, 1, GameResult.DRAW));

        // GameStatsRepository throws RuntimeException
        doThrow(RuntimeException.class).when(gameStatsRepository).addGameToTotal();

        rpsGameService.playRound(sessionId1);

        String errorLog = output.getOut();
        Assertions.assertTrue(errorLog.contains("Error saving game in repositories"));

    }

    // Test OK playround()
    @Test
    void testPlayRoundOK() {

        String sessionId3 = "SESSION_ID_3";

        when(gameSimulator.playRPSRound()).thenReturn(new GameRound(1, 1, GameResult.DRAW));

        // Execute method playRound() 5 times.
        for (int i = 0; i < 5; i++) {
            rpsGameService.playRound(sessionId3);
        }
    }

    @Test
    void testGetRounds() {

        when(gameSimulator.playRPSRound()).thenReturn(new GameRound(1, 1, GameResult.DRAW));

        for (int i = 0; i < 5; i++) {
            rpsGameService.playRound(sessionId1);
        }
        Integer sessionRoundCount = rpsGameService.getSessionRoundCount(sessionId1);
        List<GameRoundResponseDTO> sessionRounds = rpsGameService.getSessionRounds(sessionId1);
        System.out.println(sessionRoundCount);
        System.out.println(sessionRounds);

    }


}
