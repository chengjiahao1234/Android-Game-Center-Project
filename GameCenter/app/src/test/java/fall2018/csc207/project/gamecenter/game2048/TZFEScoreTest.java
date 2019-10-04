package fall2018.csc207.project.gamecenter.game2048;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * unit test for 2048 unit test
 */
public class TZFEScoreTest {

    /**
     * 2048 scores can be used to test.
     */
    private TZFEScore first = new TZFEScore("Easy", "i2", 100000, 100000);
    private TZFEScore second = new TZFEScore("Easy", "i2", 110000, 100000);
    private TZFEScore third = new TZFEScore("Easy", "i2", 110000, 1100000);


    /**
     * Test compare to in 2048
     */
    @Test
    public void testTZFECompareTo() {
        assertEquals(0, first.compareTo(first));
        assertTrue(third.compareTo(first) > 0);
        assertTrue(first.compareTo(second) < 0);
        assertTrue(third.compareTo(second) > 0);
    }


}
