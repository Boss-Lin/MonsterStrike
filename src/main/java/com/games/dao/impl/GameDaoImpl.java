package com.games.dao.impl;

import com.games.dao.GameDao;
import com.games.dto.GameQueryParams;
import com.games.dto.GameRequest;
import com.games.model.Game;
import com.games.model.ViewGame;
import com.games.rowmapper.GameRowmapper;
import com.games.rowmapper.ViewGameRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameDaoImpl implements GameDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countGames(GameQueryParams gameQueryParams) {
        String sql = "SELECT count(*) FROM view_game WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
//        if (gameQueryParams.getGameLavel() != null) {
//            sql = sql + " AND game_lavel = :gameLavel";
//            map.put("gameLavel", gameQueryParams.getGameLavel().name());
//        }
//
//        if (gameQueryParams.getSearch() != null) {
//            sql = sql + " AND game_name LIKE :search";
//            map.put("search", "%" + gameQueryParams.getSearch() + "%");
//        }

        sql = addFilteringSql(sql, map, gameQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<ViewGame> getViewGames(GameQueryParams gameQueryParams) {
        String sql = "SELECT game_id, game_name, game_lavel, create_name, create_time, update_name, update_time FROM monsterstrike.view_game WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
//        if (gameQueryParams.getGameLavel() != null) {
//            sql = sql + " AND game_lavel = :gameLavel";
//            map.put("gameLavel", gameQueryParams.getGameLavel().name());
//        }
//
//        if (gameQueryParams.getSearch() != null) {
//            sql = sql + " AND game_name LIKE :search";
//            map.put("search", "%" + gameQueryParams.getSearch() + "%");
//        }

        sql = addFilteringSql(sql, map, gameQueryParams);

        //排序
        sql = sql + " ORDER BY " + gameQueryParams.getOrderBy() + " " + gameQueryParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", gameQueryParams.getLimit());
        map.put("offset", gameQueryParams.getOffset());

        List<ViewGame> viewGameList = namedParameterJdbcTemplate.query(sql, map, new ViewGameRowmapper());

        return viewGameList;
    }

    @Override
    public ViewGame getViewGameById(Integer gameId) {
        String sql = "SELECT game_id, game_name, game_lavel, create_name, create_time, update_name, update_time FROM monsterstrike.view_game WHERE game_id = :gameId";

        Map<String, Object> map = new HashMap<>();
        map.put("gameId", gameId);

        List<ViewGame> viewGameList = namedParameterJdbcTemplate.query(sql, map, new ViewGameRowmapper());

        if (viewGameList.size() > 0) {
            return viewGameList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Game getGameById(Integer gameId) {
        String sql = "SELECT game_id, game_name, game_lavel, create_by, create_time, update_by, update_time FROM game WHERE game_id = :gameId";

//        String sql = "SELECT a.game_id, a.game_name, a.game_lavel, b.M_name, a.create_time, c.M_name, a.update_time FROM game a" +
//                " INNER JOIN manager b ON a.create_by = b.M_id" +
//                " INNER JOIN manager c ON a.update_by = c.M_id" +
//                " WHERE game_id = :gameId";

        Map<String, Object> map = new HashMap<>();
        map.put("gameId", gameId);

        List<Game> gameList = namedParameterJdbcTemplate.query(sql, map, new GameRowmapper());

        if (gameList.size() > 0) {
            return gameList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createGame(GameRequest gameRequest) {
        String sql = "INSERT INTO monsterstrike.game (game_name, game_lavel, create_by, update_by) VALUES (:gameName, :gameLavel, :createBY, :updateBY)";

        Map<String, Object> map = new HashMap<>();
        map.put("gameName", gameRequest.getGameName());
        map.put("gameLavel", gameRequest.getGameLavel().toString());
        map.put("createBY", gameRequest.getCreateBY());
        map.put("updateBY", gameRequest.getUpdateBY());

        //記錄當下時間
//        Date now = new Date();
//        map.put("createTime", now);
//        map.put("updateTime", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int gameId = keyHolder.getKey().intValue();

        return gameId;
    }

    @Override
    public void updateGame(Integer gameId, GameRequest gameRequest) {
        String sql = "UPDATE monsterstrike.game SET game_name = :gameName, game_lavel = :gameLavel, update_by = :updateBY WHERE game_id = :gameId";

        Map<String, Object> map = new HashMap<>();
        map.put("gameId", gameId);

        map.put("gameName", gameRequest.getGameName());
        map.put("gameLavel", gameRequest.getGameLavel().toString());
        map.put("updateBY", gameRequest.getUpdateBY());

//        map.put("updateTime", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteGameById(Integer gameId) {
        String sql = "DELETE FROM game WHERE game_id = :gameId";

        Map<String, Object> map = new HashMap<>();
        map.put("gameId", gameId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, GameQueryParams gameQueryParams) {

        //查詢條件
        if (gameQueryParams.getGameLavel() != null) {
            sql = sql + " AND game_lavel = :gameLavel";
            map.put("gameLavel", gameQueryParams.getGameLavel().name());
        }

        if (gameQueryParams.getSearch() != null) {
            sql = sql + " AND game_name LIKE :search";
            map.put("search", "%" + gameQueryParams.getSearch() + "%");
        }

        return sql;
    }
}

