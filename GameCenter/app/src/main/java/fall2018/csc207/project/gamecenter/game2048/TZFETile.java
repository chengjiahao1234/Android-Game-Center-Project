package fall2018.csc207.project.gamecenter.game2048;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc207.project.gamecenter.R;

/**
 * The tile for 2048 game.
 */
public class TZFETile implements Comparable<TZFETile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the background ID
     */
    public TZFETile(int backgroundId) {
        id = backgroundId + 1;
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.t_2;
                break;
            case 2:
                background = R.drawable.t_4;
                break;
            case 3:
                background = R.drawable.t_8;
                break;
            case 4:
                background = R.drawable.t_16;
                break;
            case 5:
                background = R.drawable.t_32;
                break;
            case 6:
                background = R.drawable.t_64;
                break;
            case 7:
                background = R.drawable.t_128;
                break;
            case 8:
                background = R.drawable.t_256;
                break;
            case 9:
                background = R.drawable.t_512;
                break;
            case 10:
                background = R.drawable.t_1024;
                break;
            case 11:
                background = R.drawable.t_2048;
                break;
            case 12:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
                break;
        }
    }

    @Override
    public int compareTo(@NonNull TZFETile ab) {
        return ab.id - this.id;
    }
}
