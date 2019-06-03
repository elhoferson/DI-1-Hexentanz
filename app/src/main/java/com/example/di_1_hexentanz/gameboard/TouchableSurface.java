package com.example.di_1_hexentanz.gameboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.di_1_hexentanz.gameboard.buttons.CustomButton;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.Goal;
import com.example.di_1_hexentanz.player.Winnerpop;
import com.example.di_1_hexentanz.dice.DiceUI;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.player.Witch;

import static com.example.di_1_hexentanz.dice.DiceUI.RESULT;

public class TouchableSurface extends View {
    Feld[] felder;
    Feld[] goalfelder;
    Context context;
    Gamescreen activity;
    Witch selectedWitch;
    private PlayerColor color;
    CustomButton btnYourTurn;
    CustomButton yb;
    CustomButton nb;
    DiceUI dice;
    Witch[] witches = new Witch[4];
    Player player;
    private Goal goal = new Goal();
    int goalFeld = 0;

    private int next;

    public TouchableSurface(final Context context, Feld[] felder,Feld[] goalfelder, CustomButton ytb, CustomButton yb, CustomButton nb, Gamescreen activity, DiceUI dice, Player player) {
        super(context);
        this.felder = felder;
        this.goalfelder = goalfelder;
        this.context = context;
        this.activity = activity;
        this.player = player;
        this.dice = dice;
        this.btnYourTurn = ytb;
        this.yb = yb;
        this.nb = nb;
        this.setOnTouchListener(handleTouch);

        this.next = activity.getCurrentPlayer().getWitchesAtHome();
    }

    private View.OnTouchListener handleTouch = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();


            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if (activity.getState() == GameState.MY_TURN) {
                    if (x > btnYourTurn.getLeftPosition() &&
                            x < btnYourTurn.getLeftPosition() + btnYourTurn.getBitmapWidth() &&
                            y > btnYourTurn.getTopPosition() &&
                            y < btnYourTurn.getTopPosition() + btnYourTurn.getBitMapHeight()) {
                        Intent i = new Intent(activity.getApplicationContext(), DiceUI.class);
                        i.putExtra("allWitchesOnBoard", activity.allWitchesOnBoard());
                        activity.startActivityForResult(i, 1);

                    }

                }

                if (activity.getState() == GameState.PUT_WITCH_ON_BOARD) {
                    //checkIfWitchIsOnField();
                    activity.putWitchOnGameboard(activity.getCurrentPlayer().getWitches()[next - 1], yb, nb);

                    next--;
                    activity.getCurrentPlayer().setWitchesAtHome(activity.getCurrentPlayer().getWitchesAtHome() - 1);
                    activity.updateTextAtHome(activity.getCurrentPlayer().getWitchesAtHome());
                    activity.setState(GameState.MY_TURN);
                }

                if(activity.getState() == GameState.SHOW_WITCH_COLOURS) {

                    try {
                        activity.showWitchColours();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    activity.setState(GameState.MY_TURN);
                    nb.setVisibility(INVISIBLE);
                    yb.setVisibility(INVISIBLE);
                    btnYourTurn.setVisibility(VISIBLE);
                    activity.findViewById(R.id.TestDisplay).setVisibility(INVISIBLE);


                }


                if (activity.getState() == GameState.SELECT_WITCH) {
                    for (int i = 0; i < felder.length; i++) {
                        if (x < felder[i].getX() + 45 && x > felder[i].getX() - 45 && y < felder[i].getY() + 45 && y > felder[i].getY() - 45) {
                            for (int j = 0; j < activity.getWitches().size(); j++) {
                                if (activity.getWitches().get(j).currentField.getNumber() == felder[i].getNumber()) {
                                    selectWitch(activity.getWitches().get(j));
                                }
                            }
                        }
                    }
                }

                if (activity.getState() == GameState.CONFIRM_SELECTION) {
                    if (x > yb.getLeftPosition() &&
                            x < yb.getLeftPosition() + yb.getBitmapWidth() &&
                            y > yb.getTopPosition() &&
                            y < yb.getTopPosition() + yb.getBitMapHeight()) {
                        selectedWitch.getCurrentField().unhighlight();

                        if(activity.getLastDiceResult() == 6) {
                        AlertDialog.Builder popupNumber6 = new AlertDialog.Builder(activity);
                        popupNumber6.setCancelable(false);
                            popupNumber6.setTitle("Du hast eine 6 gewürfelt, entscheide deinen nächsten Zug!");
                            popupNumber6.setPositiveButton("Farbe der Hexe anzeigen", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.showWitchColours();


                                }
                            })
                                    .setNegativeButton("6 Felder gehen", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            selectedWitch.moveWitch(activity.getFelder()[(selectedWitch.getCurrentField().getNumber()+6 + activity.getLastDiceResult()) % 40]);
                                            NetworkLogic.getInstance().sendMessageToHost(new AbstractMessage(MessageTag.MOVE_WITCH));


                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();

                        }

                        if(goal.canGoInGoal(selectedWitch, activity.getLastDiceResult())){
                            AlertDialog.Builder goInGoal = new AlertDialog.Builder(activity);

                                goInGoal.setCancelable(false);
                                goInGoal.setTitle("Mit Hexe ins Ziel gehen?");
                                goInGoal.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        goal.goInGoal(selectedWitch.getPlayer());
                                        if(goal.isWinner(selectedWitch.getPlayer())){
                                            Intent gewonnen = new Intent(activity, Winnerpop.class);
                                            activity.startActivity(gewonnen);
                                        }

                                        selectedWitch.moveWitch(goalfelder[goalFeld]);
                                        goalFeld++;
                                        activity.updateTextInGoal(activity.getCurrentPlayer().getWitchesInGoal());
                                    }
                                })
                                        .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                selectedWitch.moveWitch(activity.getFelder()[(selectedWitch.getCurrentField().getNumber()+1 + activity.getLastDiceResult()) % 40]);


                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();


                        }else if(goal.checkIfGoalInWay(selectedWitch,activity.getLastDiceResult())){


                            selectedWitch.moveWitch(activity.getFelder()[(selectedWitch.getCurrentField().getNumber()+1 + activity.getLastDiceResult()) % 40]);


                        }else selectedWitch.moveWitch(activity.getFelder()[(selectedWitch.getCurrentField().getNumber() + activity.getLastDiceResult()) % 40]);


                        //checkIfWitchIsOnField();

                        activity.setState(GameState.MY_TURN);
                        nb.setVisibility(INVISIBLE);
                        yb.setVisibility(INVISIBLE);
                        btnYourTurn.setVisibility(VISIBLE);
                        activity.findViewById(R.id.TestDisplay).setVisibility(INVISIBLE);
                    }
                    if (x > nb.getLeftPosition() &&
                            x < nb.getLeftPosition() + nb.getBitmapWidth() &&
                            y > nb.getTopPosition() &&
                            y < nb.getTopPosition() + nb.getBitMapHeight()) {
                        yb.setVisibility(INVISIBLE);
                        nb.setVisibility(INVISIBLE);
                        activity.returnToWitchSelection();
                    }
                }
                return false;
            }


            return true;
        }
    };


    /**
     * check if there is already a witch on the field
     */
/*
    public void checkIfWitchIsOnField() {
        for(int i = 0; i < activity.witches.size(); i++) {

                if(witches[i].getCurrentField().getNumber() == selectedWitch.getCurrentField().getNumber()+1 + activity.getLastDiceResult() % 40) {
                    witches[i].moveWitch(activity.getFelder()[witches[i].getCurrentField().getNumber() %40- 4]);
                }
        }
    }
    */




    private void selectWitch(Witch witch) {
        selectedWitch = witch;
        activity.witchSelected(witch, yb, nb);
    }



    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getColor() {
        return this.color;
    }

    public void hideYourTurnButton() {
        btnYourTurn.setVisibility(INVISIBLE);
    }

    public void showYourTurnButton() {
        btnYourTurn.setVisibility(VISIBLE);
    }

    public Witch getSelectedWitch() {
        return selectedWitch;
    }

    public boolean isYourTurnButtonVisible() {
        return activity.getState() == GameState.MY_TURN;
    }


    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }



}