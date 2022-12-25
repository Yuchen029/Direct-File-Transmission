import javax.swing.*;

public class SocketConnect implements Connect {

    private String connectIP;
    private int connectPort;

    public SocketConnect(){

    }

    public String connect(String ip, int port){
        this.connectIP = ip;
        this.connectPort = port;
        return ip + ":" + port + " is connected" + "\n";
    }

    public String disConnect(){
        this.connectIP = null;
        this.connectPort = 0;
        return "disconnect successful";
    }

    public String getConnectIP(){
        return connectIP;
    }

    public int getConnectPort(){
        return connectPort;
    }


}
