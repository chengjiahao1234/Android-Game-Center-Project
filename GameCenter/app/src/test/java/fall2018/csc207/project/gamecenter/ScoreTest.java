package fall2018.csc207.project.gamecenter;

import org.junit.Test;

import fall2018.csc207.project.gamecenter.slidingtiles.SlidingTileScore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreTest {
    /**
     * Sliding scores can be used to test.
     */

    private SlidingTileScore first = new SlidingTileScore("3*3", "i", 100000, 100000);

    /**
     * test getType in score
     */
    @Test
    public void testGetScoreType() {
        assertEquals("3*3", first.getType());
    }

    /**
     * test getTime in score
     */
    @Test
    public void testGetTime() {
        assertTrue("check string", String.class.isInstance(first.getTime()));
    }

    /**
     * test toString in score
     */
    @Test
    public void testToString() {
        assertTrue("check string", String.class.isInstance(first.toString()));
    }

    /**
     * test getLong in score
     */
    @Test
    public void testGetLong() {
        assertTrue("check long", Long.class.isInstance(first.getLong()));
    }

    /**
     * test getMove in score
     */
    @Test
    public void testGetMove() {
        assertTrue("check int", Integer.class.isInstance(first.getMove()));
    }

    /**
     * Test getList in ScoreBoard
     */
    @Test
    public void testGetList() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        correct.update(first);
        assertTrue(String[].class.isInstance(correct.getList()));
        assertEquals("No. Game type       User name       Move     Time", correct.getList()[0]);
    }

}
