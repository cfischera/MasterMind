package com.tsg.fischer.mastermind.service;

import com.tsg.fischer.mastermind.data.GameDAO;
import com.tsg.fischer.mastermind.data.RoundDAO;
import com.tsg.fischer.mastermind.model.Game;
import com.tsg.fischer.mastermind.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterMindService {
    @Autowired
    private GameDAO games;

    @Autowired
    private RoundDAO rounds;

    public Game startGame() {
        Game freshGame = new Game(this.generateAnswer());
        return this.sanitizeGame(games.addGame(freshGame));
    }

    private String generateAnswer() {
        String  answer = "";
        int[] answerIntArray = new int[4];
        for(int i=0;i<4;i++) {
            do {
                answerIntArray[i] = (int)(Math.random()*(10));
            } while(!isUnique(answerIntArray, i));
            answer += answerIntArray[i];
        }
        return answer;
    }

    private boolean isUnique(int[] arr, int target) {
        for(int i=target-1;i>=0;i--) {
            if(arr[target] == arr[i]) {
                return false;
            }
        }
        return true;
    }

    public Round makeGuess(int gameID, String guess) {
        Round freshRound = new Round(guess);
        freshRound.setResult(this.calculateResult(gameID, guess));
        return rounds.addRound(gameID, freshRound);
    }

    private String calculateResult(int gameID, String guess) {
        int exact = 0, partial = 0;
        String answer = games.getGameByID(gameID).getAnswer();
        for(int i=0;i<answer.length();i++) {
            for(int j=0;j<guess.length();j++) {
                if(answer.charAt(i) == (guess.charAt(j))) {
                    if(i == j) {
                        exact++;
                    } else {
                        partial++;
                    }
                }
            }
        }
        if(exact == 4) {
            games.updateGameStatusToFinished(getGameByID(gameID));
        }
        return "e:"+exact+":p:"+partial;
    }

    public List<Game> getAllGames() {
        return this.sanitizeGames(games.getAllGames());
    }

    public Game getGameByID(int id) {
        return this.sanitizeGame(games.getGameByID(id));
    }

    public List<Round> getRoundsByGameID(int gameID) {
        return rounds.getAllFromGameById(gameID);
    }

    private Game sanitizeGame(Game game) {
        if(!game.isFinished()) {
            game.setAnswer("----");
        }
        return game;
    }

    private List<Game> sanitizeGames(List<Game> games) {
        for(Game game : games) {
            game = sanitizeGame(game);
        }
        return games;
    }
}
