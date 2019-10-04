package fall2018.csc207.project.gamecenter.slidingtiles;

import android.support.annotation.NonNull;

import java.util.NoSuchElementException;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The size of the rectangle board.
     */
    private int size;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public Board(List<Tile> tiles, int size) {
        Iterator<Tile> iter = tiles.iterator();
        this.size = size;
        this.tiles = new Tile[size][size];
        for (int row = 0; row != size; row++) {
            for (int col = 0; col != size; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * the size of the rectangle board.
     *
     * @return size the size of the rectangle board
     */

    public int getSize() {
        return size;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return tiles.length * tiles.length;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {

        Tile temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * implement the iterator for Board
     *
     * @return BoardIterator
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * Iterator for the class Board.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * The row that currently refers to.
         */
        private int currRow = 0;

        /**
         * The col that currently refers to.
         */
        private int currCol = 0;

        @Override
        public boolean hasNext() {
            return currRow < size && currCol < size;
        }

        @Override
        public Tile next() {
            if (currCol == size) {
                currRow++;
                currCol = 0;
            }
            if (!hasNext()) {
                throw new NoSuchElementException(
                        String.format("Index out of range (%s, %s)", currRow, currCol));
            }
            return tiles[currRow][currCol++];
        }
    }


}
