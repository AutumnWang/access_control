package ui;

import utils.FileOperation;
import utils.User;
import utils.UserDaoImplement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * @date 2020/11/7
 */

public class FrameLogin extends JFrame{

    private JLabel jl1 = new JLabel("username");
    private JLabel jl2 = new JLabel("password");
    private JTextField jtf = new JTextField();
    private JPasswordField jpf = new JPasswordField();

    private JButton btn1 = new JButton("login");

    public FrameLogin(){

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(160,140,70,30);
        jtf.setBounds(230,140,100,30);
        jl2.setBounds(160,180,70,30);
        jpf.setBounds(230,180,100,30);
        btn1.setBounds(190,260,120,30);

        panel.add(jl1);
        panel.add(jtf);
        panel.add(jl2);
        panel.add(jpf);
        panel.add(btn1);

        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);

        listener();
    }

    private void listener(){
        File file = new File("src/test/user.txt");
        btn1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        UserDaoImplement udi = new UserDaoImplement();
                        ArrayList<User> userArrayList = new ArrayList<User>();
                        FileOperation fileOperation = new FileOperation(file);

                        String username = jtf.getText();
                        String pwd = String.valueOf(jpf.getPassword());
                        int level = 0;

                        userArrayList = fileOperation.read();
                        boolean flag = udi.isValid(userArrayList, username, pwd);

                        if (username.equals("admin") && pwd.equals("123456")){
                            new FrameAdmin();
                        }
                        else if (flag){
                            for (User tmp : userArrayList){
                                if (tmp.getUserName().equals(username)){
                                    level = tmp.getLevel();
                                    break;
                                }
                            }
                            FrameUser frameUser = new FrameUser();
                            frameUser.setUsername(jtf.getText());
                            frameUser.setLevel(level);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"your username or password is invalid");
                        }
                    }
                }
        );
    }
}
