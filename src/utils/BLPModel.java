package src.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Bell LaPuda: No read up, no write down. Each subject and object have levels
 * assigned, and higher levels dominate lower ones.
 */

public class BLPModel {

    private String content;
    private String subjectName;
    private String objectName;
    private int subjectClearance;
    private int objectClearance;

    // Getter methods

    public String getContent() {
        return content;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getSubjectClearance() {
        return subjectClearance;
    }

    public int getObjectClearance() {
        return objectClearance;
    }

    /**
     * Compares the user's clearance level with the file they are accessing, and
     * returns a boolean determining whether they can proceed with the action.
     * 
     * @param reader the user requesting read access
     * @param file   the file being read
     * @param id     the file containing user clearance levels
     * @return true or false, whether the read is successful
     */
    public boolean isReadAllowed(String reader, File file, File id) {

        boolean flag = false;
        this.content = "";
        FileOperation op1 = new FileOperation(file);
        FileOperation op2 = new FileOperation(id);
        ArrayList<User> userArrayList;
        Map<String, String> result;
        result = op1.readUserFile();
        userArrayList = op2.read();
        int readerLevel = 0;
        int fileLevel = 0;

        for (User tmp : userArrayList) {
            if (reader.equals(tmp.getUserName())) {
                readerLevel = tmp.getLevel();
                this.subjectName = reader;
                this.subjectClearance = readerLevel;
                break;
            }
        }

        for (User tmp : userArrayList) {
            if (result.get("username").equals(tmp.getUserName())) {
                fileLevel = tmp.getLevel();
                this.objectName = tmp.getUserName();
                this.objectClearance = fileLevel;
                break;
            }
        }

        if (readerLevel >= fileLevel)
            flag = true;

        this.content = result.get("content");
        return flag;
    }

    /**
     * Compares the user's clearance level with the file they are accessing, and
     * returns a boolean determining whether they can proceed with the action.
     * 
     * @param writer the user requesting write access
     * @param file   the file being written to
     * @param id     the file containing user clearance levels
     * @param str    the string being written
     * @return true or false, whether the write is successful
     */
    public boolean isWriteAllowed(String writer, File file, File id, String str) {

        boolean flag = false;
        this.content = "";
        FileOperation op1 = new FileOperation(file);
        FileOperation op2 = new FileOperation(id);
        ArrayList<User> userArrayList;
        Map<String, String> result;
        result = op1.readUserFile();
        userArrayList = op2.read();
        int writerLevel = 0;
        int fileLevel = 0;

        for (User tmp : userArrayList) {
            if (writer.equals(tmp.getUserName())) {
                writerLevel = tmp.getLevel();
                this.subjectName = writer;
                this.subjectClearance = writerLevel;
                break;
            }
        }

        for (User tmp : userArrayList) {
            if (result.get("username").equals(tmp.getUserName())) {
                fileLevel = tmp.getLevel();
                this.objectName = tmp.getUserName();
                this.objectClearance = fileLevel;
                break;
            }
        }

        if (writerLevel <= fileLevel)
            flag = true;

        this.content = str + System.lineSeparator();
        op1.write(this.content);
        return flag;
    }

}
