import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    private static boolean end = false;
    public static ArrayList<String> commandsOnWait = new ArrayList();

    private static ServerSocket ss;

    public static void main(String[] args) throws IOException {
        startServerSocket();
    }

    private static void startServerSocket() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    ss = new ServerSocket(1002);
                    System.out.println("Server online.");

                    while (!end) {
                        //Server is waiting for client here, if needed
                        Socket s = null;
                        while (true) {
                            try {
                                s = ss.accept();
                            } catch (IOException e) {
                                System.err.println("I/O error: " + e);
                            }
                            new HandleClient(s).start();
                        }


                    }
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }

    public static void restartServerSocket() {
        end = true;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ss.isClosed()) {
            end = false;
            startServerSocket();
        } else {
            System.out.println("ERROR DURING RESTART");
        }
    }

    public static void stopServerSocket() {
        end = true;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Server offline.");
    }

//        if (args.length != 1) {
//            System.err.println("Usage: java ServerBetweenPhoneAndPC <port number>");
//            System.exit(1);
//        }
//
//        int portNumber = Integer.parseInt(args[0]);
//        System.out.println("Server gestartet.");
//
//        try (
//                ServerSocket serverSocket = new ServerSocket(portNumber);
//                Socket clientSocket = serverSocket.accept();
//                PrintWriter out =
//                        new PrintWriter(clientSocket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(clientSocket.getInputStream()));
//        ) {
//
//            String inputLine, outputLine;
//
//            // Listen to input
//            Protocol protocol = new Protocol();
//
//            while (true) {
//                outputLine = protocol.processInput(in.readLine());
//                if(outputLine != null){
//                    out.println(outputLine);
//                }
//                if (outputLine.equals("Critiacl Error."))
//                    break;
//                out.close();
//                clientSocket.close();
//            }
//        } catch (IOException e) {
//            System.out.println("Exception caught when trying to listen on port "
//                    + portNumber + " or listening for a connection");
//            System.out.println(e.getMessage());
//        }
//    }

}
