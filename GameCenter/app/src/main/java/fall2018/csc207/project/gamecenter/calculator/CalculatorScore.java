package fall2018.csc207.project.gamecenter.calculator;

import android.support.annotation.NonNull;

import fall2018.csc207.project.gamecenter.Score;

/**
 * The score for game calculator.
 */
public class CalculatorScore extends Score {


    /**
     * Construct a score for sliding tile game.
     *
     * @param type the specific name
     * @param user the name of the user
     * @param move the total number of moves
     * @param time the total time
     */
    public CalculatorScore(String type, String user, int move, long time) {
        super(type, user, move, time);
    }

    /**
     * compares a score where the more move and less time, the higher the score
     *
     * @param other the other score to compare to
     * @return negative when this score is higher, 0 when same, positive when this score is lower
     */
    @Override
    public int compareTo(@NonNull Score other) {

        if (this.getMove() != other.getMove()) {
            return other.getMove() - this.getMove();
        } else {
            return Long.compare(this.getLong(), other.getLong());
        }
    }
}

