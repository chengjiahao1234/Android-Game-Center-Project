package fall2018.csc207.project.gamecenter.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc207.project.gamecenter.GameCenterActivity;
import fall2018.csc207.project.gamecenter.LoginActivity;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.User;

/**
 * The initial activity for the 2048 game.
 */
public class TZFEStartingActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    private TZFEBoardManager boardManager;

    /**
     * this user that is linked to the game
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        activateUser(intent);
        setContentView(R.layout.activity_2048_starting);
        addLoadButtonListener();
        addLevel3ButtonListener();
        addLevel4ButtonListener();
        addLevel5ButtonListener();
        addBackButtonListener();
        addScoreBoardButtonListener();
        addLogOutButtonListener();
        TextView usernameDisplay = findViewById(R.id.username_startingTV_2048);
        usernameDisplay.setText(user.getUsername());
    }

    /**
     * Activate back button.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.backToCenter_2048);
        back.setOnClickListener(view -> {
            Intent opGame = new Intent(TZFEStartingActivity.this,
                    GameCenterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            opGame.putExtras(bundle);
            startActivity(opGame);
        });
    }

    /**
     * The easy 2048 game button.
     */
    private void addLevel3ButtonListener() {
        Button startButton = findViewById(R.id.level3_2048);
        startButton.setOnClickListener(v -> {
            boardManager = new TZFEBoardManager(0, "Easy", user.getUsername());
            switchToGame();
        });
    }

    /**
     * The hard 2048 game button.
     */
    private void addLevel4ButtonListener() {
        Button startButton = findViewById(R.id.level4_2048);
        startButton.setOnClickListener(v -> {
            boardManager = new TZFEBoardManager(1, "Hard", user.getUsername());
            switchToGame();
        });
    }

    /**
     * The nightmare 2048 game button.
     */
    private void addLevel5ButtonListener() {
        Button startButton = findViewById(R.id.level5_2048);
        startButton.setOnClickListener(v -> {
            boardManager = new TZFEBoardManager(2, "Nightmare",
                    user.getUsername());
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
        Button logout = findViewById(R.id.StartingAct_LogoutBtn_2048);
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
        Button ScoreBoard = findViewById(R.id.scoreboardBtn_2048);
        ScoreBoard.setOnClickListener(view -> openScoreBoard());
    }

    /**
     * activate button to scoreboard
     */
    private void openScoreBoard() {
        Intent OpScoreBoard = new Intent(this,
                TZFEScoreBoardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        OpScoreBoard.putExtras(bundle);
        startActivity(OpScoreBoard);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton_2048);
        loadButton.setOnClickListener(v -> {
            AlertDialog.Builder loadBuilder = new AlertDialog.Builder(
                    TZFEStartingActivity.this);
            View loadView = getLayoutInflater().inflate(R.layout.game2048_load_dialog,
                    null);
            Button load1 = loadView.findViewById(R.id.load_1_2048);
            Button load2 = loadView.findViewById(R.id.load_2_2048);
            Button load3 = loadView.findViewById(R.id.load_3_2048);
            Button loadAuto = loadView.findViewById(R.id.Load_auto_2048);
            loadBuilder.setView(loadView);
            final AlertDialog loadDialog = loadBuilder.create();
            load1.setOnClickListener(view -> {
                if (user.getGame("tzfe", 1) != null) {
                    boardManager = (TZFEBoardManager) user.getGame("tzfe", 1);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            load2.setOnClickListener(view -> {
                if (user.getGame("tzfe", 2) != null) {
                    boardManager = (TZFEBoardManager) user.getGame("tzfe", 2);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            load3.setOnClickListener(view -> {
                if (user.getGame("tzfe", 3) != null) {
                    boardManager = (TZFEBoardManager) user.getGame("tzfe", 3);
                    makeToastLoadedText();
                    switchToGame();
                    loadDialog.dismiss();
                } else {
                    toastNoSave();
                }
            });
            loadAuto.setOnClickListener(v1 -> {
                if (user.getGame("tzfe", 0) != null) {
                    boardManager = (TZFEBoardManager) user.getGame("tzfe", 0);
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
        Toast.makeText(TZFEStartingActivity.this, "there is no saved file",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
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
}
