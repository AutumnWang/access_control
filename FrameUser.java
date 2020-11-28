package ui;

import utils.BLPModel;
import utils.BibaModel;
import utils.FileOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;



/**
 * @date 2020/11/7
 */

public class FrameUser extends JFrame {

    private JLabel jl1 = new JLabel("current user");
    private JLabel jl2 = new JLabel("level");
    private JTextField jtf1 = new JTextField();     //current username, can't edit
    private JTextField jtf2 = new JTextField();     //current user's level, can't edit
    private JTextField jtf3 = new JTextField();     //name of file which you want to create
    private JTextField jtf4 = new JTextField();     //path of file you select, can't edit

    private JTextArea jta1 = new JTextArea();
    private JScrollPane jsp1 = new JScrollPane();
    private JTextArea jta2 = new JTextArea();
    private JScrollPane jsp2 = new JScrollPane();


    private JButton btn1 = new JButton("create file");
    private JButton btn2 = new JButton("select file");
    private JButton btn3 = new JButton("read");
    private JButton btn4 = new JButton("write");
    private JButton btn5 = new JButton("reset");

    private JLabel jl3 = new JLabel("select a model");
    private String[] arr = {"select","Biba","Bell-LaPuda"};
    private JComboBox jcb = new JComboBox(arr);

    public FrameUser(){
        this.setTitle("user");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        jl1.setBounds(30,30,100,30);
        jtf1.setBounds(140,30,100,30);
        jl2.setBounds(30,60,100,30);
        jtf2.setBounds(140,60,100,30);

        btn1.setBounds(20,120,100,30);
        jtf3.setBounds(140,120,300,30);

        jl3.setBounds(30,180,100,30);
        jcb.setBounds(140,180,100,30);

        btn2.setBounds(20,215,100,30);
        jtf4.setBounds(140,215,300,30);

        btn3.setBounds(20,250,100,30);
        jta1.setBounds(30,280,440,70);
        jsp1.setBounds(30,280,440,70);

        btn4.setBounds(20,360,100,30);
        btn5.setBounds(130,360,100,30);
        jta2.setBounds(30,390,440,70);
        jsp2.setBounds(30,390,440,70);

        jtf1.setEditable(false);
        jtf2.setEditable(false);
        jtf4.setEditable(false);

        panel.add(jl1);
        panel.add(jtf1);
        panel.add(jl2);
        panel.add(jtf2);

        panel.add(btn1);
        panel.add(jtf3);

        panel.add(jl3);
        panel.add(jcb);

        panel.add(btn2);
        panel.add(jtf4);

        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);

        panel.add(jta1);
        panel.add(jsp1);
        jta1.setLineWrap(true);
        jsp1.setViewportView(jta1);

        panel.add(jta2);
        panel.add(jsp2);
        jta2.setLineWrap(true);
        jsp2.setViewportView(jta2);

        this.setContentPane(panel);
        this.setResizable(false);
        this.setVisible(true);

        listener();
    }

    public void setUsername(String username){
        jtf1.setText(username);
    }

    public void setLevel(int level){
        jtf2.setText(String.valueOf(level));
    }

    private void listener() {
        btn1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        File file = new File("src/test/" + jtf3.getText() + ".txt");
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
                                FileOperation fileOperation = new FileOperation(file);
                                if(fileOperation.write("created by user:" + jtf1.getText() + System.lineSeparator())) {
                                    JOptionPane.showMessageDialog(null, "success!");
                                }
                            }
                        }
                    }
                }
        );

        btn2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser jfc = new JFileChooser(new File("src/test"));  //set default directory
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

        btn3.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = jtf1.getText();
                        File file1 = new File(jtf4.getText());
                        File file2 = new File("src/test/user.txt");
                        jta1.setText("");
                        switch(jcb.getSelectedIndex()){

                            // Biba Model
                            case 1:
                                BibaModel bibaModel = new BibaModel();
                                if(bibaModel.isBibaRead(username, file1, file2)){
                                    jta1.append("the user(name:" + bibaModel.getUsernameOfSubject() + ", level:" +
                                            bibaModel.getLevelOfSubject() + ") wants to read the file " +
                                            System.lineSeparator() + file1.getName() + "(created by user:" +
                                            bibaModel.getUsernameOfObject() + ", level:" + bibaModel.getLevelOfObject() +
                                            ")" + System.lineSeparator());
                                    jta1.append("success" + System.lineSeparator());
                                    jta1.append("content:" + System.lineSeparator() + bibaModel.getContent());
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,
                                            "your level is too high, you cannot read the file");
                                }
                                break;

                            // BLP Model
                            case 2:
                                BLPModel blpModel = new BLPModel();

                                if (blpModel.isReadAllowed(username, file1, file2)) {
                                    jta1.append("the user(name:" + blpModel.getSubjectName() + ", level:"
                                            + blpModel.getSubjectClearance() + ") wants to read the file "
                                            + System.lineSeparator() + file1.getName() + "(created by user:"
                                            + blpModel.getObjectName() + ", level:" + blpModel.getObjectClearance()
                                            + ")" + System.lineSeparator());
                                    jta1.append("success" + System.lineSeparator());
                                    jta1.append("content:" + System.lineSeparator() + blpModel.getContent());
                                }

                                else {
                                    JOptionPane.showMessageDialog(null,
                                            "your level is too low, you cannot read the file (no read up!)");
                                }
                            default:
                                JOptionPane.showMessageDialog(null, "you must select a model!");
                                break;
                        }
                    }
                }
        );

        btn4.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = jtf1.getText();
                        File file1 = new File(jtf4.getText());
                        File file2 = new File("src/test/user.txt");
                        switch(jcb.getSelectedIndex()){
                            case 1:     //biba model
                                BibaModel bibaModel = new BibaModel();
                                if (jta2.getText().trim().length() == 0){
                                    JOptionPane.showMessageDialog(null, "please input something!");
                                }
                                else{
                                    if(bibaModel.isBibaWrite(username, file1, file2, jta2.getText())){
                                        jta2.setText("");
                                        jta2.append("the user(name:" + bibaModel.getUsernameOfSubject() + ", level:" +
                                                bibaModel.getLevelOfSubject() + ") wants to write to the file " +
                                                System.lineSeparator() + file1.getName() + "(created by user:" +
                                                bibaModel.getUsernameOfObject() + ", level:" + bibaModel.getLevelOfObject()
                                                + ")" + System.lineSeparator());
                                        jta2.append("success" + System.lineSeparator());
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null,
                                                "your level is too low, you cannot write to the file");
                                    }
                                }
                                break;

                            // BLP Model
                            case 2:
                                BLPModel blpModel = new BLPModel();

                                if (jta2.getText().trim().length() == 0) {
                                    JOptionPane.showMessageDialog(null, "please input something!");
                                }

                                else {
                                    if (blpModel.isWriteAllowed(username, file1, file2, jta2.getText())) {
                                        jta2.setText("");
                                        jta2.append("the user(name:" + blpModel.getSubjectName() + ", level:"
                                                + blpModel.getSubjectClearance() + ") wants to write to the file "
                                                + System.lineSeparator() + file1.getName() + "(created by user:"
                                                + blpModel.getObjectName() + ", level:"
                                                + blpModel.getObjectClearance() + ")" + System.lineSeparator());
                                        jta2.append("success" + System.lineSeparator());
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "your level is too high, you cannot write to the file (no write down!)");
                                    }
                                }
                                break;

                            default:
                                JOptionPane.showMessageDialog(null, "you must select a model!");
                                break;
                        }
                    }
                }
        );

        //empty jtextarea
        btn5.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jta2.setText("");
                    }
                }
        );
    }
}

