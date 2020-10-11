import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TrayStuff {

    private static TrayIcon trayIcon = null;

    public TrayStuff() {
        //TrayIcon
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            Image image = Toolkit.getDefaultToolkit().getImage("child-solid.png");
            // create a action listener to listen for default action executed on the tray icon
            ItemListener muteListener = new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    ClientController.muted = !ClientController.muted;
                }
            };
            ActionListener stopListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();
            // create menu item for the default action
            CheckboxMenuItem mute = new CheckboxMenuItem("Mute");
            MenuItem stop = new MenuItem("Beenden");
            stop.addActionListener(stopListener);
            mute.addItemListener(muteListener);
            popup.add(mute);
            popup.add(stop);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "SignalToPC", popup);
            trayIcon.setImageAutoSize(true);
            // set the TrayIcon properties
//            trayIcon.addActionListener(listener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        } else {
            // disable tray option in your application or
            // perform other actions
        }
// ...
// some time later
// the application state has changed - update the image
//        if (trayIcon != null) {
//            trayIcon.setImage(updatedImage);
//        }
// ...
    }

    public static void displayTrayNotification(String title, String message, TrayIcon.MessageType type) {
        trayIcon.displayMessage(title, message, type);
    }
}
