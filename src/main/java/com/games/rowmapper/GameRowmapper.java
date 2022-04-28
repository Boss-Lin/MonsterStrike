package com.games.rowmapper;

import com.games.constant.GameCategory;
import com.games.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRowmapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet resultSet, int i) throws SQLException {
        Game game = new Game();

        game.setGameId(resultSet.getInt("game_id"));
        game.setGameName(resultSet.getString("game_name"));

        String categoryStr = resultSet.getString("game_lavel");
        GameCategory category = GameCategory.valueOf(categoryStr);
        game.setGameLavel(category);

        //        game.setGameLavel(GameCategory.valueOf(resultSet.getString("game_lavel")));

        game.setCreateBY(resultSet.getInt("create_by"));
        game.setCreateTime(resultSet.getTimestamp("create_time"));
        game.setUpdateBY(resultSet.getInt("update_by"));
        game.setUpdateTime(resultSet.getTimestamp("update_time"));

//        game.setCreateName(resultSet.getString("create_name"));
//        game.setUpdateName(resultSet.getString("update_name"));

        return game;
    }
}