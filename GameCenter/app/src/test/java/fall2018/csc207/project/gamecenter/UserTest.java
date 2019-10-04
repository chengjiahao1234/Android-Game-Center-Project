package fall2018.csc207.project.gamecenter;

import org.junit.Test;

import fall2018.csc207.project.gamecenter.calculator.CalculatorManager;
import fall2018.csc207.project.gamecenter.game2048.TZFEBoardManager;
import fall2018.csc207.project.gamecenter.slidingtiles.BoardManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * unit test for User.
 */
public class UserTest {

    /*
    initate items used for test.
     */
    private User second = new User("test", "123");
    private BoardManager test = new BoardManager(2, "3*3", "i");
    private TZFEBoardManager tzfeGame = new TZFEBoardManager(2, "easy", "i");
    private ScoreBoardManager scoreBoardManager = new ScoreBoardManager(4);
    private CalculatorManager calculatorManager = new CalculatorManager("easy");


    /**
     * test getter and setter for userName in User
     */
    @Test
    public void testUserName() {
        second.setUsername("i");
        assertEquals("i", second.getUsername());
    }

    /**
     * test checkPassword function or not
     */
    @Test
    public void testCheckPassword() {
        assertTrue(second.checkPassword("123"));
    }

    /**
     * test PerUserScoreBoard in User
     */
    @Test
    public void testPerUserScoreBoard() {
        second.setPerUserScoreBoard(scoreBoardManager);
        assertEquals(scoreBoardManager, second.getPerUserScoreBoard());
    }


    /**
     * test toString in User
     */
    @Test
    public void testToString() {
        assertEquals(second.getUsername(), second.toString());
    }

    /**
     * test getGame in User
     */
    @Test
    public void testGetGame() {
        assertNull(second.getGame("slidingtile", 0));
        assertNull(second.getGame("t", 0));

    }

    /**
     * test saveGame in User
     */
    @Test
    public void testSaveGame() {
        second.saveGame(test, 0);
        System.out.println(test);
        BoardManager newTest = ((BoardManager) second.getGame("slidingtile", 0));
        assertEquals(test.getSize(), newTest.getSize());
        second.saveGame(tzfeGame, 0);
        TZFEBoardManager secondTest = ((TZFEBoardManager) second.getGame("tzfe", 0));
        assertEquals(secondTest.getComplexity(), tzfeGame.getComplexity());
        second.saveGame(calculatorManager, 0);
        CalculatorManager thirdTest = ((CalculatorManager) second.getGame("calculator", 0));
        assertEquals(thirdTest.getCalculator().getOpr1(), calculatorManager.getCalculator().getOpr1());

    }
}
