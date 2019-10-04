package fall2018.csc207.project.gamecenter.slidingtiles;

import android.content.Context;
import android.widget.Toast;

/**
 * Keep track the sliding tile game and control the movement.
 */
public class MovementController {

    /**
     * The boardManager.
     */
    private BoardManager boardManager = null;

    /**
     * Constructor the
     */
    public MovementController() {
    }

    /**
     * Setter for the boardManager
     *
     * @param boardManager the boardManager
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process the sliding tile game.
     *
     * @param context  the context
     * @param position the position that need to move
     * @param display  whether it will display
     */
    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
