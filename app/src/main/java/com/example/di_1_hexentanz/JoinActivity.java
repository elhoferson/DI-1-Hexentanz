package com.example.di_1_hexentanz;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.di_1_hexentanz.wifi.p2p.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pConnectionListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pDiscoverPeersListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JoinActivity extends AbstractWifiP2pActivity {

    private List<WifiP2pDevice> peerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ListView hostList = (ListView) findViewById(R.id.hostList);
        ListAdapter adapter = new ArrayAdapter<WifiP2pDevice>(this, android.R.layout.simple_list_item_1, peerList);
        hostList.setAdapter(adapter);
        getManager().discoverPeers(getChannel(), new WifiP2pDiscoverPeersListener());
    }

    private void connect() {
        WifiP2pDevice device = null;//peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        getManager().connect(getChannel(), config, new WifiP2pConnectionListener());
    }

}
