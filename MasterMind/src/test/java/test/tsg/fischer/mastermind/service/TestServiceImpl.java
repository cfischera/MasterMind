package test.tsg.fischer.mastermind.service;

import com.tsg.fischer.mastermind.TestAppConfiguration;
import com.tsg.fischer.mastermind.data.GameDAO;
import com.tsg.fischer.mastermind.data.RoundDAO;
import com.tsg.fischer.mastermind.model.Game;
import com.tsg.fischer.mastermind.model.Round;
import com.tsg.fischer.mastermind.service.MasterMindService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestAppConfiguration.class)
public class TestServiceImpl {
    @Autowired
    private MasterMindService service;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private RoundDAO roundDAO;

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
    public void testStartGetGame() {
        Game game = service.startGame();

        Assert.assertEquals(game, service.getGameByID(game.getGameID()));

        Assert.assertEquals("----", service.getGameByID(game.getGameID()).getAnswer());
    }

    @Test
    public void testStartGetAllGames() {
        Game game1 = service.startGame();
        Game game2 = service.startGame();

        List<Game> games = service.getAllGames();

        Assert.assertEquals(2, games.size());
        Assert.assertTrue(games.contains(game1));
        Assert.assertTrue(games.contains(game2));
    }

    @Test
    public void testMakeGuess() {
        Game game = service.startGame();
        Round round = service.makeGuess(game.getGameID(), gameDAO.getGameByID(game.getGameID()).getAnswer());

        System.out.println(game.getAnswer());

        List<Round> rounds = new ArrayList<>();
        rounds.add(round);

        Assert.assertEquals(rounds, service.getRoundsByGameID(game.getGameID()));

        Assert.assertEquals("e:4:p:0", round.getResult());
    }

    @Test
    public void testUniqueAnswers() {
        Assert.assertTrue(this.isUniqueEachIndex(new int[]{2, 6, 1, 9}));

        Assert.assertFalse(this.isUniqueEachIndex(new int[]{2, 8, 1, 2}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{1, 5, 9, 5}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{0, 1, 1, 7}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{2, 6, 7, 7}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{1, 1, 2, 8}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{1, 3, 3, 3}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{1, 1, 1, 3}));
        Assert.assertFalse(this.isUniqueEachIndex(new int[]{1, 1, 1, 1}));


        Game game1 = service.startGame();
        Game game2 = service.startGame();

        int[] game1Answer = new int[4];
        int[] game2Answer = new int[4];

        for(int i=0;i<4;i++) {
            game1Answer[i] = gameDAO.getGameByID(game1.getGameID()).getAnswer().charAt(i);
            game2Answer[i] = gameDAO.getGameByID(game2.getGameID()).getAnswer().charAt(i);
        }

        Assert.assertTrue(this.isUniqueEachIndex(game1Answer));
        Assert.assertTrue(this.isUniqueEachIndex(game2Answer));
    }

    private boolean isUniqueEachIndex(int[] arr) {
        for(int i=0;i<arr.length-1;i++) {
            for(int j=i+1;j<arr.length;j++) {
                if(arr[i] == arr[j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
