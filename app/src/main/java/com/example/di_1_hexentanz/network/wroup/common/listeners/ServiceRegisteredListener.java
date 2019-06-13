package com.example.di_1_hexentanz.network.wroup.common.listeners;


import com.example.di_1_hexentanz.network.wroup.common.WiFiP2PError;

public interface ServiceRegisteredListener {

    void onSuccessServiceRegistered();

    void onErrorServiceRegistered(WiFiP2PError wiFiP2PError);

}
