package fall2018.csc207.project.gamecenter.game2048;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import fall2018.csc207.project.gamecenter.slidingtiles.GameActivity;

/**
 * The special GridView for the 2048 game.
 */
public class TZFEGestureDetectGridView extends GridView {
    public static final int SWIPE_MIN_DISTANCE = 100;
//    public static final int SWIPE_MAX_OFF_PATH = 100;
//    public static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private GestureDetectorCompat gDetector;
    private TZFEMovementController mController;
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;
    private TZFEBoardManager tzfeBoardManager;

    /**
     * The constructor of the 2048 game GridView
     *
     * @param context the context
     */
    public TZFEGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * The constructor of the 2048 game GridView
     *
     * @param context the context
     * @param attrs   the attribute set
     */
    public TZFEGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * The constructor of the 2048 game GridView
     *
     * @param context      the context
     * @param attrs        the attribute set
     * @param defStyleAttr the def style attribute
     */
    public TZFEGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public TZFEGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Initialize the controller and detector.
     *
     * @param context the context
     */
    private void init(final Context context) {
        mController = new TZFEMovementController();
        gDetector = new GestureDetectorCompat(context, new LearnGesture());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        this.gDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    /**
     * Set the boardManager
     *
     * @param boardManager the 2048 game boardManager
     */
    public void setBoardManager(TZFEBoardManager boardManager) {
        this.tzfeBoardManager = boardManager;
        mController.setBoardManager(boardManager);
    }

    /**
     * The Fling detector to find out which direction the user does.
     */
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            // move right
            float horizontal = event2.getX() - event1.getX();
            float vertical = event2.getY() - event1.getY();
            if (horizontal > 0 && horizontal > Math.abs(vertical)) {
                //move Right
                mController.processFlingMovement(getContext(), GameActivity.RIGHT, true);

            } else if (horizontal < 0 && Math.abs(horizontal) > Math.abs(vertical)) {
                //move left
                mController.processFlingMovement(getContext(), GameActivity.LEFT, true);

            } else if (vertical > 0 && vertical > Math.abs(horizontal)) {
                //move down
                mController.processFlingMovement(getContext(), GameActivity.DOWN, true);
            } else if (vertical < 0 && Math.abs(vertical) > Math.abs(horizontal)) {
                //move up
                mController.processFlingMovement(getContext(), GameActivity.UP, true);
            }
            return true;
        }
    }

}


