package com.games.service.impl;

import com.games.dao.GameDao;
import com.games.dto.GameQueryParams;
import com.games.dto.GameRequest;
import com.games.model.Game;
import com.games.model.ViewGame;
import com.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDao gameDao;

//    @Override
//    public List<Game> getGames(GameQueryParams gameQueryParams) {
//        return gameDao.getGames(gameQueryParams);
//    }

    @Override
    public Integer countGames(GameQueryParams gameQueryParams) {
        return gameDao.countGames(gameQueryParams);
    }

    @Override
    public List<ViewGame> getViewGames(GameQueryParams gameQueryParams) {
        return gameDao.getViewGames(gameQueryParams);
    }

    @Override
    public ViewGame getViewGameById(Integer gameId) {
        return gameDao.getViewGameById(gameId);
    }

    @Override
    public Game getGameById(Integer gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public Integer createGame(GameRequest gameRequest) {
        return gameDao.createGame(gameRequest);
    }

    @Override
    public void updateGame(Integer gameId, GameRequest gameRequest) {
        gameDao.updateGame(gameId, gameRequest);
    }

    @Override
    public void deleteGameById(Integer gameId) {
        gameDao.deleteGameById(gameId);
    }
}