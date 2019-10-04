package fall2018.csc207.project.gamecenter.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import fall2018.csc207.project.gamecenter.FileManagerAdapter.ScoreboardManagerFileAdapter;
import fall2018.csc207.project.gamecenter.R;
import fall2018.csc207.project.gamecenter.ScoreBoard;
import fall2018.csc207.project.gamecenter.ScoreBoardManager;
import fall2018.csc207.project.gamecenter.User;

/**
 * The activity that show the score board of the sliding tile game
 */
public class SlidingTilesScoreBoardActivity extends AppCompatActivity {

    /**
     * All score board in the choose spinner.
     */
    private String[] boards = {"World 3*3", "World 4*4", "World 5*5", "User"};

    /**
     * the user corresponding to the scoreboard
     */
    private User user;

    /**
     * Two score boards corresponding to two save files.
     */
    public static final String SAVE_SCOREBOARDST = "slidingtiles_scoreboardST.ser";

    /**
     * Four score boards.
     */
    private ScoreBoard scoreboard;
    private ScoreBoardManager slidingTileScoreboards;
    private ScoreboardManagerFileAdapter scoreboardManagerFileAdapter =
            new ScoreboardManagerFileAdapter();

    ListView lst;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activateUser(getIntent());
        // Load score boards from files.
        scoreboardManagerFileAdapter.loadFromFile(
                this.getFilesDir().getPath()
                        + "/" + SAVE_SCOREBOARDST);
        slidingTileScoreboards = scoreboardManagerFileAdapter.getAdaptee();
        scoreboard = user.getPerUserScoreBoard().getScoreBoard(0);
        setContentView(R.layout.activity_scoreboard);
        activateSpinner();
        addBackButtonListener();
    }

    /**
     * Activate Back Button which backs to the Game Center.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.back_stScoreboard);
        back.setOnClickListener(view -> {
            Intent opGame = new Intent(SlidingTilesScoreBoardActivity.this,
                    StartingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            opGame.putExtras(bundle);
            startActivity(opGame);
        });
    }

    /**
     * Activate spinner
     */
    private void activateSpinner() {
        Spinner sp = findViewById(R.id.choose);
        lst = findViewById(R.id.sb);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, boards);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] display = {};
                if (position == 0) {
                    display = slidingTileScoreboards.getScoreBoard(0).getList();
                } else if (position == 1) {
                    display = slidingTileScoreboards.getScoreBoard(1).getList();
                } else if (position == 2) {
                    display = slidingTileScoreboards.getScoreBoard(2).getList();
                } else if (position == 3) {
                    display = scoreboard.getList();
                }
                listAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, display);
                lst.setAdapter(listAdapter);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String[] display = scoreboard.getList();
                listAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, display);
                lst.setAdapter(listAdapter);
            }
        });
    }

    /**
     * Activate user.
     *
     * @param intent the user
     */
    private void activateUser(Intent intent) {
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
    }

}
