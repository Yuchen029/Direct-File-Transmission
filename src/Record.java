import javax.swing.*;
import java.util.ArrayList;

public interface Record {

    public int getSendTaskNum();

    public void addSendTask(String taskID, ArrayList<String> task);

    public void addReceiveTask(String taskID, ArrayList<String> task);

    public void updateTask(String taskID, int parameter, String value);

    public Boolean checkSendTasks(String taskID);

    public Boolean checkReceiveTasks(String taskID);

    public ArrayList<String> queryTask(String taskID);
}
