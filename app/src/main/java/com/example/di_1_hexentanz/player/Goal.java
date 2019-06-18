package com.example.di_1_hexentanz.player;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;


public class Goal implements Serializable {




    public boolean canGoInGoal( Witch witch, int lastDiceResult) {
        for (int i = 1; i <= lastDiceResult; i++) {
            if((witch.getCurrentField().getNumber()+i)%40 == witch.player.getZielFeld().getNumber()
            || witch.getCurrentField().getNumber()+i == witch.player.getZielFeld().getNumber()){
               return true;

            }

    }
    return false;

    }

    public void checkRightGoal(Witch witch, Player player){
        if(witch.getPlayer() != player){
            player.setWitchesInGoal(player.getWitchesAtHome()+1);
        }else witch.getPlayer().setWitchesInGoal(witch.getPlayer().getWitchesInGoal()+1);
    }


    public boolean isWinner(Player player){
       if( player.getWitchesInGoal() == 4){
           return true;
       }else return false;
    }

    public boolean checkIfGoalInWay(Witch witch, int lastDiceResult) {
       int witchFeld;


       if(witch.getCurrentField() == null){
          witchFeld = witch.getPlayer().getZielFeld().getNumber();
       }else witchFeld = witch.getCurrentField().getNumber();

      return loop(witch,lastDiceResult,witchFeld);
       

}




    public boolean loop(Witch witch, int lastDiceResult, int witchFeld){


        for (int i = 1; i <= lastDiceResult; i++) {
            if(witchFeld+i != witch.player.getZielFeld().getNumber() && (witchFeld+i)%40 == 0 ||
                    witchFeld+i != witch.player.getZielFeld().getNumber() && witchFeld+i == 14||
                    witchFeld+i != witch.player.getZielFeld().getNumber() && witchFeld+i == 20||
                    witchFeld+i != witch.player.getZielFeld().getNumber() && witchFeld+i == 34){
                return true;
            }

        } return false;
    }

}
