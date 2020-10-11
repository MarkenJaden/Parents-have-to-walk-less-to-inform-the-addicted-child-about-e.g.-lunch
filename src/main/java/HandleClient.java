import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;

public class HandleClient extends Thread {

    protected Socket s;

    private String stringData = null;

    public HandleClient(Socket clientSocket) {
        this.s = clientSocket;
    }

    public void run() {

        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter output = new PrintWriter(s.getOutputStream());

            stringData = input.readLine();
            System.out.println(new Timestamp(System.currentTimeMillis()) + " : " + s.getRemoteSocketAddress() + " " + stringData);
            switch (stringData) {
                case "checkForCommands":
                    String commands = "";
                    for (String cmd : Storgae.commandsOnWait) {
                        commands = commands + cmd + " ";
                    }
                    output.println(commands);
                    output.flush();
                    Storgae.commandsOnWait.clear();
                    break;
                case "list":
                    for (String cmd : Storgae.commandsOnWait) {
                        System.out.println(cmd);
                    }
                    break;
                case "clear":
                    Storgae.commandsOnWait.clear();
                    System.out.println("--- STORED COMMANDS CLEARED ---");
                    break;
                case "restart":
                    System.out.println("Server restart initiated.");
                    Main.restartServerSocket();
                    break;
                case "stop":
                    Main.stopServerSocket();
                    break;
                default:
                    output.println(stringData.toUpperCase());
                    Storgae.commandsOnWait.add(stringData);
                    output.flush();
                    break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            output.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
