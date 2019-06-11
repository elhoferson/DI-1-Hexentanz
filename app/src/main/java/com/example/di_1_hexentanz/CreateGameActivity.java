package com.example.di_1_hexentanz;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.di_1_hexentanz.player.ColourChoosing;
import com.example.di_1_hexentanz.wifi.network.AbstractWifiP2pActivity;
import com.example.di_1_hexentanz.wifi.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.wifi.network.messages.std.ColorPickMessage;
import com.example.di_1_hexentanz.wifi.network.messages.std.StartMessage;
import com.example.di_1_hexentanz.wifi.network.messages.std.TestMessage;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ClientAdapter;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ClientListener;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Command;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Server;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.ServerAdapter;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pDeviceAdapter;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pIntentFilter;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pServerBroadcastReceiver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CreateGameActivity extends AbstractWifiP2pActivity {

    private ArrayList<WifiP2pDevice> devices = new ArrayList<>();
    private WifiP2pServerBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        TextView myDevice = findViewById(R.id.text_mydevice);
        NetworkLogic.init();
        NetworkLogic.getInstance().getHost().addServerListener(new ServerAdapter() {
            @Override
            public void messageReceived(Server server, Server.ConnectionToClient client, Object msg) {
                AbstractMessage myMsg = (AbstractMessage) msg;
                switch (myMsg.getTag()) {
                    case TEST:
                        TestMessage tm = (TestMessage) myMsg;
                        Log.e("MSG", tm.getMsg());
                        break;
                    case PICK_COLOR:
                        ColorPickMessage pm = (ColorPickMessage) myMsg;
                        break;
                    case START:
                        startActivity(new Intent(getApplicationContext(), ColourChoosing.class));

                    default:
                        Log.i("MSG", myMsg.getTag() + " not handled here");
                        break;
                }
            }
        });


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
        receiver = new WifiP2pServerBroadcastReceiver(getManager(), getChannel(), peerListAdapter, myDevice);

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_startGame:

                getManager().requestPeers(getChannel(), new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList peers) {
                        devices.clear();
                        devices.addAll(peers.getDeviceList());
                    }
                });


                if (NetworkLogic.getInstance().addPlayers(devices)) {
                    for (WifiP2pDevice device : devices) {
                        connect(device);
                        Toast.makeText(getApplicationContext(), "Connected to: " + device.deviceName, Toast.LENGTH_LONG).show();
                    }



                    //start activity on all peers
                    SendMessageTask msgTask = new SendMessageTask();
                    StartMessage start = new StartMessage();

                    msgTask.execute(start);


                } else {
                    Toast.makeText(getApplicationContext(), "No devices to connect!", Toast.LENGTH_LONG).show();
                }

                //if (NetworkLogic.getInstance().addPlayers(devices)) {
                // connect
                //} else {
                // toast
                //}
                break;
            default:
                // nothing to do
        }
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

    @Override
    public void onStop(){
        super.onStop();
        getManager().removeGroup(getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                 Log.i(WIFI_P2P_TAG, "Succesfully removed group");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "Removal of group failed");
            }
        });
    }

    private void connect(WifiP2pDevice device) {

        final WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        getManager().connect(getChannel(), config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG, "successful connected to " + config.deviceAddress);
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "cannot connect to with " + config.deviceAddress + " reason " + reason);
            }
        });
    }


    public class SendMessageTask extends AsyncTask<StartMessage, Void, StartMessage> {
        /**AsyncTask: Task, der nebenläufig ausgeführt wird
         *            Main-Thread muss nicht auf dessen Vollendung warten
         *            Android erlaubt keine Netzwerkzugriffe im Main-Thread!**/


        /**
         * Soll die Server-Abfrage im Hintergrund (nicht im Main-Thread) ausführen
         **/
        @Override
        protected StartMessage doInBackground(StartMessage... params) {
            try {

                NetworkLogic.getInstance().getHost().sendToAll(params[0]);

                return(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }
        }


        /**
         * Wird nach doInBackground(...) ausgeführt --> übernimmt return-Wert der vorherigen Methode als Input
         **/

        @Override
        public void onPostExecute(StartMessage param) {
            Toast.makeText(getApplicationContext(), "Msg: " + param.getTag() + " has been sent", Toast.LENGTH_LONG).show();

        }
    }

}
