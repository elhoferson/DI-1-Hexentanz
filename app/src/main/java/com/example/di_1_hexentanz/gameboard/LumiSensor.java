package com.example.di_1_hexentanz.gameboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.di_1_hexentanz.gameplay.GameConfig;


public class LumiSensor {

    Gamescreen gamescreen;
    GameConfig config;

    private float luminosity;

    private String luminosityState;
    boolean sensorActive;
    private boolean firedSensorThisRound;
    Context context;

    public LumiSensor(Context context) {
        this.context = context;
        gamescreen = new Gamescreen();
        this.config = GameConfig.getInstance();
    }


    public float getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(float luminosity) {
        this.luminosity = luminosity;
    }

    public String getLuminosityState() {
        return luminosityState;
    }

    public void setLuminosityState(String luminosityState) {
        this.luminosityState = luminosityState;
    }

    public boolean getSensorActive() {
        return sensorActive;
    }

    public boolean getFiredSensorThisRound() {
        return firedSensorThisRound;
    }

    public void setFiredSensorThisRound(boolean firedSensorThisRound) {
        this.firedSensorThisRound = firedSensorThisRound;
    }

    public void alertDialogDoYouWantToCheat() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("It is dark and cloudy tonight. This may be an opportunity for you! " +
                "You look around, but you don't see anybody. Do you want to cheat?")
                .setCancelable(false)
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //YES


                        gamescreen.getCurrentPlayer().setHasCheated(true); // bug


                        gamescreen.showWitchColours();
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        gamescreen.showWitchColours();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //NO
                        //re-enable sensor for next round
                        sensorActive = true;
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.show();
    }

    public void askForCheated() {
        final AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Did the current Player cheat?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (gamescreen.getCurrentPlayer().getHasCheated()) {
                            //TRUE
                            //Cheater muss zwei Runden aussetzen
                            Toast.makeText(context, "True! What a Cheater...",
                                    Toast.LENGTH_LONG).show();


                        } else {
                            //FALSE
                            //Petze muss eine Runde aussetzen
                            Toast.makeText(context, "Your're wrong...",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (gamescreen.getCurrentPlayer().getHasCheated()) {
                            //TRUE
                            //Cheater muss zwei Runden aussetzen
                            Toast.makeText(context, "but he or she did cheat...",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            //FALSE
                            //Petze muss eine Runde aussetzen
                            Toast.makeText(context, "You're right!",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.show();
    }

}
