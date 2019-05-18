package com.example.di_1_hexentanz.wifi.network.util;

import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonUtil {

    private static Gson gson;

    private JsonUtil(){
    }

    static {
        gson = new GsonBuilder().create();
    }

    private static Gson getGson() {
        return gson;
    }

    public static <T extends AbstractMessage> T getMessage(String msgString, Class<T> classMsg) {
        return getGson().fromJson(msgString, classMsg);
    }

    public static String toJson(AbstractMessage msg) {
        return getGson().toJson(msg);
    }
}
