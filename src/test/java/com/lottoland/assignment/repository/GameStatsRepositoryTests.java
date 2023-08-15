package com.lottoland.assignment.repository;

import com.lottoland.assignment.repository.gamestats.GameStatsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameStatsRepositoryTests {

    @Autowired
    GameStatsRepository gameStatsRepository;

    @Test
    void testAddTotal() {
        for (int i = 0; i < 5; i++) {
            gameStatsRepository.addGameToTotal();
        }
        Assertions.assertEquals(5, gameStatsRepository.getTotalGames());
    }
}
