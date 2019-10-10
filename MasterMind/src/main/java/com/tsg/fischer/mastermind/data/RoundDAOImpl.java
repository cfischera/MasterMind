package com.tsg.fischer.mastermind.data;

import com.tsg.fischer.mastermind.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class RoundDAOImpl implements RoundDAO {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Round addRound(int gameID, Round round) {
        String sql = "INSERT INTO Round (UserGuess,GuessTime,Result,GameID) VALUES (?,?,?,?)";
        jdbc.update(sql, round.getUserGuess(), Timestamp.valueOf(round.getGuessTime()),
                    round.getResult(), gameID);
        round.setRoundID(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return round;
    }

    @Override
    public Round getRoundByID(int id) {
        return jdbc.queryForObject("SELECT * FROM Round WHERE RoundID = ?", new RoundMapper(), id);
    }

    @Override
    public List<Round> getAllFromGameById(int gameID) {
        String sql = "SELECT * FROM Round WHERE GameID = ?";
        return jdbc.query(sql, new RoundMapper(), gameID);
    }

    @Override
    public Round deleteRoundByID(int id) {
        Round popped = this.getRoundByID(id);
        jdbc.update("DELETE FROM Round WHERE RoundID = ?", id);
        return popped;
    }

    private static class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet resultSet, int i) throws SQLException {
            Round round = new Round();
            round.setRoundID(resultSet.getInt("RoundID"));
            round.setUserGuess(resultSet.getString("UserGuess"));
            round.setGuessTime(resultSet.getTimestamp("GuessTime").toLocalDateTime());
            round.setResult(resultSet.getString("Result"));
            return round;
        }
    }
}
