package fall2018.csc207.project.gamecenter.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc207.project.gamecenter.R;

import static org.junit.Assert.*;

/**
 * unit test for SlideTile board manager.
 */
public class SlideTileBoardManagerTest {
    /**
     * The SlideTile board manager for testing.
     */
    private BoardManager boardManager;
    private BoardManager test = new BoardManager(2,"3*3","test");

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int size) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = size * size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, size));
        }

        return tiles;
    }

    /*
     * Make a solved Board.
     */
    private void setUpCorrect(int size) {
        List<Tile> tiles = makeTiles(size);
        Board board = new Board(tiles, size);
        boardManager = new BoardManager(board, size);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        for (int i = 3; i <= 5; i++) {
            setUpCorrect(i);
            assertTrue(boardManager.puzzleSolved());
            swapFirstTwoTiles();
            assertFalse(boardManager.puzzleSolved());
        }
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        for (int i = 3; i <= 5; i++) {
            setUpCorrect(i);
            assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
            assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
            boardManager.getBoard().swapTiles(0, 0, 0, 1);
            assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
            assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
        }
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        for (int i = 3; i <= 5; i++) {
            setUpCorrect(i);
            assertEquals(i * i - 1, boardManager.getBoard().getTile(i - 1, i - 2).getId());
            assertEquals(i * i, boardManager.getBoard().getTile(i - 1, i - 1).getId());
            boardManager.getBoard().swapTiles(i - 1, i - 1, i - 1, i - 2);
            assertEquals(i * i, boardManager.getBoard().getTile(i - 1, i - 2).getId());
            assertEquals(i * i - 1, boardManager.getBoard().getTile(i - 1, i - 1).getId());
        }
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        for (int i = 3; i <= 5; i++) {
            setUpCorrect(i);
            assertTrue(boardManager.isValidTap(i * (i - 1) - 1));
            assertTrue(boardManager.isValidTap(i * i - 2));
            assertFalse(boardManager.isValidTap(3));
        }
    }

    /**
     * get list of id for a board of <size>
     *
     * @param size  size of a board
     * @return list of id for size i
     */

    private List<Integer> expectedId(int size) {
        List<Integer> ids = new ArrayList<>();
        for (int j = 1; j <= size * size; j++) {
            ids.add(j);
        }
        return ids;
    }

    /**
     * whether the board with size i has all the id correctly.
     *
     * @param size  Size of the board
     * @return      true if the the board formed correctly.
     *              otherwise false.
     */

    private boolean boardFormation(int size) {
        boardManager = new BoardManager(size, "test", "tester");
        List<Integer> expected = expectedId(size);
        for (Tile tile : boardManager.getBoard()) {
            int id = tile.getId();
            if (expected.contains(id)) {
                expected.remove((Integer) id);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * test all three possible sizes whether they formed correctly
     */

    @Test
    public void testBoardFormation() {
        assertTrue(boardFormation(3));
        assertTrue(boardFormation(4));
        assertTrue(boardFormation(5));
    }


    /**
     * test GetPrev is functional or not.
     */
    @Test
    public void testGetPrev() {
        for (int i = 3; i <= 5; i++) {
            setUpCorrect(i);
            boardManager.touchMove(i * i - 1);
            assertEquals(i * i, boardManager.getBoard().getTile(i - 1, i - 1).getId());
            assertEquals(i * i - 1, boardManager.getBoard().getTile(i - 1, i - 2).getId());
            boardManager.touchMove(i * i - 1);
            boardManager.getPrev();
            assertEquals(i * i, boardManager.getBoard().getTile(i - 1, i - 1).getId());
            assertEquals(i * i - 1, boardManager.getBoard().getTile(i - 1, i - 2).getId());
        }
    }

    /**
     * test the solvable in the boardManager
     */

    @Test
    public void testSolvable() {
        for (int i = 3; i <= 5; i++) {
            List<Tile> test = makeTiles(i);
            Collections.swap(test, i * i - 2, i * i - 1);
            Board board = new Board(test, i);
            boardManager = new BoardManager(board, i);
            assertTrue(boardManager.canSolve(test));
            List<Tile> newTest = makeTiles(i);
            Collections.swap(newTest, i * i - 2, i * i - 3);
            Board newBoard = new Board(newTest, i);
            boardManager = new BoardManager(newBoard, i);
            assertFalse(boardManager.canSolve(newTest));

        }
    }

    /**
     * test the touch move in the boardManager
     */

    @Test
    public void testTouchMove() {
        setUpCorrect(4);
        swapFirstTwoTiles();
        Board first = boardManager.getBoard();
        boardManager.touchMove(15);                                               //wrong
        assertEquals(16, first.getTile(3, 3).getId());
        boardManager.touchMove(14);                                              //left
        assertEquals(16, first.getTile(3, 2).getId());
        boardManager.touchMove(10);                                            //up
        assertEquals(16, first.getTile(2, 2).getId());
        boardManager.touchMove(11);
        assertEquals(16, first.getTile(2, 3).getId()); //right
        boardManager.touchMove(15);
        assertEquals(16, first.getTile(3, 3).getId()); //down
    }


    /**
     * test the getUser in the boardManager
     */
    @Test
    public void testGetUser() {

        assertTrue("check string", String.class.isInstance(test.getUser()));
    }

    /**
     * test the getType in the boardManager
     */
    @Test
    public void testGetType() {

        assertTrue("check string", String.class.isInstance(test.getType()));
    }

    /**
     * test the Move in the boardManager
     */
    @Test
    public void testMove() {
        test.setMove(10);
        assertEquals(10, test.getMove());
    }

    /**
     * test the Time in the boardManager
     */
    @Test
    public void testTime() {
        test.setTimeElapsed(10);
        assertEquals(10, test.getTimeElapsed());
    }

    /**
     * test the getPrevious in the boardManager
     */
    @Test
    public void testGetPrevious() {
        setUpCorrect(4);
        swapFirstTwoTiles();
        boardManager.touchMove(14);
        assertEquals(16, boardManager.getBoard().getTile(3,2).getId());
        boardManager.getPrev();
        assertEquals(16,boardManager.getBoard().getTile(3,3).getId());
    }

    /**
     * test default background for tile
     */
    @Test
    public void testDefaultTile(){
        Tile test =new Tile(26,6);
        assertEquals(test.getBackground(), R.drawable.tile_blank);
    }
}

