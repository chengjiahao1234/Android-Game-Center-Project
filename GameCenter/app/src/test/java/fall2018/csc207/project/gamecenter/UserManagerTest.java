package fall2018.csc207.project.gamecenter;

import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc207.project.gamecenter.calculator.CalculatorScore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * unit test for  UserManager.
 */
public class UserManagerTest {

    private UserManager userManager = new UserManager();

    /**
     * set up a usermanager with three test users
     */
    private void initiateUserManager(){

        userManager.addUser("test1", "1");
        userManager.addUser("test2", "1");
        userManager.addUser("test3", "1");
    }

    /**
     * test setUserGroup method and make sure it does completely change the usergroup to the given one
     */
    @Test
    public void testSetUserGroup(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("bob","bob"));
        initiateUserManager();
        userManager.setUserGroup(users);
        assertEquals("bob", userManager.getUser("bob").getUsername());
        assertTrue(userManager.getUser("bob").checkPassword("bob"));
        assertNull(userManager.getUser("test1"));
    }

    /**
     * test getUserGroup method and make sure we get back the usergroup
     */
    @Test
    public void testGetUserGroup(){
        userManager = new UserManager();
        assertNull(userManager.getUser("test1"));
        initiateUserManager();
        ArrayList<User> users = (ArrayList<User>) userManager.getUserGroup();
        assertEquals("test1", users.get(0).getUsername());
        assertTrue(users.get(0).checkPassword("1"));
    }

    /**
     * test if addUser method is functional
     */
    @Test
    public void testAddUser(){
        userManager = new UserManager();
        userManager.addUser("ryan", "reynold");
        assertTrue( userManager.getUser("ryan").checkPassword("reynold"));
        //test to see if adding the same user would cause issue
        userManager.addUser("ryan", "someOtherDude");
        assertTrue( userManager.getUser("ryan").checkPassword("reynold"));
        assertEquals(1,userManager.getUserGroup().size());
    }

    /**
     * test hasUser method
     */
    @Test
    public void testHasUser(){
        initiateUserManager();
        assertFalse(userManager.hasUser("Ryan"));
        userManager.addUser("Ryan", "test");
        assertTrue(userManager.hasUser("Ryan"));
    }

    /**
     * test UpdateUser method if we can update the scoreboard in a user
     */
    @Test
    public void testUpdateUser(){
        initiateUserManager();
        User user = new User("Morty", "rick");
        userManager.addUser("Morty", "rick");
        assertEquals(0,userManager.getUser("Morty").getPerUserScoreBoard().getScoreBoard(1).getSize());
        ScoreBoardManager scoreBoardManager = user.getPerUserScoreBoard();
        Score score = new CalculatorScore("test","Morty",4,111);
        scoreBoardManager.getScoreBoard(1).update(score);
        user.setPerUserScoreBoard(scoreBoardManager);
        userManager.updateUser(user);
        assertEquals(1,userManager.getUser("Morty").getPerUserScoreBoard().getScoreBoard(1).getSize());

        // make sure we can update multiple score and that it is added to the correct user
        Score score2 = new CalculatorScore("test","Morty",7,11);
        scoreBoardManager.getScoreBoard(1).update(score2);
        user.setPerUserScoreBoard(scoreBoardManager);
        userManager.updateUser(user);
        assertEquals(2,userManager.getUser("Morty").getPerUserScoreBoard().getScoreBoard(1).getSize());
    }
}
