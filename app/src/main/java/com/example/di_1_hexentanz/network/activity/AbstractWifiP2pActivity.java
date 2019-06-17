package com.example.di_1_hexentanz.network.activity;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.logic.std.WifiP2pLogic;
import com.example.di_1_hexentanz.network.obj.IWifiP2pConstants;

public abstract class AbstractWifiP2pActivity extends AppCompatActivity implements IWifiP2pConstants {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WifiP2pManager manager = (WifiP2pManager) getApplicationContext().getSystemService(WIFI_P2P_SERVICE);
        WifiP2pManager.Channel channel = manager.initialize(this, getMainLooper(), null);
        WifiP2pLogic.instance().init(manager, channel);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WifiP2pLogic.instance().disconnect();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (NetworkLogic.getInstance() != null) {
            NetworkLogic.getInstance().close();
        }
    }

    public WifiP2pManager getManager() {
        return WifiP2pLogic.instance().getManager();
    }

    public WifiP2pManager.Channel getChannel() {
        return WifiP2pLogic.instance().getChannel();
    }


}
