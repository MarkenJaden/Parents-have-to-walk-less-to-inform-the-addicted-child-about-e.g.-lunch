package net.landofrails.SignalToPC;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import network.client.Client;
import network.client.ClientListener;
import network.common.Command;
import network.common.Logger;

/**
 * Created by Girish Bhalerao on 5/4/2017.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextViewReplyFromServer;
    private EditText mEditTextSendMessage;
    private TextView status;

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonSleep = (Button) findViewById(R.id.btn_sleep);
        final Button buttonFood = (Button) findViewById(R.id.btn_essen);

        mTextViewReplyFromServer = (TextView) findViewById(R.id.tv_reply_from_server);
        status = (TextView) findViewById(R.id.status);

        buttonSleep.setOnClickListener(this);
        buttonFood.setOnClickListener(this);

        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.setUpdateFrom(UpdateFrom.JSON);
        appUpdater.setUpdateJSON("https://app.landofrails.net/update/update.json");
        appUpdater.setTitleOnUpdateAvailable("Update verfügbar");
        appUpdater.setButtonUpdate("Herunterladen");
        appUpdater.setButtonDismiss("Später");
        appUpdater.setButtonDoNotShowAgain("");
        appUpdater.start();

        client = new Client("116.202.236.57", 1002, new Logger());
        client.addClientListener(new ClientListener() {
            @Override
            public void messageReceived(Client client, Object o) {
                switch (o.toString()) {
                    case "online=true":
                        status.setTextColor(Color.GREEN);
                        status.setText("Jaden ist am PC");
                        buttonFood.setClickable(true);
                        buttonSleep.setClickable(true);
                        break;
                    case "online=false":
                        status.setTextColor(Color.RED);
                        status.setText("Jaden ist nicht am PC!");
                        buttonFood.setClickable(false);
                        buttonSleep.setClickable(false);
                        break;
                }
            }

            @Override
            public void commandReceived(Client client, Command command) {

            }

            @Override
            public void disconnected(Client client) {

            }

            @Override
            public void messageSent(Client client, Object o) {

            }

            @Override
            public void commandSent(Client client, Command command) {

            }

            @Override
            public void connected(Client client) {

            }
        });
        Connection("connect");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Connection("online");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_sleep:
                Connection("sleep");
                break;
            case R.id.btn_essen:
                Connection("essen");
                break;
        }
    }

    private void Connection(final String cmd) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                switch (cmd) {
                    case "connect":
                        connect();
                        break;
                    default:
                        sendMessage(cmd);
                        break;
                }
            }

            public void sendMessage(String msg) {
                client.send(msg);
            }

            public void connect() {
                client.start();
            }
        });

        thread.start();
    }
}