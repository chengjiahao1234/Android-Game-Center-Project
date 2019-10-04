package fall2018.csc207.project.gamecenter.game2048;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test for (2048) TZFEBoard.
 */
public class TZFEBoardTest {

    /**
     * Board can be used for test
     */
    private TZFEBoard board;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private TZFETile[][] makeTiles() {
        TZFETile[][] tiles = new TZFETile[TZFEBoard.SIZE][TZFEBoard.SIZE];
        for (int tileNum = 0; tileNum != TZFEBoard.SIZE * TZFEBoard.SIZE; tileNum++) {
            if (tileNum < TZFEBoard.SIZE - 1) {
                tiles[tileNum / TZFEBoard.SIZE][tileNum % TZFEBoard.SIZE] =
                        new TZFETile(0);
            } else {
                tiles[tileNum / TZFEBoard.SIZE][tileNum % TZFEBoard.SIZE] =
                        new TZFETile(11);
            }
        }
        return tiles;
    }

    private void setUpSpecial() {
        List<TZFETile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != TZFEBoard.SIZE * TZFEBoard.SIZE; tileNum++) {
            if (tileNum <= 9) {
                tiles.add(new TZFETile(11));
            } else {
                tiles.add(new TZFETile(0));
            }
        }
        board = new TZFEBoard(tiles, 1);
    }

    /*
     * Make a Board.
     */
    private void setUpCorrect(int complexity) {
        TZFETile[][] tiles = makeTiles();
        board = new TZFEBoard(tiles, complexity);
    }

    /**
     * Test the toString in Board class
     */
    @Test
    public void testToString() {
        setUpCorrect(0);
        assertEquals("TZFEBoard{tiles=", board.toString().substring(0, 16));
    }

    /**
     * Test the iterator in Board class
     */
    @Test
    public void testIterator() {
        setUpCorrect(0);
        Iterator iterator = board.iterator();
        assertTrue(iterator.getClass().getName().contains("BoardIterator"));
        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        TZFETile test = (TZFETile) iterator.next();
        assertEquals(test.getId(), makeTiles()[1][0].getId());
    }

    /**
     * Test whether changeTile method works as expected.
     */
    @Test
    public void testChangeTile() {
        setUpCorrect(0);
        board.changeTile(3, 3);
        assertTrue(board.getTZFETile(3, 3).getId() <= 2);
        setUpCorrect(1);
        board.changeTile(3, 3);
        assertTrue(board.getTZFETile(3, 3).getId() <= 3);
        setUpCorrect(2);
        board.changeTile(3, 3);
        assertTrue(board.getTZFETile(3, 3).getId() <= 4);
    }

    /**
     * Test whether changeTile method works as expected.
     */
    @Test
    public void testSwapTiles() {
        setUpCorrect(0);
        int before = board.getTZFETile(0, 0).getId();
        int after = board.getTZFETile(1, 1).getId();
        board.swapTiles(0, 0, 1, 1);
        assertEquals(before, board.getTZFETile(1, 1).getId());
        assertEquals(after, board.getTZFETile(0, 0).getId());
    }

    /**
     * Test whether changeTile method works as expected.
     */
    @Test
    public void testMergeTiles() {
        setUpSpecial();
        int before = board.getTZFETile(3, 3).getId();
        int blank = 12;
        board.mergeTiles(3, 2, 3, 3);
        assertEquals(before + 1, board.getTZFETile(3, 3).getId());
        assertEquals(blank, board.getTZFETile(3, 2).getId());
    }

}
