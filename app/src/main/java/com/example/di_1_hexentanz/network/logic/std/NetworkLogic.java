package com.example.di_1_hexentanz.network.logic.std;

import android.util.Log;

import com.example.di_1_hexentanz.gameplay.GameConfig;
import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.network.mordechaim_server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkLogic {

    private static final String TAG = "NETWORK";
    private static final int PORT = 9872;
    private static NetworkLogic instance = null;
    private UsageType usageType;
    // only if usage type host
    private Server host;

    private Client client;

    private NetworkLogic() {

    }

    public static void init() {
        if (instance == null) {
            runInThread(new Runnable() {
                @Override
                public void run() {
                    instance = new NetworkLogic();
                    Server server = new Server(PORT);
                    server.setClientLimit(GameConfig.getInstance().getMaxPlayers());
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

    private static void runBackgroundThread(Runnable r) {
        runInThread(r, false);
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

    public Client getClient() {
        return client;
    }

    public void sendMessageToHost(final AbstractMessage msg) {
        runBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getClient().send(msg);
            }
        });
    }

    public void sendMessageToAll(final AbstractMessage msg) {
        runBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getHost().sendToAll(msg);
            }
        });
    }

    public void sendMessageToClient(final AbstractMessage msg, final int clientId) {
        runBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getHost().send(msg, clientId);
            }
        });
    }

    private void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public Boolean isHost() {
        return getUsageType().equals(UsageType.HOST);
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public enum UsageType {
        HOST,
        CLIENT
    }
}
