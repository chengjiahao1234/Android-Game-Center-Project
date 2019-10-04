package fall2018.csc207.project.gamecenter.game2048;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import fall2018.csc207.project.gamecenter.slidingtiles.GameActivity;

import static org.junit.Assert.*;


/**
 * unit test for (2048) TZFEBoard manager.
 *
 */
public class TZFEBoardManagerTest {

    /**
     * The 2048 board manager for testing.
     */
    private TZFEBoardManager tzfeboardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<TZFETile> makeTiles(int id) {
        List<TZFETile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != TZFEBoard.SIZE * TZFEBoard.SIZE; tileNum++) {
            if (tileNum <= 9) {
                tiles.add(new TZFETile(11));
            } else {
                tiles.add(new TZFETile(id));
            }
        }
        return tiles;
    }

    /**
     * Make a set of tiles that can not be solved.
     *
     * @return a set of tiles that can not be solved
     */
    private List<TZFETile> makeNewTiles() {
        List<TZFETile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum < 16; tileNum++) {
            if (tileNum < 10) {
                tiles.add(new TZFETile(tileNum));
            } else {
                tiles.add(new TZFETile(tileNum - 10));
            }
        }
        return tiles;
    }

    /*
     * Make a solved Board.
     */
    private void setUpCorrect(int id) {
        List<TZFETile> tiles = makeTiles(id);
        TZFEBoard board = new TZFEBoard(tiles, 1);
        tzfeboardManager = new TZFEBoardManager(board);
    }

    /**
     * merge the last two tiles to 2048 .
     */
    private void mergeLastTwoTiles() {
        tzfeboardManager.getBoard().mergeTiles(3, 2, 3, 3);
    }


    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsWin() {
        setUpCorrect(9);
        assertFalse(tzfeboardManager.isWin());
        mergeLastTwoTiles();
        assertTrue(tzfeboardManager.isWin());
    }

    /*
     * Make a cannot solve Board.
     */
    private void setUpWrong() {
        List<TZFETile> tiles = makeNewTiles();
        TZFEBoard board = new TZFEBoard(tiles, 1);
        tzfeboardManager = new TZFEBoardManager(board);
    }


    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */

    @Test
    public void testIsLose() {
        setUpCorrect(9);
        assertFalse(tzfeboardManager.isLose());
        setUpWrong();
        assertTrue(tzfeboardManager.isLose());

    }

    /**
     * Test whether touchMove work well.
     */
    @Test
    public void testTouchMove() {
        //check right move
        setUpCorrect(0);
        tzfeboardManager.touchMove(GameActivity.RIGHT);
        assertEquals(2, tzfeboardManager.getBoard().getTZFETile(3, 3).getId());
        //check up move
        setUpCorrect(0);
        tzfeboardManager.touchMove(GameActivity.UP);
        assertEquals(2, tzfeboardManager.getBoard().getTZFETile(0, 3).getId());
        //check left move
        setUpCorrect(0);
        tzfeboardManager.touchMove(GameActivity.LEFT);
        assertEquals(2, tzfeboardManager.getBoard().getTZFETile(3, 0).getId());
        //check right move
        setUpCorrect(0);
        tzfeboardManager.touchMove(GameActivity.DOWN);
        assertEquals(2, tzfeboardManager.getBoard().getTZFETile(3, 3).getId());
        assertEquals(1, tzfeboardManager.getBoard().getTZFETile(3, 0).getId());
    }

    /**
     * Test whether getter and setter work well.
     */
    @Test
    public void testGetterAndSetter() {
        TZFEBoard board = new TZFEBoard(makeTiles(0), 0);
        tzfeboardManager = new TZFEBoardManager(board,"Easy", "test", 0);
        assertEquals("test", tzfeboardManager.getUser());
        assertEquals(0, tzfeboardManager.getComplexity());
        tzfeboardManager.setMove(3);
        assertEquals(3, tzfeboardManager.getMove());
        tzfeboardManager = new TZFEBoardManager(0, "Easy", "test");
        tzfeboardManager.setTimeElapsed(4L);
        assertEquals(4L, tzfeboardManager.getTimeElapsed());
        assertEquals("Easy", tzfeboardManager.getType());
    }
}
