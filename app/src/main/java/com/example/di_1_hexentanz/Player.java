package com.example.di_1_hexentanz;

import android.graphics.Color;

public class Player {

        private int ID = 0;
        private static int IDCounter = 0;
        private String name;
        //these variables could me modified to "cheat"
        private Color color;
        private int WitchesInHome;
        private int WitchesInGoal;

        public Player(String name, Color color) {
            this.ID = IDCounter;
            IDCounter++;
            this.name = name;
            this.color = color;

            this.WitchesInHome = 4; // this will depend on the amount of players.
            this.WitchesInGoal = 0;
        }


        // GETTER AND SETTER

        public int getWitchesInHome() {
            return WitchesInHome;
        }

        public void setWitchesInHome(int witchesInHome) {
            this.WitchesInHome = witchesInHome;
        }

        public int getWitchesInGoal() {
            return WitchesInGoal;
        }

        public void setWitchesInGoal(int witchesInGoal) {
            this.WitchesInGoal = witchesInGoal;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

