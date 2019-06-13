package com.example.di_1_hexentanz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.di_1_hexentanz.network.activity.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.network.obj.std.WroupDeviceAdapter;
import com.example.di_1_hexentanz.network.wroup.client.WroupClient;
import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PError;
import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;
import com.example.di_1_hexentanz.network.wroup.common.WroupServiceDevice;
import com.example.di_1_hexentanz.network.wroup.common.listeners.ServiceConnectedListener;
import com.example.di_1_hexentanz.network.wroup.common.listeners.ServiceDisconnectedListener;
import com.example.di_1_hexentanz.network.wroup.common.listeners.ServiceDiscoveredListener;
import com.example.di_1_hexentanz.player.ColourChoosing;

import java.util.ArrayList;
import java.util.List;

public class JoinGameActivity extends AbstractWifiP2pActivity {

    private List<WroupDevice> devices = new ArrayList<>();
    private WroupDeviceAdapter deviceListAdapter;
    private WroupClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        ListView hostList = findViewById(R.id.hostList);
        deviceListAdapter = new WroupDeviceAdapter(this, devices);
        hostList.setAdapter(deviceListAdapter);
        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                final WroupServiceDevice selectedPeer = (WroupServiceDevice) devices.get(position);
                AlertDialog.Builder connectDialog = new AlertDialog.Builder(JoinGameActivity.this);
                connectDialog.setTitle("Willst du dem Spiel von " + selectedPeer.getDeviceName() + " beitreten?");
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

        client = WroupClient.getInstance(getApplicationContext());
        client.setServerDisconnetedListener(new ServiceDisconnectedListener() {
            @Override
            public void onServerDisconnectedListener() {
            }
        });

        discover();

    }

    private void discover() {
        deviceListAdapter.clear();
        client.discoverServices(5000L, new ServiceDiscoveredListener() {
            @Override
            public void onNewServiceDeviceDiscovered(WroupServiceDevice serviceDevice) {
                //deviceListAdapter.add(serviceDevice);

            }

            @Override
            public void onFinishServiceDeviceDiscovered(List<WroupServiceDevice> serviceDevices) {
                deviceListAdapter.addAll(serviceDevices);
                deviceListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(WiFiP2PError wiFiP2PError) {
                Log.e(WIFI_P2P_TAG, "discovering error with reason " + wiFiP2PError);
            }
        });
    }

    private void connect(WroupServiceDevice host) {
        client.connectToService(host, new ServiceConnectedListener() {
            @Override
            public void onServiceConnected(WroupDevice serviceDevice) {
                chooseColor();
            }
        });

    }

    private void chooseColor() {
        Intent intent = new Intent(getApplicationContext(), ColourChoosing.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.disconnect();
        }
    }
}
