package com.lottoland.assignment.utils.enums;

public enum GameResult {

    PLAYER_1("Player 1 Wins"),
    PLAYER_2("Player 2 Wins"),
    DRAW("Draw");

    private final String value;

    GameResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}