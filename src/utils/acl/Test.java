package utils.acl;

import utils.FileOperationAcl;
import utils.UserAcl;
import utils.UserDaoImplementAcl;

import java.io.File;
import java.util.ArrayList;

public class Test {
    public static void main(String args[]) throws Exception {
        File fileAcl = new File("src/test/resources/user_acl.txt");
        ArrayList<UserAcl> userArrayListAcl = new ArrayList<UserAcl>();
        FileOperationAcl fileOperationAcl = new FileOperationAcl(fileAcl);
        userArrayListAcl = fileOperationAcl.read();
    }
}
