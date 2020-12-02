package utils;


import java.util.ArrayList;

/**
 * @date 2020/11/9
 */
public interface UserDao {

    //add user without check
    public abstract void addUser(ArrayList<User> userArrayList, User user);

    //add user and check if there are two same username
    public abstract boolean addCheck(ArrayList<User> userArrayList, User user);

    //check whether username and password are valid
    public abstract boolean isValid(ArrayList<User>userArrayList, String username, String password);
}
