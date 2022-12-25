import javax.swing.*;
import java.net.Socket;

public class Send extends Transmit{

    private JTextArea command;
    private Connect connector;

    public Send(int port, Record recorder, Control controller, Connect connector){
        super(port, recorder, controller);
        this.connector = connector;
    }

    public void sendFiles(){
        try {
            Socket sendSocket = new Socket(connector.getConnectIP(), connector.getConnectPort());
            new SendHandler(super.port, sendSocket, command, super.recorder, super.controller).start();
        } catch (Exception e) {
            java.lang.System.out.println("File Send Error." + e);
        }
    }

    public void setCommand(JTextArea command){
        this.command = command;
    }
}
