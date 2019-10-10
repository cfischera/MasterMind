package com.tsg.fischer.mastermind.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Round {
    private int roundID;
    private LocalDateTime guessTime;
    private String userGuess, result;

    public Round() {
        this.roundID = 0;
        this.guessTime = null;
        this.userGuess = null;
        this.result = null;
    }

    public Round(String userGuess) {
        this();
        this.setUserGuess(userGuess);
        this.setGuessTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.setResult("");
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public String getUserGuess() {
        return userGuess;
    }

    public void setUserGuess(String userGuess) {
        this.userGuess = userGuess;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return roundID == round.roundID &&
                userGuess.equals(round.userGuess) &&
                guessTime.equals(round.guessTime) &&
                result.equals(round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundID, userGuess, guessTime, result);
    }
}
