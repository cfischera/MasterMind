package test.tsg.fischer.mastermind.dao;

import com.tsg.fischer.mastermind.TestAppConfiguration;
import com.tsg.fischer.mastermind.data.GameDAO;
import com.tsg.fischer.mastermind.data.RoundDAO;
import com.tsg.fischer.mastermind.model.Game;
import com.tsg.fischer.mastermind.model.Round;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestAppConfiguration.class)
public class TestDAOImpl {
    @Autowired
    private RoundDAO roundDAO;

    @Autowired
    private GameDAO gameDAO;

    @Before
    public void setUp() {
        this.tearDown();
    }

    @After
    public void tearDown() {
        List<Game> games = gameDAO.getAllGames();
        for(Game game : games) {
            List<Round> rounds = roundDAO.getAllFromGameById(game.getGameID());
            for(Round round: rounds) {
                roundDAO.deleteRoundByID(round.getRoundID());
            }
            gameDAO.deleteGameByID(game.getGameID());
        }
    }

    @Test
    public void testAddGetGame() {
        Game game = new Game("1234");

        gameDAO.addGame(game);

        Game fromDAO = gameDAO.getGameByID(game.getGameID());

        Assert.assertEquals(game, fromDAO);
    }

    @Test
    public void testGetAllGames() {
        Game game1 = new Game("1234");
        Game game2 = new Game("2345");

        gameDAO.addGame(game1);

        gameDAO.addGame(game2);

        List<Game> games = gameDAO.getAllGames();

        Assert.assertEquals(2, games.size());
        Assert.assertTrue(games.contains(game1));
        Assert.assertTrue(games.contains(game2));
    }

    @Test
    public void testGameFinished() {
        Game game = new Game("1234");
        gameDAO.addGame(game);

        Assert.assertFalse(game.isFinished());

        gameDAO.updateGameStatusToFinished(game);

        game = gameDAO.getGameByID(game.getGameID());

        Assert.assertTrue(game.isFinished());
    }

    @Test
    public void testDeleteGame() {
        Game game = new Game("0163");
        gameDAO.addGame(game);

        Assert.assertNotNull(gameDAO.getGameByID(game.getGameID()));

        gameDAO.deleteGameByID(game.getGameID());

        try {
            gameDAO.getGameByID(game.getGameID());
            Assert.fail("Game not deleted.");
        } catch(EmptyResultDataAccessException e) {
            System.out.println("Game deleted.");
        }
    }

    @Test
    public void testAddGetRound() {
        Game game = new Game("1234");
        Round round = new Round("5678");

        gameDAO.addGame(game);
        gameDAO.addRound(game.getGameID(), round);

        Round fromDAO = roundDAO.getRoundByID(round.getRoundID());

        Assert.assertEquals(round, fromDAO);
    }

    @Test
    public void testGetAllRoundsByGameID() {
        Game game = new Game("1234");
        Round round1 = new Round("2345");
        Round round2 = new Round("6524");

        gameDAO.addGame(game);
        roundDAO.addRound(game.getGameID(), round1);
        roundDAO.addRound(game.getGameID(), round2);

        List<Round> rounds = roundDAO.getAllFromGameById(game.getGameID());

        Assert.assertEquals(2, rounds.size());
        Assert.assertTrue(rounds.contains(round1));
        Assert.assertTrue(rounds.contains(round2));
    }

    @Test
    public void testDeleteRound() {
        Game game = new Game("1346");
        Round round = new Round("1839");

        gameDAO.addGame(game);
        roundDAO.addRound(game.getGameID(), round);

        Assert.assertNotNull(roundDAO.getRoundByID(round.getRoundID()));

        roundDAO.deleteRoundByID(round.getRoundID());

        try {
            roundDAO.getRoundByID(round.getRoundID());
            Assert.fail("Round not deleted.");
        } catch(EmptyResultDataAccessException e) {
            System.out.println("Round deleted.");
        }
    }
}
