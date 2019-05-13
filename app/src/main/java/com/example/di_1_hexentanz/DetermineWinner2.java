package com.example.di_1_hexentanz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class DetermineWinner2 extends AppCompatActivity {



    public void isrightWitch(Player player, Witch witch) {
        if(witch.player.getColor().equals(player.getColor())){
            player.setWitchesinGoal(player.getWitchesinGoal()+1);
        }

    }

    public boolean hasWon(Player player){

        if(player.getWitchesinGoal() == 2){
            return true;
        }else return false;

    }




    public void endGame(){
        Intent i = new Intent(getApplicationContext(),Startscreen.class);
        startActivity(i);
    }

}
