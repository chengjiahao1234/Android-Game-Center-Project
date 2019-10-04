package fall2018.csc207.project.gamecenter.slidingtiles;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * unit test for slidingTile unit test
 */
public class SlidingTileScoreTest {

    /**
     * slidingTile scores can be used to test.
     */
    private SlidingTileScore first = new SlidingTileScore("3*3", "i2", 100000, 100000);
    private SlidingTileScore second = new SlidingTileScore("3*3", "i2", 110000, 100000);
    private SlidingTileScore third = new SlidingTileScore("3*3", "i2", 110000, 1100000);


    /**
     * Test compare to in SlidingTile
     */
    @Test
    public void testTZFECompareTo() {
        assertEquals(0, first.compareTo(first));
        assertTrue(third.compareTo(first) > 0);
        assertTrue(first.compareTo(second) < 0);
        assertTrue(second.compareTo(third) < 0);

    }


}

