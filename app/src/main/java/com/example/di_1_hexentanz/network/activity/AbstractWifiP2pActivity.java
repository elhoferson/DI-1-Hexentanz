package com.example.di_1_hexentanz.network.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.network.obj.IWifiP2pConstants;
import com.example.di_1_hexentanz.network.obj.std.WifiP2pIntentFilter;
import com.example.di_1_hexentanz.network.wroup.common.WiFiDirectBroadcastReceiver;
import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PInstance;
import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;
import com.example.di_1_hexentanz.network.wroup.common.listeners.DeviceChangedListener;

public abstract class AbstractWifiP2pActivity extends AppCompatActivity implements IWifiP2pConstants {

    private WiFiP2PInstance wiFiP2PInstance;
    private WiFiDirectBroadcastReceiver wiFiDirectBroadcastReceiver;
    private static final WifiP2pIntentFilter FILTER = new WifiP2pIntentFilter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wiFiP2PInstance = WiFiP2PInstance.getInstance(getApplicationContext());
        wiFiDirectBroadcastReceiver = wiFiP2PInstance.getBroadcastReceiver();
        wiFiP2PInstance.setDeviceChangedListener(new DeviceChangedListener() {
            @Override
            public void onDeviceSet(WroupDevice device) {
                TextView myDevice = findViewById(R.id.text_mydevice);
                myDevice.setText(device.getDeviceName() + " - " + device.getDeviceMac());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wiFiDirectBroadcastReceiver, FILTER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wiFiDirectBroadcastReceiver);
    }


}
