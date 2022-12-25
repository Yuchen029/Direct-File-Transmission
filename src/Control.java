public interface Control {

    public void setSendControl(String taskID);

    public void setReceiveControl(String taskID);

    public Boolean getSending(String taskID);

    public Boolean getReceiving(String taskID);

    public void suspendSending(String taskID);

    public void continueSending(String taskID);

    public void suspendReceiving(String taskID);

    public void continueReceiving(String taskID);

    public void suspendTask(String taskID);

    public void continueTask(String taskID);

}
