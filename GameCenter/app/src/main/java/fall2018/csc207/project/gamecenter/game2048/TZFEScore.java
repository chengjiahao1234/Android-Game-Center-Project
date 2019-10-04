package fall2018.csc207.project.gamecenter.game2048;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc207.project.gamecenter.Score;

/**
 * The score of the 2048 game.
 */
public class TZFEScore extends Score implements Serializable, Comparable<Score> {

    /**
     * Construct a new score for 2048 game.
     * @param type the type of the game
     * @param user the user name
     * @param move the move the user takes
     * @param time the time elapsed
     */
    public TZFEScore(String type, String user, int move, long time) {
        super(type, user, move, time);
    }

    @Override
    public int compareTo(@NonNull Score other) {
        if (this.getLong() != other.getLong()) {
            return Long.compare(this.getLong(), other.getLong());
        } else {
            return this.getMove() - other.getMove();
        }
    }
}
