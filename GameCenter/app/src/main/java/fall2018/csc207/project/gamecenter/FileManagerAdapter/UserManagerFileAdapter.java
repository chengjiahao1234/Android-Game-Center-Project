package fall2018.csc207.project.gamecenter.FileManagerAdapter;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fall2018.csc207.project.gamecenter.User;
import fall2018.csc207.project.gamecenter.UserManager;

public class UserManagerFileAdapter implements FileManager {

    /**
     * The user manager.
     */
    private UserManager adaptee;

    /**
     * Set new file adapter.
     */
    private static UserManagerFileAdapter instance = new UserManagerFileAdapter();

    /**
     * Constructor a new empty user manager.
     */
    public UserManagerFileAdapter() {}

    /**
     * Setter for the user manager.
     * @param adaptee the user manager.
     */
    public void setAdaptee(UserManager adaptee) { this.adaptee = adaptee; }

    /**
     * Return the current adaptee object
     * @return  adaptee the current adaptee
     */
    public UserManager getAdaptee() {
        return adaptee;
    }

    /**
     * Getter for the user manager.
     * @return
     */
    public static UserManagerFileAdapter getInstance() { return  instance; }

    @Override
    public void saveToFile(String pathToFile) {
        try {
            File userFile = new File(pathToFile);
            FileOutputStream fos = new FileOutputStream(userFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(adaptee.getUserGroup());
            fos.close();
            oos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void loadFromFile(String pathToFile) {
        try {
            File userFile = new File(pathToFile);
            if (!userFile.createNewFile()) {
                FileInputStream fis = new FileInputStream(userFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                adaptee.setUserGroup((ArrayList<User>) ois.readObject());
                ois.close();
                fis.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("UserManagerFileAdapter", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " +
                    e.toString());
        }
        saveToFile(pathToFile);
    }
}
