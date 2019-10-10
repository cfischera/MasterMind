package com.tsg.fischer.mastermind.data;

import com.tsg.fischer.mastermind.model.Game;
import com.tsg.fischer.mastermind.model.Round;

import java.util.List;

public interface GameDAO {
    Game addGame(Game game);
    List<Round> addRound(int gameID, Round round);
    Game getGameByID(int id);
    List<Game> getAllGames();
    Game updateGameStatusToFinished(Game game);
    Game deleteGameByID(int id);
}
