import javax.swing.*;

public abstract class TransmitHandler extends Thread {

    public Record recorder;
    public Control controller;
    public JTextArea command;

    public TransmitHandler(JTextArea command, Record recorder, Control controller){
        this.recorder = recorder;
        this.controller = controller;
        this.command = command;
    }

    public void run(){}
}
