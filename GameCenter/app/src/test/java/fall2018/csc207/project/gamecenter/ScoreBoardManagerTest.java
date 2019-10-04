package fall2018.csc207.project.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * unit test for  ScoreBoardManager.
 */
public class ScoreBoardManagerTest {

    /**
     * initiate a scoreboard for unit test
     */
    private ScoreBoard testBoard = new ScoreBoard(true, "test");
    private ScoreBoardManager test = new ScoreBoardManager(3);
    private ScoreBoardManager testNew = new ScoreBoardManager(3);

    /**
     * Test whether getScoreBoard and setScoreBoard in ScoreBoardManager works well.
     */
    @Test
    public void testGetScoreBoard() {
        test.setScoreBoard(testBoard, 0);
        testNew.setScoreBoard(testBoard,0);
        assertSame(test.getScoreBoard(0), testBoard);
        assertNull(test.getScoreBoard(4));
        test.setScoreBoard(testBoard, 4);
        assertNull(test.getScoreBoard(4));
        for (int i = 0;i<5;i++){
            assertSame(test.getScoreBoard(i), testNew.getScoreBoard(i));
        }
    }

}
