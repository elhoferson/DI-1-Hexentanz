package com.example.di_1_hexentanz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.di_1_hexentanz.gameplay.GameConfig;
import com.example.di_1_hexentanz.network.activity.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.messages.listener.AbstractHostMessageReceivedListener;
import com.example.di_1_hexentanz.network.messages.std.ColorPickMessage;
import com.example.di_1_hexentanz.network.messages.std.ColorPickResultMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Server;
import com.example.di_1_hexentanz.network.obj.std.WroupDeviceAdapter;
import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PError;
import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;
import com.example.di_1_hexentanz.network.wroup.common.listeners.ClientConnectedListener;
import com.example.di_1_hexentanz.network.wroup.common.listeners.ClientDisconnectedListener;
import com.example.di_1_hexentanz.network.wroup.common.listeners.ServiceRegisteredListener;
import com.example.di_1_hexentanz.network.wroup.service.WroupService;
import com.example.di_1_hexentanz.player.ColourChoosing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGameActivity extends AbstractWifiP2pActivity {

    private List<WroupDevice> devices = new ArrayList<>();
    private WroupService host;
    private WroupDeviceAdapter peerListAdapter;

    private AbstractHostMessageReceivedListener<ColorPickMessage> cpm = new AbstractHostMessageReceivedListener<ColorPickMessage>() {
        @Override
        public void handleReceivedMessage(Server server, Server.ConnectionToClient client, ColorPickMessage msg) {
            boolean success = GameConfig.getInstance().registerPlayerColor(client.getClientId(), msg.getPlayerColor());
            if (success) {
                //TODO update list of devices with colors
            }
            NetworkLogic.getInstance().sendMessageToClient(new ColorPickResultMessage(success, msg.getPlayerColor()), client.getClientId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        host = WroupService.getInstance(getApplicationContext());
        host.registerService(UUID.randomUUID().toString(), new ServiceRegisteredListener() {
            @Override
            public void onSuccessServiceRegistered() {
                // yippie
            }

            @Override
            public void onErrorServiceRegistered(WiFiP2PError wiFiP2PError) {
                Log.e(WIFI_P2P_TAG, "Error on creating service is "+ wiFiP2PError);
            }
        });



        ListView peerList = findViewById(R.id.peerList);
        peerListAdapter = new WroupDeviceAdapter(this, devices);
        peerList.setAdapter(peerListAdapter);

        host.setClientConnectedListener(new ClientConnectedListener() {
            @Override
            public void onClientConnected(WroupDevice wroupDevice) {
                peerListAdapter.add(wroupDevice);
                peerListAdapter.notifyDataSetChanged();
            }
        });
        host.setClientDisconnectedListener(new ClientDisconnectedListener() {
            @Override
            public void onClientDisconnected(WroupDevice wroupDevice) {
                peerListAdapter.remove(wroupDevice);
                peerListAdapter.notifyDataSetChanged();
            }
        });

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_startGame:
                // player count has to be greater than min players without me
                if (GameConfig.getInstance().getPlayerColors().size() >= GameConfig.getInstance().getMinPlayers() - 1) {
                    Intent intent = new Intent(getApplicationContext(), ColourChoosing.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Game can't be started, too few players", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                // nothing to do
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (host != null) {
            host.disconnect();
        }
    }
}
