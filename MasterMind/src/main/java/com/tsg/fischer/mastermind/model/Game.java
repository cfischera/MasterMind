package com.tsg.fischer.mastermind.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int gameID;
    private String answer;
    private boolean isFinished;
    private List<Round> rounds;

    public Game() {
        this.gameID = 0;
        this.answer = null;
        this.isFinished = false;
        this.rounds = new ArrayList<>();
    }

    public Game(String answer) {
        this();
        this.setAnswer(answer);
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<Round> getRounds() {
        if(rounds == null) {
            return new ArrayList<>();
        } else {
            return rounds;
        }
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameID == game.gameID &&
                isFinished == game.isFinished &&
                answer.equals(game.answer) &&
                Objects.equals(rounds, game.rounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, answer, isFinished, rounds);
    }
}
