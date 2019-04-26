package com.example.di_1_hexentanz;

import android.graphics.Color;
import android.widget.Toast;

public class DetermineWinner {


    Player p1 = new Player("Kevin", Color.valueOf(1));
    Player p2 = new Player("Chiara", Color.valueOf(2));

    Color witch1 = Color.valueOf(1);
    Color witch2 = Color.valueOf(2);


    String winner = "";



    public void isrightWitch(Player player, Color witch) {         //Color witch is placeholder for real witches
        if(player.getColor().equals(witch)){
            int goal = player.getWitchesInGoal();
            player.setWitchesInGoal(goal++);

            if(player.getWitchesInGoal() == 4){                       //number of ingoal is gonna change depending on player numbers
                winner = player.getName();                            //placeholder for ending game method
                endGame();
            }
        }
        else {
            wrongWitchinGoal(witch);                                  //placeholder for switching the witch to the real owner of the color
        }
    }




    public void endGame(){





    }

    public void wrongWitchinGoal(Color witch){                      //changes the witch to the right owners goal


        





    }




















}
