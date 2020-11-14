package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author lianpeng zuo
 */

public class FrameUser extends JFrame {

    private JLabel jl1 = new JLabel("current user");
    private JLabel jl2 = new JLabel("level");
    private JTextField jtf1 = new JTextField();
    private JTextField jtf2 = new JTextField();
    private JTextField jtf3 = new JTextField();
    private JTextField jtf4 = new JTextField();
    private JTextArea jta = new JTextArea();
    private JButton btn1 = new JButton("create file");
    private JButton btn2 = new JButton("select file");
    private JButton btn3 = new JButton("read");
    private JButton btn4 = new JButton("write");

    public FrameUser(){
        this.setTitle("User");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(30,30,100,30);
        jtf1.setBounds(140,30,100,30);
        jl2.setBounds(30,60,100,30);
        jtf2.setBounds(140,60,100,30);

        btn1.setBounds(20,130,100,30);
        jtf3.setBounds(140,130,300,30);

        btn2.setBounds(20,190,100,30);
        jtf4.setBounds(140,190,300,30);

        btn3.setBounds(20,250,100,30);
        btn4.setBounds(140,250,100,30);

        jta.setBounds(30,310,440,120);

        jtf1.setEditable(false);
        jtf2.setEditable(false);

        panel.add(jl1);
        panel.add(jtf1);
        panel.add(jl2);
        panel.add(jtf2);

        panel.add(btn1);
        panel.add(jtf3);

        panel.add(btn2);
        panel.add(jtf4);

        panel.add(btn3);
        panel.add(btn4);

        panel.add(jta);

        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);


        listener();
    }
    private void listener(){
        btn2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser jfc = new JFileChooser();
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
    }
}

