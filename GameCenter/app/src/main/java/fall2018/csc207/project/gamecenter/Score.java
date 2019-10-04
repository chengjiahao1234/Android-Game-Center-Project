package fall2018.csc207.project.gamecenter;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * The abstract score system
 */
public abstract class Score implements Serializable, Comparable<Score> {

    /**
     * The type of the specific game.
     */
    private String type;

    /**
     * The name of user.
     */
    private String user;

    /**
     * The total number of moves that the user takes.
     */
    private int move;

    /**
     * The total time that the user takes.
     */
    private long time;

    /**
     * Solution found on:
     * https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     * Resolved incompatible class type issue read from file storing all users
     */
    private static final long serialVersionUID = 8533272975450805903L;

    /**
     * Construct a score for sliding tile game.
     *
     * @param type the specific name
     * @param user the name of the user
     * @param move the total number of moves
     * @param time the total time
     */
    public Score(String type, String user, int move, long time) {
        this.type = type;
        this.user = user;
        this.move = move;
        this.time = time;
    }

    /**
     * Getter for the move.
     *
     * @return the total number of moves
     */
    public int getMove() {
        return move;
    }

    /**
     * Getter for the time (long type)
     *
     * @return the long type time
     */
    public long getLong() {
        return time;
    }

    /**
     * Getter for the name.
     *
     * @return the name of the score
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for the user.
     *
     * @return the name of the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the time.
     *
     * @return the total time in Chronometer.
     */
    public String getTime() {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        return String.format("%s:%s:%s", h, m, s);
    }

    /**
     * Show each score in a string.
     * @return a string representation of the score
     */
    @Override
    public String toString() {
        return String.format("%5s %15s %18s %10s", getType(), getUser(), move, this.getTime());
    }

    /**
     * Compare two score.
     * @param other the other score.
     * @return negative if this score is higher than other, 0 if two score are the same, positive
     * if this score is lower than other.
     */
    @Override
    public abstract int compareTo(@NonNull Score other);

}
