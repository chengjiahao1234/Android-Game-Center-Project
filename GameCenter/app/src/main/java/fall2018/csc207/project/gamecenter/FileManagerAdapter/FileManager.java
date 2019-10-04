package fall2018.csc207.project.gamecenter.FileManagerAdapter;

/**
 * An interface containing client methods
 */
public interface FileManager {

    /**
     * Save things to the file
     * @param pathToFile the file path
     */
    void saveToFile(String pathToFile);

    /**
     * Load things from the file
     * @param pathToFile the file path
     */
    void loadFromFile(String pathToFile);
}
