package com.example.di_1_hexentanz.wifi.network.obj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.di_1_hexentanz.player.ColourChoosing;
import com.example.di_1_hexentanz.wifi.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.wifi.network.messages.std.StartMessage;
import com.example.di_1_hexentanz.wifi.network.messages.std.TestMessage;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ClientAdapter;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pDeviceAdapter;

import java.net.InetAddress;

public abstract class AbstractWifiP2pBroadcastReceiver extends BroadcastReceiver implements IWifiP2pConstants {

    private WifiP2pDeviceAdapter deviceListAdapter;
    private TextView myDeviceView;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    public AbstractWifiP2pBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter, TextView myDeviceView) {
        this.manager = manager;
        this.channel = channel;
        this.deviceListAdapter = deviceListAdapter;
        this.myDeviceView = myDeviceView;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Log.i(WIFI_P2P_TAG, "WIFI P2p enabled");
            } else {
                // Wi-Fi P2P is not enabled
                Toast.makeText(deviceListAdapter.getContext(), "WIFI P2p disabled",   Toast.LENGTH_LONG).show();
                Log.e(WIFI_P2P_TAG, "WIFI P2p disabled");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.i(WIFI_P2P_TAG, "WIFI P2p peers changed");
            fillList(manager, channel, deviceListAdapter);

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            if (manager == null) {
                return;
            }
            Log.i(WIFI_P2P_TAG, "WIFI P2p connection changed");

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            Log.i(WIFI_P2P_TAG, "Network info:" + networkInfo.toString());
            if (networkInfo.isConnected()) {

                // We are connected with the other device, request connection
                // info to find group owner IP
                //manager.requestConnectionInfo(channel, connectionInfo);
                manager.requestConnectionInfo(channel, new WifiP2pManager.ConnectionInfoListener() {
                    @Override
                    public void onConnectionInfoAvailable(WifiP2pInfo info) {
                        InetAddress groupOwnerAddress = info.groupOwnerAddress;
                        Log.i(WIFI_P2P_TAG, info.toString());

                        if (info.groupFormed && info.isGroupOwner) {
                            Log.i(WIFI_P2P_TAG, "I'am the owner");
                        } else if (info.groupFormed) {
                            Log.i(WIFI_P2P_TAG, "I'am a client and will connect to the owner");
                            NetworkLogic.initClient(groupOwnerAddress);
                            NetworkLogic.getInstance().update(new ClientAdapter(){
                                @Override
                                public void messageReceived(Client client, Object message){
                                    if(message instanceof StartMessage){
                                        NetworkLogic.getInstance().sendMessageToHost((StartMessage)message);
                                        context.startActivity(new Intent(context, ColourChoosing.class));

                                    }
                                }
                            });
                         //   NetworkLogic.getInstance().sendMessageToHost(new TestMessage());

                        }
                    }
                });
            } else {
                Log.i(WIFI_P2P_TAG, "disconnected");
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            WifiP2pDevice myDevice = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.i(WIFI_P2P_TAG, "WIFI P2p device changed device: "+ myDevice.toString());
            myDeviceView.setText(myDevice.deviceName + " - " + myDevice.deviceAddress);


        }
    }

    public abstract void fillList(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter);

}
