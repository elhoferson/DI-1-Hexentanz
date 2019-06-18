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
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.dice.DiceUI;
import com.example.di_1_hexentanz.gameboard.buttons.CustomButton;
import com.example.di_1_hexentanz.gameboard.buttons.IButton;
import com.example.di_1_hexentanz.gameplay.GameConfig;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.messages.listener.AbstractClientMessageReceivedListener;
import com.example.di_1_hexentanz.network.messages.listener.AbstractHostMessageReceivedListener;
import com.example.di_1_hexentanz.network.messages.std.BeginTurnMessage;
import com.example.di_1_hexentanz.network.messages.std.EndTurnMessage;
import com.example.di_1_hexentanz.network.messages.std.MoveMessage;
import com.example.di_1_hexentanz.network.messages.std.TurnMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Client;
import com.example.di_1_hexentanz.network.mordechaim_server.Server;
import com.example.di_1_hexentanz.player.Goal;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.player.Winnerpop;
import com.example.di_1_hexentanz.player.Witch;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Gamescreen extends AppCompatActivity implements SensorEventListener {

    ArrayList<Witch> allWitches = new ArrayList<>();
    private Feld[] felder = new Feld[40];
    private Feld[] goalfelder = new Feld[16];
    Witch selectedWitch;
    int height;
    int fieldRadius;
    int width;
    int fieldwidth;
    DisplayMetrics displayMetrics;
    private boolean colorVisible = false;
    private GameState state;
    private int lastDiceResult;
    TouchableSurface surface;
    private Player currentPlayer;
    private TextView txtHome;
    private TextView txtGoal;
    private DiceUI dice = new DiceUI();
    private Goal goal = new Goal();
    MoveMessage moveMessage;


    //handleTouch
    int next;
    //private Goal goal = new Goal();
    int goalFeld = 0;
    Witch[] witchesPlayer = new Witch[4];


    //Sensor variables:
    private ImageView luminosityIcon;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Button askForCheated;
    LumiSensor lumiSensor;

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
        lumiSensor = new LumiSensor();
        lumiSensor.sensorActive = true;

        luminosityIcon = findViewById(R.id.luminosityView);
        luminosityIcon.setImageResource(R.drawable.bright_transparent);

        askForCheated = findViewById(R.id.askForCheatedButton);
        askForCheated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lumiSensor.askForCheated();
            }
        });
        lumiSensor.setFiredSensorThisRound(false);


        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //State to start with
        state = GameState.MY_TURN;

        findViewById(R.id.TestDisplay).setVisibility(INVISIBLE);

        int maxWitches = 4;

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (displayMetrics.heightPixels / 2) - 80;
        width = displayMetrics.widthPixels / 2;
        fieldRadius = width / 20;
        fieldwidth = 2 * fieldRadius + 10;

        // add listeners for clients - remember also the host is a client
        NetworkLogic.getInstance().getClient().addClientListener(new AbstractClientMessageReceivedListener<TurnMessage>() {

            @Override
            public void handleReceivedMessage(Client client, TurnMessage msg) {
                Log.e("TURN", "It's my turn");

            }
        });


        NetworkLogic.getInstance().getClient().addClientListener(new AbstractClientMessageReceivedListener<MoveMessage>() {
            @Override
            public void handleReceivedMessage(Client client, MoveMessage msg) {
                // TODO move the witch, maybe if i was the client who send the message i don't have to move the witch again because it's already done
                if (NetworkLogic.getInstance().getClient() == client) {
                    return;
                } else {
                    moveWitchesAllClients(msg);
                }

            }
        });

        NetworkLogic.getInstance().getClient().addClientListener(new AbstractClientMessageReceivedListener<BeginTurnMessage>() {
            @Override
            public void handleReceivedMessage(Client client, BeginTurnMessage msg) {
                surface.itsMyTurn();
            }
        });
        
        drawBoardGame();


        CustomButton yourTurnButton = new CustomButton(getApplicationContext(), displayMetrics, IButton.BtnType.YOUR_TURN_BTN);
        addContentView(yourTurnButton, findViewById(R.id.contraintLayout).getLayoutParams());

        CustomButton yb = new CustomButton(getApplicationContext(), displayMetrics, IButton.BtnType.YES_BTN);
        addContentView(yb, findViewById(R.id.contraintLayout).getLayoutParams());
        yb.setVisibility(INVISIBLE);

        CustomButton nb = new CustomButton(getApplicationContext(), displayMetrics, IButton.BtnType.NO_BTN);
        addContentView(nb, findViewById(R.id.contraintLayout).getLayoutParams());
        nb.setVisibility(INVISIBLE);



        PlayerColor color = (PlayerColor) getIntent().getSerializableExtra("playerColor");
        this.currentPlayer = getPlayerFromColour(color, maxWitches);


        currentPlayer.initWitches(getApplicationContext(), fieldRadius);

        for (int i = 0; i < maxWitches; i++) {
            this.allWitches.add(currentPlayer.getWitches()[i]);
        }

        txtHome = findViewById(R.id.txtHome);
        txtHome.setText(String.format("At home: %d", currentPlayer.getWitchesAtHome()) );

        txtGoal = findViewById(R.id.txtGoal);
        txtGoal.setText(String.format("At goal: %d" ,currentPlayer.getWitchesInGoal()));

        surface = new TouchableSurface(getApplicationContext(), this, dice, currentPlayer, yourTurnButton, yb, nb);
        surface.setColor(color);
        addContentView(surface, findViewById(R.id.contraintLayout).getLayoutParams());
        
        // add listeners for the host
        if (NetworkLogic.getInstance().isHost()) {
            NetworkLogic.getInstance().getHost().addServerListener(new AbstractHostMessageReceivedListener<EndTurnMessage>() {
                @Override
                public void handleReceivedMessage(Server server, Server.ConnectionToClient client, EndTurnMessage msg) {
                    Integer nextClient = GameConfig.getInstance().getNextClient(client.getClientId());
                    NetworkLogic.getInstance().sendMessageToClient(new BeginTurnMessage(), nextClient);
                }
            });

            NetworkLogic.getInstance().getHost().addServerListener(new AbstractHostMessageReceivedListener<MoveMessage>() {
                @Override
                public void handleReceivedMessage(Server server, Server.ConnectionToClient client, MoveMessage msg) {
                    // distribute move message to all clients
                    NetworkLogic.getInstance().sendMessageToAll(new MoveMessage());
                }
            });
            
            // calculate turn order and send starter turn msg
            GameConfig.getInstance().calculateTurnOrder();
            Integer firstPlayer = GameConfig.getInstance().getStarter();
            NetworkLogic.getInstance().sendMessageToClient(new TurnMessage(), firstPlayer);
        } 

    }

    private void moveWitchesAllClients(MoveMessage msg) {
        msg.getSelectedWitch().moveWitch(getFelder()[msg.getSelectedWitch().getCurrentField().getNumber() + msg.getDiceResult()]);
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
                    for (Witch w : allWitches) {
                        w.showColor();
                    }
                    colorVisible = true;
                } else {
                    for (Witch w : allWitches) {
                        w.hideColor();
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
            goalfelder[i] = new Feld(i, width + (6 * fieldwidth) * 3, height + (2 * fieldwidth) + ((i - 4) - 31) * fieldwidth + 50, fieldRadius, getApplicationContext());
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

        sensorManager.registerListener(Gamescreen.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(Gamescreen.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                lastDiceResult = data.getIntExtra("result", -1);

                if (allWitchesOnBoard()) {

                    selectWitch();

                } else {

                    setState(GameState.PUT_WITCH_ON_BOARD);

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
        selectedWitch.getCurrentField().unhighlight();
        selectWitch();
    }


    public void selectWitch() {
        surface.hideYourTurnButton();
        TextView output = findViewById(R.id.TestDisplay);
        String outputText;
        output.setVisibility(VISIBLE);

        if (lastDiceResult == 7) {
            state = GameState.SELECT_WITCH_COLOR;
            outputText = "Wähle eine Hexe zum Aufdecken";

        } else {
            state = GameState.SELECT_WITCH_MOVE;
            outputText = "Bewege eine Hexe um " + lastDiceResult + " Felder!";
        }

        output.setText(outputText);
    }

    public ArrayList<Witch> getAllWitches() {
        return allWitches;
    }


    public void witchSelected(final Witch witch, CustomButton yb, CustomButton nb) {
        yb.setVisibility(VISIBLE);
        nb.setVisibility(VISIBLE);
        witch.getCurrentField().highlight();
        TextView outputtext = findViewById(R.id.TestDisplay);

        if (lastDiceResult == 7) {
            setState(GameState.CONFIRM_WITCH_COLOR);
            outputtext.setText("Diese Hexe aufdecken?");

        } else {
            setState(GameState.CONFIRM_WITCH_MOVE);
            witch.getCurrentField().highlight();
            outputtext.setText("Diese Hexe bewegen?");
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

    public void updateTextInGoal(int number) {
        this.txtGoal.setText("At goal: " + number);
    }

    public void putWitchOnGameboard(Witch witch) {
        Feld destination;

        if (goal.checkIfGoalInWay(witch, lastDiceResult)) {

            destination = felder[(witch.getPlayer().getStartFeld().getNumber() + 1 + lastDiceResult - 1) % 40];
        } else
            destination = felder[(witch.getPlayer().getStartFeld().getNumber() + lastDiceResult - 1) % 40];


        witch.putWitchOnGameboard(this, destination);
        surface.yb.setVisibility(INVISIBLE);
        surface.nb.setVisibility(INVISIBLE);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        updateSensor(event);
    }

    private void updateSensor(SensorEvent event) {
        //only fire sensor action if Player hasn't cheated before
        if (!currentPlayer.getHasCheated() && lumiSensor.getSensorActive() && !lumiSensor.getFiredSensorThisRound()) {
            //needed for canceling if alert is showing


            lumiSensor.setLuminosity(event.values[0]);
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                //Light Sensor action

                if (lumiSensor.getLuminosity() > 100) {
                    //bright
                    luminosityIcon.setImageResource(R.drawable.bright_transparent);
                    lumiSensor.setLuminosityState("bright");

                } else if (lumiSensor.getLuminosity() < 100 && lumiSensor.getLuminosity() >= 50) {
                    //cloudy
                    luminosityIcon.setImageResource(R.drawable.cloudy_transparent);
                    lumiSensor.setLuminosityState("cloudy");

                } else if (lumiSensor.getLuminosity() < 50 && lumiSensor.getLuminosity() >= 25) {
                    //dusky
                    luminosityIcon.setImageResource(R.drawable.dusky_transparent);
                    lumiSensor.setLuminosityState("dusky");

                } else if (lumiSensor.getLuminosity() < 25 && lumiSensor.getLuminosity() >= 5) {
                    //nearly_dark
                    luminosityIcon.setImageResource(R.drawable.nearly_dark_transparent);
                    lumiSensor.setLuminosityState("nearly_dark");

                } else if (lumiSensor.getLuminosity() < 5) {
                    //dark
                    luminosityIcon.setImageResource(R.drawable.dark_transparent);
                    lumiSensor.setLuminosityState("dark");


                    //pause sensor
                    lumiSensor.sensorActive = false;
                    lumiSensor.setFiredSensorThisRound(true);

                    lumiSensor.alertDialogDoYouWantToCheat();

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
    
    public void playGame() {

        if (getState() == GameState.MY_TURN) {

            if (colorVisible) {
                showWitchColours();
            }

            if (surface.clickedYourTurnButton()) {
                Intent i = new Intent(getApplicationContext(), DiceUI.class);
                i.putExtra("allWitchesOnBoard", allWitchesOnBoard());
                startActivityForResult(i, 1);

            }


        }

        /**
         * put all allWitches on game board
         */
        if (getState() == GameState.PUT_WITCH_ON_BOARD) {

            putWitchOnGameboard(getCurrentPlayer().getWitches()[surface.next - 1]);

            surface.next--;
            getCurrentPlayer().setWitchesAtHome(getCurrentPlayer().getWitchesAtHome() - 1);
            updateTextAtHome(getCurrentPlayer().getWitchesAtHome());
            setState(GameState.MY_TURN);
        }


        /**
         * if rolled number 6 and want to show color of one witch
         */
        if (getState() == GameState.SELECT_WITCH_COLOR || getState() == GameState.SELECT_WITCH_MOVE) {
            getSelectedWitch();
        }


        /**
         * witch, where color is shown
         */
        if (getState() == GameState.CONFIRM_WITCH_COLOR) {
            if (surface.clickedYesButton()) {
                unhighlightSelectedWitch();

                selectedWitch.showColor();

                colorVisible = true;

                surface.setNextPlayer();
            } else if (surface.clickedNoButton()) {
                surface.changeSelectedWitch();

            }
        }


        /**
         * move selected witch
         */
        if (getState() == GameState.CONFIRM_WITCH_MOVE) {
            if (surface.clickedYesButton()) {
                unhighlightSelectedWitch();


                if (getLastDiceResult() == 6) {
                    AlertDialog.Builder popupNumber6 = new AlertDialog.Builder(Gamescreen.this);
                    popupNumber6.setCancelable(false);
                    popupNumber6.setTitle("Du hast eine 6 gewürfelt, entscheide deinen nächsten Zug!");
                    popupNumber6.setPositiveButton("Farbe der Hexe anzeigen", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            showWitchColours();


                        }
                    })
                            .setNegativeButton("6 Felder gehen", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    selectedWitch.moveWitch(getFelder()[(selectedWitch.getCurrentField().getNumber() + 6 + getLastDiceResult()) % 40]);
                                    moveMessage.setSelectedWitch(selectedWitch);
                                    moveMessage.setDiceResult(6);
                                    NetworkLogic.getInstance().sendMessageToHost(moveMessage);


                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }


                /**
                 * move witch in goal or not
                 */
                if (goal.canGoInGoal(selectedWitch, getLastDiceResult())) {
                    AlertDialog.Builder goInGoal = new AlertDialog.Builder(Gamescreen.this);

                    goInGoal.setCancelable(false);
                    goInGoal.setTitle("Mit Hexe ins Ziel gehen?");
                    goInGoal.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            goal.goInGoal(selectedWitch.getPlayer());
                            if (goal.isWinner(selectedWitch.getPlayer())) {
                                Intent gewonnen = new Intent(Gamescreen.this, Winnerpop.class);
                                startActivity(gewonnen);
                            }

                            selectedWitch.moveWitch(goalfelder[goalFeld]);
                            goalFeld++;
                            updateTextInGoal(getCurrentPlayer().getWitchesInGoal());
                        }
                    })
                            .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    selectedWitch.moveWitch(getFelder()[(selectedWitch.getCurrentField().getNumber() + 1 + getLastDiceResult()) % 40]);


                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();


                } else if (goal.checkIfGoalInWay(selectedWitch, getLastDiceResult())) {
                    selectedWitch.moveWitch(getFelder()[(selectedWitch.getCurrentField().getNumber() + 1 + getLastDiceResult()) % 40]);

                } else {
                    //checkIfWitchIsOnField();
                    selectedWitch.moveWitch(getFelder()[(selectedWitch.getCurrentField().getNumber() + getLastDiceResult()) % 40]);
                    moveMessage.setSelectedWitch(selectedWitch);
                    moveMessage.setDiceResult(getLastDiceResult());
                    NetworkLogic.getInstance().sendMessageToHost(moveMessage);

                }

                surface.setNextPlayer();

            } else if (surface.clickedNoButton()) {
                surface.changeSelectedWitch();
            }

        }
    }

    private void unhighlightSelectedWitch() {
        selectedWitch.getCurrentField().unhighlight();
    }

    private void getSelectedWitch() {
        for (int i = 0; i < felder.length; i++) {
            if (surface.x < felder[i].getX() + 45 && surface.x > felder[i].getX() - 45 && surface.y < felder[i].getY() + 45 && surface.y > felder[i].getY() - 45) {
                for (int j = 0; j < getAllWitches().size(); j++) {
                    if (getAllWitches().get(j).currentField.getNumber() == felder[i].getNumber()) {
                        selectWitch(getAllWitches().get(j));
                    }
                }
            }
        }
    }


    private void selectWitch(Witch witch) {
        selectedWitch = witch;
        witchSelected(witch, surface.yb, surface.nb);
    }


    /**
     * check if there is already a witch on the field
     */
   /* public void checkIfWitchIsOnField() {
        for (int i = 0; i < allWitches.size(); i++) {

            if (allWitches.get(i).getCurrentField().getNumber() == selectedWitch.getCurrentField().getNumber() + getLastDiceResult()) {
                allWitches.get(i).moveWitch(getFelder()[allWitches.get(i).getCurrentField().getNumber() % 40 - 4]);
            }

        }
    }*/

   public Player getPlayerFromColour(PlayerColor color, int maxWitches){
       Player currentPlayer;
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
       return currentPlayer;
   }

   public boolean isColorVisible(){
       return this.colorVisible;
   }


}

