package com.example.di_1_hexentanz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pChannelListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pConnectionInfoListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pConnectionListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pDiscoverPeersListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pGroupInfoListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pGroupListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pPeerListListener;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pBroadcastReceiver;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pIntentFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View v) {

        if (!activeWifi()) {
            Toast.makeText(this, "Wifi not active!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.btnCreateGame:
                startChild(CreateActivity.class);
                break;
            case R.id.btnJoinGame:
                startChild(JoinActivity.class);
                break;
            default:
                // nothing to do
        }
    }

    private Boolean activeWifi() {
        Boolean active = false;
        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            //wifi is enabled
            active = true;
        }
        return active;
    }



    private void startChild(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }


}
