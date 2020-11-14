package Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lianpeng zuo
 */

public class FrameLogin extends JFrame{

    private JLabel jl1 = new JLabel("username");
    private JLabel jl2 = new JLabel("password");
    private JTextField jtf1 = new JTextField();
    private JTextField jtf2 = new JTextField();

    private JButton btn1 = new JButton("login");

    public FrameLogin(){

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(160,140,70,30);
        jtf1.setBounds(230,140,100,30);
        jl2.setBounds(160,180,70,30);
        jtf2.setBounds(230,180,100,30);
        btn1.setBounds(190,260,120,30);

        panel.add(jl1);
        panel.add(jtf1);
        panel.add(jl2);
        panel.add(jtf2);
        panel.add(btn1);

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
                        String username = jtf1.getText();
                        if (username.equals("1")){
                            new FrameAdmin();
                        }
                        else {
                            new FrameUser();
                        }
                    }
                }
        );
    }
}
