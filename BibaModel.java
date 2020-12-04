package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Biba model: no read down, no write up
 * the bigger the user.level, the higher the security level
 * @date 2020/11/11
 */
public class BibaModel {

    private String content;             //store file's content
    private String usernameOfSubject;     //get subject's name to show in the User frame's jtextarea
    private int levelOfSubject;           //get subject's level to ...
    private String usernameOfObject;      //get object's name to ...
    private int levelOfObject;            //get object's level to ...

    public String getContent(){
        return this.content;
    }

    public String getUsernameOfSubject(){
        return this.usernameOfSubject;
    }

    public int getLevelOfSubject(){
        return this.levelOfSubject;
    }

    public String getUsernameOfObject(){
        return this.usernameOfObject;
    }

    public int getLevelOfObject(){
        return this.levelOfObject;
    }


    //check whether the Biba's read or write is satisfied
    //username1 wants to read the file1 belongs to username2
    //file2 contains user's information (username, password, level)

    public boolean isBibaRead(String username1, File file1, File file2){
        boolean flag = false;
        this.content = "";
        FileOperation fileOperation1 = new FileOperation(file1);
        FileOperation fileOperation2 = new FileOperation(file2);
        ArrayList<User> userArrayList;
        Map<String, String> result;
        result = fileOperation1.readUserFile();
        userArrayList = fileOperation2.read();
        int level1 = 0;
        int level2 = 0;
        for(User tmp : userArrayList){
            if (username1.equals(tmp.getUserName())){
                level1 = tmp.getLevel();
                this.usernameOfSubject = username1;
                this.levelOfSubject = level1;
                break;
            }
        }
        for(User tmp : userArrayList){
            if (result.get("username").equals(tmp.getUserName())){
                level2 = tmp.getLevel();
                this.usernameOfObject = tmp.getUserName();
                this.levelOfObject = level2;
                break;
            }
        }
        if (level1 <= level2) {
            flag = true;
        }
        this.content = result.get("content");
        return flag;
    }

    //str is the content you want to write to the file1
    public boolean isBibaWrite(String username1, File file1, File file2, String str){
        boolean flag = false;
        this.content = "";
        FileOperation fileOperation1 = new FileOperation(file1);
        FileOperation fileOperation2 = new FileOperation(file2);
        ArrayList<User> userArrayList;
        Map<String, String> result;
        result = fileOperation1.readUserFile();
        userArrayList = fileOperation2.read();
        int level1 = 0;
        int level2 = 0;
        for(User tmp : userArrayList){
            if (username1.equals(tmp.getUserName())){
                level1 = tmp.getLevel();
                this.usernameOfSubject = username1;
                this.levelOfSubject = level1;
                break;
            }
        }
        for(User tmp : userArrayList){
            if (result.get("username").equals(tmp.getUserName())){
                level2 = tmp.getLevel();
                this.usernameOfObject = tmp.getUserName();
                this.levelOfObject = level2;
                break;
            }
        }
        if (level1 >= level2){
            flag = true;
            this.content = str + System.lineSeparator();
            fileOperation1.write(this.content);
        }
        return flag;
    }
}
