import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskControl implements Control {

    private static Map<String, Boolean> sending;
    private static Map<String, Boolean> receiving;
    private Record recorder;

    public TaskControl(Record recorder){
        sending = new HashMap<>();
        receiving = new HashMap<>();
        this.recorder = recorder;
    }

    public void setSendControl(String taskID){
        sending.put(taskID, true);

    }

    public void setReceiveControl(String taskID){
        receiving.put(taskID, true);
    }

    public Boolean getSending(String taskID){
        return sending.get(taskID);
    }

    public Boolean getReceiving(String taskID){
        return receiving.get(taskID);
    }

    public void suspendSending(String taskID){
        sending.put(taskID, false);
    }

    public void continueSending(String taskID){
        sending.put(taskID, true);
    }

    public void suspendReceiving(String taskID){
        receiving.put(taskID, false);
    }

    public void continueReceiving(String taskID){
        receiving.put(taskID, true);
    }

    public void suspendTask(String taskID){
        if (recorder.checkSendTasks(taskID)){
            suspendSending(taskID);
        } else if (recorder.checkReceiveTasks(taskID)){
            suspendReceiving(taskID);
        }
    }

    public void continueTask(String taskID){
        if (recorder.checkSendTasks(taskID)){
            continueSending(taskID);
        } else if (recorder.checkReceiveTasks(taskID)){
            continueReceiving(taskID);
        }
    }
}
