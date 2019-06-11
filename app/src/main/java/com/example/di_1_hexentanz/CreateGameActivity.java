package com.example.di_1_hexentanz;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.di_1_hexentanz.gameplay.GameConfig;
import com.example.di_1_hexentanz.network.activity.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.messages.listener.AbstractHostMessageReceivedListener;
import com.example.di_1_hexentanz.network.messages.std.ColorPickMessage;
import com.example.di_1_hexentanz.network.messages.std.ColorPickResultMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Server;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pDeviceAdapter;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pIntentFilter;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pServerBroadcastReceiver;
import com.example.di_1_hexentanz.player.ColourChoosing;

import java.util.ArrayList;
import java.util.List;

public class CreateGameActivity extends AbstractWifiP2pActivity {

    private List<WifiP2pDevice> devices = new ArrayList<>();
    private WifiP2pServerBroadcastReceiver receiver;

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
        TextView myDevice = findViewById(R.id.text_mydevice);
        NetworkLogic.init();
        NetworkLogic.getInstance().getHost().addServerListener(cpm);


        getManager().createGroup(getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG, "succesful created group");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "failed on created group with reason " + reason);
            }
        });
        ListView peerList = findViewById(R.id.peerList);
        WifiP2pDeviceAdapter peerListAdapter = new WifiP2pDeviceAdapter(this, devices);
        peerList.setAdapter(peerListAdapter);
        receiver = new WifiP2pServerBroadcastReceiver(getManager(), getChannel(), peerListAdapter, myDevice, this);

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
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new WifiP2pIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
