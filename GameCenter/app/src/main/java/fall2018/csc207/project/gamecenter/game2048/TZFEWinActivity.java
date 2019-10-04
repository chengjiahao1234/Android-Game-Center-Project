package fall2018.csc207.project.gamecenter.game2048;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc207.project.gamecenter.FileManagerAdapter.ScoreboardManagerFileAdapter;
import fall2018.csc207.project.gamecenter.FileManagerAdapter.UserManagerFileAdapter;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.ScoreBoard;
import fall2018.csc207.project.gamecenter.ScoreBoardManager;
import fall2018.csc207.project.gamecenter.User;
import fall2018.csc207.project.gamecenter.UserManager;

/**
 * The activity for showing win page if the user win the game.
 */
public class TZFEWinActivity extends AppCompatActivity {

    /**
     * Four score boards.
     */
    private ScoreBoard scoreBoard3;
    private ScoreBoard scoreBoard4;
    private ScoreBoard scoreBoard5;

    /**
     * The scoreboard manager for per-user and per-game scoreboards.
     */
    private ScoreBoardManager tzfeScoreBoards = new ScoreBoardManager(3);
    private ScoreBoardManager perUserScoreBoard = new ScoreBoardManager(3);
    private ScoreboardManagerFileAdapter scoreboardManagerFileAdapter =
            new ScoreboardManagerFileAdapter();

    /**
     * The user.
     */
    private User user;
    private UserManager userGroup = new UserManager();
    private UserManagerFileAdapter userManagerFileAdapter =
            UserManagerFileAdapter.getInstance();

    /**
     * The score that will show.
     */
    private TZFEScore score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_win);
        score = (TZFEScore) getIntent().getSerializableExtra("Score");
        user = (User) getIntent().getSerializableExtra("user");
        userManagerFileAdapter.setAdaptee(userGroup);
        userManagerFileAdapter.loadFromFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
        update(score);
        display();

        addBackButtonListener();
        addScoreBoardButtonListener();
    }

    /**
     * Activate the ScoreBoard button.
     */
    private void addScoreBoardButtonListener() {
        Button scoreBoard = findViewById(R.id.toboard_2048);
        scoreBoard.setOnClickListener(view -> openScoreBoard());
    }

    /**
     * Activate the ScoreBoard button.
     */
    private void openScoreBoard() {
        Intent opScoreBoard = new Intent(this,
                TZFEScoreBoardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        opScoreBoard.putExtras(bundle);
        startActivity(opScoreBoard);
    }

    /**
     * Activate the Back button.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.back_2048);
        back.setOnClickListener(view -> openGameCenter());
    }

    /**
     * Activate the Back button
     */
    private void openGameCenter() {
        Intent opGame = new Intent(this, TZFEStartingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        opGame.putExtras(bundle);
        startActivity(opGame);
    }

    /**
     * Display the score.
     */
    private void display() {
        TextView scoreView = findViewById(R.id.score_2048);
        scoreView.setText(String.format("Move: %s Time: %s", score.getMove(), score.getTime()));
    }

    /**
     * Display the "New record!" string if the user reach the new record.
     */
    @SuppressLint("SetTextI18n")
    private void newRecord() {
        TextView record = findViewById(R.id.record_2048);
        record.setText("NEW RECORD!");
    }

    /**
     * Load all score boards from files.
     */
    private void loadFromFiles() {
        scoreboardManagerFileAdapter.loadFromFile(this.getFilesDir().getPath()
                + "/"
                + TZFEScoreBoardActivity.SAVE_SCOREBOARDTZFE);
        tzfeScoreBoards = scoreboardManagerFileAdapter.getAdaptee();
        scoreBoard3 = tzfeScoreBoards.getScoreBoard(0);
        scoreBoard4 = tzfeScoreBoards.getScoreBoard(1);
        scoreBoard5 = tzfeScoreBoards.getScoreBoard(2);
        perUserScoreBoard = user.getPerUserScoreBoard();
    }

    /**
     * Update the score board.
     *
     * @param score the score of the game.
     */
    private void update(TZFEScore score) {
        loadFromFiles();
        perUserScoreBoard.getScoreBoard(1).update(score);
        if (score.getType().equals(scoreBoard3.getType())) {
            scoreBoard3.update(score);
            if (scoreBoard3.getScore(0) == score) newRecord();
            tzfeScoreBoards.setScoreBoard(scoreBoard3, 0);
        } else if (score.getType().equals(scoreBoard4.getType())) {
            scoreBoard4.update(score);
            if (scoreBoard4.getScore(0) == score) newRecord();
            tzfeScoreBoards.setScoreBoard(scoreBoard4, 1);
        } else if (score.getType().equals(scoreBoard5.getType())) {
            scoreBoard5.update(score);
            if (scoreBoard5.getScore(0) == score) newRecord();
            tzfeScoreBoards.setScoreBoard(scoreBoard5, 2);
        }
        saveToFiles();
    }

    /**
     * Save the updated scoreboards to their corresponding files.
     */
    private void saveToFiles() {
        scoreboardManagerFileAdapter.setAdaptee(tzfeScoreBoards);
        scoreboardManagerFileAdapter.saveToFile(this.getFilesDir().getPath()
                + "/"
                + TZFEScoreBoardActivity.SAVE_SCOREBOARDTZFE);
        userGroup.updateUser(user);
        userManagerFileAdapter.saveToFile(
                this.getFilesDir().getPath() + "/" + UserManager.USER_FILENAME);
    }

}
