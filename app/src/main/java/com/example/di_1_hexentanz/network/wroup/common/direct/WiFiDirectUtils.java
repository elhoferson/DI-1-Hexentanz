package com.example.di_1_hexentanz.network.wroup.common.direct;


import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PError;
import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PInstance;

import java.lang.reflect.Method;


public class WiFiDirectUtils {

    private static final String TAG = WiFiDirectUtils.class.getSimpleName();

    public static void clearServiceRequest(WiFiP2PInstance wiFiP2PInstance) {
        wiFiP2PInstance.getWifiP2pManager().clearServiceRequests(wiFiP2PInstance.getChannel(), new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Success clearing service request");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Error clearing service request: " + reason);
            }

        });
    }

    public static void clearLocalServices(WiFiP2PInstance wiFiP2PInstance) {
        wiFiP2PInstance.getWifiP2pManager().clearLocalServices(wiFiP2PInstance.getChannel(), new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Local services cleared");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Error clearing local services: " + WiFiP2PError.fromReason(reason));
            }

        });
    }

    public static void cancelConnect(WiFiP2PInstance wiFiP2PInstance) {
        wiFiP2PInstance.getWifiP2pManager().cancelConnect(wiFiP2PInstance.getChannel(), new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Connect canceled successfully");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Error canceling connect: " + WiFiP2PError.fromReason(reason));
            }

        });
    }

    public static void removeGroup(final WiFiP2PInstance wiFiP2PInstance) {
        wiFiP2PInstance.getWifiP2pManager().requestGroupInfo(wiFiP2PInstance.getChannel(), new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(final WifiP2pGroup group) {
                if (group != null) {
                    wiFiP2PInstance.getWifiP2pManager().removeGroup(wiFiP2PInstance.getChannel(), new WifiP2pManager.ActionListener() {
                        @Override
                        public void onSuccess() {
                            Log.i(TAG, "Group removed: " + group.getNetworkName());
                            deletePersistentGroup(group);
                        }

                        @Override
                        public void onFailure(int reason) {
                            Log.e(TAG, "Fail disconnecting from group. Reason: " + WiFiP2PError.fromReason(reason));
                        }

                        private void deletePersistentGroup(WifiP2pGroup wifiP2pGroup) {
                            try {

                                Method getNetworkId = WifiP2pGroup.class.getMethod("getNetworkId");
                                Integer networkId = (Integer) getNetworkId.invoke(wifiP2pGroup);
                                Method deletePersistentGroup = WifiP2pManager.class.getMethod("deletePersistentGroup",
                                        WifiP2pManager.Channel.class, int.class, WifiP2pManager.ActionListener.class);
                                deletePersistentGroup.invoke(wiFiP2PInstance.getWifiP2pManager(), wiFiP2PInstance.getChannel(), networkId, new WifiP2pManager.ActionListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i(TAG, "deletePersistentGroup onSuccess");
                                    }

                                    @Override
                                    public void onFailure(int reason) {
                                        Log.e(TAG, "deletePersistentGroup failure: " + reason);
                                    }
                                });
                            } catch (Exception e) {
                                Log.e(TAG, "Could not delete persistent group", e);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void stopPeerDiscovering(WiFiP2PInstance wiFiP2PInstance) {
        wiFiP2PInstance.getWifiP2pManager().stopPeerDiscovery(wiFiP2PInstance.getChannel(), new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Peer disconvering stopped");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Error stopping peer discovering: " + WiFiP2PError.fromReason(reason));
            }

        });
    }

}
