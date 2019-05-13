package com.example.di_1_hexentanz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Gamescreen extends AppCompatActivity implements SensorEventListener {

    ArrayList<Witch> witches = new ArrayList<>();
    private Feld[] felder = new Feld[36];
    Witch selectedWitch;
    private static PlayerColor color;
    int height;
    int fieldRadius;
    int width;
    int fieldwidth;
    DisplayMetrics displayMetrics;
    boolean colorVisible = false;
    private GameState state;
    private int lastDiceResult;
    TouchableSurface surface;
    private int maxWitches;
    private Player currentPlayer;
    private TextView txtHome;
    private Dice dice;

    //Sensor variables:
    TextView luminosity;
    SensorManager sensorManager;
    Sensor sensor;

    public Feld[] getFelder() {
        return felder;
    }

    public int getLastDiceResult() {
        return lastDiceResult;
    }

    public void setLastDiceResult(int lastDiceResult) {
        this.lastDiceResult = lastDiceResult;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }


    public static void setColor(PlayerColor color) {
        Gamescreen.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamescreen);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //Sensor Stuff:
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        this.dice = new Dice();
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //State to start with
        state = GameState.MyTurn;

        findViewById(R.id.TestDisplay).setVisibility(View.INVISIBLE);

        maxWitches = 4;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (displayMetrics.heightPixels / 2) - 80;
        width = displayMetrics.widthPixels / 2;
        fieldRadius = width / 20;
        fieldwidth = 2 * fieldRadius + 10;


        drawBoardGame();

        //rollDice();

        YourTurnButton yourTurnButton = new YourTurnButton(getApplicationContext(), displayMetrics);
        addContentView(yourTurnButton, findViewById(R.id.contraintLayout).getLayoutParams());

        YesButton yb = new YesButton(getApplicationContext(), displayMetrics);
        addContentView(yb, findViewById(R.id.contraintLayout).getLayoutParams());
        yb.setVisibility(View.INVISIBLE);

        NoButton nb = new NoButton(getApplicationContext(), displayMetrics);
        addContentView(nb, findViewById(R.id.contraintLayout).getLayoutParams());
        nb.setVisibility(View.INVISIBLE);

        Button testButton1 = findViewById(R.id.button2);
        testButton1.bringToFront();
        testButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorVisible) {
                    for (int i = 0; i < witches.size(); i++) {
                        witches.get(i).hideColor();
                    }
                    colorVisible = false;
                } else {
                    for (int i = 0; i < witches.size(); i++) {
                        witches.get(i).showColor();
                    }
                    colorVisible = true;
                }
            }
        });


        switch (color) {
            case BLUE:
                currentPlayer = new Player("Player1", PlayerColor.BLUE, 1, maxWitches, felder[0], felder[35]);
                break;

            case GREEN:
                currentPlayer = new Player("Player2", PlayerColor.GREEN, 2, maxWitches, felder[12], felder[11]);
                break;

            case YELLOW:
                currentPlayer = new Player("Player3", PlayerColor.YELLOW, 3, maxWitches, felder[18], felder[17]);
                break;

            case RED:
                currentPlayer = new Player("Player4", PlayerColor.RED, 4, maxWitches, felder[30], felder[29]);
                break;
            default:
                throw new RuntimeException("unreachable case");
        }


        currentPlayer.initWitches(getApplicationContext(), fieldRadius);

        for (int i = 0; i < maxWitches; i++) {
            //   currentPlayer.getWitches()[i].putWitchOnGameboard(this);
            this.witches.add(currentPlayer.getWitches()[i]);
        }

        txtHome = findViewById(R.id.txtHome);
        txtHome.setText("At home: " + currentPlayer.getWitchesAtHome());

        surface = new TouchableSurface(getApplicationContext(), felder, yourTurnButton, yb, nb, this, this.dice);
        surface.setColor(color);
        addContentView(surface, findViewById(R.id.contraintLayout).getLayoutParams());

    }


    public void startDice() {

    }


    private void drawBoardGame() {

        for (int i = 0; i < 13; i++) {
            felder[i] = new Feld(i, width - (6 * fieldwidth) + i * fieldwidth, height + (3 * fieldwidth), fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 13; i < 18; i++) {
            felder[i] = new Feld(i, width + (6 * fieldwidth), height - (3 * fieldwidth) - (i - 18) * fieldwidth, fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 18; i < 31; i++) {
            felder[i] = new Feld(i, width + (6 * fieldwidth) - (i - 18) * fieldwidth, height - (3 * fieldwidth), fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 31; i < 36; i++) {
            felder[i] = new Feld(i, width - (6 * fieldwidth), height - (2 * fieldwidth) + (i - 31) * fieldwidth, fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (allWitchesOnBoard()) {

                    int result = data.getIntExtra("result", -1);
                    lastDiceResult = result;
                    state = GameState.SelectWitch;
                    surface.hideYourTurnButton();
                    TextView output = findViewById(R.id.TestDisplay);
                    String outputText = "Bewege eine Hexe um " + lastDiceResult + " Felder!";
                    output.setText(outputText);
                    output.setVisibility(View.VISIBLE);

                } else {
                    int result = data.getIntExtra("result", -1);
                    lastDiceResult = result;
                    state = GameState.PutWitchOnBoard;
                    /**PERFORM TOUCH**/
                    this.surface.dispatchTouchEvent(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis()+100,
                            MotionEvent.ACTION_DOWN,
                            0F,
                            0F,
                            0
                    ));

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void returnToWitchSelection() {
        state = GameState.SelectWitch;
        surface.getSelectedWitch().getCurrentField().unhighlight();
        surface.hideYourTurnButton();
        TextView output = findViewById(R.id.TestDisplay);
        String outputText = "Bewege eine Hexe um " + lastDiceResult + " Felder!";
        output.setText(outputText);
        output.setVisibility(View.VISIBLE);
    }

    public ArrayList<Witch> getWitches() {
        return witches;
    }

    public void step2(int result) {
        selectedWitch.moveWitch(felder[result]);
    }


    public void witchSelected(final Witch witch, YesButton yb, NoButton nb) {
        setState(GameState.ConfirmSelection);
        witch.getCurrentField().highlight();
        TextView outputtext = findViewById(R.id.TestDisplay);
        outputtext.setText("Diese Hexe bewegen?");
        yb.setVisibility(View.VISIBLE);
        nb.setVisibility(View.VISIBLE);

    }

    public boolean allWitchesOnBoard() {
        return this.currentPlayer.getWitchesAtHome() == 0;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void updateTextAtHome(int n) {
        this.txtHome.setText("At home: " + n);
    }

    public void putWitchOnGameboard(Witch witch, YesButton yb, NoButton nb) {
        Feld destination = felder[(witch.getPlayer().getStartFeld().getNumber() + lastDiceResult-1) % 36];
        witch.putWitchOnGameboard(this, destination);
        yb.setVisibility(View.INVISIBLE);
        nb.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            //Light Sensor action

            if(event.values[0] > 100){
                //bright
            }else if(event.values[0] < 100 && event.values[0] >= 50){
                //cloudy
            }else if(event.values[0] < 50 && event.values[0] >= 25){
                //dusky
            }else if(event.values[0] < 25 && event.values[0] >= 5){
                //nearly_dark
            }else if (event.values[0] < 5) {
                //dark

                //pause sensor
                sensorManager.unregisterListener(this);

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Gamescreen.this, R.style.AlertDialogStyle);
                a_builder.setMessage("It is dark and cloudy tonight. The New Moon is rising in the sky," +
                        " but it is barely giving off light. This may be an opportunity for you! " +
                        "You look around but you don't see anybody. Do you want to cheat?")
                        .setCancelable(false)
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO:
                                //YES I WANT TO CHEAT!
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO:
                                //I DONT WANT TO CHEAT!
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.show();


                //for now register Listener again, so Sensor restarts after action
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}