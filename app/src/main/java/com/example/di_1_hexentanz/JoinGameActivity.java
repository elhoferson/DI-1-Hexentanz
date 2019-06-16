package com.example.di_1_hexentanz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.di_1_hexentanz.network.activity.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pClientBroadcastReceiver;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pDeviceAdapter;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pIntentFilter;

import java.util.ArrayList;
import java.util.List;

public class JoinGameActivity extends AbstractWifiP2pActivity implements View.OnClickListener {

    private List<WifiP2pDevice> devices = new ArrayList<>();
    private WifiP2pClientBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        Button btnProblem = findViewById(R.id.btnProblemToFindHost);
        btnProblem.setOnClickListener(this);
        ListView hostList = findViewById(R.id.hostList);
        WifiP2pDeviceAdapter deviceListAdapter = new WifiP2pDeviceAdapter(this, devices);
        hostList.setAdapter(deviceListAdapter);
        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {

                final WifiP2pDevice selectedPeer = devices.get(position);
                AlertDialog.Builder connectDialog = new AlertDialog.Builder(JoinGameActivity.this);
                connectDialog.setTitle("Willst du dem Spiel von "+selectedPeer.deviceName+" beitreten?");
                connectDialog.setPositiveButton("Beitreten", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "Peer Selected : "+selectedPeer.toString(),   Toast.LENGTH_LONG).show();
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
        receiver = new WifiP2pClientBroadcastReceiver(getManager(), getChannel(), deviceListAdapter, myDeviceView,this);

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

    private void connect(WifiP2pDevice device) {

        final WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        //config.groupOwnerIntent = 15;
        //config.wps.setup = WpsInfo.PBC;

        getManager().connect(getChannel(), config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG,"successful connected to "+ config.deviceAddress);
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "cannot connect to with "+ config.deviceAddress+" reason "+ reason);
            }
        });
    }

    @Override
    protected void onDestroy() {
        getManager().cancelConnect(getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(WIFI_P2P_TAG, "disconnect succesful");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "disconnect not succesful with reason "+ reason);
            }
        });
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProblemToFindHost:
                AlertDialog problemDialog = new AlertDialog.Builder(JoinGameActivity.this).create();
                problemDialog.setTitle("Haben Sie Problem den Host zu finden?");
                problemDialog.setMessage("Gehen Sie in den Einstellungen auf WLAN dann Wifi Direct (in älteren Versionen rechts oben auf die 3 Punkte und dann auf Erweitert, dann auf Wifi Direct)."+
                                         "Verbinden Sie sich per klick auf den Host und wechseln Sie zurück ins Spiel.");
                problemDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                problemDialog.show();
                break;
        }
    }
}
