package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class MoveMessage extends AbstractMessage {





    private String msg = "witch moved";

    public MoveMessage() {
        super(MessageTag.MOVE_WITCH);
    }


    public String getMsg() {
        return msg;
    }
}
