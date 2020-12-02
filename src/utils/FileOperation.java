package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020/11/9
 */
public class FileOperation {
    private File file;

    public FileOperation(File file){
        this.file = file;
    }

    //read file to get user's information, return Arraylist user
    public ArrayList<User> read(){
        ArrayList<User> userArrayList = new ArrayList<User>();
        UserDaoImplement udi = new UserDaoImplement();
        String tmpLine;

        BufferedReader breader = null;
        try{
            breader = new BufferedReader(new FileReader(file));
            while ((tmpLine = breader.readLine()) != null){
                String[] userInfo = tmpLine.split(" ");
                User user = new User(userInfo[0],userInfo[1],Integer.parseInt(userInfo[2]));
                udi.addUser(userArrayList, user);
            }
            breader.close();
        } catch (IOException exception){
            exception.printStackTrace();
        } finally {
            if (breader != null){
                try{
                    breader.close();
                } catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }
        return userArrayList;
    }


    //write str to the file, return boolean
    public boolean write(String str){
        FileWriter fwriter = null;
        BufferedWriter bwriter = null;
        boolean flag = false;
        try{
            fwriter = new FileWriter(file, true);
            bwriter = new BufferedWriter(fwriter);
            bwriter.write(str);
        } catch (IOException exception){
            exception.printStackTrace();
        } finally {
            try {
                bwriter.close();
                fwriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            flag = true;
        }
        return flag;
    }

    //delete a line in file
    public boolean deleteLine(String username){
        boolean flag = false;
        ArrayList<User> userArrayList = new ArrayList<User>();
        userArrayList = this.read();
        for (User tmp : userArrayList){
            if (tmp.getUserName().equals(username)){
                userArrayList.remove(tmp);
                flag = true;
                break;
            }
        }
        try{
            FileWriter fwriter = new FileWriter(file);
            fwriter.write("");
            fwriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String str;
        for (User tmp : userArrayList){
            str = tmp.getUserName() + " " + tmp.getPwd() + " " + tmp.getLevel() + System.lineSeparator();
            this.write(str);
        }
        return flag;
    }

    //read the file that users create, return Map<username, content>
    public Map<String, String> readUserFile(){
        Map<String, String> result = new HashMap<>();
        String username = null;
        String content = null;

        String tmpLine;
        BufferedReader breader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try{
            breader = new BufferedReader(new FileReader(file));
            if((tmpLine = breader.readLine()) != null){

                //int the first line of the file, "created by user:"+username
                username = tmpLine.substring(16,tmpLine.length());
            }
            while((tmpLine = breader.readLine()) != null){
                stringBuffer.append(tmpLine + System.lineSeparator());
            }
            breader.close();
            content = stringBuffer.toString();
        } catch (IOException exception){
            exception.printStackTrace();
        } finally {
            if (breader != null){
                try{
                    breader.close();
                } catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }
        result.put("username", username);
        result.put("content", content);
        return result;
    }
}
