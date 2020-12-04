package ui;

import utils.FileOperationAcl;
import utils.UserAcl;
import utils.UserDaoImplementAcl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class FrameAdminAcl extends JFrame {

    private String[] arr1={"user","group1","group2","group3"};    // when it is stored in a file, use the index(1,2,3,4)
    private String[] arr2={"read","write", "negativeRead", "negativeWrite"};    // when it is stored in a file, use the index(1,2,3,4)
    private JLabel jl1 = new JLabel("username");
    private JLabel jl2 = new JLabel("password");
    private JLabel jl3 = new JLabel("group");
    private JLabel jl4 = new JLabel("permission");
    private JTextField jtf1 = new JTextField();         //add user.username
    private JTextField jtf2 = new JTextField();         //add user.password
    private JTextField jtf3 = new JTextField();         //delete user by username

    private JTextArea jta = new JTextArea();            //all users are shown here (except admin)
    private JScrollPane jsp = new JScrollPane();

    private JComboBox jcb1=new JComboBox(arr1);           //add user.level
    private JComboBox jcb2=new JComboBox(arr2);           //add user.group
    private JButton btn1 = new JButton("add user");
    private JButton btn2 = new JButton("query user");
    private JButton btn3 = new JButton("delete user");

    public FrameAdminAcl(){
        this.setTitle("acl");
        this.setSize(500, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(30,30,70,30);
        jtf1.setBounds(100,30,100,30);
        jl2.setBounds(30,70,70,30);
        jtf2.setBounds(100,70,100,30);
        jl3.setBounds(30,110,70,30);
        jcb1.setBounds(100,110,100,30);
        jl4.setBounds(30,150,70,30);
        jcb2.setBounds(100,150,100,30);
        btn1.setBounds(300,90,120,30);

        jta.setBounds(30,240,220,100);
        jsp.setBounds(30,240,220,100);
        btn2.setBounds(300,260,120,30);

        jtf3.setBounds(100,410,100,30);
        btn3.setBounds(300,410,120,30);

        panel.add(jl1);
        panel.add(jtf1);
        panel.add(jl2);
        panel.add(jtf2);
        panel.add(jl3);
        panel.add(jcb1);
        panel.add(jl4);
        panel.add(jcb2);

        panel.add(jta);
        panel.add(jsp);

        panel.add(jtf3);
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);

        jsp.setViewportView(jta);
        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);

        listener();
    }
    private void listener(){
        File file = new File("src/test/resources/user_acl.txt");
        btn1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                        FileOperationAcl fileOperation = new FileOperationAcl(file);

                        if (jtf1.getText().trim().length() == 0 || jtf2.getText().trim().length() == 0
                                || jcb1.getSelectedIndex() == 0){
                            JOptionPane.showMessageDialog(null, "username or password or group cannot be empty");
                        }
                        else if (jtf1.getText().indexOf(" ") != -1 || jtf2.getText().indexOf(" ") != -1){
                            //cannot input space as username or password
                            JOptionPane.showMessageDialog(null, "you cannot use space as your username or password");
                        }
                        else{
                            ArrayList<UserAcl> userArrayList = new ArrayList<UserAcl>();
                            userArrayList = fileOperation.read();
                            UserDaoImplementAcl udi = new UserDaoImplementAcl();
                            UserAcl user = new UserAcl(jtf1.getText(),jtf2.getText(),jcb1.getSelectedIndex(),jcb2.getSelectedIndex());
                            if (udi.addCheck(userArrayList, user)){
                                String content = jtf1.getText() + " " + jtf2.getText() + " " + jcb1.getSelectedIndex() + " " + jcb2.getSelectedIndex() + System.lineSeparator();
                                if (fileOperation.write(content)){
                                    JOptionPane.showMessageDialog(null,"success!");
                                }

                            }
                            else{
                                JOptionPane.showMessageDialog(null,"the user " + jtf1.getText() + "already exists");
                            }

                        }
                    }
                }
        );
        btn2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<UserAcl> userArrayList = new ArrayList<UserAcl>();
                        FileOperationAcl fileOperation = new FileOperationAcl(file);
                        userArrayList = fileOperation.read();
                        String str = "";
                        for (UserAcl tmp: userArrayList){
                            str = str + tmp.getUserName() + System.lineSeparator();
                        }
                        jta.setText(str);
                    }
                }
        );
        btn3.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FileOperationAcl fileOperation = new FileOperationAcl(file);
                        boolean flag = fileOperation.deleteLine(jtf3.getText());
                        if (flag) {
                            JOptionPane.showMessageDialog(null,"success!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"the user is not found!");
                        }
                    }
                }
        );
    }

}
