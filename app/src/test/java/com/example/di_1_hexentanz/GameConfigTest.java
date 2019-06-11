package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.gameplay.GameConfig;
import com.example.di_1_hexentanz.player.PlayerColor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameConfigTest {

    @Before
    public void setUp() {
        GameConfig.getInstance().registerPlayerColor(1, PlayerColor.BLACK);
        GameConfig.getInstance().registerPlayerColor(2, PlayerColor.BLUE);
        GameConfig.getInstance().registerPlayerColor(3, PlayerColor.ORANGE);
        GameConfig.getInstance().registerPlayerColor(4, PlayerColor.GREEN);
        GameConfig.getInstance().registerPlayerColor(5, PlayerColor.RED);
    }

    @Test
    public void preventionTest() {
        // picked colors can't be taken
        assertFalse(GameConfig.getInstance().registerPlayerColor(6, PlayerColor.RED));

        // overflow
        assertTrue(GameConfig.getInstance().registerPlayerColor(6, PlayerColor.PINK));
        assertFalse(GameConfig.getInstance().registerPlayerColor(7, PlayerColor.YELLOW));

        // game started
        GameConfig.getInstance().gameStarted();
        assertFalse(GameConfig.getInstance().registerPlayerColor(8, PlayerColor.RED));
    }

    @Test
    public void calculateTurnOrderTest() {
        List<Integer> startOrder = new ArrayList<>(GameConfig.getInstance().getPlayerColors().keySet());
        int equalsStartOrder = 0;
        int testSize = 1000;
        for (int i = 1; i <= testSize; i++) {
            GameConfig.getInstance().calculateTurnOrder();
            if (startOrder.equals(GameConfig.getInstance().getTurnOrder())) {
                equalsStartOrder++;
            }
        }
        assertTrue(equalsStartOrder <= (testSize / GameConfig.getInstance().getTurnOrder().size()));
    }

    @Test
    public void getNextTest() {
        GameConfig.getInstance().calculateTurnOrder();

        Integer next = GameConfig.getInstance().getTurnOrder().get(1);
        Integer starter = GameConfig.getInstance().getStarter();
        Integer nextClient = GameConfig.getInstance().getNextClient(starter);
        assertEquals(next.intValue(), nextClient.intValue());

        Integer last = GameConfig.getInstance().getTurnOrder().get(GameConfig.getInstance().getTurnOrder().size() - 1);
        Integer starterClient = GameConfig.getInstance().getNextClient(last);
        assertEquals(starter.intValue(), starterClient.intValue());
    }



    @After
    public void afterTest() {
        GameConfig.getInstance().reset();
    }
}
