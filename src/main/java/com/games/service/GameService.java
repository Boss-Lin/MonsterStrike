package com.games.service;

import com.games.dto.GameRequest;
import com.games.model.Game;

public interface GameService {
    Game getGameById(Integer ID);

    Integer createGame(GameRequest gameRequest);

    void updateGame(Integer gameId, GameRequest gameRequest);

    void deleteGameById(Integer gameId);
}
