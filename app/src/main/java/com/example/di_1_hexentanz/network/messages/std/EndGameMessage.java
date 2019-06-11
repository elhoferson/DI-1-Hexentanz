package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.Goal;

public class EndGameMessage extends AbstractMessage {

    Goal goal = new Goal();



    public EndGameMessage() {
        super(MessageTag.END_GAME);
    }


}
