package fall2018.csc207.project.gamecenter;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import fall2018.csc207.project.gamecenter.calculator.CalculatorManager;
import fall2018.csc207.project.gamecenter.game2048.TZFEBoardManager;
import fall2018.csc207.project.gamecenter.slidingtiles.BoardManager;

/**
 * a user class for users playing the game
 */
public class User implements Serializable {

    /**
     * this is the username that identifies the user
     */
    private String userName;

    /**
     * the password is to confirm user identity
     */
    private String password;

    /**
     * User's saved slidingTileGames
     */
    private BoardManager[] slidingTileGames;

    /**
     * User's saved Calculator games
     */
    private CalculatorManager[] calculatorGames;

    /**
     * User's saved 2048 games
     */
    private TZFEBoardManager[] tzfeGames;

    /**
     *
     */
    private ScoreBoardManager perUserScoreBoard;

    /**
     * Solution found on:
     * https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     * Resolved incompatible class type issue read from file storing all users
     */
    private static final long serialVersionUID = -221468530942040709L;

    /**
     * a new user with username and password as given
     *
     * @param userName is the the id that the user want to use as
     * @param password is the secured String to confirm user identity
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        slidingTileGames = new BoardManager[4];
        tzfeGames = new TZFEBoardManager[4];
        calculatorGames = new CalculatorManager[4];
        perUserScoreBoard = new ScoreBoardManager(3);
        ScoreBoard stScoreboard = new ScoreBoard(false, userName);
        ScoreBoard tzfeScoreboard = new ScoreBoard(false, userName);
        ScoreBoard calcScoreboard = new ScoreBoard(false, userName);
        perUserScoreBoard.setScoreBoard(stScoreboard, 0);
        perUserScoreBoard.setScoreBoard(tzfeScoreboard, 1);
        perUserScoreBoard.setScoreBoard(calcScoreboard, 2);
    }

    /**
     * a method to retrieve username
     *
     * @return the username of this user
     */
    public String getUsername() {
        return userName;
    }

    /**
     * allows user to change their user name
     *
     * @param username is the new username desired to be changed to
     */
    public void setUsername(String username) {
        userName = username;
    }

    /**
     * check to see if the given password is correct
     *
     * @param Password is the string that is passed in to be confirmed if it is right or not
     * @return a boolean on whether it is right or not
     */
    public boolean checkPassword(String Password) {
        return Password.equals(this.password);
    }

    /**
     * Save user's game at a given slot
     * Try clone(YOUR_GAME game) if your saved game changes
     *
     * @param game  a game to be saved
     * @param index the position that need to be stored
     */
    public <T> void saveGame(T game, int index) {
        String type = game.getClass().getTypeName();
        if (type.contains("TZFEBoardManager")) {
            TZFEBoardManager tzfeGame = (TZFEBoardManager) game;
            tzfeGames[index] =
                    new TZFEBoardManager(
                            clone(tzfeGame.getBoard()), tzfeGame.getType(), tzfeGame.getUser(),
                            tzfeGame.getComplexity());
            tzfeGames[index].setMove(tzfeGame.getMove());
            tzfeGames[index].setTimeElapsed(tzfeGame.getTimeElapsed());

        } else if (type.contains("CalculatorManager"))
            calculatorGames[index] = (CalculatorManager) game;
        else if (type.contains("BoardManager")) {
            BoardManager tileGame = (BoardManager) game;
            slidingTileGames[index] =
                    new BoardManager(
                            clone(tileGame.getBoard()), tileGame.getSize(), tileGame.getType(),
                            tileGame.getUser());
            slidingTileGames[index].setMove(tileGame.getMove());
            slidingTileGames[index].setTimeElapsed(tileGame.getTimeElapsed());
        }
        // Add your new game here...
        // e.g. else if (type.contains(YOUR_GAME)) YOUR_GAME[index] = (YOUR_GAME) game;
    }


    /**
     * Adapted from: https://dzone.com/articles/easy-deep-cloning-serializable
     * <p>
     * A helper method created for saving games
     * implemented with Observable.
     * It produces new copies of game objects
     * such that games in progress are not
     * at the same memory address as the saved ones.
     * <p>
     * Requirement: input objects MUST be Serializable.
     *
     * @param <T>      Class type of the original object
     * @param original Original object to be cloned
     * @return New copy of the original
     */
    private <T> T clone(T original) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(original);
            oos.flush();
            oos.close();
            byte[] bytes = bos.toByteArray();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("Cloning via serialization: ", "Clone failed");
            return null;
        }
    }

    /**
     * Return a user's saved game at a given slot
     * only if there is one.
     * Some type-casting in your game activity class required
     * e.g.
     * game = (YOUR_GAME_TYPE) user.getGame(YOUR_GAME_TYPE, index);
     *
     * @param game
     * @param index the position that need to be stored
     * @return the boardManager
     */
    public Object getGame(String game, int index) {
        if (game.equalsIgnoreCase("slidingtile")) return slidingTileGames[index];
        else if (game.equalsIgnoreCase("tzfe")) return tzfeGames[index];
        else if (game.equalsIgnoreCase("calculator")) return calculatorGames[index];
        // Add your new game here...
        return null;
    }

    /**
     * Get the per user scoreboard from the user.
     *
     * @return the scoreboard Manager for per-user scoreboards
     */
    public ScoreBoardManager getPerUserScoreBoard() {
        return perUserScoreBoard;
    }

    /**
     * Update the per-user scoreboards.
     *
     * @param perUserScoreBoard the new perUserScorebaord (scoreboardManager)
     */
    public void setPerUserScoreBoard(ScoreBoardManager perUserScoreBoard) {
        this.perUserScoreBoard = perUserScoreBoard;
    }

    @Override
    public String toString() {
        return userName;
    }

}
