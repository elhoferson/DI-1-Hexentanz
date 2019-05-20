package com.example.di_1_hexentanz;

import android.content.Context;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.player.Witch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockListener;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private Player testplayer;
    private Feld[] felder = new Feld[36];
    private int maxWitches = 4;
    private Feld startfeld;
    private Feld zielfeld;
    private PlayerColor playerColor;
    private Witch[] witches = new Witch[maxWitches];
    private Context context;
    private Player testplayerMock;


    @Before
    public void setTestPlayer() {
        testplayer = new Player("Player1", PlayerColor.BLACK, 1, maxWitches, felder[0], felder[35]);
    }


    @Test
    public void testMockCreation() {
        assertNotNull(testplayer);
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

    @Test
    public void testStartFeld() {
        testplayer.setStartFeld(startfeld);
        assertEquals(testplayer.getStartFeld(), startfeld);
    }

    @Test
    public void testZielFeld() {
        testplayer.setZielFeld(zielfeld);
        assertEquals(testplayer.getZielFeld(), zielfeld);
    }

    @Test
    public void testColor() {
        assertEquals(testplayer.getColor(), PlayerColor.BLACK);

    }

    @Test
    public void testSetWitches() {
        testplayer.setWitches(witches);
        assertEquals(testplayer.getWitches().length, maxWitches);
    }


    @Test
    public void testInitWitches() {

    }

}
