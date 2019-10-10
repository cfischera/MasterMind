package com.tsg.fischer.mastermind.data;

import com.tsg.fischer.mastermind.model.Game;
import com.tsg.fischer.mastermind.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDAOImpl implements GameDAO {
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private RoundDAO rounds;

    @Override
    public Game addGame(Game game) {
        String sql = "INSERT INTO Game (Answer,IsFinished) VALUES (?,?)";
        jdbc.update(sql, game.getAnswer(), game.isFinished());
        game.setGameID(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return game;
    }

    @Override
    public List<Round> addRound(int gameID, Round round) {
        rounds.addRound(gameID, round);
        return getGameByID(gameID).getRounds();
    }

    @Override
    public Game getGameByID(int id) {
        return jdbc.queryForObject("SELECT * FROM Game WHERE GameID = ?", new GameMapper(), id);
    }

    @Override
    public List<Game> getAllGames() {
        return jdbc.query("SELECT * FROM Game", new GameMapper());
    }

    @Override
    public Game updateGameStatusToFinished(Game game) {
        Game previous = this.getGameByID(game.getGameID());
        jdbc.update("UPDATE Game SET IsFinished=? WHERE GameID=?",
                            true, game.getGameID());
        return previous;
    }

    @Override
    public Game deleteGameByID(int id) {
        Game popped = this.getGameByID(id);
        jdbc.update("DELETE FROM Game WHERE GameID = ?", id);
        return popped;
    }

    private class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet resultSet, int i) throws SQLException {
            Game game = new Game();
            game.setGameID(resultSet.getInt("GameID"));
            game.setAnswer(resultSet.getString("Answer"));
            game.setFinished(resultSet.getBoolean("IsFinished"));
            game.setRounds(rounds.getAllFromGameById(game.getGameID()));
            return game;
        }
    }
}
