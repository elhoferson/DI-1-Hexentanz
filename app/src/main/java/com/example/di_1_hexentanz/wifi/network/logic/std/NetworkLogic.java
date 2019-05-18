package com.example.di_1_hexentanz.wifi.network.logic.std;

import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Handler;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.network.threads.std.CommunicationThread;
import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkLogic {

    private static final String TAG = "NETWORK";
    private static final int PORT = 9872;
    private static NetworkLogic instance = null;
    private UsageType usageType;
    private CommunicationThread comm;

    private ServerSocket host;
    private List<WifiP2pDevice> clients = new ArrayList<>();

    private Socket client;


    private NetworkLogic() {

    }

    public static void init() {
        if (instance == null) {
            try {

                ServerSocket server = new ServerSocket(PORT);
                instance = new NetworkLogic();
                instance.setHost(server);
                instance.setClient(server.accept());
                instance.setUsageType(UsageType.HOST);
                instance.startCommunicationThread();
            } catch (IOException e) {
                Log.e(TAG, "error creating server socket", e);
            }

        }
    }

    public static void initClient(final InetAddress hostDevice) {
        if (instance == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket(hostDevice, PORT);
                        instance = new NetworkLogic();
                        instance.setUsageType(UsageType.CLIENT);
                        instance.setClient(socket);
                        instance.startCommunicationThread();
                    } catch (IOException e) {
                        Log.e(TAG, "error creating server socket", e);
                    }
                }
            }).start();

        }
    }

    public static NetworkLogic getInstance() {
        return instance;
    }

    public Boolean addPlayers(List<WifiP2pDevice> devices) {
        if (usageType.equals(UsageType.CLIENT)) {
            Log.e(TAG, "Client not allowed to addPlayers");
            return false;
        }

        if (devices == null || devices.isEmpty()) {
            Log.e(TAG, "No devices");
            return false;
        }

        for (WifiP2pDevice device : devices) {
            clients.clear();
            clients.add(device);
        }
        return true;
    }

    private class ServerThread extends Thread {

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(PORT);
                Socket mySocket = server.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(new BufferedInputStream(mySocket.getInputStream())));
                while(!Thread.currentThread().isInterrupted()) {
                    String message = input.readLine();
                    if (message.length() > 0) {
                        Log.e(TAG, message);
                    }
                }
            } catch(IOException e) {
                Log.e(TAG, "error creating server socket", e);
            }
        }
    }

    public void close() {
        comm.interrupt();
        instance = null;
    }

    private void setHost(ServerSocket host) {
        this.host = host;
    }

    private void setClient(Socket client) {
        this.client = client;
    }

    private void startCommunicationThread() {
        this.comm = new CommunicationThread();
        comm.start();
    }

    private ServerSocket getHost() {
        return host;
    }

    public Socket getClient() {
        return client;
    }

    public List<WifiP2pDevice> getClients() {
        return clients;
    }

    public void registerCommunicationHandler(Handler handler) {
        comm.registerHandler(handler);
    }

    public void writeMessage(AbstractMessage message) {
        comm.write(message);
    }

    private void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public Boolean isHost() {
        return usageType.equals(UsageType.HOST);
    }

    public enum UsageType {
        HOST,
        CLIENT
    }
}
