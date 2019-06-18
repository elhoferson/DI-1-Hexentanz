package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.gameboard.GameState;
import com.example.di_1_hexentanz.gameboard.Gamescreen;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class GamescreenTest {

    private Gamescreen testscreen;
    private Feld[] felder = new Feld[36];
    private int lastResult = 6;
    private int maxWitches = 4;
    private Player testPlayer = new Player("Player1", PlayerColor.BLUE, 1, maxWitches, felder[0], felder[35]);

    @Before
    public void setTestScreen() {
        testscreen = new Gamescreen();
    }

    @Test
    public void testGetState() {
        testscreen.setState(GameState.SELECT_WITCH_COLOR);
        assertEquals(testscreen.getState(), GameState.SELECT_WITCH_COLOR);
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

    @Test
    public void testGetPlayerFromColor(){
        PlayerColor[] colors  = new PlayerColor[]{PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED};
        for(int i = 0; i < colors.length; i++) {
            Assert.assertEquals(colors[i], testscreen.getPlayerFromColour(colors[i], maxWitches).getColor());
        }

    }

    @Test (expected = RuntimeException.class)
    public void testGetPlayerFromColorFail(){
        PlayerColor color = PlayerColor.BLACK;
        testscreen.getPlayerFromColour(color, maxWitches);
    }

    @Test
    public void testShowWitchColours(){
        /**introduce sleeps to wait for showWitchColours' action**/
        //first call should change colorVisible to true
        testscreen.showWitchColours();
        try{
            Thread.sleep(2001);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Assert.assertTrue(testscreen.isColorVisible());

        //second call should change colorVisible to false
        testscreen.showWitchColours();
        try{
            Thread.sleep(2001);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Assert.assertFalse(testscreen.isColorVisible());
    }

   }

