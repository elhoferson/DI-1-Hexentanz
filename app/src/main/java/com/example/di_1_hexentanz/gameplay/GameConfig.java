package com.example.di_1_hexentanz.gameplay;

import com.example.di_1_hexentanz.player.PlayerColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameConfig {

    private static GameConfig instance = new GameConfig();

    private Map<Integer, PlayerColor> playerColors = Collections.synchronizedMap(new HashMap<Integer, PlayerColor>());

    private List<Integer> turnOrder;

    private GameConfig () {

    }

    public static GameConfig getInstance() {
        return instance;
    }

    public Boolean registerPlayerColor(Integer clientId, PlayerColor color) {
        synchronized (playerColors) {
            if (playerColors.containsValue(color)) {
                return false;
            } else {
                playerColors.put(clientId, color);
                return true;
            }
        }
    }

    public List<Integer> getTurnOrder() {
        return turnOrder;
    }

    public Map<Integer, PlayerColor> getPlayerColors() {
        return playerColors;
    }

    public void calculateTurnOrder() {
        if (turnOrder == null) {
            turnOrder = new ArrayList<>(playerColors.keySet());
            Collections.shuffle(turnOrder);
        }
    }


}
