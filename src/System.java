public class System{
    public static void main(String[] args) {
        for(int i=0; i<2; i++) {

            int finalI = i;
            new Thread(() -> {
                Client client = new Client(9282+ finalI);
                client.showUI();
                client.runReceiver();
            }).start();

        }
    }
}
