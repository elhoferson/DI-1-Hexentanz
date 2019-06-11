package com.example.di_1_hexentanz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.di_1_hexentanz.player.ColourChoosing;
import com.example.di_1_hexentanz.wifi.network.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.wifi.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.wifi.network.messages.std.StartMessage;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ClientAdapter;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Server;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ServerAdapter;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pClientBroadcastReceiver;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pDeviceAdapter;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pIntentFilter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class JoinGameActivity extends AbstractWifiP2pActivity {

    private List<WifiP2pDevice> devices = new ArrayList<>();
    private WifiP2pClientBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        ListView hostList = findViewById(R.id.hostList);
        WifiP2pDeviceAdapter deviceListAdapter = new WifiP2pDeviceAdapter(this, devices);
        hostList.setAdapter(deviceListAdapter);
        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                final WifiP2pDevice selectedPeer = devices.get(position);
                AlertDialog.Builder connectDialog = new AlertDialog.Builder(JoinGameActivity.this);
                connectDialog.setTitle("Willst du dem Spiel von " + selectedPeer.deviceName + " beitreten?");
                connectDialog.setPositiveButton("Beitreten", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        connect(selectedPeer);

                    }
                })
                        .setNegativeButton("Nicht beitreten", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

            }
        });
        TextView myDeviceView = findViewById(R.id.text_mydevice);
        receiver = new WifiP2pClientBroadcastReceiver(getManager(), getChannel(), deviceListAdapter, myDeviceView);
        getManager().discoverPeers(getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG, "successful discovering peers");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "cannot discover peers with reason " + reason);
            }
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getManager().stopPeerDiscovery(getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG, "successful stopped discovering peers");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "cannot stop discover peers with reason " + reason);
            }
        });
    }


    private void connect(WifiP2pDevice device) {

        final WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        //config.groupOwnerIntent = 15;
        //config.wps.setup = WpsInfo.PBC;

        getManager().connect(getChannel(), config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG, "successful connected to " + config.deviceAddress);


            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "cannot connect to with " + config.deviceAddress + " reason " + reason);
            }
        });
    }
}
