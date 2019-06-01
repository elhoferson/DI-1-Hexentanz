package com.example.di_1_hexentanz.gameboard;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.di_1_hexentanz.dice.DiceUI;
import com.example.di_1_hexentanz.gameboard.buttons.CustomButton;
import com.example.di_1_hexentanz.gameboard.buttons.IButton;
import com.example.di_1_hexentanz.player.Goal;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.player.Witch;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Gamescreen extends AppCompatActivity implements SensorEventListener {

    ArrayList<Witch> witches = new ArrayList<>();
    private Feld[] felder = new Feld[40];
    private Feld[] goalfelder = new Feld[16];
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
    private TextView txtGoal;
    private DiceUI dice = new DiceUI();
    private Goal goal = new Goal();

    //Sensor variables:
    private float luminosity;
    private ImageView luminosityIcon;
    private SensorManager sensorManager;
    private Sensor sensor;
    private String luminosityState;
    private boolean sensorActive;

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
        luminosityIcon = findViewById(R.id.luminosityView);
        luminosityIcon.setImageResource(R.drawable.bright_transparent);
        sensorActive = true;

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //State to start with
        state = GameState.MY_TURN;

        findViewById(R.id.TestDisplay).setVisibility(View.INVISIBLE);

        maxWitches = 4;

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (displayMetrics.heightPixels / 2) - 80;
        width = displayMetrics.widthPixels / 2;
        fieldRadius = width / 20;
        fieldwidth = 2 * fieldRadius + 10;


        drawBoardGame();


        CustomButton yourTurnButton = new CustomButton(getApplicationContext(), displayMetrics, IButton.BtnType.YOUR_TURN_BTN);
        addContentView(yourTurnButton, findViewById(R.id.contraintLayout).getLayoutParams());

        CustomButton yb = new CustomButton(getApplicationContext(), displayMetrics, IButton.BtnType.YES_BTN);
        addContentView(yb, findViewById(R.id.contraintLayout).getLayoutParams());
        yb.setVisibility(View.INVISIBLE);

        CustomButton nb = new CustomButton(getApplicationContext(), displayMetrics, IButton.BtnType.NO_BTN);
        addContentView(nb, findViewById(R.id.contraintLayout).getLayoutParams());
        nb.setVisibility(View.INVISIBLE);

        Button testButton1 = findViewById(R.id.button2);
        testButton1.bringToFront();
        showWitchColours(testButton1);


        switch (color) {
            case BLUE:
                currentPlayer = new Player("Player1", PlayerColor.BLUE, 1, maxWitches, felder[1], felder[7]);
                break;

            case GREEN:
                currentPlayer = new Player("Player2", PlayerColor.GREEN, 2, maxWitches, felder[15], felder[14]);
                break;

            case YELLOW:
                currentPlayer = new Player("Player3", PlayerColor.YELLOW, 3, maxWitches, felder[21], felder[20]);
                break;

            case RED:
                currentPlayer = new Player("Player4", PlayerColor.RED, 4, maxWitches, felder[35], felder[34]);
                break;
            default:
                throw new RuntimeException("unreachable case");
        }


        currentPlayer.initWitches(getApplicationContext(), fieldRadius);

        for (int i = 0; i < maxWitches; i++) {
            this.witches.add(currentPlayer.getWitches()[i]);
        }

        txtHome = findViewById(R.id.txtHome);
        txtHome.setText("At home: " + currentPlayer.getWitchesAtHome());

        txtGoal = findViewById(R.id.txtGoal);
        txtGoal.setText("At goal: " + currentPlayer.getWitchesInGoal());

        surface = new TouchableSurface(getApplicationContext(), felder,goalfelder, yourTurnButton, yb, nb, this, dice, currentPlayer);
        surface.setColor(color);
        addContentView(surface, findViewById(R.id.contraintLayout).getLayoutParams());

    }


    public void showWitchColours(Button testButton1) {
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
    }


    /**
     * show witch colours, when rolling a 6 and clicking on positive button of alert dialog
     */
    public void showWitchColours() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
           public void run() {
                if (!colorVisible) {
                    for (int i = 0; i < witches.size(); i++) {
                        witches.get(i).showColor();
                    }
                    colorVisible = true;
                } else {
                    for (int i = 0; i < witches.size(); i++) {
                        witches.get(i).hideColor();
                    }
                    colorVisible = false;
                }
            }
            }, 2000);
    }



    private void drawBoardGame() {

        for (int i = 1; i < 14; i++) {
            felder[i] = new Feld(i, width - (6 * fieldwidth) + (i - 1) * fieldwidth, height + (3 * fieldwidth), fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        felder[0] = new Feld(0, width - (6 * fieldwidth), height - (2 * fieldwidth) + (37 - 31) * fieldwidth, fieldRadius, getApplicationContext());
        addContentView(felder[0].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());


        for (int i = 15; i < 20; i++) {
            felder[i] = new Feld(i, width + (6 * fieldwidth), height - (3 * fieldwidth) - ((i - 2) - 18) * fieldwidth, fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        felder[14] = new Feld(14, width + (6 * fieldwidth), height - (2 * fieldwidth) + (37 - 31) * fieldwidth, fieldRadius, getApplicationContext());
        addContentView(felder[14].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());


        for (int i = 21; i < 34; i++) {
            felder[i] = new Feld(i, width + (6 * fieldwidth) - ((i - 3) - 18) * fieldwidth, height - (3 * fieldwidth), fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }
        felder[20] = new Feld(20, width + (6 * fieldwidth) + 75, height - (3 * fieldwidth), fieldRadius, getApplicationContext());
        addContentView(felder[20].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());

        for (int i = 35; i <= 39; i++) {
            felder[i] = new Feld(i, width - (6 * fieldwidth), height - (2 * fieldwidth) + ((i - 4) - 31) * fieldwidth, fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        felder[34] = new Feld(34, width - (6 * fieldwidth) - 75, height - (3 * fieldwidth), fieldRadius, getApplicationContext());
        addContentView(felder[34].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());

        for (int i = 0; i <= 15; i++) {
            goalfelder[i] = new Feld(i, width + (6 * fieldwidth)*3, height + (2 * fieldwidth) + ((i-4) - 31) * fieldwidth+50, fieldRadius, getApplicationContext());
            addContentView(goalfelder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
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

                    if(lastDiceResult == 7) {
                        state = GameState.SELECT_WITCH_COLOR;
                        surface.hideYourTurnButton();
                        TextView output = findViewById(R.id.TestDisplay);
                        String outputText = "Wähle Hexe zum Aufdecken!";
                        output.setText(outputText);
                        output.setVisibility(View.VISIBLE);
                    }

                    else {
                        state = GameState.SELECT_WITCH_MOVE;
                        surface.hideYourTurnButton();
                        TextView output = findViewById(R.id.TestDisplay);
                        String outputText = "Bewege eine Hexe um " + lastDiceResult + " Felder!";
                        output.setText(outputText);
                        output.setVisibility(View.VISIBLE);
                    }


                } else {

                    int result = data.getIntExtra("result", -1);
                    lastDiceResult = result;

                    state = GameState.PUT_WITCH_ON_BOARD;

                    /**PERFORM TOUCH**/
                    this.surface.dispatchTouchEvent(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis() + 100,
                            MotionEvent.ACTION_DOWN,
                            0F,
                            0F,
                            0
                    ));

                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                throw new RuntimeException("wrong result");
            }

        }
    }

    public void returnToWitchSelection() {
        if(lastDiceResult == 7) {
            state = GameState.SELECT_WITCH_COLOR;
            surface.getSelectedWitch().getCurrentField().unhighlight();
            surface.hideYourTurnButton();
            TextView output = findViewById(R.id.TestDisplay);
            String outputText = "Wähle eine Hexe zum Aufdecken";
            output.setText(outputText);
            output.setVisibility(View.VISIBLE);
        }
        else {
            state = GameState.SELECT_WITCH_MOVE;
            surface.getSelectedWitch().getCurrentField().unhighlight();
            surface.hideYourTurnButton();
            TextView output = findViewById(R.id.TestDisplay);
            String outputText = "Bewege eine Hexe um " + lastDiceResult + " Felder!";
            output.setText(outputText);
            output.setVisibility(View.VISIBLE);
        }
    }

    public ArrayList<Witch> getWitches() {
        return witches;
    }


    public void witchSelected(final Witch witch, CustomButton yb, CustomButton nb) {
        if(lastDiceResult == 7) {
            setState(GameState.CONFIRM_WITCH_COLOR);
            witch.getCurrentField().highlight();
            TextView outputtext = findViewById(R.id.TestDisplay);
            outputtext.setText("Diese Hexe aufdecken?");
            yb.setVisibility(View.VISIBLE);
            nb.setVisibility(View.VISIBLE);
        }
        else {
            setState(GameState.CONFIRM_WITCH_MOVE);
            witch.getCurrentField().highlight();
            TextView outputtext = findViewById(R.id.TestDisplay);
            outputtext.setText("Diese Hexe bewegen?");
            yb.setVisibility(View.VISIBLE);
            nb.setVisibility(View.VISIBLE);
        }

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

    public void updateTextInGoal(int number){
        this.txtGoal.setText("At goal: " + number);
    }

    public void putWitchOnGameboard(Witch witch, CustomButton yb, CustomButton nb) {
        Feld destination;

        if (goal.checkIfGoalInWay(witch, lastDiceResult)) {

            destination = felder[(witch.getPlayer().getStartFeld().getNumber()+1 + lastDiceResult-1) % 40];
        }else destination = felder[(witch.getPlayer().getStartFeld().getNumber() + lastDiceResult-1) % 40];




        witch.putWitchOnGameboard(this, destination);
        yb.setVisibility(View.INVISIBLE);
        nb.setVisibility(View.INVISIBLE);
    }

    public void setLuminosity(float luminosity) {
        this.luminosity = luminosity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //only fire sensor action if Player hasn't cheated before
        if(!currentPlayer.getHasCheated()) {
            //needed for canceling if alert is showing
            if (sensorActive) {

                luminosity = event.values[0];
                if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    //Light Sensor action

                    if (event.values[0] > 100) {
                        //bright
                        luminosityIcon.setImageResource(R.drawable.bright_transparent);
                        luminosityState = "bright";

                    } else if (luminosity < 100 && luminosity >= 50) {
                        //cloudy
                        luminosityIcon.setImageResource(R.drawable.cloudy_transparent);
                        luminosityState = "cloudy";

                    } else if (luminosity < 50 && luminosity >= 25) {
                        //dusky
                        luminosityIcon.setImageResource(R.drawable.dusky_transparent);
                        luminosityState = "dusky";

                    } else if (luminosity < 25 && luminosity >= 5) {
                        //nearly_dark
                        luminosityIcon.setImageResource(R.drawable.nearly_dark_transparent);
                        luminosityState = "nearly_dark";

                    } else if (luminosity < 5) {
                        //dark
                        luminosityIcon.setImageResource(R.drawable.dark_transparent);
                        luminosityState = "dark";


                        //pause sensor
                        sensorActive = false;

                        //build and show Alert Dialog
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Gamescreen.this);
                        a_builder.setMessage("It is dark and cloudy tonight. This may be an opportunity for you! " +
                                "You look around, but you don't see anybody. Do you want to cheat?")
                                .setCancelable(false)
                                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //YES I WANT TO CHEAT!
                                        currentPlayer.setHasCheated(true);
                                        showWitchColours();
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        showWitchColours();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //I DONT WANT TO CHEAT!
                                        sensorActive = true;
                                    }
                                });

                        AlertDialog alert = a_builder.create();
                        alert.show();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use

    }

    public ImageView getLuminosityIcon() {
        return luminosityIcon;
    }

    public String getLuminosityState() {
        return luminosityState;
    }

    public float getLuminosity() {
        return luminosity;
    }

}