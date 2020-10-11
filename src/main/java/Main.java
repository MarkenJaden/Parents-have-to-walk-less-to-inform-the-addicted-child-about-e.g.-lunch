import java.net.Socket;

public class Main {

    private static boolean end = false;

    public static Socket s;

    public static void main(String[] args) {

        TrayStuff ts = new TrayStuff();

        ClientController cc = new ClientController();
        cc.checkForCommands();
    }
}
