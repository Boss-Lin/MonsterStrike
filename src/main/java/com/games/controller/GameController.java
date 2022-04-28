package com.games.controller;

import com.games.constant.GameCategory;
import com.games.dto.GameQueryParams;
import com.games.dto.GameRequest;
import com.games.model.Game;
import com.games.model.ViewGame;
import com.games.service.GameService;
import com.games.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<Page<ViewGame>> getGames(
            //查詢條件 Filtering
            @RequestParam(required = false) GameCategory gameLavel,
            @RequestParam(required = false) String search,

            //排序 Sorting
            @RequestParam(defaultValue = "create_time") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 Pagination
            //取得幾筆資料
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            //跳過多少筆資料
            @RequestParam(defaultValue = "0") @Min(0) Integer offset

    ) {
        GameQueryParams gameQueryParams = new GameQueryParams();
        gameQueryParams.setGameLavel(gameLavel);
        gameQueryParams.setSearch(search);
        gameQueryParams.setOrderBy(orderBy);
        gameQueryParams.setSort(sort);
        gameQueryParams.setLimit(limit);
        gameQueryParams.setOffset(offset);

        //取得 Game List;
        List<ViewGame> viewGameList = gameService.getViewGames(gameQueryParams);

        //取得 Game 總數;
        Integer total = gameService.countGames(gameQueryParams);

        //分頁
        Page<ViewGame> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(viewGameList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<ViewGame> getGame(@PathVariable Integer gameId) {
        ViewGame viewGame = gameService.getViewGameById(gameId);

        if (viewGame != null) {
            return ResponseEntity.status(HttpStatus.OK).body(viewGame);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @GetMapping("/games/{gameId}")
//    public ResponseEntity<Game> getGame(@PathVariable Integer gameId) {
//        Game game = gameService.getGameById(gameId);
//
//        if (game != null) {
//            return ResponseEntity.status(HttpStatus.OK).body(game);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @PostMapping("/addgames")
    public ResponseEntity<Game> createGame(@RequestBody @Valid GameRequest gameRequest) {
        Integer gameId = gameService.createGame(gameRequest);

        Game game = gameService.getGameById(gameId);

        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @PutMapping("/updategames/{gameId}")
    public ResponseEntity<Game> updateGame(@PathVariable Integer gameId, @RequestBody @Valid GameRequest gameRequest) {

        //檢查game是否存在
        Game game = gameService.getGameById(gameId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //修改Game的數據
        gameService.updateGame(gameId, gameRequest);

        Game updateGame = gameService.getGameById(gameId);//更新後查看更新後的資料

        return ResponseEntity.status(HttpStatus.OK).body(updateGame);
    }

    @DeleteMapping("/deletegames/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable Integer gameId) {
        gameService.deleteGameById(gameId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
