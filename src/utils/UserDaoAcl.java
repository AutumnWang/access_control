package utils;


import java.util.ArrayList;

/**
 * @date 2020/11/9
 */
public interface UserDaoAcl {

    //add user without check
    public abstract void addUser(ArrayList<UserAcl> userArrayList, UserAcl user);

    //add user and check if there are two same username
    public abstract boolean addCheck(ArrayList<UserAcl> userArrayList, UserAcl user);

    //check whether username and password are valid
    public abstract boolean isValid(ArrayList<UserAcl>userArrayList, String username, String password);
}
