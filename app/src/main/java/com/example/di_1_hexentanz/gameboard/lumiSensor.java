package com.example.di_1_hexentanz.gameboard;

import android.view.View;
import android.widget.ImageView;

public class lumiSensor {

    private float luminosity;

    private String luminosityState;
    private boolean sensorActive;
    private boolean firedSensorThisRound;

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

    public void setSensorActive(boolean sensorActive) {
        this.sensorActive = sensorActive;
    }

    public boolean getFiredSensorThisRound() {
        return firedSensorThisRound;
    }

    public void setFiredSensorThisRound(boolean firedSensorThisRound) {
        this.firedSensorThisRound = firedSensorThisRound;
    }




}
