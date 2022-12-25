public class Client {

    private int port;
    private Record recorder;
    private Control controller;
    private Connect connector;
    private Send sender;
    private Receive receiver;
    private GUI ui;

    public Client(int port){
        this.port = port;
        this.recorder = new TaskRecord();
        this.controller = new TaskControl(this.recorder);
        this.connector = new SocketConnect();
        this.sender = new Send(port, this.recorder, this.controller, this.connector);
        this.receiver = new Receive(port, this.recorder, this.controller);
        this.ui = new GUI(port, recorder, controller, connector, sender, receiver);
    }

    public void showUI(){
        this.ui.showUI();
    }

    public void runReceiver(){
        this.receiver.taskHandleThread.start();
    }
}
