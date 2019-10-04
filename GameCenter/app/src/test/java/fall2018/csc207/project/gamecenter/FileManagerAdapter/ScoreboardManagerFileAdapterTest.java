package fall2018.csc207.project.gamecenter.FileManagerAdapter;

import org.junit.Test;

import java.io.File;

import fall2018.csc207.project.gamecenter.ScoreBoard;
import fall2018.csc207.project.gamecenter.ScoreBoardManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for file adapter for ScoreBoardManager
 * Means of testing file-saving and -loading features are stated above the tests
 */
public class ScoreboardManagerFileAdapterTest {

    private String FILE_NAME = "test.ser";
    private int SIZE = 100;
    private ScoreboardManagerFileAdapter scoreboardManagerFileAdapter =
            new ScoreboardManagerFileAdapter();
    private ScoreBoardManager scoreBoardManager = new ScoreBoardManager(SIZE);

    /**
     * Set up ScoreBoardManager for testing
     */
    private void setScoreboardManager() {
        for (int i = 0; i < SIZE; i++)
            scoreBoardManager.setScoreBoard(
                    new ScoreBoard(true, "unittest" + i),
                    i);
    }

    /**
     * Delete file created for testing
     */
    private void cleanUp() {
        File test = new File(FILE_NAME);
        if (test.exists()) test.delete();
    }

    /**
     * Test one of the 2 constructors that initializes
     * the adapter with a ScoreBoardManager
     */
    @Test
    public void testConstructor() {
        ScoreboardManagerFileAdapter testConstructor =
                new ScoreboardManagerFileAdapter(scoreBoardManager);
        assertEquals(scoreBoardManager, testConstructor.getAdaptee());
    }
    /**
     * Test setAdaptee() method
     */
    @Test
    public void testSetAdaptee() {
        assertNotEquals(scoreBoardManager, scoreboardManagerFileAdapter.getAdaptee());
        scoreboardManagerFileAdapter.setAdaptee(scoreBoardManager);
        assertEquals(scoreBoardManager, scoreboardManagerFileAdapter.getAdaptee());
    }

    /**
     * test getAdaptee() method
     */
    @Test
    public void testGetAdaptee() {
        scoreboardManagerFileAdapter.setAdaptee(scoreBoardManager);
        ScoreBoardManager testGetter = scoreboardManagerFileAdapter.getAdaptee();
        assertEquals(testGetter, scoreBoardManager);
    }


    /**
     * Test saveToFile() method by:
     * 1. Populating a ScoreboardManager <scoreBoardManager> with newly created scoreboards
     * 2. Creating a new file with zero data
     * 3. Populate the file with whatever in <scoreBoardManager> via saveToFile()
     * 4. Check if the file has data by whether examining its size is zero
     */
    @Test
    public void testSaveToFile() {
        setScoreboardManager();
        scoreboardManagerFileAdapter.setAdaptee(scoreBoardManager);
        File testSave = new File(FILE_NAME);
        assertEquals(0, testSave.length());
        scoreboardManagerFileAdapter.saveToFile(FILE_NAME);
        assertNotEquals(0,testSave.length());
    }


    /**
     * Test loadFromFile() by:
     * 1. Populating a ScoreBoardManager <scoreBoardManager> with newly created scoreboards
     * 2. Creating a new ScoreBoardManager <testLoad>
     * 3. Checking if <testLoad> has no data
     * 4. Populating <testLoad> via loadFromFile()
     * 5. Checking if every scoreboard of <testLoad> is the same as
     *    that of <scoreBoardManager>.
     */
    @Test
    public void testLoadFromFile() {
        setScoreboardManager();
        ScoreBoardManager testLoad = new ScoreBoardManager(SIZE);
        scoreboardManagerFileAdapter.setAdaptee(testLoad);
        for (int i = 0; i < SIZE; i++)
            assertNull(testLoad.getScoreBoard(i));
        scoreboardManagerFileAdapter.loadFromFile(FILE_NAME);
        testLoad = scoreboardManagerFileAdapter.getAdaptee();
        for (int i = 0; i < SIZE; i++)
            assertTrue(
                    testLoad.getScoreBoard(i).getType().equals(
                            scoreBoardManager.getScoreBoard(i).getType()));
        cleanUp();
    }
}
