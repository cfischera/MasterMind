package com.tsg.fischer.mastermind.data;

import com.tsg.fischer.mastermind.model.Round;

import java.util.List;

public interface RoundDAO {
    Round addRound(int gameID, Round round);
    Round getRoundByID(int id);
    List<Round> getAllFromGameById(int id);
    Round deleteRoundByID(int id);
}
