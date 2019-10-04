package fall2018.csc207.project.gamecenter.game2048;

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

import fall2018.csc207.project.gamecenter.slidingtiles.CustomAdapter;
import fall2018.csc207.project.gamecenter.FileManagerAdapter.UserManagerFileAdapter;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.User;
import fall2018.csc207.project.gamecenter.UserManager;

/**
 * The 2048 game activity.
 * This class is ***UNTESTABLE***
 * because all the methods either involve
 * file saving and writing features, or are just buttons.
 */
public class TZFEGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private TZFEBoardManager tzfeBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private TZFEGestureDetectGridView gridView;
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
    private UserManager userGroup = new UserManager();
    private User user;
    private UserManagerFileAdapter userManagerFileAdapter = UserManagerFileAdapter.getInstance();

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
        userManagerFileAdapter.setAdaptee(userGroup);
        userManagerFileAdapter.loadFromFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
        activateSetting(getIntent());
        createTileButtons(this);
        setContentView(R.layout.activity_2048_game);
        addSaveButtonListener();
        // initiate time and move
        setInitialTime();
        setInitialMove();
        addBackButtonListener();
        // Add View to activity
        setGameView();
    }

    /**
     * Initialize the game view.
     */
    private void setGameView() {
        gridView = findViewById(R.id.grid_2048);
        gridView.setNumColumns(TZFEBoard.SIZE);
        gridView.setBoardManager(tzfeBoardManager);
        tzfeBoardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / TZFEBoard.SIZE;
                        columnHeight = displayHeight / TZFEBoard.SIZE;

                        display();
                    }
                });
    }

    /**
     * Activate Back Button which backs to the Game Center.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.backToStart_2048);
        back.setOnClickListener(view -> {
            Intent opGame = new Intent(TZFEGameActivity.this,
                    TZFEStartingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            opGame.putExtras(bundle);
            save(UserManager.AUTO_SAVE);
            startActivity(opGame);
        });
    }

    /**
     * pass the updated user back to starting activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TZFEGameActivity.this, TZFEStartingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        save(UserManager.AUTO_SAVE);
        startActivity(intent);
    }

    /**
     * Operations to be performed when the app is shut down
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        save(UserManager.AUTO_SAVE);
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.saveBtn_2048);
        saveButton.setOnClickListener(v -> {
            AlertDialog.Builder SavesDialog = new AlertDialog.Builder(
                    TZFEGameActivity.this);
            View save_view = getLayoutInflater().inflate(R.layout.game2048_save_dialog,
                    null);
            Button save1 = save_view.findViewById(R.id.Save_1_2048);
            Button save2 = save_view.findViewById(R.id.Save_2_2048);
            Button save3 = save_view.findViewById(R.id.Save_3_2048);
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
     * Receive user and tzfeBoardManager from other activity
     *
     * @param intent the receiving information from other activity.
     */
    private void activateSetting(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
            tzfeBoardManager = (TZFEBoardManager) bundle.getSerializable("board");
        }
    }

    /**
     * this method initiates the move counter on the UI
     */
    private void setInitialMove() {
        //set the initial number of move
        moveTV = findViewById(R.id.move_2048);
        move = tzfeBoardManager.getMove();
        moveTV.setText(String.format("Move: %s", String.valueOf(move)));
    }

    /**
     * this method initiates the time counter on the UI
     */
    private void setInitialTime() {
        // Set the initial time
        time = findViewById(R.id.chronometer_2048);
        time.setFormat("Time: %s");
        time.setBase(SystemClock.elapsedRealtime());
        timeElapsed = tzfeBoardManager.getTimeElapsed();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        TZFEBoard tzfeBoard = tzfeBoardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != TZFEBoard.SIZE; row++) {
            for (int col = 0; col != TZFEBoard.SIZE; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(tzfeBoard.getTZFETile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        TZFEBoard tzfeBoard = tzfeBoardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / TZFEBoard.SIZE;
            int col = nextPos % TZFEBoard.SIZE;
            b.setBackgroundResource(tzfeBoard.getTZFETile(row, col).getBackground());
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
        tzfeBoardManager.setTimeElapsed(timeElapsed);
        tzfeBoardManager.setMove(move);
    }


    @Override
    public void update(Observable o, Object arg) {
        display();
        startChronometer();
        move = tzfeBoardManager.getMove();
        moveTV.setText(String.format("Move: %s", String.valueOf(move)));
        pauseChronometer();
        tzfeBoardManager.setTimeElapsed(timeElapsed);
        if (tzfeBoardManager.isWin()) {
            TZFEScore score = new TZFEScore(tzfeBoardManager.getType(),
                    tzfeBoardManager.getUser(), tzfeBoardManager.getMove(),
                    tzfeBoardManager.getTimeElapsed());
            Intent win = new Intent(this, TZFEWinActivity.class);
            win.putExtra("Score", score);
            win.putExtra("user", user);
            startActivity(win);
        } else if (tzfeBoardManager.isLose()) {
            Intent lose = new Intent(this, TZFELoseActivity.class);
            lose.putExtra("complexity", tzfeBoardManager.getComplexity());
            lose.putExtra("type", tzfeBoardManager.getType());
            lose.putExtra("user", user);
            startActivity(lose);
        } else {
            startChronometer();
        }
            user.saveGame(tzfeBoardManager, UserManager.AUTO_SAVE);
            userGroup.updateUser(user);
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
     * Save the game.
     * @param position the index of the save game.
     */
    private void save(int position) {
        pauseChronometer();
        tzfeBoardManager.setTimeElapsed(timeElapsed);
        user.saveGame(tzfeBoardManager, position);
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath()
                        + "/" + UserManager.USER_FILENAME);
        makeToastSavedText();
    }
}
