package fall2018.csc207.project.gamecenter.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * canSolve get idea from:
 * https://blog.csdn.net/Get_Better/article/details/53896398
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The latest moves (up to 4).
     */
    private ArrayList<int[]> savedMoves;

    /**
     * The time used for the board.
     */
    private long timeElapsed = 0;

    /**
     * The number of moves used for the board.
     */
    private int move = 0;

    private int undoTimes = 3;

    private String user;
    private String type;
    private int size;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardManager(Board board, int size) {
        this.board = board;
        this.size = size;
        savedMoves = new ArrayList<>();
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardManager(Board board, int size, String type, String user) {
        this.board = board;
        this.size = size;
        this.type = type;
        this.user = user;
        savedMoves = new ArrayList<>();
    }

    /**
     * Return the current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter for the user.
     *
     * @return the user name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the type.
     *
     * @return the type of the game.
     */
    public String getType() {
        return type;
    }

    /**
     * Manage a new shuffled board.
     */
    public BoardManager(int size, String type, String user) {
        List<Tile> tiles = new ArrayList<>();
        this.size = size;
        for (int tileNum = 0; tileNum != size * size; tileNum++) {
            tiles.add(new Tile(tileNum, size));
        }
        this.type = type;
        this.user = user;
        savedMoves = new ArrayList<>();

        Collections.shuffle(tiles);
        while (!canSolve(tiles)) {
            Collections.shuffle(tiles);
        }
        this.board = new Board(tiles, size);
    }

    /**
     * find whether the shuffled tile list can be solved
     */
    boolean canSolve(List<Tile> tiles) {
        boolean blankInOdd = true;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getId() == tiles.size() && (i / size) % 2 == 0) {
                blankInOdd = false;
            }
        }
        if (tiles.size() % 2 == 1) {
            return getInvert(tiles) % 2 == 0;
        } else {
            if (blankInOdd) {
                return getInvert(tiles) % 2 == 0;
            } else {
                return getInvert(tiles) % 2 == 1;
            }
        }
    }

    /**
     * calculate the inversion fot the list of tile after shuffle.
     *
     * @param tiles     tile objects that have been re-positioned in array
     * @return          number of inversion
     */
    private int getInvert(List<Tile> tiles) {
        int inversion = 0;
        int num = 0;
        int blankId = tiles.size();
        for (int tileNum = 0; tileNum < tiles.size(); tileNum++) {
            for (int invert = tileNum + 1; invert < tiles.size(); invert++) {
                int front = tiles.get(tileNum).getId();
                if (tiles.get(tileNum).getId() != blankId && tiles.get(invert).getId() != blankId &&
                        tiles.get(invert).getId() < front) {
                    num++;
                }
            }
            inversion += num;
            num = 0;
        }
        return inversion;
    }

    /**
     * @return the time the user took
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Getter for the size.
     *
     * @return the size of the board.
     */
    public int getSize() {
        return size;
    }

    /**
     * this sets the user's time taken to be the parameter
     *
     * @param timeElapsed is the desired time taken by user
     */
    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * @return the number of moves of user
     */
    public int getMove() {
        return move;
    }

    /**
     * sets the number of moves by user
     *
     * @param move is the new number of moves by the user
     */
    public void setMove(int move) {
        this.move = move;
    }

    /**
     * Return the older board if there exists one.
     * Otherwise return the current board.
     */
    void getPrev() {
        if (savedMoves.size() >= 1 && undoTimes > 0) {
            int[] m = savedMoves.remove(savedMoves.size() - 1);
            board.swapTiles(m[0], m[1], m[2], m[3]);
            undoTimes--;
            move -= 2;
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        int row = 0;
        int col = 0;
        Tile current = board.getTile(0, 0);
        while (row * col < (size - 1) * (size - 1)) {
            col++;
            if (col == size) {
                row++;
                col = 0;
            }
            if (current.compareTo(board.getTile(row, col)) < 0) {
                return false;
            }
            current = board.getTile(row, col);
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / size;
        int col = position % size;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == size - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == size - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Save the board whenever a change is made to the board.
     * Drop the oldest move when there were 3 steps already.
     *
     * @param board the board with the latest status.
     */
    private void saveMove(int[] board) {
        if (savedMoves.size() > 0 && savedMoves.size() == undoTimes) {
            savedMoves.remove(0);
        }
        if (undoTimes < 3) {
            undoTimes++;
        }
        savedMoves.add(board);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / size;
        int col = position % size;
        int blankId = board.numTiles();
        //Check whether it is surrounded by a blank tile (above, below, left, right).
        // If so, swap it. If not, do nothing.
        if (row - 1 >= 0 && board.getTile(row - 1, col).getId() == blankId) {
            int[] t = {row - 1, col, row, col};
            saveMove(t);
            move++;
            board.swapTiles(row, col, row - 1, col);
        } else if (row + 1 < size &&
                board.getTile(row + 1, col).getId() == blankId) {
            int[] t = {row + 1, col, row, col};
            saveMove(t);
            move++;
            board.swapTiles(row, col, row + 1, col);
        } else if (col - 1 >= 0 && board.getTile(row, col - 1).getId() == blankId) {
            int[] t = {row, col - 1, row, col};
            saveMove(t);
            move++;
            board.swapTiles(row, col, row, col - 1);
        } else if (col + 1 < size &&
                board.getTile(row, col + 1).getId() == blankId) {
            int[] t = {row, col + 1, row, col};
            saveMove(t);
            move++;
            board.swapTiles(row, col, row, col + 1);
        }
    }

}
