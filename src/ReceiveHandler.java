import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveHandler extends TransmitHandler{

    private Socket receiveSocket;
    private String saveDirectory;

    public ReceiveHandler(Socket receiveSocket, JTextArea command, Record recorder, Control controller, String saveDirectory){
        super(command, recorder, controller);
        this.receiveSocket = receiveSocket;
        this.saveDirectory = saveDirectory;
    }

    public void run(){
        try {
            DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
            String taskID = dis.readUTF();
            super.command.append("Task" + taskID + " started receiving" + "\n");
            ArrayList<String> task = new ArrayList<String>();
            int fileNum = dis.readInt();
            FileInfo[] fileInfos = new FileInfo[fileNum];
            for (int i=0; i<fileNum; i++){
                String fileName = dis.readUTF();
                task.add(fileName);
                Long fileLength = dis.readLong();
                task.add("0%");
                fileInfos[i] = new FileInfo(fileName, fileLength);
            }
            long totalSize = dis.readLong();
            super.recorder.addReceiveTask(taskID, task);
            super.controller.setReceiveControl(taskID);

            int leftSize = 0; // left byte size in buffer
            int bufferSize = 0; // byte size in buffer
            int writeSize = 0; // write byte size
            long writtenSize = 0; // written byte size
            long totalWrittenSize = 0; // total written byte size
            byte[] bytes = new byte[8192];
            for (int j=0; j<fileNum; j++) {
                writtenSize = 0;
                FileOutputStream fos = new FileOutputStream(this.saveDirectory + fileInfos[j].getFileName());
                super.command.append("Task" + taskID + " start receiving file: " + fileInfos[j].getFileName() + "\n");

                while (true) {
                    if (leftSize > 0) {
                        bufferSize = leftSize;
                    } else {
                        bufferSize = dis.read(bytes);
                    }
                    if (bufferSize == -1)
                        return;
                    // check if the bytes will be written exceeds the file
                    if (writtenSize + bufferSize >= fileInfos[j].getFileLength()) {
                        leftSize = (int) (writtenSize + bufferSize - fileInfos[j].getFileLength());
                        writeSize = bufferSize - leftSize;
                        // write part of bytes
                        fos.write(bytes, 0, writeSize);
                        super.recorder.updateTask(taskID, 2*j+1, "100%");
                        totalWrittenSize += writeSize;
                        move(bytes, writeSize, leftSize);
                        break;
                    } else {
                        // write all bytes
                        while (!super.controller.getReceiving(taskID)){

                        }
                        fos.write(bytes, 0, bufferSize);
                        writtenSize += bufferSize;
                        super.recorder.updateTask(taskID, 2*j+1, Math.round(writtenSize*1.0/fileInfos[j].getFileLength()*100)+"%");
                        totalWrittenSize += bufferSize;
                        if (totalWrittenSize >= totalSize) {
                            return;
                        }
                        leftSize = 0;
                    }
                }
                fos.close();
            }
        } catch (Exception e) {
            java.lang.System.out.println("Receive Error."+e);
            e.printStackTrace();
        }
    }

    private static void move(byte[] bytes, int writeSize, int leftSize){
        for (int i = 0; i < leftSize; i++) {
            if(bytes[writeSize+i] != 0){
                bytes[i] = bytes[writeSize+i];
            }
        }
    }
}
