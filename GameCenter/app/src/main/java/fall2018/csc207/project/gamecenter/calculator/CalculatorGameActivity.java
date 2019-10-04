package fall2018.csc207.project.gamecenter.calculator;
/*
  This game is a imitation of an app store game called "Calculator: the game". all ideas come from
  that game but the coding are completely done by ourselves
 */

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc207.project.gamecenter.FileManagerAdapter.ScoreboardManagerFileAdapter;
import fall2018.csc207.project.gamecenter.FileManagerAdapter.UserManagerFileAdapter;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.ScoreBoardManager;
import fall2018.csc207.project.gamecenter.User;
import fall2018.csc207.project.gamecenter.UserManager;

/**
 * Activity class for calculator game.
 * <p>
 * This class is ***UNTESTABLE***
 * because all the methods are either related to
 * file saving and reading features, or are just buttons.
 */
public class CalculatorGameActivity extends AppCompatActivity implements Operation {

    /**
     * declare the instance variable User
     */
    private User user;

    /**
     * declare a calculatorManager
     */

    private CalculatorManager calcManager;
    /**
     * connect the textView with the available Moves left.
     */

    private TextView curMoveLeft;
    /**
     * connect the textView to the Current value
     */

    private TextView curValueView;
    /**
     * declare variable for timer function
     */
    private Chronometer timer;
    private boolean isTiming;
    private long timeElapsed;

    /**
     * declare a Usermanager for saving and loading functions
     */
    private UserManager userGroup = new UserManager();
    private UserManagerFileAdapter userManagerFileAdapter =
            UserManagerFileAdapter.getInstance();

    /**
     * initiate all the buttons and the values that requires displaying.
     *
     * @param savedInstanceState is required to start the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_game);
        activateUser();
        userManagerFileAdapter.setAdaptee(userGroup);
        userManagerFileAdapter.loadFromFile(
                this.getFilesDir().getPath()
                        + "/" + UserManager.USER_FILENAME);
        setInitialTime();
        curValueView = findViewById(R.id.CurCalcValue);
        curValueView.setText(String.valueOf(calcManager.getCurrNum()));
        TextView goalValue = findViewById(R.id.CalcGoal);
        goalValue.setText(String.valueOf(calcManager.getCalculator().getGoal()));
        curMoveLeft = findViewById(R.id.CalcMove);
        curMoveLeft.setText(String.valueOf(calcManager.getUsedMove()));
        addUndoButtonListener();
        addSaveGameButtonListener();
        addAppendNumButtonListener();
        addOperation1ButtonListener();
        addOperation2ButtonListener();
        addReverseButtonListener();
        addClearButtonListener();
        addHomeButtonListener();

    }

    /**
     * activate a save game button for user to save game
     */
    private void addSaveGameButtonListener() {
        Button saveBtn = findViewById(R.id.CalcSave);
        saveBtn.setOnClickListener(v -> {
            AlertDialog.Builder saveBuilder = new AlertDialog.Builder(CalculatorGameActivity.this);
            View saveView = getLayoutInflater().inflate(R.layout.calculator_save_dialog, null);
            saveBuilder.setView(saveView);
            Button save1 = saveView.findViewById(R.id.Calc_Save_1);
            Button save2 = saveView.findViewById(R.id.Calc_Save_2);
            Button save3 = saveView.findViewById(R.id.Calc_Save_3);
            AlertDialog saveDialog = saveBuilder.create();
            saveDialog.show();
            save1.setOnClickListener(v1 -> {
                save(1);
                saveDialog.dismiss();
            });
            save2.setOnClickListener(v12 -> {
                save(2);
                saveDialog.dismiss();
            });
            save3.setOnClickListener(v13 -> {
                save(3);
                saveDialog.dismiss();
            });
        });
    }

    /**
     * make toast that a game is saved
     */

    private void toastSaved() {
        Toast.makeText(CalculatorGameActivity.this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * activates a button for operation 1
     */
    private void addOperation1ButtonListener() {
        Button opr1 = findViewById(R.id.CalcOperation1);
        opr1.setText(calcManager.getCalculator().getOpr1());
        opr1.setOnClickListener(v -> {
            startChronometer();
            update("opr1");
        });
    }

    /**
     * activates a button far operation 2
     */
    private void addOperation2ButtonListener() {
        Button opr2 = findViewById(R.id.CalcOperation2);
        opr2.setText(calcManager.getCalculator().getOpr2());

        opr2.setOnClickListener(v -> {
            startChronometer();
            update("opr2");
        });

    }

    /**
     * activates the append number button
     */
    private void addAppendNumButtonListener() {
        Button appendNum = findViewById(R.id.CalcAppendNum);
        int appendDig = calcManager.getCalculator().getLastDigit();
        appendNum.setText(String.valueOf(appendDig));
        appendNum.setOnClickListener(v -> {
            startChronometer();
            update("append");
        });

    }

    /**
     * links the user to the calculator game
     */
    private void activateUser() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        calcManager = (CalculatorManager) intent.getSerializableExtra("calc");
    }

    /**
     * activates an undo button for the user
     */
    private void addUndoButtonListener() {
        Button undo = findViewById(R.id.CalcUndo);
        undo.setOnClickListener(v -> {
            undo();
            startChronometer();

        });
    }

    /**
     * activates the reverse button
     */
    private void addReverseButtonListener() {
        Button reverse = findViewById(R.id.CalcReverse);
        reverse.setOnClickListener(v -> update("reverse"));

    }


    /**
     * every time a button is pressed, cur moves and cur value are updated.
     */
    private void update(String str) {
        int curNum;
        curNum = calcManager.update(str);
        curValueView.setText(String.valueOf(curNum));
        //update moves left
        pauseChronometer();
        calcManager.setTimeElapsed(timeElapsed);
        startChronometer();
        curMoveLeft.setText(String.valueOf(calcManager.getUsedMove()));
        checkIfSolved();

    }

    /**
     * a helper method that updates the board upon pressing of the undo button
     */
    private void undo() {
        int curValue = Integer.parseInt(curValueView.getText().toString());
        if (curValue != calcManager.getPrev()) {
            calcManager.updateUsedMoves(-1);
            curValueView.setText(String.valueOf(calcManager.undo()));
            curMoveLeft.setText(String.valueOf(calcManager.getUsedMove()));
        }

    }

    /**
     * a method to check if the game is lost, and make toast if the game is lost
     */

    private void checkIfLost() {
        if (Integer.parseInt(curMoveLeft.getText().toString()) == 0) {
            Toast.makeText(this, "You Lost, resetting the game", Toast.LENGTH_SHORT).show();
            clearAll();
        }
    }

    /**
     * a method to check if a game is won, and if so, pass the score to scoreboard
     */
    private void checkIfSolved() {
        //check to see if game has been completed
        if (calcManager.isSolved()) {
            pauseChronometer();
            addToScoreBoard();
            AlertDialog.Builder winLog = new AlertDialog.Builder(this);
            View winView = getLayoutInflater().inflate(R.layout.calculator_win_dialog, null);
            Button back = winView.findViewById(R.id.CalcHomeMenu);
            winLog.setView(winView);
            final AlertDialog winDialog = winLog.create();
            winDialog.show();
            back.setOnClickListener(v -> {
                backToMenu();
                winDialog.dismiss();
            });
        } else {
            checkIfLost();
        }
    }

    /**
     * added the winning score to the scoreboard and update the score if needed
     */
    private void addToScoreBoard() {
        CalculatorScore score = new CalculatorScore(calcManager.getType(), user.getUsername(), calcManager.getUsedMove(), calcManager.getTimeElapsed());
        userManagerFileAdapter.setAdaptee(userGroup);
        userManagerFileAdapter.loadFromFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
        user.getPerUserScoreBoard().getScoreBoard(2).update(score);
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
        ScoreboardManagerFileAdapter calcScoreboardAdapter = new ScoreboardManagerFileAdapter();
        calcScoreboardAdapter.loadFromFile(this.getFilesDir().getPath() + "/"
                + CalculatorScoreBoardActivity.SAVE_SCOREBOARDCAL);
        ScoreBoardManager calcScoreBoardManager = calcScoreboardAdapter.getAdaptee();
        calcScoreBoardManager = updateScore(calcScoreBoardManager, score);
        calcScoreboardAdapter.setAdaptee(calcScoreBoardManager);
        calcScoreboardAdapter.saveToFile(this.getFilesDir().getPath() + "/"
                + CalculatorScoreBoardActivity.SAVE_SCOREBOARDCAL);
    }

    /**
     * update the score passed in to the Scoreboardmanager
     *
     * @param calcScoreBoardManager is the Scoreboardmaneger that holds all the global scoreboard
     * @param score                 is the score that need to updates
     * @return the updated scoreboardmanager with the score updated
     */
    private ScoreBoardManager updateScore(ScoreBoardManager calcScoreBoardManager, CalculatorScore score) {
        if (score.getType().equals(calcScoreBoardManager.getScoreBoard(0).getType())) {
            calcScoreBoardManager.getScoreBoard(0).update(score);
        } else if (score.getType().equals(calcScoreBoardManager.getScoreBoard(1).getType())) {
            calcScoreBoardManager.getScoreBoard(1).update(score);
        } else if (score.getType().equals(calcScoreBoardManager.getScoreBoard(2).getType())) {
            calcScoreBoardManager.getScoreBoard(2).update(score);
        }
        return calcScoreBoardManager;
    }

    /**
     * activate a back button
     */
    private void backToMenu() {
        save(UserManager.AUTO_SAVE);
        Intent intent = new Intent(CalculatorGameActivity.this,
                CalculatorStartingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     * set the initial time of the game
     */
    private void setInitialTime() {
        timer = findViewById(R.id.CalcTime);
        timer.setFormat("Time: %s");
        timer.setBase(SystemClock.elapsedRealtime());
        timeElapsed = calcManager.getTimeElapsed();
    }

    /**
     * start the chronometer
     */
    private void startChronometer() {
        if (!isTiming) {
            timer.setBase(SystemClock.elapsedRealtime() - timeElapsed);
            timer.start();
            isTiming = true;
        }
    }

    /**
     * pause the chronometer
     */
    private void pauseChronometer() {
        if (isTiming) {
            timer.stop();
            timeElapsed = SystemClock.elapsedRealtime() - timer.getBase();
            isTiming = false;
        }
    }

    /**
     * add a button to return to home
     */
    private void addHomeButtonListener() {
        Button back = findViewById(R.id.CalcHome);
        back.setOnClickListener(v -> backToMenu());
    }

    /**
     * add a button to reset game
     */
    private void addClearButtonListener() {
        Button clear = findViewById(R.id.CalcClear);
        clear.setOnClickListener(v -> clearAll());
    }

    /**
     * add a method to reset the game including move time and cur value displayed
     */
    private void clearAll() {
        TextView MoveView = findViewById(R.id.CalcMove);
        calcManager.reset();
        curValueView.setText(String.valueOf(calcManager.getCurrNum()));
        MoveView.setText(String.valueOf(calcManager.getUsedMove()));
        setInitialTime();
        timeElapsed = 0;
    }

    /**
     * autosave the game upon destroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        save(UserManager.AUTO_SAVE);
    }

    /**
     * auto save the game upon back pressed
     */
    @Override
    public void onBackPressed() {
        save(UserManager.AUTO_SAVE);
        backToMenu();
    }

    /**
     * save the game according to the position in user's list of games
     *
     * @param position is the list position in which a game is saved
     */
    private void save(int position) {
        pauseChronometer();
        calcManager.setTimeElapsed(timeElapsed);
        user.saveGame(calcManager, position);
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath()
                        + "/" + UserManager.USER_FILENAME);
        toastSaved();
    }

}
