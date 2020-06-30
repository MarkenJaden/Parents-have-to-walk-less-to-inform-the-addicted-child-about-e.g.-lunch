import java.net.Socket;

public class Main {

    private static boolean end = false;

    public static Socket s;

    public static void main(String[] args) {
        TimerTasks timerTasks = new TimerTasks();
        timerTasks.checkForCommands();
    }
}
