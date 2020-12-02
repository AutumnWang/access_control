package src.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lianpeng zuo
 */

public class FrameAdmin extends JFrame {

    private String arr[]={"select","1","2","3","4"};
    private JLabel jl1 = new JLabel("username");
    private JLabel jl2 = new JLabel("password");
    private JLabel jl3 = new JLabel("level");
    private JTextField jtf1 = new JTextField();
    private JTextField jtf2 = new JTextField();
    private JTextField jtf3 = new JTextField();
    private JTextArea jta = new JTextArea();
    private JComboBox jcb=new JComboBox(arr);
    private JButton btn1 = new JButton("add user");
    private JButton btn2 = new JButton("query user");
    private JButton btn3 = new JButton("delete user");

    public FrameAdmin(){
        this.setTitle("Admin");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(30,30,70,30);
        jtf1.setBounds(100,30,100,30);
        jl2.setBounds(30,70,70,30);
        jtf2.setBounds(100,70,100,30);
        jl3.setBounds(30,110,70,30);
        jcb.setBounds(100,110,100,30);
        btn1.setBounds(300,70,120,30);

        jta.setBounds(30,200,220,100);
        btn2.setBounds(300,220,120,30);

        jtf3.setBounds(100,370,100,30);
        btn3.setBounds(300,370,120,30);

        panel.add(jl1);
        panel.add(jtf1);
        panel.add(jl2);
        panel.add(jtf2);
        panel.add(jl3);
        panel.add(jcb);
        panel.add(jta);
        panel.add(jtf3);
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);

        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);

        listener();
    }
    private void listener(){
        btn1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = jtf1.getText();
                        int level = jcb.getSelectedIndex();
                        if (name.equals(null) || name.trim().length() == 0
                                || level == 0){
                            JOptionPane.showMessageDialog(null,
                                    "Please input name or level");
                        }
                        else {
                            System.out.println(name);
                            System.out.println(level);
                        }
                    }
                }
        );
    }
}

