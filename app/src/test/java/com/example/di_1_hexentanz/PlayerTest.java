package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player testplayer;
    private Feld[] felder = new Feld[36];
    private int maxWitches;

    @Before
    public void setTestPlayer() {
        testplayer = new Player("Player1", PlayerColor.BLACK, 1, maxWitches, felder[0], felder[35]);
    }

    @Test
    public void testWitchesAtHome() {
        testplayer.setWitchesAtHome(4);
        assertEquals(testplayer.getWitchesAtHome(), 4);
    }

    @Test
    public void testWitchesInGoal() {
        testplayer.setWitchesInGoal(1);
        assertEquals(testplayer.getWitchesInGoal(), 1);
    }

    @Test
    public void testNumber() {
        testplayer.setNumber(10);
        assertEquals(testplayer.getNumber(), 10);
    }


}
