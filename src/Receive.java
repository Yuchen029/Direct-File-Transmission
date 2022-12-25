import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Receive extends Transmit{

    private JTextArea command;
    private String saveDirectory;

    public Receive(int port, Record recorder, Control controller){
        super(port, recorder, controller);
        this.saveDirectory = "save/";
    }

    Thread taskHandleThread = new Thread() {
        public void run() {
            ServerSocket receiverSocket = null;
            try {
                // build socket to receive file from other clients
                receiverSocket = new ServerSocket(port);
                command.append("receiver started" + "\n");
            } catch (Exception e) {
                java.lang.System.out.println("Receiver Socket Error." + e);
            }
            Socket receiveSocket = null;
            while (true) {
                try {
                    // wait for other clients to connect
                    receiveSocket = receiverSocket.accept();
                    new ReceiveHandler(receiveSocket, command, Receive.super.recorder, Receive.super.controller, saveDirectory).start();
                } catch (Exception e) {
                    java.lang.System.out.println("Receiver Thread Error." + e);
                }
            }
        }
    };

    public void setCommand(JTextArea command){
        this.command = command;
    }
}
