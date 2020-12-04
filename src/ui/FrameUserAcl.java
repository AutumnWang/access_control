package ui;

import utils.FileOperationAcl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.Permission;
import java.security.Principal;
import java.security.acl.NotOwnerException;
import java.util.ArrayList;

import utils.UserAcl;
import utils.UserDaoImplementAcl;
import utils.acl.*;

/**
 * @date 2020/11/7
 */

public class FrameUserAcl extends JFrame {

    private PrincipalCreator owner = new PrincipalCreator("owner");
    private AclCreator acl = new AclCreator(owner, "demoacl");
    private PrincipalCreator currentuser = new PrincipalCreator();
    private PermissionCreator read = new PermissionCreator("read");
    private PermissionCreator write = new PermissionCreator("write");

    public void setupAcl() throws NotOwnerException {

        File fileAcl = new File("src/test/resources/user_acl.txt");
        ArrayList<UserAcl> userArrayListAcl = new ArrayList<UserAcl>();
        FileOperationAcl fileOperationAcl = new FileOperationAcl(fileAcl);
        userArrayListAcl = fileOperationAcl.read();

        GroupCreator g1 = new GroupCreator("group1");
        GroupCreator g2 = new GroupCreator("group2");
        GroupCreator g3 = new GroupCreator("group3");



        AclEntryCreator et1 = new AclEntryCreator(g1);
        et1.addPermission(read);
        AclEntryCreator et2 = new AclEntryCreator(g2);
        et2.addPermission(write);
        AclEntryCreator et3 = new AclEntryCreator(g3);
        et3.addPermission(read);
        et3.addPermission(write);

        acl.addEntry(owner, et1);
        acl.addEntry(owner, et2);
        acl.addEntry(owner, et3);


        for (UserAcl tmp: userArrayListAcl){
            PrincipalCreator p = new PrincipalCreator(tmp.getUserName());

            switch (tmp.getGroup()) {
                case 1:
                    g1.addMember(p);
                    break;
                case 2:
                    g2.addMember(p);
                    break;
                case 3:
                    g3.addMember(p);
                    break;
            }

            AclEntryCreator etmp = new AclEntryCreator(p);

            switch (tmp.getPermission()) {
                case 0:
                    etmp.addPermission(read);
                    break;
                case 1:
                    etmp.addPermission(write);
                    break;
                case 2:
                    etmp.addPermission(read);
                    etmp.setNegativePermissions();
                    break;
                case 3:
                    etmp.addPermission(write);
                    etmp.setNegativePermissions();
                    break;
            }

            acl.addEntry(owner, etmp);

        }
    }

    private JLabel jl1 = new JLabel("current user");
    private JLabel jl2 = new JLabel("group");
    private JTextField jtf1 = new JTextField(); // current username, can't edit
    private JTextField jtf2 = new JTextField(); // current user's group, can't edit
    private JTextField jtf3 = new JTextField(); // name of file which you want to create
    private JTextField jtf4 = new JTextField(); // path of file you select, can't edit

    private JTextArea jta1 = new JTextArea();
    private JScrollPane jsp1 = new JScrollPane();

    private JButton btn1 = new JButton("create file");
    private JButton btn2 = new JButton("select file");
    private JButton btn3 = new JButton("read");


    public FrameUserAcl() {
        this.setTitle("user");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(30, 30, 100, 30);
        jtf1.setBounds(140, 30, 100, 30);
        jl2.setBounds(30, 60, 100, 30);
        jtf2.setBounds(140, 60, 100, 30);

        btn1.setBounds(20, 120, 100, 30);
        jtf3.setBounds(140, 120, 300, 30);

        btn2.setBounds(20, 215, 100, 30);
        jtf4.setBounds(140, 215, 300, 30);

        btn3.setBounds(20, 250, 100, 30);
        jta1.setBounds(30, 280, 440, 70);
        jsp1.setBounds(30, 280, 440, 70);

        jtf1.setEditable(false);
        jtf2.setEditable(false);
        jtf4.setEditable(false);

        panel.add(jl1);
        panel.add(jtf1);
        panel.add(jl2);
        panel.add(jtf2);

        panel.add(btn1);
        panel.add(jtf3);

        panel.add(btn2);
        panel.add(jtf4);

        panel.add(btn3);

        panel.add(jta1);
        panel.add(jsp1);
        jta1.setLineWrap(true);
        jsp1.setViewportView(jta1);


        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);

        listener();

        try {
            setupAcl();
        } catch (NotOwnerException e) {
            e.printStackTrace();
        }

    }

    public void setUsername(String username) {
        jtf1.setText(username);
    }

    public void setGroup(int group) {
        jtf2.setText(String.valueOf(group));
    }

    public void setPrincipal(String user) {
        currentuser.setName(user);
    }


    private void listener() {
        btn1.addActionListener(
                new ActionListener() {


                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if(acl.checkPermission(currentuser, write)) {
                            File file = new File("src/test/resources/" + jtf3.getText() + ".txt");
                            if (jtf3.getText().trim().length() == 0){
                                JOptionPane.showMessageDialog(null, "file name cannot be empty");
                            }
                            else if (jtf3.getText().indexOf(" " ) != -1){
                                JOptionPane.showMessageDialog(null, "you cannot use space as you file name");
                            }
                            else{
                                if (file.exists()){
                                    JOptionPane.showMessageDialog(null, "the file already exists");
                                }
                                else{
                                    try {
                                        file.createNewFile();
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }

                                    //set username as txt file's property
                                    FileOperationAcl fileOperation = new FileOperationAcl(file);
                                    if(fileOperation.write("created by user:" + jtf1.getText() + System.lineSeparator())) {
                                        JOptionPane.showMessageDialog(null, "success!");
                                    }
                                }
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "you do not have a permission to write a file");
                        }
                    }
                }
        );

        btn2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JFileChooser jfc = new JFileChooser(new File("src/test/resources"));  //set default directory
                        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        jfc.setDialogTitle("select");
                        int result = jfc.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION){
                            File file = jfc.getSelectedFile();
                            String filepath = file.getAbsolutePath();
                            jtf4.setText(filepath);
                        }
                    }
                }
        );

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file1 = new File(jtf4.getText());
                File file2 = new File("src/test/resources/user.txt");

                if (acl.checkPermission(currentuser, read)) {
                    jta1.append("the user(name:" + currentuser.getName() + ") wants to read the file "
                            + System.lineSeparator() + file1.getName() + System.lineSeparator());
                    jta1.append("success" + System.lineSeparator());
                }

                else {
                    JOptionPane.showMessageDialog(null,
                            "you do not have a permission to read a file");
                }
            }
        });
    }
}
