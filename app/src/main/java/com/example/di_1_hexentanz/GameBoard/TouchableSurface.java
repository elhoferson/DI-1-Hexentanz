package com.example.di_1_hexentanz.GameBoard;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.example.di_1_hexentanz.Dice.DiceUI;
import com.example.di_1_hexentanz.Player;
import com.example.di_1_hexentanz.PlayerColor;
import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.Witch;

public class TouchableSurface extends View {
    Feld[] felder;
    Context context;
    Gamescreen activity;
    private Witch selectedWitch;
    private PlayerColor color;
    YourTurnButton ytb;
    YesButton yb;
    NoButton nb;
    DiceUI dice;
    Witch[] witches;
    Player player;
    Feld field;

    private int next;

    public TouchableSurface(final Context context, Feld[] felder, YourTurnButton ytb, YesButton yb, NoButton nb, Gamescreen activity, DiceUI dice, Player player) {
        super(context);
        this.felder = felder;
        this.context = context;
        this.activity = activity;
        this.player = player;
        this.dice = dice;
        this.ytb = ytb;
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

                if (activity.getState() == GameState.MyTurn) {
                    if (x > ytb.getLeftPosition() &&
                            x < ytb.getLeftPosition() + ytb.getBitmapWidth() &&
                            y > ytb.getTopPosition() &&
                            y < ytb.getTopPosition() + ytb.getBitMapHeight()) {
                        Intent i = new Intent(activity.getApplicationContext(), DiceUI.class);
                        i.putExtra("allWitchesOnBoard", activity.allWitchesOnBoard());
                        activity.startActivityForResult(i, 1);

                    }

                }

                if (activity.getState() == GameState.PutWitchOnBoard) {
                    activity.putWitchOnGameboard(activity.getCurrentPlayer().getWitches()[next - 1], yb, nb);


                    //checkIfWitchIsOnField();

                    next--;
                    activity.getCurrentPlayer().setWitchesAtHome(activity.getCurrentPlayer().getWitchesAtHome() - 1);
                    activity.updateTextAtHome(activity.getCurrentPlayer().getWitchesAtHome());
                    activity.setState(GameState.MyTurn);
                }


                if (activity.getState() == GameState.SelectWitch) {
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

                if (activity.getState() == GameState.ConfirmSelection) {
                    if (x > yb.getLeftPosition() &&
                            x < yb.getLeftPosition() + yb.getBitmapWidth() &&
                            y > yb.getTopPosition() &&
                            y < yb.getTopPosition() + yb.getBitMapHeight()) {
                        selectedWitch.getCurrentField().unhighlight();
                        selectedWitch.moveWitch(activity.getFelder()[(selectedWitch.getCurrentField().getNumber() + activity.getLastDiceResult()) % 36]);

                        //checkIfWitchIsOnField();

                        activity.setState(GameState.MyTurn);
                        nb.setVisibility(INVISIBLE);
                        yb.setVisibility(INVISIBLE);
                        ytb.setVisibility(VISIBLE);
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

    private void checkIfWitchIsOnField() {
        for(int i = 0; i < witches.length; i++) {

           if(witches[i].getCurrentField() == selectedWitch.getCurrentField()) {
               witches[i].moveWitch(activity.getFelder()[witches[i].getCurrentField().getNumber() - 4]);
            }
        }
    }

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
        ytb.setVisibility(INVISIBLE);
    }

    public void showYourTurnButton() {
        ytb.setVisibility(VISIBLE);
    }

    public Witch getSelectedWitch() {
        return selectedWitch;
    }

    public boolean isYourTurnButtonVisible() {
        return activity.getState() == GameState.MyTurn;
    }
}