package fall2018.csc207.project.gamecenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that performs all operations of managing a group of users
 */
public class UserManager {

    /**
     * The main save file.
     */
    public static final String USER_FILENAME = "user.ser";

    /**
     * The AUTO_SAVE index in save_game.
     */
    public static final int AUTO_SAVE = 0; // 0 position is by default auto-saved game

    /**
     * All users
     */
    private List<User> users;

    /**
     * Constructor a new empty arrayList.
     */
    public UserManager() {
        this.users = new ArrayList<>();
    }

    /**
     * Populate this user group
     *
     * @param users the users
     */
    public void setUserGroup(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Return this group of users
     *
     * @return the users
     */
    public List<User> getUserGroup() {
        return users;
    }

    /**
     * Add a user to the group given a username and password
     *
     * @param userName the name of the user
     * @param password the password
     */
    public void addUser(String userName, String password) {
        if(! hasUser(userName)) users.add(new User(userName, password));
    }

    /**
     * Return whether a user of the given username exists in this group
     *
     * @param userName the name of the user
     * @return true if a user is in users, otherwise return false
     */
    public boolean hasUser(String userName) {
        if (users.size() == 0) return false;
        for (User u : users)
            if (userName.equals(u.getUsername()))
                return true;
        return false;
    }

    /**
     * Return a user object of given username
     *
     * @param userName the name of the user
     * @return the User
     */
    public User getUser(String userName) {
        if (users.size() > 0) {
            return getUser(users.get(0), userName);
        } else {
            return null;
        }
    }

    /**
     * Compare each user's username against the given one
     *
     * @param user     the User
     * @param userName the name of the user
     * @return the user by the given userName
     */
    private User getUser(User user, String userName) {
        if (user == null) return null;
        for (User u : users)
            if (userName.equals(u.getUsername()))
                return u;
        return null;
    }

    /**
     * Update a given user's status in the group
     *
     * @param user the user
     */
    public void updateUser(User user) {
        users.set(users.indexOf(getUser(user.getUsername())), user);
    }
}
