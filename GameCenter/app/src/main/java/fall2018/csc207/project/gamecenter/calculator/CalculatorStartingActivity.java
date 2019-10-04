package fall2018.csc207.project.gamecenter.calculator;

/*
This Activity cannot be tested because it is composed of buttons and other method associated with viewing and android app functionalities.
Thus this activity will be excluded from code coverage testing.
 */

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc207.project.gamecenter.GameCenterActivity;
import fall2018.csc207.project.gamecenter.LoginActivity;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.User;

/**
 * The calculator game activity for choosing game complexity.
 */
public class CalculatorStartingActivity extends AppCompatActivity {

    /**
     * the user that is associated with the starting activity
     */
    private User user;

    /**
     * the calculatormaneger need to start the game
     */
    CalculatorManager calcManager;

    /**
     * activates all the button and set user name
     *
     * @param savedInstanceState need eto start the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_starting);
        addEasyButtonListener();
        addHardButtonListener();
        addLogoutButtonListener();
        addNightmareButtonListener();
        addLoadButtonListener();
        activateUser();
        addGameCenterButton();
        addScoreboardButton();
        TextView username = findViewById(R.id.CalculatorUsername);
        username.setText(user.getUsername());

    }

    /**
     * initiate a scoreboard button
     */
    private void addScoreboardButton() {
        Button ScoreBoard = findViewById(R.id.CalculatorScoreBoard);
        ScoreBoard.setOnClickListener(view -> {
            Intent opScoreBoard = new Intent(CalculatorStartingActivity.this,
                    CalculatorScoreBoardActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            opScoreBoard.putExtras(bundle);
            startActivity(opScoreBoard);
        });
    }

    /**
     * this method activates the game center button that returns to the game center page
     * upon clicking
     */
    private void addGameCenterButton() {
        Button gameCenter = findViewById(R.id.CalculatorGameCenter);
        gameCenter.setOnClickListener(v -> {
            Intent intent = new Intent(CalculatorStartingActivity.this, GameCenterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    /**
     * this method links the user upon creation of this activity
     */
    private void activateUser() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

    }

    /**
     * added a button to load saved games
     */
    private void addLoadButtonListener() {
        Button loadBtn = findViewById(R.id.CalculatorLoadGame);
        loadBtn.setOnClickListener(v -> {
            AlertDialog.Builder loadBuilder = new AlertDialog.Builder(CalculatorStartingActivity.this);
            View loadView = getLayoutInflater().inflate(R.layout.calculator_load_dialog, null);
            loadBuilder.setView(loadView);
            Button loadAuto = loadView.findViewById(R.id.Calc_Load_auto);
            Button load1 = loadView.findViewById(R.id.Calc_load_1);
            Button load2 = loadView.findViewById(R.id.Calc_load_2);
            Button load3 = loadView.findViewById(R.id.Calc_load_3);
            AlertDialog loadDialog = loadBuilder.create();
            loadDialog.show();
            loadAuto.setOnClickListener(v14 -> {
                if (user.getGame("calculator", 0) != null) {
                    calcManager = (CalculatorManager) user.getGame("calculator", 0);
                    toastLoaded();
                    switchToGame();
                } else {
                    toastNoSaveGame();
                }
            });
            load1.setOnClickListener(v13 -> {
                if (user.getGame("calculator", 1) != null) {
                    calcManager = (CalculatorManager) user.getGame("calculator", 1);
                    toastLoaded();
                    switchToGame();
                } else {
                    toastNoSaveGame();
                }
            });
            load2.setOnClickListener(v12 -> {
                if (user.getGame("calculator", 2) != null) {
                    calcManager = (CalculatorManager) user.getGame("calculator", 2);
                    toastLoaded();
                    switchToGame();
                } else {
                    toastNoSaveGame();
                }
            });

            load3.setOnClickListener(v1 -> {
                if (user.getGame("calculator", 3) != null) {
                    calcManager = (CalculatorManager) user.getGame("calculator", 3);
                    toastLoaded();
                    switchToGame();
                } else {
                    toastNoSaveGame();
                }
            });

        });

    }

    /**
     * toast message that there is no saved game
     */
    private void toastNoSaveGame() {
        Toast.makeText(CalculatorStartingActivity.this, "No Save Found", Toast.LENGTH_SHORT).show();
    }

    /**
     * toast message that the game has been loaded
     */
    private void toastLoaded() {
        Toast.makeText(CalculatorStartingActivity.this, "Game Loaded", Toast.LENGTH_SHORT).show();
    }

    /**
     * return to the login page once clicked
     */
    private void addLogoutButtonListener() {
        Button logout = findViewById(R.id.CalculatorLogout);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(CalculatorStartingActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * this method switches to a nightmare difficulty game
     */
    private void addNightmareButtonListener() {
        Button nightmare = findViewById(R.id.CalculatorNightmare);
        nightmare.setOnClickListener(v -> {
            calcManager = new CalculatorManager("nightmare");
            switchToGame();
        });
    }

    /**
     * this starts the game in hard level
     */
    private void addHardButtonListener() {
        Button hard = findViewById(R.id.CalculatorHard);
        hard.setOnClickListener(v -> {
            calcManager = new CalculatorManager("hard");
            switchToGame();

        });
    }

    /**
     * this starts a easy level game
     */
    private void addEasyButtonListener() {
        Button easy = findViewById(R.id.CalculatorEasy);
        easy.setOnClickListener(v -> {
            calcManager = new CalculatorManager("easy");
            switchToGame();

        });
    }

    /**
     * this activates the game activity
     */
    private void switchToGame() {
        Intent intent = new Intent(this, CalculatorGameActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("calc", calcManager);
        startActivity(intent);
    }

}
