package fall2018.csc207.project.gamecenter.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.User;

/**
 * The activity for showing win page if the user lose the game.
 */
public class TZFELoseActivity extends AppCompatActivity {

    /**
     * The user.
     */
    private User user;

    /**
     * The complexity of the game.
     */
    private int complexity;

    /**
     * The type of the game.
     */
    private String type;

    /**
     * The boardManager of the 2048 game.
     */
    private TZFEBoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_lose);
        complexity = getIntent().getIntExtra("complexity", 0);
        type = getIntent().getStringExtra("type");
        user = (User) getIntent().getSerializableExtra("user");

        addBackButtonListener();
        addScoreBoardButtonListener();
        addTryAgainButtonListener();
    }

    /**
     * Activate the try again button.
     */
    private void addTryAgainButtonListener() {
        Button tryAgain = findViewById(R.id.try_2048);
        tryAgain.setOnClickListener(view -> {
            boardManager = new TZFEBoardManager(complexity, type, user.getUsername());
            switchToGame();
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, TZFEGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("board", boardManager);
        tmp.putExtras(bundle);
        startActivity(tmp);
    }

    /**
     * Activate the ScoreBoard button.
     */
    private void addScoreBoardButtonListener() {
        Button scoreBoard = findViewById(R.id.toboard_2048_2);
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
        Button back = findViewById(R.id.back_2048_2);
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

}


