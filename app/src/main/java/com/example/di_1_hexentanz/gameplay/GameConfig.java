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
    private Map<Integer, List<Integer>> skipPlayersPerRound = new HashMap<>();
    private Map<Integer, Integer> playersCheatRound = new HashMap<>();
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
    
    public Boolean putPlayerCheated(Integer clientId) {
        if (playersCheatRound.containsKey(clientId)) {
            // already cheated
            return false;
        }
        
        playersCheatRound.put(clientId, round);
            
    }
    
    public Boolean checkPlayerCheatedThisRound(Integer clientId) {
        Integer cheatRound = playersCheatRound.get(clientId);
        if (cheatRound != null) {
            //player never cheated
            return false;
        }
        
        if (cheatRound == round) {
            // the player cheated this round
            return true;
        } else {
            // cheated in a round before
            return false;
        }    
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

    public void addSkipPlayerNextRound(Integer clientId) {
        Integer nextRound = round + 1;
        List<Integer> skipPlayersNextRound = getSkipPlayers(nextRound);
        skipPlayersNextRound.add(clientId);
        skipPlayersPerRound.put(nextRound, skipPlayersNextRound);
    }

    private List<Integer> getSkipPlayers(Integer round) {
        List<Integer> skipPlayers = skipPlayersPerRound.get(round);
        if (skipPlayers == null) {
            skipPlayers = new ArrayList<>();
        }
        return skipPlayers;
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
        // get next client from turn order
        Integer nextClient = getTurnOrder().get(index);
        // if next player has to be skipped --> recursive call
        if (getSkipPlayers(round).contains(nextClient)) {
            nextClient = getNextClient(nextClient);
        }
        return nextClient;
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
