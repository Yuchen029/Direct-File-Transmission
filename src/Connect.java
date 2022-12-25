import javax.swing.*;

public interface Connect {

    public String connect(String ip, int port);

    public String disConnect();

    public String getConnectIP();

    public int getConnectPort();
}
