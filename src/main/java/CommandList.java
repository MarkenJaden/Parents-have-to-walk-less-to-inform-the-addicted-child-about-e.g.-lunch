import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CommandList {

    public void checkCommand(String command) {
        switch (command) {
            case "essen":
                InputStream in = null;
                try {
                    in = new FileInputStream("D:/foodIsReady.wav");
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
                break;
            default:
                break;
        }
    }

}
