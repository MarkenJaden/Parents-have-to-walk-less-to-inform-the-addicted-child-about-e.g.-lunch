import network.client.Client;
import network.client.ClientListener;
import network.common.Command;
import network.common.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClientController {

    public static boolean muted = false;

    public void checkForCommands() {
        Client client = new Client("116.202.236.57", 1002, new Logger());
        client.addClientListener(new ClientListener() {
            @Override
            public void messageReceived(Client client, Object msg) {
                switch ((String) msg) {
                    case "online":
                        client.send("online=true");
                        break;
                    case "essen":
                        if (!muted) {
                            InputStream in = null;
                            try {
                                in = new FileInputStream("D:/SignalToPC/foodIsReady.wav");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            AudioStream as = null;
                            try {
                                as = new AudioStream(in);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AudioPlayer.player.start(as);
                        } else {
                            TrayStuff.displayTrayNotification("Essen ist fertig!", "Essen ist fertig. Komm jetzt herunter.", TrayIcon.MessageType.INFO);
                        }
                        break;
                    case "sleep":
                        if (!muted) {
                            InputStream in1 = null;
                            try {
                                in1 = new FileInputStream("D:/SignalToPC/Snoring.wav");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            AudioStream as1 = null;
                            try {
                                as1 = new AudioStream(in1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AudioPlayer.player.start(as1);
                        } else {
                            TrayStuff.displayTrayNotification("Leise sein!", "Papa muss schlafen. Sei bitte jetzt ruhig.", TrayIcon.MessageType.WARNING);
                        }
                        break;
                }
            }

            @Override
            public void commandReceived(Client client, Command cmd) {

            }

            @Override
            public void disconnected(Client client) {
                
            }

            @Override
            public void messageSent(Client client, Object msg) {

            }

            @Override
            public void commandSent(Client client, Command cmd) {

            }

            @Override
            public void connected(Client client) {
                client.send("online=true");
            }
        });
        client.start();
    }

//    public void checkForCommands() {
//        TimerTask repeatedTask = new TimerTask() {
//            public void run() {
//                try {
//                    Socket s = new Socket("116.202.236.57", 1002);
//                    s.setReuseAddress(true);
//
//                    OutputStream out = s.getOutputStream();
//
//                    PrintWriter output = new PrintWriter(out);
//
//                    output.println("checkForCommands");
//                    output.flush();
//                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
//                    String command = null;
//                    if ((command = input.readLine()) != null && !command.equals("")) {
//                        System.out.println(command);
//                        String[] commands = command.split(" ");
//                        for (String cmd : commands) {
//                            cl.checkCommand(cmd);
//                        }
//                    }
//
//                    output.close();
//                    out.close();
//                    s.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        Timer timer = new Timer("Timer");
//
//        long delay = 1000L;
//        long period = 1000L * 10L;
//        timer.scheduleAtFixedRate(repeatedTask, delay, period);
//    }

}
