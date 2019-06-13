package com.example.di_1_hexentanz.network.wroup.common.listeners;



import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PError;
import com.example.di_1_hexentanz.network.wroup.common.WroupServiceDevice;

import java.util.List;

public interface ServiceDiscoveredListener {

    void onNewServiceDeviceDiscovered(WroupServiceDevice serviceDevice);

    void onFinishServiceDeviceDiscovered(List<WroupServiceDevice> serviceDevices);

    void onError(WiFiP2PError wiFiP2PError);

}
