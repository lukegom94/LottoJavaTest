package com.lottoland.assignment.utils.dto;

import com.lottoland.assignment.utils.enums.GameResult;

public record GameRoundResponseDTO(String playerOneOption, String playerTwoOption, GameResult gameResult) {
}
