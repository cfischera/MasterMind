package com.tsg.fischer.mastermind.model;

public class Guess {
    private int gameID;
    private String userGuess;

    public Guess(int gameID, String userGuess) {
        this.gameID = gameID;
        this.userGuess = userGuess;
    }

    public int getGameID() {
        return this.gameID;
    }

    public String getUserGuess() {
        return this.userGuess;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setUserGuess(String userGuess) {
        this.userGuess = userGuess;
    }
}
