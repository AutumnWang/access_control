package utils;

/**
 * @date 2020/11/9
 */
public class User {
    private String username;
    private String pwd;
    private int level;          //the bigger the level, the higher the security level

    public User(String username, String pwd, int level){
        this.username = username;
        this.pwd = pwd;
        this.level = level;
    }

    public String getUserName(){
        return username;
    }

    public String getPwd(){
        return pwd;
    }

    public int getLevel(){
        return level;
    }
}
