package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.gameboard.Gamescreen;
import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.Witch;

public class MoveMessage extends AbstractMessage {

    private int walkFields;
    //private int diceResult;
    private Witch selectedWitch;
    private Gamescreen activity;



    private String msg = "witch moved";

    public MoveMessage() {
        super(MessageTag.MOVE_WITCH);
    }


    public String getMsg() {
        return msg;
    }

    public void setWalkFields(int walkFields) {
        this.walkFields = walkFields;
    }

    public int getWalkFields() {
        return walkFields;
    }

    public void walkFields() {
        selectedWitch.moveWitch(activity.getFelder()[(selectedWitch.getCurrentField().getNumber() + getWalkFields() % 40)]);
    }
}
