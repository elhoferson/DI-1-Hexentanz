package com.example.di_1_hexentanz.threads;

import android.os.Handler;
import android.util.Log;

import com.example.di_1_hexentanz.util.JsonUtil;
import com.example.di_1_hexentanz.wifi.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CommunicationThread extends Thread {

    private static final String COMMUNICATION_TAG = "Communication";

    private BufferedWriter output;
    private BufferedReader input;
    private Handler handler;

    public CommunicationThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        //NetworkLogic.UsageType type = NetworkLogic.getInstance().getUsageType();
        Socket client = NetworkLogic.getInstance().getClient();
        //ServerSocket host = NetworkLogic.getInstance().getHost();
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            while (!this.isInterrupted()) {
                String msg = input.readLine();
                if (StringUtils.isNotBlank(msg)) {
                    AbstractMessage abstractMessage = JsonUtil.getMessage(msg, AbstractMessage.class);
                    handler.obtainMessage(abstractMessage.getTag(), msg).sendToTarget();
                }
            }
        } catch (IOException e) {
            Log.e(COMMUNICATION_TAG, "while reading input stream", e);
        }
    }

    public void write(AbstractMessage message) {
        String json = JsonUtil.toJson(message);
        try {
            output.write(json);
        } catch (IOException e) {
            Log.e(COMMUNICATION_TAG, "error while writing to outputstream", e);
        }
    }
}
