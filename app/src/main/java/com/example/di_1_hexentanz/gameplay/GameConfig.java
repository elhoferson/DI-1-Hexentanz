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
    private Boolean gameStarted = false;
    private Integer minPlayers = 2;
    private Integer maxPlayers = 6;

    private List<Integer> turnOrder;
    private Integer round = 1;

    private GameConfig() {

    }

    public static GameConfig getInstance() {
        return instance;
    }

    public Boolean registerPlayerColor(Integer clientId, PlayerColor color) {
        if (gameStarted) {
            return false;
        }
        if (playerColors.size() + 1 > maxPlayers) {
            return false;
        }
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
        turnOrder = new ArrayList<>(playerColors.keySet());
        Collections.shuffle(turnOrder);
    }

    public Integer getStarter() {
        return getTurnOrder().get(0);
    }

    public Integer getNextClient(Integer clientId) {
        // get next client in turnorder if last on is current take the first one in order
        int index = getTurnOrder().indexOf(clientId) + 1;
        if (index >= getTurnOrder().size()) {
            index = 0;
            // increase round counter
            round++;
        }
        return getTurnOrder().get(index);
    }

    public void gameStarted() {
        gameStarted = true;
    }

    public void reset() {
        instance = new GameConfig();
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Integer getRound() {
        return round;
    }
}
