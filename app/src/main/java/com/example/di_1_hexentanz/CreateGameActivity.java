package com.example.di_1_hexentanz;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.di_1_hexentanz.wifi.p2p.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pDeviceAdapter;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pIntentFilter;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pServerBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class CreateGameActivity extends AbstractWifiP2pActivity {

    private List<WifiP2pDevice> devices = new ArrayList<>();
    private WifiP2pServerBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

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
        receiver = new WifiP2pServerBroadcastReceiver(getManager(), getChannel(), this, peerListAdapter);
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
