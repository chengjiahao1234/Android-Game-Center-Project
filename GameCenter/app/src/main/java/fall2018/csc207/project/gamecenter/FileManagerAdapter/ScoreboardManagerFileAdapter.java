package fall2018.csc207.project.gamecenter.FileManagerAdapter;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc207.project.gamecenter.ScoreBoardManager;

public class ScoreboardManagerFileAdapter implements FileManager {

    /**
     * The saved scoerboard manager from the file
     */
    private ScoreBoardManager adaptee;

    /**
     * Constructor a new file adapter based on the scoreboard Manager.
     * @param adaptee
     */
    public ScoreboardManagerFileAdapter(ScoreBoardManager adaptee) { this.adaptee = adaptee; }

    /**
     * Constructor a new empty scoreboard manager.
     */
    public ScoreboardManagerFileAdapter(){ this.adaptee = new ScoreBoardManager(3); }

    /**
     * Setter for the scoreboard manager.
     * @param adaptee the scoreboard manager.
     */
    public void setAdaptee(ScoreBoardManager adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void saveToFile(String pathToFile) {
        try {
            File scoreboardManagerFile = new File(pathToFile);
            FileOutputStream fos = new FileOutputStream(scoreboardManagerFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(adaptee);
            fos.close();
            oos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void loadFromFile(String pathToFile) {
        try {
            File scoreboardManagerFile = new File(pathToFile);
            if (!scoreboardManagerFile.createNewFile()) {
                FileInputStream fis = new FileInputStream(scoreboardManagerFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.adaptee = (ScoreBoardManager) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("ScoreboardFileAdapter", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("ScoreboardFileAdapter", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("ScoreboardFileAdapter", "File contained unexpected data type: " +
                    e.toString());
        }
        saveToFile(pathToFile);
    }

    /**
     * Getter for the scoreboard manager.
     * @return the scoreboard manager.
     */
    public ScoreBoardManager getAdaptee() { return adaptee; }

}
