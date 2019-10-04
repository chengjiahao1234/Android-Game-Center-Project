package fall2018.csc207.project.gamecenter.game2048;

import android.content.Context;
import android.widget.Toast;

/**
 * Keep track the 2048 game and control the movement.
 */
public class TZFEMovementController {

    /**
     * The boardManager.
     */
    private TZFEBoardManager boardManager = null;

    public TZFEMovementController() {
    }

    /**
     * Setter for the boardManager
     *
     * @param boardManager the boardManager
     */
    public void setBoardManager(TZFEBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process the 2048 game.
     *
     * @param context   the context
     * @param direction the position that need to move
     * @param display   whether it will display
     */
    public void processFlingMovement(Context context, int direction, boolean display) {
        boardManager.touchMove(direction);
        if (boardManager.isWin()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        } else if (boardManager.isLose()) {
            Toast.makeText(context, "YOU LOSE!", Toast.LENGTH_SHORT).show();
        }

    }
}

