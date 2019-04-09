package com.example.di_1_hexentanz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.di_1_hexentanz.wifi.p2p.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pConnectionInfoListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pGroupInfoListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pGroupListener;

public class CreateActivity extends AbstractWifiP2pActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getManager().createGroup(getChannel(), new WifiP2pGroupListener("create"));
        getManager().requestGroupInfo(getChannel(), new WifiP2pGroupInfoListener());
        getManager().requestConnectionInfo(getChannel(), new WifiP2pConnectionInfoListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getManager().removeGroup(getChannel(), new WifiP2pGroupListener("remove"));
    }
}
