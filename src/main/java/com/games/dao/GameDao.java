package com.games.dao;

import com.games.constant.GameCategory;
import com.games.dto.GameQueryParams;
import com.games.dto.GameRequest;
import com.games.model.Game;
import com.games.model.ViewGame;

import java.util.List;

public interface GameDao {

//    List<Game> getGames(GameQueryParams gameQueryParams);

    Integer countGames(GameQueryParams gameQueryParams);

    List<ViewGame> getViewGames(GameQueryParams gameQueryParams);

    ViewGame getViewGameById(Integer gameId);

    Game getGameById(Integer gameId);

    Integer createGame(GameRequest gameRequest);

    void updateGame(Integer gameId, GameRequest gameRequest);

    void deleteGameById(Integer gameId);
}