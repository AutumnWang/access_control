package utils;

public class UserAcl {

    private String username;
    private String pwd;
    private int group;
    private int permission;

    public UserAcl(String username, String pwd, int group, int permission) {
        this.username = username;
        this.pwd = pwd;
        this.group = group;
        this.permission = permission;
    }


    public String getUserName(){
        return username;
    }

    public String getPwd(){
        return pwd;
    }

    public int getGroup(){
        return group;
    }

    public int getPermission(){
        return permission;
    }

}
