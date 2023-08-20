package com.lottoland.assignment.service.simulator;

import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.enums.GameResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RPSGameSimulator {

    // Gets strategy of both players from .properties file
    @Value("${strategies.player1}")
    String player1Strategy;

    @Value("${strategies.player2}")
    String player2Strategy;


    // Simulates a Rock, Paper, Scissors game round
    public GameRound playRPSRound() {

        // Gets players' options
        var p1Option = getPlayerOption(player1Strategy);
        var p2Option = getPlayerOption(player2Strategy);

        // Calculates and returns result
        var gameResult = getWinner(p1Option, p2Option);
        return new GameRound(p1Option, p2Option, gameResult);
    }

    // Converts the value retrieved from .properties file to a player strategy.
    private Integer getPlayerOption(String playerStrategy) {
        return switch (playerStrategy) {
            case ("RANDOM") -> this.simulateRandom();
            case ("ONLY_ROCK") -> this.simulateOnlyRock();
            case ("ONLY_PAPER") -> this.simulateOnlyPaper();
            case ("ONLY_SCISSORS") -> this.simulateOnlyScissors();
            default -> throw new IllegalArgumentException();
        };
    }

    // Calculates the winner.
    private GameResult getWinner(Integer p1Option, Integer p2Option) {
        if (p1Option == p2Option) {
            return GameResult.DRAW;
        }
        if ((p1Option - p2Option) > 1) {
            return p1Option < p2Option ? GameResult.PLAYER_1 : GameResult.PLAYER_2;
        }
        return p1Option  > p2Option ? GameResult.PLAYER_1 : GameResult.PLAYER_2;
    }

    // Random strategy simulator
    private Integer simulateRandom() {
        Random random = new Random();
        return random.nextInt(3);
    }

    // Only rock strategy simulator
    private Integer simulateOnlyRock() {
        return 0;
    }

    // Only paper strategy simulator
    private Integer simulateOnlyPaper() {
        return 1;
    }

    // Only scissors strategy simulator
    private Integer simulateOnlyScissors() {
        return 2;
    }
}
