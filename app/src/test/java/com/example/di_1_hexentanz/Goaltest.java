package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.player.Goal;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Goaltest {


    Goal goal;
    Feld[] felder = new Feld[5];
    Player player = new Player("kevin", PlayerColor.GREEN,1,4,felder[1],felder[2]);



    @Before
    public void setUp(){
        goal = new Goal();
    }


    @Test
    public void goInGoalTest(){

    }


}
