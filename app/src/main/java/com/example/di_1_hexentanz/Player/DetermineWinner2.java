package com.example.di_1_hexentanz.player;

import android.support.v7.app.AppCompatActivity;

public class DetermineWinner2 extends AppCompatActivity {



    public boolean canGoInGoal( Witch witch, int lastDiceResult) {
        for (int i = 1; i <= lastDiceResult; i++) {
            if((witch.getCurrentField().getNumber()+i)%40 == witch.player.getZielFeld().getNumber()
            || witch.getCurrentField().getNumber()+1 == witch.player.getZielFeld().getNumber()){
               return true;
            }

    }
    return false;

    }

    public void goInGoal(Witch witch){

        witch.getPlayer().setWitchesInGoal(witch.getPlayer().getWitchesInGoal()+1);
        witch.witchView.hideColor();
    }

    public boolean isWinner(Witch witch){
       if( witch.getPlayer().getWitchesInGoal() == 4){
           return true;
       }else return false;
    }

    public boolean checkIfGoalInWay(Witch witch, int lastDiceResult) {
       int witchFeld = witch.getCurrentField().getNumber();

        for (int i = 1; i <= lastDiceResult; i++) {
            if(witchFeld+i != witch.player.getZielFeld().getNumber() && (witchFeld+i)%40 == 0 ||
                    witchFeld+i != witch.player.getZielFeld().getNumber() && witchFeld+i == 14||
                    witchFeld+i != witch.player.getZielFeld().getNumber() && witchFeld+i == 20||
                    witchFeld+i != witch.player.getZielFeld().getNumber() && witchFeld+i == 34){
                return true;
            }

    } return false;


    

} }
