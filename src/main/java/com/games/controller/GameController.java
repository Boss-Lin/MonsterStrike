package com.games.controller;

import com.games.dto.GameRequest;
import com.games.model.Game;
import com.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable Integer gameId) {
        Game game = gameService.getGameById(gameId);

        if (game != null) {
            return ResponseEntity.status(HttpStatus.OK).body(game);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/addgames")
    public ResponseEntity<Game> createGame(@RequestBody @Valid GameRequest gameRequest) {
        Integer gameId = gameService.createGame(gameRequest);

        Game game = gameService.getGameById(gameId);

        return  ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @PutMapping("/updategames/{gameId}")
    public ResponseEntity<Game> updateGame(@PathVariable Integer gameId, @RequestBody @Valid GameRequest gameRequest) {

        //檢查game是否存在
        Game game = gameService.getGameById(gameId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ///修改Game的數據
        gameService.updateGame(gameId, gameRequest);

        Game updateGame = gameService.getGameById(gameId);//更新後查看更新後的資料

        return ResponseEntity.status(HttpStatus.OK).body(updateGame);
    }
}
