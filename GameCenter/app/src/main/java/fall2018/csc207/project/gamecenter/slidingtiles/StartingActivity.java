package fall2018.csc207.project.gamecenter.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc207.project.gamecenter.GameCenterActivity;
import fall2018.csc207.project.gamecenter.LoginActivity;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.User;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * this user that is linked to the game
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        activateUser(intent);
        setContentView(R.layout.activity_starting_);
        addLoadButtonListener();
        addLevel3ButtonListener();
        addLevel4ButtonListener();
        addLevel5ButtonListener();
        addScoreBoardButtonListener();
        addLogOutButtonListener();
        addBackButtonListener();
        TextView usernameDisplay = findViewById(R.id.username_startingTV);
        usernameDisplay.setText(user.getUsername());
    }

    /**
     * Activate back button.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.backbtn_sT);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(StartingActivity.this, GameCenterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    /**
     * The 3*3 sliding tile game button.
     */
    private void addLevel3ButtonListener() {
        Button startButton = findViewById(R.id.level3);
        startButton.setOnClickListener(v -> {
            boardManager = new BoardManager(3, "3*3", user.getUsername());
            switchToGame();
        });
    }

    /**
     * The 4*4 sliding tile game button.
     */
    private void addLevel4ButtonListener() {
        Button startButton = findViewById(R.id.level4);
        startButton.setOnClickListener(v -> {
            boardManager = new BoardManager(4, "4*4", user.getUsername());
            switchToGame();
        });
    }

    /**
     * The 5*5 sliding tile game button.
     */
    private void addLevel5ButtonListener() {
        Button startButton = findViewById(R.id.level5);
        startButton.setOnClickListener(v -> {
            boardManager = new BoardManager(5, "5*5", user.getUsername());
            switchToGame();
        });
    }

    /**
     * Activate the user.
     * <p>
     * Adapted from: https://youtu.be/3YCkW7J9NP0
     * Allows passing objects among activities
     *
     * @param intent the user
     */
    private void activateUser(Intent intent) {
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");

    }

    /**
     * The logout button.
     */
    private void addLogOutButtonListener() {
        Button logout = findViewById(R.id.StartingAct_LogoutBtn);
        logout.setOnClickListener(view -> openLogin());
    }

    /**
     * Log out.
     */
    private void openLogin() {
        Intent OpLogin = new Intent(this, LoginActivity.class);
        startActivity(OpLogin);
    }

    /**
     * activate the scoreboard button that redirects to scoreboard menu
     */
    private void addScoreBoardButtonListener() {
        Button ScoreBoard = findViewById(R.id.scoreboardBtn);
        ScoreBoard.setOnClickListener(view -> openScoreBoard());
    }

    /**
     * activate button to scoreboard
     */
    private void openScoreBoard() {
        Intent OpScoreBoard = new Intent(this,
                SlidingTilesScoreBoardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        OpScoreBoard.putExtras(bundle);
        startActivity(OpScoreBoard);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(v -> {
            AlertDialog.Builder loadBuilder = new AlertDialog.Builder(
                    StartingActivity.this);
            View loadView = getLayoutInflater().inflate(R.layout.slidingtile_load_dialog,
                    null);
            Button load1 = loadView.findViewById(R.id.load_1);
            Button load2 = loadView.findViewById(R.id.load_2);
            Button load3 = loadView.findViewById(R.id.load_3);
            Button loadAuto = loadView.findViewById(R.id.Load_auto);
            loadBuilder.setView(loadView);
            final AlertDialog loadDialog = loadBuilder.create();
            load1.setOnClickListener(view -> {
                if (user.getGame("slidingtile", 1) != null) {
                    boardManager = (BoardManager) user.getGame("SlidingTile", 1);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            load2.setOnClickListener(view -> {
                if (user.getGame("slidingtile", 2) != null) {
                    boardManager = (BoardManager) user.getGame("slidingtile", 2);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            load3.setOnClickListener(view -> {
                if (user.getGame("slidingTile", 3) != null) {
                    boardManager = (BoardManager) user.getGame("slidingTile", 3);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            loadAuto.setOnClickListener(v1 -> {
                if (user.getGame("slidingTIle", 0) != null) {
                    boardManager = (BoardManager) user.getGame("slidingtile", 0);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            loadDialog.show();
        });
    }

    /**
     * Assert not save if the user click load game which isn't be saved.
     */
    private void toastNoSave() {
        Toast.makeText(StartingActivity.this, "there is no saved file",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        boardManager = (BoardManager) user.getGame("slidingTile", 0);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("board", boardManager);
        tmp.putExtras(bundle);
        startActivity(tmp);
    }
}
