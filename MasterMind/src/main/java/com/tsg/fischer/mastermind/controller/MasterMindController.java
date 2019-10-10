package com.tsg.fischer.mastermind.controller;

import com.tsg.fischer.mastermind.model.Game;
import com.tsg.fischer.mastermind.model.Guess;
import com.tsg.fischer.mastermind.model.Round;
import com.tsg.fischer.mastermind.service.MasterMindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MasterMindController {
    @Autowired
    private MasterMindService service;

    @PostMapping("/begin")
    public ResponseEntity<Integer> startGame() {
        Game freshGame = service.startGame();
        if(freshGame.getRounds() == null || freshGame.getGameID() == 0) {
            return new ResponseEntity<>(0, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(freshGame.getGameID(), HttpStatus.CREATED);
    }

    @PostMapping("/guess")
    public Round guess(@RequestBody Guess guess) {
        return service.makeGuess(guess.getGameID(), guess.getUserGuess());
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("/game/{gameID}")
    public Game getGameByID(@PathVariable int gameID) {
        return service.getGameByID(gameID);
    }

    @GetMapping("rounds/{gameID}")
    public List<Round> getRoundsByGameID(@PathVariable int gameID) {
        return service.getRoundsByGameID(gameID);
    }
}
