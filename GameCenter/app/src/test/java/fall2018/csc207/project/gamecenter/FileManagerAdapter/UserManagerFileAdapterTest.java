package fall2018.csc207.project.gamecenter.FileManagerAdapter;

import fall2018.csc207.project.gamecenter.User;
import fall2018.csc207.project.gamecenter.UserManager;

import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for UserManagerFileAdapter
 * Means of testing file-saving and -loading features are stated above the tests
 */
public class UserManagerFileAdapterTest {
    private UserManagerFileAdapter userManagerFileAdapter
            = UserManagerFileAdapter.getInstance();
    private UserManager userManager = new UserManager();


    /**
     * Populate user manager
     */
    private void setUserManager() {
        for (int i = 1; i <= 100; i++)
            userManager.addUser("user" + i, "password" + i);
    }

    /**
     * Delete the created user file used in testing
     */
    private void cleanUp() {
        File userFile = new File(UserManager.USER_FILENAME);
        if (userFile.exists()) userFile.delete();
    }

    /**
     * Test getInstance() method
     */
    @Test
    public void testGetInstance() {
        assertEquals(userManagerFileAdapter, UserManagerFileAdapter.getInstance());
    }

    /**
     * Test setAdaptee() method
     */
    @Test
    public void testSetAdaptee() {
        userManagerFileAdapter.setAdaptee(userManager);
        assertEquals(userManager, userManagerFileAdapter.getAdaptee());
    }

    /**
     * Test getAdaptee() method
     */
    @Test
    public void testGetAdaptee() {
        assertNull(userManagerFileAdapter.getAdaptee());
        userManagerFileAdapter.setAdaptee(userManager);
        assertEquals(userManager, userManagerFileAdapter.getAdaptee());
    }

    /**
     * Test saveToFile() method by
     * 1. Creating a new file
     * 2. Checking if file has no data by checking whether its size is 0
     * 3. executing saveToFile() to populate the file with whatever in UserManager
     * 4. Checking if the file has data by checking whether its size is not 0
     */
    @Test
    public void testSaveToFile() {
        setUserManager();
        userManagerFileAdapter.setAdaptee(userManager);
        File userFile = new File(UserManager.USER_FILENAME);
        try {
            userFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Before saving
        assertEquals(0, userFile.length());
        userManagerFileAdapter.saveToFile(UserManager.USER_FILENAME);
        // After saving
        assertNotSame(0, userFile.length());
    }

    /**
     * Test loadFromFile() by:
     * 1. Populate a UserManager <userManager>
     * 2. Creating a new UserManager <testLoad> with no users
     * 3. Loading from the file just populated by saveToFile() to populate <testLoad>
     * 4. Check if everything loaded from the file is the same as data in <userManager>
     */
    @Test
    public void testLoadFromFile() {
        setUserManager();
        UserManager testLoad = new UserManager();
        userManagerFileAdapter.setAdaptee(testLoad);
        userManagerFileAdapter.loadFromFile(UserManager.USER_FILENAME);
        for (User u : testLoad.getUserGroup())
            assertTrue(userManager.hasUser(u.getUsername()));
        cleanUp();
    }
}
