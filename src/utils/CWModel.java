package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * @date 2020/11/28
 * Chinese Wall: Read, write only to company (level) assigned. Each subject and object has company
 * assigned.
 */

public class CWModel {

    private String content;
    private String subjectName;
    private String objectName;
    private int subjectCompany;
    private int objectCompany;

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

    public int getSubjectCompany() {
        return subjectCompany;
    }

    public int getObjectCompany() {
        return objectCompany;
    }

    /**
     * Compares the user's company with the file they are accessing, and
     * returns a boolean determining whether they can proceed with the action (if they match).
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
        int readerCompany = 0;
        int fileCompany = 0;

        for (User tmp : userArrayList) {
            if (reader.equals(tmp.getUserName())) {
                readerCompany = tmp.getLevel();
                this.subjectName = reader;
                this.subjectCompany = readerCompany;
                break;
            }
        }

        for (User tmp : userArrayList) {
            if (result.get("username").equals(tmp.getUserName())) {
                fileCompany = tmp.getLevel();
                this.objectName = tmp.getUserName();
                this.objectCompany = fileCompany;
                break;
            }
        }

        if (readerCompany == fileCompany) {
            flag = true;
        }
        
        this.content = result.get("content");
        return flag;
    }

    /**
     * Compares the user's company with the file they are accessing, and
     * returns a boolean determining whether they can proceed with the action (if they match).
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
        int writerCompany = 0;
        int fileCompany = 0;

        for (User tmp : userArrayList) {
            if (writer.equals(tmp.getUserName())) {
                writerCompany = tmp.getLevel();
                this.subjectName = writer;
                this.subjectCompany = writerCompany;
                break;
            }
        }

        for (User tmp : userArrayList) {
            if (result.get("username").equals(tmp.getUserName())) {
                fileCompany = tmp.getLevel();
                this.objectName = tmp.getUserName();
                this.objectCompany = fileCompany;
                break;
            }
        }

        if (writerCompany == fileCompany) {
            flag = true;
            this.content = str + System.lineSeparator();
            op1.write(this.content);
    	}
        return flag;
    }
}
