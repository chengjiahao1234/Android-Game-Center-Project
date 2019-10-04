package fall2018.csc207.project.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The score board for slidingtile game.
 */
public class ScoreBoard implements Serializable {

    /**
     * A initial scoreBoard.
     */
    private List<Score> scoreBoard;

    /**
     * Whether the scoreBoard is per-game or per-user.
     */
    private boolean isPerGame;

    /**
     * The type of the game for the scoreBoard.
     */
    private String type;

    /**
     * Solution found on:
     * https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     * Resolved incompatible class type issue read from file storing all users
     */
    private static final long serialVersionUID = -7930972121575214374L;

    /**
     * Construct a per-game score board or per-user score board
     *
     * @param isPerGame the type of the score board
     * @param type      the type of the game
     */
    public ScoreBoard(boolean isPerGame, String type) {
        scoreBoard = new ArrayList<>();
        this.isPerGame = isPerGame;
        this.type = type;
    }

    /**
     * Get the size of the list scoreBoard
     *
     * @return the size of the scoreBoard
     */
    public int getSize() {
        return scoreBoard.size();
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
     * Setter for the scoreBoard
     *
     * @param scoreBoard the new scoreBoard.
     */
    void setScoreBoard(List<Score> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    /**
     * Getter for isPerGame.
     *
     * @return the type of the game (whether the score board is per game or per user)
     */
    boolean isPerGame() {
        return isPerGame;
    }

    /**
     * Return the score in the index i
     *
     * @param i the index of the score
     * @return Score
     */
    public Score getScore(int i) {
        return scoreBoard.get(i);
    }

    /**
     * Add new score to the score board.
     *
     * @param score the new core
     */
    public void update(Score score) {
        // check if this is a per user game, if so, add the score at the end
        if (!this.isPerGame()) {
            scoreBoard.add(score);
        } else {
            //check if the user has score in scoreboard
            boolean found = false;
            for (int i = 0; i < scoreBoard.size(); i++) {
                if (score.getUser().equals(scoreBoard.get(i).getUser())) {
                    found = true;
                    //check their score to see if it is a new high score
                    if (scoreBoard.get(i).compareTo(score) > 0) {
                        scoreBoard.remove(i);
                        scoreBoard.add(score);
                    }
                }
            }
            if (!found) scoreBoard.add(score);
        }
        Collections.sort(scoreBoard);
    }

    /**
     * Convert the score board to string array and return it
     *
     * @return the string array that represent the score board
     */
    public String[] getList() {
        String[] display = new String[getSize() + 1];
        display[0] = String.format("%s %5s %15s %10s %8s", "No.", "Game type", "User name",
                "Move", "Time");
        for (int i = 1; i < display.length; i++)
            display[i] = String.format("%5s %-1s", i,
                    scoreBoard.get(i - 1).toString());
        return display;
    }

}
