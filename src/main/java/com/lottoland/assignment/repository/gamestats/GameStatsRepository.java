package com.lottoland.assignment.repository.gamestats;


// General interface for all games.
public interface GameStatsRepository {

    void addGameToTotal();

    void addPlayer1Victory();

    void addPlayer2Victory();

    void addDraw();

    Integer getTotalGames();

    Integer getPlayer1Victories();

    Integer getPlayer2Victories();

    Integer getDraws();
}
