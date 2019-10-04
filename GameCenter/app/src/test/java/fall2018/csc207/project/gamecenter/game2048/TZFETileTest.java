package fall2018.csc207.project.gamecenter.game2048;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Unit test for TZFE tile.
 */
public class TZFETileTest {

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

    /*
     * Make a Board.
     */
    private void setUpCorrect() {
        TZFETile[][] tiles = makeTiles();
        board = new TZFEBoard(tiles, 0);
    }

    /**
     * The init TZFETile.
     */
    private TZFETile tile;

    /**
     * Test whether compareTo works as expected.
     */
    @Test
    public void testCompareTile() {
        setUpCorrect();
        assertEquals(0, board.getTZFETile(0, 0).compareTo(board.getTZFETile(0, 1)));
        assertNotEquals(0, board.getTZFETile(0, 0).compareTo(board.getTZFETile(1, 1)));
        assertEquals(board.getTZFETile(1, 0).getBackground(), new TZFETile(23).getBackground());
    }
}
