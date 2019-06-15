package com.example.di_1_hexentanz.network.logic.std;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

import com.example.di_1_hexentanz.network.obj.IWifiP2pConstants;

import java.lang.reflect.Method;

public class WifiP2pLogic implements IWifiP2pConstants {

    private static final WifiP2pLogic instance = new WifiP2pLogic();
    private WifiP2pManager manager;
    private Channel channel;

    public static WifiP2pLogic instance() {
        return instance;
    }

    public void init (WifiP2pManager manager, Channel channel) {
        this.manager = manager;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public WifiP2pManager getManager() {
        return manager;
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
