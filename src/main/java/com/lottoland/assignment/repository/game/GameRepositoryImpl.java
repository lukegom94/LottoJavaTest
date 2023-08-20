package com.lottoland.assignment.repository.game;

import com.lottoland.assignment.utils.dto.GameRound;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private Map<String, ArrayList<GameRound>> userGameSessions;

    public GameRepositoryImpl() {
        this.userGameSessions = new HashMap<>();
    }

    @Override
    public boolean addGameToSession(String sessionId, GameRound game) {
        if (!userGameSessions.containsKey(sessionId)) {
            this.createGameSession(sessionId);
        }
        userGameSessions.get(sessionId).add(game);
        return true;
    }

    @Override
    public ArrayList<GameRound> getGamesFromSession(String sessionId) {
        if (!userGameSessions.containsKey(sessionId)) {
            this.createGameSession(sessionId);
        }
        return userGameSessions.get(sessionId);
    }

    @Override
    public void clearGameSession(String sessionId) {
        userGameSessions.remove(sessionId);
    }

    private void createGameSession(String sessionId) {
        userGameSessions.put(sessionId, new ArrayList<GameRound>());
    }

}
