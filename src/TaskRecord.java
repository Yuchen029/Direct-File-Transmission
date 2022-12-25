import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskRecord implements Record {

    private static Map<String, ArrayList<String>> sendTasks;
    private static Map<String, ArrayList<String>> receiveTasks;

    public TaskRecord(){
        sendTasks = new HashMap<>();
        receiveTasks = new HashMap<>();
    }

    public int getSendTaskNum(){
        return sendTasks.size();
    }

    private ArrayList<String> getTask(String taskID){
        if (sendTasks.get(taskID) != null){
            return sendTasks.get(taskID);
        } else {
            return receiveTasks.get(taskID);
        }
    }

    public void addSendTask(String taskID, ArrayList<String> task){
        sendTasks.put(taskID, task);
    }

    public void addReceiveTask(String taskID, ArrayList<String> task){
        receiveTasks.put(taskID, task);
    }

    public void updateTask(String taskID, int parameter, String value){
        if (sendTasks.get(taskID) != null){
            sendTasks.get(taskID).set(parameter, value);
        } else {
            receiveTasks.get(taskID).set(parameter, value);
        }
    }

    public Boolean checkSendTasks(String taskID){
        if (sendTasks.get(taskID) != null){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkReceiveTasks(String taskID){
        if (receiveTasks.get(taskID) != null){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> queryTask(String taskID){
        ArrayList<String> task = getTask(taskID);
        ArrayList<String> display = new ArrayList<>();
        display.add("Task" + taskID + "\n");
        for (int i=0; i<task.size()/2; i++){
            display.add(task.get(2*i) + "\n");
            display.add(task.get(2*i+1) + "\n");
        }
        return display;
    }
}
