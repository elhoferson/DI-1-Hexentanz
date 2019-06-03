package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.Witch;

public class MoveMessage extends AbstractMessage {

    private Witch selectedWitch;
    private int diceResult;



    private String msg = "witch moved";

    public MoveMessage() {
        super(MessageTag.MOVE_WITCH);
    }


    public String getMsg() {
        return msg;
    }


    public void setSelectedWitch(Witch selectedWitch) {
        this.selectedWitch = selectedWitch;
    }

    public Witch getSelectedWitch() {
        return selectedWitch;
    }

    public void setDiceResult(int diceResult) {
        this.diceResult = diceResult;
    }

    public int getDiceResult() {
        return diceResult;
    }

}
