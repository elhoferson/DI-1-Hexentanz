package com.example.di_1_hexentanz.wifi.network.logic.std;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ClientAdapter;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class NetworkLogic {

    private static final String TAG = "NETWORK";
    private static final int PORT = 9872;
    private static final int CLIENT_LIMIT = 6;
    private static NetworkLogic instance = null;
    private UsageType usageType;
    // only if usage type host
    private Server host;

    private Client client;

    private ArrayList<WifiP2pDevice> players = new ArrayList<>();

    private NetworkLogic() {

    }

    public static void init() {
        if (instance == null) {
            runInThread(new Runnable() {
                @Override
                public void run() {
                    instance = new NetworkLogic();
                    Server server = new Server(PORT);
                    server.setClientLimit(CLIENT_LIMIT);
                    server.start();
                    instance.setHost(server);
                    instance.setUsageType(UsageType.HOST);

                    // the host is also a client
                    try {
                        InetAddress hostAddress = InetAddress.getLocalHost();
                        innerInitClient(hostAddress);
                    } catch (UnknownHostException e) {
                        Log.e(TAG, "unknown host ", e);
                    }
                }
            });
        }
    }

    private static void runInThread(Runnable r) {
        runInThread(r, true);
    }
    private static void runInThread(Runnable r, Boolean join) {
        Thread t = new Thread(r);
        t.start();
        if (join) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Log.e(TAG, "interrupted", e);
            }
        }
    }

    private static void innerInitClient(InetAddress hostDevice) {
        Client client = new Client(hostDevice.getHostAddress(), PORT);
        client.start();
        instance.setClient(client);
    }

    public static void initClient(final InetAddress hostDevice) {
        if (instance == null) {
            runInThread(new Runnable() {
                @Override
                public void run() {
                    instance = new NetworkLogic();
                    innerInitClient(hostDevice);
                    instance.setUsageType(UsageType.CLIENT);
                }
            });
        }
    }

    public static NetworkLogic getInstance() {
        return instance;
    }

    public void close() {
        runInThread(new Runnable() {
            @Override
            public void run() {
                if (host != null) {
                    host.shutDown();
                }

                if (client != null) {
                    client.shutDown();
                }
            }
        });
        instance = null;
    }

    private void setHost(Server host) {
        this.host = host;
    }

    private void setClient(Client client) {
        this.client = client;
    }

    public Server getHost() {
        return host;
    }

    private Client getClient() {
        return client;
    }

    public void sendMessageToHost(final AbstractMessage msg) {
        runInThread(new Runnable() {
            @Override
            public void run() {
                getClient().send(msg);
            }
        }, false);
    }

    private void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public enum UsageType {
        HOST,
        CLIENT
    }

    public boolean addPlayers(ArrayList<WifiP2pDevice> devices){
        return this.players.addAll(devices);

    }

    public void update(ClientAdapter clientAdapter){
         getClient().addClientListener(clientAdapter);
    }
}
