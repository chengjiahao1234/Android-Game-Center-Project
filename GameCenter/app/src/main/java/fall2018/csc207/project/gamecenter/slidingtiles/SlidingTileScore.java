package fall2018.csc207.project.gamecenter.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc207.project.gamecenter.Score;

/**
 * The sliding tile score for the game slidingTile.
 */
public class SlidingTileScore extends Score implements Serializable, Comparable<Score>{

    /**
     * Construct a score for sliding Tile game.
     * @param type the type of the game
     * @param user the name of the user
     * @param move the move of the game
     * @param time the time the user takes
     */
    public SlidingTileScore(String type, String user, int move, long time){
        super(type, user, move, time);
    }

    @Override
    public int compareTo(@NonNull Score other) {
        if (other.getMove() != this.getMove()) {
            return this.getMove() - other.getMove();
        } else {
            return Long.compare(this.getLong(), other.getLong());
        }
    }
}
