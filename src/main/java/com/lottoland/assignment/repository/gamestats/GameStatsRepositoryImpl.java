package com.lottoland.assignment.repository.gamestats;

import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class GameStatsRepositoryImpl implements GameStatsRepository {

    private final AtomicInteger totalGames;
    private final AtomicInteger totalP1Victories;
    private final AtomicInteger totalP2Victories;
    private final AtomicInteger totalDraws;

    public GameStatsRepositoryImpl() {
        totalGames = new AtomicInteger(0);
        totalP1Victories = new AtomicInteger(0);
        totalP2Victories = new AtomicInteger(0);
        totalDraws = new AtomicInteger(0);
    }

    @Override
    public void addGameToTotal() {
        totalGames.incrementAndGet();
    }

    @Override
    public void addPlayer1Victory() {
        totalP1Victories.incrementAndGet();
    }

    @Override
    public void addPlayer2Victory() {
        totalP2Victories.incrementAndGet();
    }

    @Override
    public void addDraw() {
        totalDraws.incrementAndGet();
    }

    @Override
    public Integer getTotalGames() {
        return totalGames.get();
    }

    @Override
    public Integer getPlayer1Victories() {
        return totalP1Victories.get();
    }

    @Override
    public Integer getPlayer2Victories() {
        return totalP2Victories.get();
    }

    @Override
    public Integer getDraws() {
        return totalDraws.get();
    }
}
