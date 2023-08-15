package com.lottoland.assignment.service.simulator;

import com.lottoland.assignment.utils.dto.GameRound;
import com.lottoland.assignment.utils.enums.GameResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RPSGameSimulator {

    @Value("${strategies.player1}")
    String player1Strategy;

    @Value("${strategies.player2}")
    String player2Strategy;
    
    public GameRound playRPSRound() {

        // Gets players' options
        var p1Option = getPlayerOption(player1Strategy);
        var p2Option = getPlayerOption(player2Strategy);

        // Calculates and returns result
        var gameResult = getWinner(p1Option, p2Option);
        return new GameRound(p1Option, p2Option, gameResult);
    }

    private GameResult getWinner(Integer p1Option, Integer p2Option) {
        if (p1Option == p2Option) {
            return GameResult.DRAW;
        }
        if ((p1Option - p2Option) > 1) {
            return p1Option < p2Option ? GameResult.PLAYER_1 : GameResult.PLAYER_2;
        }
        return p1Option  > p2Option ? GameResult.PLAYER_1 : GameResult.PLAYER_2;
    }

    private Integer getPlayerOption(String playerStrategy) {
        return switch (playerStrategy) {
            case ("RANDOM") -> this.simulateRandom();
            case ("ONLY_ROCK") -> this.simulateOnlyRock();
            case ("ONLY_PAPER") -> this.simulateOnlyPaper();
            case ("ONLY_SCISSORS") -> this.simulateOnlyScissors();
            default -> throw new IllegalArgumentException();
        };
    }

    private Integer simulateRandom() {
        Random random = new Random();
        return random.nextInt(3);
    }

    private Integer simulateOnlyRock() {
        return 0;
    }

    private Integer simulateOnlyPaper() {
        return 1;
    }

    private Integer simulateOnlyScissors() {
        return 2;
    }
}
