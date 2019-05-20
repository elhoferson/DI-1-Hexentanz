package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.gameboard.GameState;
import com.example.di_1_hexentanz.gameboard.Gamescreen;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GamescreenTest {

    private Gamescreen testscreen;
    private Feld[] felder = new Feld[36];
    private int lastResult = 6;
    private int maxWitches;
    private Player testPlayer = new Player("Player1", PlayerColor.BLUE, 1, maxWitches, felder[0], felder[35]);

    @Before
    public void setTestScreen() {
        testscreen = new Gamescreen();
    }

    @Test
    public void testGetState() {
        testscreen.setState(GameState.SHOW_WITCH_COLOURS);
        assertEquals(testscreen.getState(), GameState.SHOW_WITCH_COLOURS);
    }

    @Test
    public void testGetResult() {
        testscreen.setLastDiceResult(lastResult);
        assertEquals(testscreen.getLastDiceResult(), 6);
    }

    @Test
    public void testPlayerColor() {
        assertTrue(testPlayer.getColor() == PlayerColor.BLUE);
    }

   }

