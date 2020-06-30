import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            System.out.println(s.getRemoteSocketAddress() + " " + stringData);
            switch (stringData) {
                case "checkForCommands":
                    String commands = "";
                    for (String cmd : Main.commandsOnWait) {
                        commands = commands + cmd + " ";
                    }
                    output.println(commands);
                    output.flush();
                    Main.commandsOnWait.clear();
                    break;
                case "list":
                    for (String cmd : Main.commandsOnWait) {
                        System.out.println(cmd);
                    }
                    break;
                case "clear":
                    Main.commandsOnWait.clear();
                    System.out.println("--- COMMANDS CLEARED ---");
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
                    Main.commandsOnWait.add(stringData);
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
