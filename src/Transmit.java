import javax.swing.*;

public abstract class Transmit {

    public int port;
    public Record recorder;
    public Control controller;
    public JTextArea command;

    public Transmit(int port, Record recorder, Control controller) {
        this.port = port;
        this.recorder = recorder;
        this.controller = controller;
    }

    public void setCommand(JTextArea command){

    }
}
