package fall2018.csc207.project.gamecenter;

import java.io.Serializable;

/**
 * The score board manager for all per-game score boards of the sliding tiles game
 */
public class ScoreBoardManager implements Serializable {

    /**
     * The list of all pre-game score boards.
     */
    private ScoreBoard[] scoreBoards;

    /**
     * Construct an empty list of sliding tile score board.
     */
    public ScoreBoardManager(int size) {
        this.scoreBoards = new ScoreBoard[size];
    }

    /**
     * Add per-game score board to the list
     *
     * @param sb the per-game score board.
     */
    public void setScoreBoard(ScoreBoard sb, int position) {
        if (position < scoreBoards.length) {
            scoreBoards[position] = sb;
        }
    }

    /**
     * Get the score board by the given position
     *
     * @param position the index of the score board
     * @return the score board in the list at the index position.
     */
    public ScoreBoard getScoreBoard(int position) {
        if (position < scoreBoards.length) {
            return scoreBoards[position];
        } else {
            return null;
        }
    }
}
