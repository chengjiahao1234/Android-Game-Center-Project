package fall2018.csc207.project.gamecenter.game2048;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc207.project.gamecenter.slidingtiles.GameActivity;

/**
 * The board manager for 2048 game.
 */
public class TZFEBoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private TZFEBoard board;

    /**
     * The time used for the board.
     */
    private long timeElapsed = 0;

    /**
     * The number of moves used for the board.
     */
    private int move = 0;

    /**
     * The user name.
     */
    private String user;

    /**
     * The type of the game
     */
    private String type;

    /**
     * The complexity of the board
     */
    private int complexity;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public TZFEBoardManager(TZFEBoard board) {
        this.board = board;
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public TZFEBoardManager(TZFEBoard board, String type, String user, int complexity) {
        this.board = board;
        this.type = type;
        this.user = user;
        this.complexity = complexity;
    }

    /**
     * Return the current board.
     */
    public TZFEBoard getBoard() {
        return board;
    }

    /**
     * The getter for the user.
     *
     * @return the user name.
     */
    public String getUser() {
        return user;
    }

    /**
     * The getter for the type.
     *
     * @return the type of the game.
     */
    public String getType() {
        return type;
    }

    /**
     * Manage a new shuffled board.
     */
    public TZFEBoardManager(int complexity, String type, String user) {
        List<TZFETile> tiles = new ArrayList<>();
        this.complexity = complexity;
        for (int tileNum = 0; tileNum != TZFEBoard.SIZE * TZFEBoard.SIZE; tileNum++) {
            if (tileNum < TZFEBoard.SIZE - 1) {
                tiles.add(new TZFETile(0));
            } else {
                tiles.add(new TZFETile(11));
            }
        }
        this.type = type;
        this.user = user;

        Collections.shuffle(tiles);
        this.board = new TZFEBoard(tiles, complexity);
    }

    /**
     * @return the time the user took
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * The getter for the complexity.
     *
     * @return the complexity of the board.
     */
    public int getComplexity() {
        return complexity;
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
     * Return whether the user reach 2048.
     *
     * @return whether the board has 2048 tile.
     */
    boolean isWin() {
        for (int row = 0; row < TZFEBoard.SIZE; row++) {
            for (int col = 0; col < TZFEBoard.SIZE; col++) {
                if (board.getTZFETile(row, col).getId() == 11) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether the user lose the game.
     *
     * @return whether the board has blank tile.
     */
    boolean isLose() {
        for (int row = 0; row < TZFEBoard.SIZE; row++) {
            for (int col = 0; col < TZFEBoard.SIZE; col++) {
                int currentId = board.getTZFETile(row, col).getId();
                TZFETile above = row == 0 ? null : board.getTZFETile(row - 1, col);
                TZFETile below = row == TZFEBoard.SIZE - 1 ? null : board.getTZFETile(row + 1, col);
                TZFETile left = col == 0 ? null : board.getTZFETile(row, col - 1);
                TZFETile right = col == TZFEBoard.SIZE - 1 ? null : board.getTZFETile(row, col + 1);
                if ((below != null && below.getId() == currentId)
                        || (above != null && above.getId() == currentId)
                        || (left != null && left.getId() == currentId)
                        || (right != null && right.getId() == currentId)
                        || currentId == 12) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param direction the position
     */
    void touchMove(int direction) {

        //Check whether it is surrounded by a blank tile (above, below, left, right).
        // If so, swap it. If not, do nothing.
        move++;
        if (direction == GameActivity.UP) {
            mergeUP();
        } else if (direction == GameActivity.DOWN) {
            mergeDOWN();
        } else if (direction == GameActivity.LEFT) {
            mergeLEFT();
        } else if (direction == GameActivity.RIGHT) {
            mergeRIGHT();
        }
        addTile(direction);
    }

    /**
     * Add tile randomly after swiping
     *
     * @param direction the swipe direction
     */
    private void addTile(int direction) {
        List<int[]> blank = new ArrayList<>();
        addBlankVertical(blank, direction);
        addBlankHorizontal(blank, direction);
        if (blank.size() > 0) {
            int[] position = blank.get((int) (Math.random() * blank.size()));
            board.changeTile(position[0], position[1]);
        }
    }

    /**
     * Find all blank tile horizontally depends on the direction and add them to blank.
     * @param blank the list of blank id's position.
     * @param direction the direction
     */
    private void addBlankHorizontal(List<int[]> blank, int direction) {
        if (direction == GameActivity.LEFT) {
            for (int row = 0; row < TZFEBoard.SIZE; row++) {
                if (board.getTZFETile(row, TZFEBoard.SIZE - 1).getId() == 12) {
                    blank.add(new int[]{row, TZFEBoard.SIZE - 1});
                }
            }
        } else if (direction == GameActivity.RIGHT) {
            for (int row = 0; row < TZFEBoard.SIZE; row++) {
                if (board.getTZFETile(row, 0).getId() == 12) {
                    blank.add(new int[]{row, 0});
                }
            }
        }
    }

    /**
     * Find all blank tile vertically depends on the direction and add them to blank.
     * @param blank the list of blank id's position.
     * @param direction the direction
     */
    private void addBlankVertical(List<int[]> blank, int direction) {
        if (direction == GameActivity.UP) {
            for (int col = 0; col < TZFEBoard.SIZE; col++) {
                if (board.getTZFETile(TZFEBoard.SIZE - 1, col).getId() == 12) {
                    blank.add(new int[]{TZFEBoard.SIZE - 1, col});
                }
            }
        } else if (direction == GameActivity.DOWN) {
            for (int col = 0; col < TZFEBoard.SIZE; col++) {
                if (board.getTZFETile(0, col).getId() == 12) {
                    blank.add(new int[]{0, col});
                }
            }
        }
    }


    /**
     * Merge tiles for the right swipe
     */
    private void mergeRIGHT() {
        moveRIGHT();
        for (int row = 0; row < TZFEBoard.SIZE; row++) {
            for (int col = TZFEBoard.SIZE - 1; col > 0; col--) {
                if (board.getTZFETile(row, col).getId() != 12 &&
                        board.getTZFETile(row, col).getId() ==
                                board.getTZFETile(row, col - 1).getId()) {
                    board.mergeTiles(row, col - 1, row, col);
                }
            }
        }
        moveRIGHT();
    }

    /**
     * Move all tiles to the right.
     */
    private void moveRIGHT() {
        for (int row = 0; row < TZFEBoard.SIZE; row++) {
            int count = TZFEBoard.SIZE - 1;
            for (int col = TZFEBoard.SIZE - 1; col >= 0; col--) {
                if (board.getTZFETile(row, col).getId() != 12) {
                    board.swapTiles(row, col, row, count);
                    count--;
                }
            }
        }
    }

    /**
     * Merge tiles for the left swipe
     */
    private void mergeLEFT() {
        moveLEFT();
        for (int row = 0; row < TZFEBoard.SIZE; row++) {
            for (int col = 0; col < TZFEBoard.SIZE - 1; col++) {
                if (board.getTZFETile(row, col).getId() != 12 &&
                        board.getTZFETile(row, col).getId() ==
                                board.getTZFETile(row, col + 1).getId()) {
                    board.mergeTiles(row, col + 1, row, col);
                }
            }
        }
        moveLEFT();
    }

    /**
     * Move all tiles to the left.
     */
    private void moveLEFT() {
        for (int row = 0; row < TZFEBoard.SIZE; row++) {
            int count = 0;
            for (int col = 0; col < TZFEBoard.SIZE; col++) {
                if (board.getTZFETile(row, col).getId() != 12) {
                    board.swapTiles(row, col, row, count);
                    count++;
                }
            }
        }
    }

    /**
     * Merge tiles for the down swipe.
     */
    private void mergeDOWN() {
        moveDOWN();
        for (int col = 0; col < TZFEBoard.SIZE; col++) {
            for (int row = TZFEBoard.SIZE - 1; row > 0; row--) {
                if (board.getTZFETile(row, col).getId() != 12 &&
                        board.getTZFETile(row, col).getId() ==
                                board.getTZFETile(row - 1, col).getId()) {
                    board.mergeTiles(row - 1, col, row, col);
                }
            }
        }
        moveDOWN();
    }

    /**
     * Move all tiles to the bottom.
     */
    private void moveDOWN() {
        for (int col = 0; col < TZFEBoard.SIZE; col++) {
            int count = TZFEBoard.SIZE - 1;
            for (int row = TZFEBoard.SIZE - 1; row >= 0; row--) {
                if (board.getTZFETile(row, col).getId() != 12) {
                    board.swapTiles(row, col, count, col);
                    count--;
                }
            }
        }
    }

    /**
     * Merge tiles for the up swipe.
     */
    private void mergeUP() {
        moveUP();
        for (int col = 0; col < TZFEBoard.SIZE; col++) {
            for (int row = 0; row < TZFEBoard.SIZE - 1; row++) {
                if (board.getTZFETile(row, col).getId() != 12 &&
                        board.getTZFETile(row, col).getId() ==
                                board.getTZFETile(row + 1, col).getId()) {
                    board.mergeTiles(row + 1, col, row, col);
                }
            }
        }
        moveUP();
    }

    /**
     * Move all tiles to the top.
     */
    private void moveUP() {
        for (int col = 0; col < TZFEBoard.SIZE; col++) {
            int count = 0;
            for (int row = 0; row < TZFEBoard.SIZE; row++) {
                if (board.getTZFETile(row, col).getId() != 12) {
                    board.swapTiles(row, col, count, col);
                    count++;
                }
            }
        }
    }

}
