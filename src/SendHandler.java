import javax.swing.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SendHandler extends TransmitHandler {

    private Socket sendSocket;
    private int port;

    public SendHandler(int port, Socket sendSocket, JTextArea command, Record recorder, Control controller){
        super(command, recorder, controller);
        this.port = port;
        this.sendSocket = sendSocket;
    }

    public void run(){
        try {
            File[] files = Select.selectMultiFile();
            if (files != null){
                DataOutputStream dos = new DataOutputStream(sendSocket.getOutputStream());
                String taskID = String.valueOf(port) + (super.recorder.getSendTaskNum() + 1);
                super.command.append("Task" + taskID + " started sending" + "\n");
                ArrayList<String> task = new ArrayList<String>();
                for (int i=0; i<files.length; i++){
                    task.add(files[i].getName());
                    task.add("0%");
                }
                super.recorder.addSendTask(taskID, task);
                super.controller.setSendControl(taskID);

                dos.writeUTF(taskID);
                dos.flush();
                dos.writeInt(files.length);
                dos.flush();

                long totalSize = 0;
                for (int i=0; i<files.length; i++){
                    dos.writeUTF(files[i].getName());
                    dos.flush();
                    dos.writeLong(files[i].length());
                    dos.flush();
                    totalSize += files[i].length();
                }
                dos.writeLong(totalSize);
                dos.flush();

                for (int j=0; j<files.length; j++){
                    FileInputStream fis = new FileInputStream(files[j]);
                    super.command.append("Task" + taskID + " start sending file: " + files[j].getName() + "\n");
                    byte[] bytes = new byte[8192];
                    long writtenSize = 0;
                    int writeSize = 0;
                    // start sending
                    while ((writeSize = fis.read(bytes, 0, bytes.length)) != -1) {
                        while (!super.controller.getSending(taskID)){

                        }
                        dos.write(bytes, 0, writeSize);
                        dos.flush();
                        writtenSize += writeSize;
                        super.recorder.updateTask(taskID, 2*j+1, Math.round(writtenSize*1.0/files[j].length()*100)+"%");
                    }
                    fis.close();
                }
                dos.close();
            }
        } catch (Exception e) {
            java.lang.System.out.println("Send Error."+e);
            e.printStackTrace();
        }
    }
}
