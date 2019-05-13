package com.example.di_1_hexentanz.wifi.p2p.obj.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pGroupInfoListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pPeerListListener;
import com.example.di_1_hexentanz.wifi.p2p.obj.AbstractWifiP2pBroadcastReceiver;

public class WifiP2pServerBroadcastReceiver extends AbstractWifiP2pBroadcastReceiver {

    public WifiP2pServerBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter, TextView myDeviceView) {
        super(manager, channel, deviceListAdapter, myDeviceView);
    }

    @Override
    public void fillList(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter) {
        manager.requestGroupInfo(channel, new WifiP2pGroupInfoListener(deviceListAdapter));
    }
}
