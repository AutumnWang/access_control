package utils;

import java.util.ArrayList;

/**
 * @date 2020/11/9
 */
    public class UserDaoImplement implements UserDao{


    @Override
    public void addUser(ArrayList<User>userArrayList, User user) {
        userArrayList.add(user);
    }

    @Override
    public boolean addCheck(ArrayList<User> userArrayList, User user) {
        boolean flag = true;
        for (User tmp: userArrayList){
            if (user.getUserName().equals(tmp.getUserName())){
                flag = false;
                break;
            }
        }
        if (flag){
            userArrayList.add(user);
        }
        return flag;
    }

    @Override
    public boolean isValid(ArrayList<User> userArrayList, String username, String password) {
        boolean flag = false;
        for (User tmp : userArrayList){
            if (username.equals(tmp.getUserName()) && password.equals(tmp.getPwd())){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
