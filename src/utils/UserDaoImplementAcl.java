package utils;
import java.util.ArrayList;

public class UserDaoImplementAcl implements UserDaoAcl {
    @Override
    public void addUser(ArrayList<UserAcl>userArrayList, UserAcl user) {
        userArrayList.add(user);
    }

    @Override
    public boolean addCheck(ArrayList<UserAcl> userArrayList, UserAcl user) {
        boolean flag = true;
        for (UserAcl tmp: userArrayList){
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
    public boolean isValid(ArrayList<UserAcl> userArrayList, String username, String password) {
        boolean flag = false;
        for (UserAcl tmp : userArrayList){
            if (username.equals(tmp.getUserName()) && password.equals(tmp.getPwd())){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
