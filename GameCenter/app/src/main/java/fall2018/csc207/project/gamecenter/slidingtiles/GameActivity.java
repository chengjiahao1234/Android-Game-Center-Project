package fall2018.csc207.project.gamecenter.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.project.gamecenter.FileManagerAdapter.UserManagerFileAdapter;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.User;
import fall2018.csc207.project.gamecenter.UserManager;

/**
 * Sliding tile game activity.
 * This class is ***UNTESTABLE***
 * because all the methods either
 * involve file saving writing and reading features,
 * or are just buttons.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    // Set the time for a game.
    private Chronometer time;
    private boolean isRunning;
    private long timeElapsed;

    // The number of move for a game
    private int move;
    private TextView moveTV;

    /**
     * Current user
     */
    private User user;

    /**
     * For some reason IDE says it is not assigned
     * But it will be assigned in adapter
     */
    private UserManager userGroup = new UserManager();
    private UserManagerFileAdapter userManagerFileAdapter =
            UserManagerFileAdapter.getInstance();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activateSetting(getIntent());
        userManagerFileAdapter.setAdaptee(userGroup);
        userManagerFileAdapter.loadFromFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addSaveButtonListener();
        addUndoButtonListener();
        // initiate time and move
        setInitialTime();
        setInitialMove();
        // Add View to activity
        int size = boardManager.getSize();
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(size);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getSize();
                        columnHeight = displayHeight / boardManager.getSize();

                        display();
                    }
                });
    }

    /**
     * pass the updated user back to starting activity
     */
    @Override
    public void onBackPressed() {
        user.saveGame(boardManager, 0); // Auto-save
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
        Intent intent = new Intent(GameActivity.this, StartingActivity.class);
        pauseChronometer();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Operations to be performed when the app is shut down
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pauseChronometer();
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(v -> {
            AlertDialog.Builder SavesDialog = new AlertDialog.Builder(
                    GameActivity.this);
            View save_view = getLayoutInflater().inflate(R.layout.slidingtile_save_dialog,
                    null);
            Button save1 = save_view.findViewById(R.id.Save_1);
            Button save2 = save_view.findViewById(R.id.Save_2);
            Button save3 = save_view.findViewById(R.id.Save_3);
            SavesDialog.setView(save_view);
            final AlertDialog save_pop = SavesDialog.create();
            save1.setOnClickListener(view -> {
                save(1);
                save_pop.dismiss();
            });
            save2.setOnClickListener(view -> {
                save(2);
                save_pop.dismiss();
            });
            save3.setOnClickListener(view -> {
                save(3);
                save_pop.dismiss();
            });
            save_pop.show();
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Get boardManager from the other activity.
     *
     * @param intent    User and BoardManager objects passed in from other activities
     */
    private void activateSetting(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
            boardManager = (BoardManager) bundle.getSerializable("board");
        }
    }

    /**
     * this method initiates the move counter on the UI
     */
    private void setInitialMove() {
        //set the initial number of move
        moveTV = findViewById(R.id.move);
        move = boardManager.getMove();
        moveTV.setText(String.format("Move: %s", String.valueOf(move)));
    }

    /**
     * this method initiates the time counter on the UI
     */
    private void setInitialTime() {
        // Set the initial time
        time = findViewById(R.id.chronometer);
        time.setFormat("Time: %s");
        time.setBase(SystemClock.elapsedRealtime());
        timeElapsed = boardManager.getTimeElapsed();
    }

    /**
     * activates undo button
     */
    private void addUndoButtonListener() {
        Button undo = findViewById(R.id.undo);
        undo.setOnClickListener(view -> undo());
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getSize(); row++) {
            for (int col = 0; col != boardManager.getSize(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getSize();
            int col = nextPos % boardManager.getSize();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        startChronometer();
        save(UserManager.AUTO_SAVE);
    }

    /**
     * Resume to the previous move.
     */
    public void undo() {
        boardManager.getPrev();
        move = boardManager.getMove();
        moveTV.setText(String.format("Move: %s", String.valueOf(move)));
    }


    @Override
    public void update(Observable o, Object arg) {
        display();
        startChronometer();
        moveTV.setText(String.format("Move: %s", String.valueOf(++move)));
        pauseChronometer();
        boardManager.setTimeElapsed(timeElapsed);
        boardManager.setMove(move);
        if (boardManager.puzzleSolved()) {
            passScoreToWin();
        } else {
            startChronometer();
        }
        user.saveGame(boardManager, UserManager.AUTO_SAVE);
    }

    /**
     * Generate the score and pass it to Win Activity
     */
    private void passScoreToWin() {
        SlidingTileScore score = new SlidingTileScore(boardManager.getType(), boardManager.getUser(),
                boardManager.getMove(), boardManager.getTimeElapsed());
        Intent win = new Intent(this, SlidingTileWinActivity.class);
        win.putExtra("Score", score);
        win.putExtra("user", user);
        startActivity(win);
    }

    /**
     * Start/Continue counting time.
     */
    private void startChronometer() {
        if (!isRunning) {
            time.setBase(SystemClock.elapsedRealtime() - timeElapsed);
            time.start();
            isRunning = true;
        }
    }

    /**
     * Stop counting time.
     */
    private void pauseChronometer() {
        if (isRunning) {
            time.stop();
            timeElapsed = SystemClock.elapsedRealtime() - time.getBase();
            isRunning = false;
        }
    }

    /**
     * Save the game to the user manager.
     * @param position
     */
    private void save(int position) {
        pauseChronometer();
        boardManager.setTimeElapsed(timeElapsed);
        user.saveGame(boardManager, position);
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath()
                        + "/" + UserManager.USER_FILENAME);
        makeToastSavedText();
    }
}
