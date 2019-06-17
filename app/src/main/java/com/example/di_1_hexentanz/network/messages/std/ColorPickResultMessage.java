package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractColorMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.PlayerColor;

public class ColorPickResultMessage extends AbstractColorMessage {

    private boolean successful;

    public ColorPickResultMessage(boolean successful, PlayerColor color) {
        super(MessageTag.PICK_COLOR, color);
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
