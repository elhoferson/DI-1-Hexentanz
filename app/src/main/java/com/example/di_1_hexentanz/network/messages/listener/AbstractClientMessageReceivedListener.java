package com.example.di_1_hexentanz.network.messages.listener;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.network.mordechaim_server.ClientAdapter;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractClientMessageReceivedListener<M extends AbstractMessage> extends ClientAdapter {

    @Override
    public void messageReceived(Client client, Object msg) {
        Class<M> clazz = getGenericClass();
        if (isInstanceOf(msg.getClass(), clazz)) {
            M myMsg = ((M) msg);
            handleReceivedMessage(client, myMsg);
        }
    }



    private Class<M> getGenericClass() {
        return ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public abstract void handleReceivedMessage(Client client, M msg);

    private <T> boolean isInstanceOf(Class<T> clazz, Class<M> targetClass) {
        return clazz.isInstance(targetClass);
    }
}
