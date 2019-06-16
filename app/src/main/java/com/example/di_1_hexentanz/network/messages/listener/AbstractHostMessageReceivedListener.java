package com.example.di_1_hexentanz.network.messages.listener;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Server;
import com.example.di_1_hexentanz.network.mordechaim_server.ServerAdapter;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractHostMessageReceivedListener<M extends AbstractMessage> extends ServerAdapter {

    @Override
    public void messageReceived(Server server, Server.ConnectionToClient client, Object msg) {
        Class<M> clazz = getGenericClass();
        if (msg.getClass().equals(clazz)) {
            M myMsg = ((M) msg);
            handleReceivedMessage(server, client, myMsg);
        }
    }

    private Class<M> getGenericClass() {
        return ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public abstract void handleReceivedMessage(Server server, Server.ConnectionToClient client, M msg);

}
