package fall2018.csc207.project.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc207.project.gamecenter.calculator.CalculatorStartingActivity;
import fall2018.csc207.project.gamecenter.game2048.TZFEStartingActivity;
import fall2018.csc207.project.gamecenter.slidingtiles.StartingActivity;

/**
 * The game center activity for the user choose the game.
 */
public class GameCenterActivity extends AppCompatActivity {

    /**
     * The user.
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle userBundle = intent.getExtras();
        setContentView(R.layout.activity_game_center);
        activateUser(userBundle);
        addLogoutButtonListener();
        addSlidingTileButtonListener(userBundle);
        addTZFEButtonListener(userBundle);
        TextView curUserName = findViewById(R.id.UserName_TV);
        curUserName.setText(user.getUsername());
        addCalculatorButtonListener();
    }

    /**
     * Activate calculator button.
     */
    private void addCalculatorButtonListener() {
        Button calc = findViewById(R.id.gameCenter_Calculator);
        calc.setOnClickListener(v -> {
            Intent intent = new Intent(GameCenterActivity.this,
                    CalculatorStartingActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        });
    }

    /**
     * Activate 2048 game button.
     *
     * @param bundle the user information.
     */
    private void addTZFEButtonListener(final Bundle bundle) {
        Button tzfe = findViewById(R.id.game2048_button);
        tzfe.setOnClickListener(view -> {
            Intent openTZFE = new Intent(GameCenterActivity.this,
                    TZFEStartingActivity.class);
            openTZFE.putExtras(bundle);
            startActivity(openTZFE);
        });

    }

    /**
     * Activate the user.
     * @param bundle    User object passed in from other activities
     */
    private void activateUser(Bundle bundle) {
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }
    }

    /**
     * Activate logout button.
     */
    private void addLogoutButtonListener() {
        Button Logout = findViewById(R.id.Logout_Button);
        Logout.setOnClickListener(view -> openLogin());
    }

    /**
     * Activate login button.
     */
    private void openLogin() {
        Intent loginACT = new Intent(this, LoginActivity.class);
        startActivity(loginACT);
    }

    /**
     * Activate slidingTile game button.
     * @param bundle the bundle
     */
    private void addSlidingTileButtonListener(final Bundle bundle) {
        Button SlidingTile = findViewById(R.id.SlidingTile_button);
        SlidingTile.setOnClickListener(view -> {
            Intent OpSlidingTile = new Intent(GameCenterActivity.this,
                    StartingActivity.class);
            OpSlidingTile.putExtras(bundle);
            startActivity(OpSlidingTile);
        });

    }
}
