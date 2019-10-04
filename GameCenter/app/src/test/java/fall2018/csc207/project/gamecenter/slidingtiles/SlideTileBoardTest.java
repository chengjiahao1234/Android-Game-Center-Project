package fall2018.csc207.project.gamecenter.slidingtiles;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

/**
 * unit test for SlideTileBoard.
 */
public class SlideTileBoardTest {

    /**
     * Board can be used for test
     */
    private Board board;

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
     * Make a Board.
     */
    private void setUpCorrect(int size) {
        List<Tile> tiles = makeTiles(size);
        board = new Board(tiles, size);

    }




    /**
     * Test the getSize in Board class
     */
    @Test
    public void testGetSize() {
        setUpCorrect(4);
        assertEquals(4,  board.getSize());

    }

    /**
     * Test the numTile in Board class
     */
    @Test
    public void testNumTiles() {
        setUpCorrect(4);
        assertEquals(16,  board.numTiles());
    }

    /**
     * Test the getSize in Board class
     */
    @Test
    public void testSwapTiles() {
        setUpCorrect(4);
        board.swapTiles(0,0,0,1);
        assertEquals(2,board.getTile(0,0).getId());
    }


    /**
     * Test the toString in Board class
     */
    @Test
    public void testToString() {
        setUpCorrect(4);
        assertEquals("Board{tiles=", board.toString().substring(0, 12));
    }

    /**
     * Test the iterator in Board class
     */
    @Test
    public void testIterator() {
        setUpCorrect(0);
        Iterator iterator = board.iterator();
        assertFalse(iterator.hasNext());
        setUpCorrect(2);
        Iterator newIterator = board.iterator();
        assertTrue(newIterator.hasNext());
        Tile test = (Tile) newIterator.next();
        assertEquals(test.getId(), makeTiles(2).get(0).getId());
        newIterator.next();
        Tile newText =(Tile) newIterator.next();
        assertEquals(newText.getId(), makeTiles(2).get(2).getId());
    }

}
