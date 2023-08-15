package com.lottoland.assignment.utils.dto;

import com.lottoland.assignment.utils.enums.GameResult;

import java.util.Objects;

public record GameRound (int playerOneOption, int playerTwoOption, GameResult gameResult) {

    // Data validation.
    public GameRound {
        if (playerOneOption < 0 || playerTwoOption < 0 || Objects.isNull(gameResult)) {
            throw new IllegalArgumentException("Not valid data");
        }
    }
}