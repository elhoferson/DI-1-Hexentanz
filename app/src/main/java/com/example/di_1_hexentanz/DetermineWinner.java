package com.example.di_1_hexentanz;

import android.graphics.Color;

public class DetermineWinner {


    Player p1 = new Player("Kevin", Color.valueOf(1));
    Player p2 = new Player("Chiara", Color.valueOf(2));

    Color witch1 = Color.valueOf(1);
    Color witch2 = Color.valueOf(2);

    int p1goal = 0;

    String winner = "";



    public boolean isrightWitch(Player player, Color witch) {         //Color witch is placeholder for real witches
        if(player.getColor().equals(witch)){
            int goal = player.getWitchesInGoal();
            player.setWitchesInGoal(goal++);

            if(player.getWitchesInGoal() == 4){                       //number of ingoal is gonna change depending on player numbers
                winner = player.getName();
            }
            return true;
        }
        else {return false;}
    }

    public boolean checkWitches(Player player, Color witch){

        return true;

    }



    public boolean isWinner(Color player){





        return true;
    }




















}
