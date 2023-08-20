package com.lottoland.assignment.repository.game;

import com.lottoland.assignment.utils.dto.GameRound;

import java.util.ArrayList;

public interface GameRepository {

    boolean addGameToSession(String sessionId, GameRound game);

    ArrayList<GameRound> getGamesFromSession(String sessionId);

    void clearGameSession(String sessionId);

}
