package com.example.di_1_hexentanz.wifi.network;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.wifi.network.obj.IWifiP2pConstants;

import java.lang.reflect.Method;

public abstract class AbstractWifiP2pActivity extends AppCompatActivity implements IWifiP2pConstants {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (WifiP2pManager) getApplicationContext().getSystemService(WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), new WifiP2pManager.ChannelListener() {
            @Override
            public void onChannelDisconnected() {
                Log.e(WIFI_P2P_TAG, "Channel disconneted");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (NetworkLogic.getInstance() != null) {
            NetworkLogic.getInstance().close();
        }
    }

    public WifiP2pManager getManager() {
        return manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    public void disconnect() {
        if (getManager() != null && getChannel() != null) {
            getManager().requestGroupInfo(getChannel(), new WifiP2pManager.GroupInfoListener() {
                @Override
                public void onGroupInfoAvailable(final WifiP2pGroup group) {
                    if (group != null && getManager() != null && getChannel() != null
                    //        && group.isGroupOwner()
                    ) {
                        getManager().removeGroup(getChannel(), new WifiP2pManager.ActionListener() {

                            @Override
                            public void onSuccess() {
                                Log.d(WIFI_P2P_TAG, "removeGroup onSuccess -");
                                deletePersistentGroup(group);
                            }

                            @Override
                            public void onFailure(int reason) {
                                Log.d(WIFI_P2P_TAG, "removeGroup onFailure -" + reason);
                            }
                            private void deletePersistentGroup(WifiP2pGroup wifiP2pGroup) {
                                try {

                                    Method getNetworkId = WifiP2pGroup.class.getMethod("getNetworkId");
                                    Integer networkId = (Integer) getNetworkId.invoke(wifiP2pGroup);
                                    Method deletePersistentGroup = WifiP2pManager.class.getMethod("deletePersistentGroup",
                                            WifiP2pManager.Channel.class, int.class, WifiP2pManager.ActionListener.class);
                                    deletePersistentGroup.invoke(manager, channel, networkId, new WifiP2pManager.ActionListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.i(WIFI_P2P_TAG, "deletePersistentGroup onSuccess");
                                        }

                                        @Override
                                        public void onFailure(int reason) {
                                            Log.e(WIFI_P2P_TAG, "deletePersistentGroup failure: " + reason);
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e(WIFI_P2P_TAG, "Could not delete persistent group", e);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
