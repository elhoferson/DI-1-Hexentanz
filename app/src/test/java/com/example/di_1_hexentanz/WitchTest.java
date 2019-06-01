package com.example.di_1_hexentanz;

import android.content.Context;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.player.Witch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WitchTest {

    private Feld[] felder = new Feld[36];
    private int maxWitches = 4;
    private Context context;
    private Witch testwitch;
    private int size = 1;
    private Feld currentField;
    private Player testplayer;

    @Before
    public void setTestWitch() {
        testwitch = new Witch(1, new Player("chiara", PlayerColor.BLACK, 1, maxWitches, felder[0], felder[35]), context, size);
    }



    @Test
    public void testCurrentField() {
        testwitch.setCurrentField(currentField);
        assertEquals(testwitch.getCurrentField(), currentField);
    }

    @Test
    public void testGetPlayer() {
        testwitch.setPlayer(testplayer);
        assertEquals(testwitch.getPlayer(), testplayer);

    }





}
