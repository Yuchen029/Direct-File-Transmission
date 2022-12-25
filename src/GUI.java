import javax.swing.*;
import java.util.ArrayList;

public class GUI {

    private int port;
    private JTextArea command;
    private JFrame frame;
    private Record recorder;
    private Control controller;
    private Connect connector;
    private Send sender;
    private Receive receiver;

    public GUI(int port, Record recorder, Control controller, Connect connector, Send sender, Receive receiver){
        this.port = port;
        this.recorder = recorder;
        this.controller = controller;
        this.connector = connector;
        this.sender = sender;
        this.receiver = receiver;
        this.command = new JTextArea(20, 50);
        this.sender.setCommand(this.command);
        this.receiver.setCommand(this.command);
        createFrame();
    }

    public void showUI(){
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    private void createFrame(){

        // create Frame
        this.frame = new JFrame(String.valueOf(this.port));
        // Setting the width and height of frame
        this.frame.setSize(620, 500);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create Panel
        JPanel panel = new JPanel();
        // add Panel
        this.frame.add(panel);

        // create command line display
        this.command.setBounds(40, 150, 260, 280);
        panel.add(this.command);

        panel.setLayout(null);

        // create ip Label
        JLabel ipLabel = new JLabel("IP Address:", JLabel.CENTER);
        ipLabel.setBounds(40,40,80,25);
        panel.add(ipLabel);

        // create ip textField
        JTextField ipText = new JTextField(20);
        ipText.setBounds(140,40,160,25);
        ipText.setText("127.0.0.1");
        panel.add(ipText);

        // create port Label
        JLabel portLabel = new JLabel("Port:", JLabel.CENTER);
        portLabel.setBounds(40,90,80,25);
        panel.add(portLabel);

        // create port textField
        JTextField portText = new JTextField(20);
        portText.setBounds(140,90,160,25);
        panel.add(portText);

        // create connect button
        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(340, 65, 100, 25);
        panel.add(connectButton);
        connectButton.addActionListener((actionEvent -> {
            try {
                if (ipText.getText() != "" && portText.getText() != "") {
                    command.append(this.connector.connect(ipText.getText(), Integer.parseInt(portText.getText())));
                }
            } catch (Exception e) {
                java.lang.System.out.println("Main Error." + e);
            }
        }));

        // create disconnect button
        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.setBounds(460, 65, 100, 25);
        panel.add(disconnectButton);
        disconnectButton.addActionListener((actionEvent -> {
            try {
                command.append(this.connector.disConnect());
            } catch (Exception e) {
                java.lang.System.out.println("Main Error." + e);
            }
        }));

        // create task button
        JButton taskButton = new JButton("Begin a task");
        taskButton.setBounds(370, 150, 160, 25);
        panel.add(taskButton);
        taskButton.addActionListener((actionEvent -> {
            try {
                this.sender.sendFiles();
            } catch (Exception e) {
                java.lang.System.out.println("Main Error." + e);
            }
        }));

        // create query Label
        JLabel queryLabel = new JLabel("Query a task", JLabel.CENTER);
        queryLabel.setBounds(400,200,100,25);
        panel.add(queryLabel);

        // create query textField
        JTextField queryText = new JTextField(20);
        queryText.setBounds(370,250,50,25);
        panel.add(queryText);

        // create query button
        JButton queryButton = new JButton("Query");
        queryButton.setBounds(440, 250, 90, 25);
        panel.add(queryButton);
        queryButton.addActionListener((actionEvent -> {
            try {
                if (queryText.getText() != "") {
                    ArrayList<String> display = this.recorder.queryTask(queryText.getText());
                    for (int i=0; i<display.size(); i++){
                        command.append(display.get(i));
                    }
                }
            } catch (Exception e) {
                java.lang.System.out.println("Main Error." + e);
            }
        }));

        // create suspend Label
        JLabel suspendLabel = new JLabel("Suspend a task", JLabel.CENTER);
        suspendLabel.setBounds(400,300,100,25);
        panel.add(suspendLabel);

        // create suspend textField
        JTextField suspendText = new JTextField(20);
        suspendText.setBounds(370,375,50,25);
        panel.add(suspendText);

        // create suspend button
        JButton suspendButton = new JButton("Suspend");
        suspendButton.setBounds(440, 350, 90, 25);
        panel.add(suspendButton);
        suspendButton.addActionListener((actionEvent -> {
            try {
                if (suspendText.getText() != ""){
                    this.controller.suspendTask(suspendText.getText());
                }
            } catch (Exception e) {
                java.lang.System.out.println("Main Error." + e);
            }
        }));

        // create continue button
        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(440, 400, 90, 25);
        panel.add(continueButton);
        continueButton.addActionListener((actionEvent -> {
            try {
                if (suspendText.getText() != ""){
                    this.controller.continueTask(suspendText.getText());
                }
            } catch (Exception e) {
                java.lang.System.out.println("Main Error." + e);
            }
        }));
    }
}
