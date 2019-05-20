package com.example.di_1_hexentanz.player;

import android.support.v7.app.AppCompatActivity;

public class Goal extends AppCompatActivity {



    public boolean canGoInGoal( Witch witch, int lastDiceResult) {
        for (int i = 1; i <= lastDiceResult; i++) {
            if((witch.getCurrentField().getNumber()+i)%40 == witch.player.getZielFeld().getNumber()
            || witch.getCurrentField().getNumber()+i == witch.player.getZielFeld().getNumber()){
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
       int witchFeld = witch.getPlayer().getZielFeld().getNumber();

       try{
           witchFeld = witch.getCurrentField().getNumber();
       }catch (NullPointerException e){
           return loop(witch,lastDiceResult,witchFeld);

       }

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
