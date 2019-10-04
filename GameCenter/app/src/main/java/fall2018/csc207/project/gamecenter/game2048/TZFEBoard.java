package fall2018.csc207.project.gamecenter.game2048;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The board for 2048 game.
 */
public class TZFEBoard extends Observable implements Serializable, Iterable<TZFETile> {

    /**
     * The size of the 2048 board.
     */
    public static final int SIZE = 4;

    /**
     * The complexity of the game.
     */
    private int complexity;

    /**
     * The tiles on the board in row-major order.
     */
    private TZFETile[][] tiles;

    /**
     * A new board of tiles set up by the given tiles.
     *
     * @param tiles      the tiles in board.
     * @param complexity the complexity of the game.
     */
    TZFEBoard(TZFETile[][] tiles, int complexity) {
        this.tiles = tiles;
        this.complexity = complexity;
    }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == size * size
     *
     * @param tiles the tiles for the board
     */
    TZFEBoard(List<TZFETile> tiles, int complexity) {
        Iterator<TZFETile> iter = tiles.iterator();
        this.complexity = complexity;
        this.tiles = new TZFETile[TZFEBoard.SIZE][TZFEBoard.SIZE];
        for (int row = 0; row != TZFEBoard.SIZE; row++) {
            for (int col = 0; col != TZFEBoard.SIZE; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    TZFETile getTZFETile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Merge the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void mergeTiles(int row1, int col1, int row2, int col2) {

        tiles[row2][col2] = new TZFETile(tiles[row1][col1].getId());
        tiles[row1][col1] = new TZFETile(11);

        setChanged();
        notifyObservers();
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

        TZFETile temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;

        setChanged();
        notifyObservers();
    }

    /**
     * Change the tile at (row, col).
     *
     * @param row the tile row
     * @param col the tile col
     */
    void changeTile(int row, int col) {
        if (complexity == 0) {
            tiles[row][col] = new TZFETile((int) (Math.random() * 2));
        } else if (complexity == 1) {
            tiles[row][col] = new TZFETile((int) (Math.random() * 3));
        } else if (complexity == 2) {
            tiles[row][col] = new TZFETile((int) (Math.random() * 4));
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "TZFEBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * The iterator for the TZFEBoard
     *
     * @return BoardIterator
     */
    @NonNull
    @Override
    public Iterator<TZFETile> iterator() {
        return new TZFEBoard.BoardIterator();
    }

    /**
     * Iterator for the class SlidingTileBoard.
     */
    private class BoardIterator implements Iterator<TZFETile> {

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
            return currRow < TZFEBoard.SIZE && currCol < TZFEBoard.SIZE;
        }

        @Override
        public TZFETile next() {
            if (currCol == TZFEBoard.SIZE) {
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
