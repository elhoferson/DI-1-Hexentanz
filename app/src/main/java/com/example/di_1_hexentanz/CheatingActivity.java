package com.example.di_1_hexentanz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CheatingActivity extends Activity implements SensorEventListener {

    TextView textView;
    SensorManager sensorManager;
    Sensor sensor;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Connect textView

        background = findViewById(R.id.imageView);
        textView.bringToFront();
        background.invalidate();
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            //Light Sensor action
            textView.setText("Luminosity: " + event.values[0]);

            if (event.values[0] < 5) {


                sensorManager.unregisterListener(this);

                AlertDialog.Builder a_builder = new AlertDialog.Builder(CheatingActivity.this);
                a_builder.setMessage("It is dark and cloudy tonight. The New Moon is rising in the sky, but it is barely giving off light. " +
                        "This may be an opportunity for you! You look around but you don't see anybody. Do you want to cheat?")
                        .setCancelable(false)
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Yes I want to cheat
                                textView.setText("CHEAT? REALLY?!? COME ON!!");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //No I dont want to cheat
                                textView.setText("Your are a fair player.. but what about the others?");
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.show();

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
