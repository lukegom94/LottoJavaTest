package com.lottoland.assignment.utils.mappers;

import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.dto.GameRoundResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RPSMapper {

    // Converts List<GameRound> to List<GameRoundResponseDTO>
    public static List<GameRoundResponseDTO> gameRoundToGameRoundResponseDTO(List<GameRound> gameRounds) {
        return gameRounds
                .stream()
                .map(gameRound -> new GameRoundResponseDTO(
                        parseRPSValues(gameRound.playerOneOption()),
                        parseRPSValues(gameRound.playerTwoOption()),
                        gameRound.gameResult()
                ))
                .collect(Collectors.toList());
    }

    // Converts values from RPSGameSimulator to values readable by users
    private static String parseRPSValues(int value) {
        return switch (value) {
            case 0 -> "ROCK";
            case 1 -> "PAPER";
            case 2 -> "SCISSORS";
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

}
