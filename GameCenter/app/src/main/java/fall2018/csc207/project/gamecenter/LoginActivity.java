package fall2018.csc207.project.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fall2018.csc207.project.gamecenter.FileManagerAdapter.ScoreboardManagerFileAdapter;
import fall2018.csc207.project.gamecenter.FileManagerAdapter.UserManagerFileAdapter;
import fall2018.csc207.project.gamecenter.calculator.CalculatorScoreBoardActivity;
import fall2018.csc207.project.gamecenter.game2048.TZFEScoreBoardActivity;
import fall2018.csc207.project.gamecenter.slidingtiles.SlidingTilesScoreBoardActivity;

/**
 * The sign in and sign up activity,
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * The editTest for username and password.
     */
    private EditText userNameEditText;
    private EditText passwordEditText;

    /**
     * Construct and load user and scoreboard.
     */
    private UserManager userGroup = new UserManager();
    private ScoreBoardManager slidingTileScoreBoards =
            new ScoreBoardManager(3);
    private ScoreBoardManager tzfeScoreBoards = new ScoreBoardManager(3);
    private ScoreBoardManager calculatorScoreBoards = new ScoreBoardManager(3);

    /**
     * Construct the user and scoreboard file adapter for loading and saving.
     */
    private UserManagerFileAdapter userManagerFileAdapter = UserManagerFileAdapter.getInstance();

    private ScoreboardManagerFileAdapter slidingTileScoreboardManagerFileAdapter
            = new ScoreboardManagerFileAdapter(slidingTileScoreBoards);
    private ScoreboardManagerFileAdapter tzfeScoreboardManagerFileAdapter =
            new ScoreboardManagerFileAdapter(tzfeScoreBoards);
    private ScoreboardManagerFileAdapter calculatorScoreboardManagerFileAdapter =
            new ScoreboardManagerFileAdapter(calculatorScoreBoards);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addLoginButtonListener();
        addSignUpListener();
        userManagerFileAdapter.setAdaptee(userGroup);
        userManagerFileAdapter.loadFromFile(this.getFilesDir().getPath()
                + "/"
                + UserManager.USER_FILENAME);
        loadScoreboards();
        userNameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.Password);
    }

    /**
     * Load per-game score boards.
     */
    private void loadScoreboards() {
        slidingTileScoreboardManagerFileAdapter.loadFromFile(this.getFilesDir().getPath()
                + "/"
                + SlidingTilesScoreBoardActivity.SAVE_SCOREBOARDST);

        tzfeScoreboardManagerFileAdapter.loadFromFile(this.getFilesDir().getPath()
                + "/"
                + TZFEScoreBoardActivity.SAVE_SCOREBOARDTZFE);
        calculatorScoreboardManagerFileAdapter.loadFromFile(this.getFilesDir().getPath()
                + "/"
                + CalculatorScoreBoardActivity.SAVE_SCOREBOARDCAL);

        if (slidingTileScoreBoards.getScoreBoard(0) == null) {
            saveToFiles(slidingTileScoreBoards,
                    slidingTileScoreboardManagerFileAdapter,
                    SlidingTilesScoreBoardActivity.SAVE_SCOREBOARDST);
        }

        if (tzfeScoreBoards.getScoreBoard(0) == null) {
            saveToFiles(tzfeScoreBoards,
                    tzfeScoreboardManagerFileAdapter,
                    TZFEScoreBoardActivity.SAVE_SCOREBOARDTZFE);
        }

        if (calculatorScoreBoards.getScoreBoard(0) == null) {
            saveToFiles(calculatorScoreBoards,
                    calculatorScoreboardManagerFileAdapter,
                    CalculatorScoreBoardActivity.SAVE_SCOREBOARDCAL);
        }
    }

    /**
     * Generate new scoreboard and save them to the corresponding scoreboard manager.
     *
     * @param scoreBoardManager the scoreboard manager
     * @param fileAdapter       the scoreboard manager file adapter
     * @param fileName          the scoreboard file name
     */
    private void saveToFiles(ScoreBoardManager scoreBoardManager,
                             ScoreboardManagerFileAdapter fileAdapter, String fileName) {
        String type1 = "Easy";
        String type2 = "Hard";
        String type3 = "Nightmare";
        if (scoreBoardManager.equals(slidingTileScoreBoards)) {
            type1 = "3*3";
            type2 = "4*4";
            type3 = "5*5";
        }
        ScoreBoard scoreBoard3 = new ScoreBoard(true,
                type1);
        ScoreBoard scoreBoard4 = new ScoreBoard(true,
                type2);
        ScoreBoard scoreBoard5 = new ScoreBoard(true,
                type3);
        scoreBoardManager.setScoreBoard(scoreBoard3, 0);
        scoreBoardManager.setScoreBoard(scoreBoard4, 1);
        scoreBoardManager.setScoreBoard(scoreBoard5, 2);
        fileAdapter.saveToFile(this.getFilesDir().getPath()
                + "/" + fileName);
    }

    /**
     * activate a login button that creates a user and confirm its validity.
     */
    private void addLoginButtonListener() {
        Button login = findViewById(R.id.Login);
        login.setOnClickListener(view -> {
            if (userNameEditText.getText().toString().isEmpty() ||
                    passwordEditText.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "please fill in all field",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (userGroup.hasUser((userNameEditText.getText().toString()))) {
                    User thisUser = userGroup.getUser(userNameEditText.getText().toString());
                    if (thisUser.checkPassword(passwordEditText.getText().toString())) {
                        openGameCenter(thisUser);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "the password is incorrect",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "the user does not exist",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * function that redirects to GameCenter Menu
     *
     * @param user takes a user to be sent to the game center
     */
    private void openGameCenter(User user) {
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("user", user);
        Intent startGameCenter = new Intent(this, GameCenterActivity.class);
        startGameCenter.putExtras(userBundle);
        startActivity(startGameCenter);
    }

    /**
     * Implemented the functionality of Signup Button which adds a new user to the user data base
     * and redirect to game center activity
     */
    private void addSignUpListener() {
        Button signUp = findViewById(R.id.Sign_up);
        signUp.setOnClickListener(view -> {
            // create a alert dialog for user to input new username and password
            AlertDialog.Builder signUpAlert = new AlertDialog.Builder(LoginActivity.this);
            View signUpView = getLayoutInflater().inflate(R.layout.signup_dialog, null);
            final EditText suUsername = signUpView.findViewById(R.id.SU_Username);
            final EditText suPassword = signUpView.findViewById(R.id.SU_Password);
            final EditText suConfpassword = signUpView.findViewById(R.id.SU_ConfPassword);
            final Button suBotton = signUpView.findViewById(R.id.SU_button);
            signUpAlert.setView(signUpView);
            final AlertDialog signUpPopUp = signUpAlert.create();
            // initialize the sign up button such that it only passes when the user inputs
            // the right information
            suBotton.setOnClickListener(view1 -> {
                if (suUsername.getText().toString().isEmpty() ||
                        suPassword.getText().toString().isEmpty() ||
                        suConfpassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (userGroup.hasUser(suUsername.getText().toString())) {
                    Toast.makeText(LoginActivity.this,
                            "The user already exists", Toast.LENGTH_SHORT).show();
                } else if (!suPassword.getText().toString().equals(
                        suConfpassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this,
                            "the password does not match",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Sign Up successful",
                            Toast.LENGTH_LONG).show();
                    String newUserName = suUsername.getText().toString();
                    userGroup.addUser(newUserName, suPassword.getText().toString());
                    userManagerFileAdapter.saveToFile(
                            LoginActivity.this.getFilesDir().getPath()
                                    + "/"
                                    + UserManager.USER_FILENAME);
                    signUpPopUp.dismiss();
                    openGameCenter(userGroup.getUser(newUserName));
                }
            });
            signUpPopUp.show();

        });
    }

}
