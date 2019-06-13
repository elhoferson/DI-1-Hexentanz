package com.example.di_1_hexentanz.network.wroup.common.listeners;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;
import com.example.di_1_hexentanz.network.wroup.common.messages.MessageWrapper;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractMessageReceivedListener<M extends AbstractMessage> implements DataReceivedListener {

    private static final Gson GSON = new Gson();
    @Override
    public void onDataReceived(MessageWrapper messageWrapper) {
        M msg = GSON.fromJson(messageWrapper.getMessage(), getGenericClass());
        if (msg != null) {
            WroupDevice client = messageWrapper.getWroupDevice();
            handleReceivedMessage(client, msg);
        }

    }

    private Class<M> getGenericClass() {
        return ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public abstract void handleReceivedMessage(WroupDevice sender, M msg);
}
