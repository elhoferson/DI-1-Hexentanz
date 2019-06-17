package com.example.di_1_hexentanz.gameboard;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.example.di_1_hexentanz.gameboard.buttons.CustomButton;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.messages.std.EndTurnMessage;
import com.example.di_1_hexentanz.player.Goal;
import com.example.di_1_hexentanz.dice.DiceUI;
import com.example.di_1_hexentanz.player.Player;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.player.Witch;

public class TouchableSurface extends View {
    Gamescreen activity;
    private PlayerColor color;
    CustomButton btnYourTurn;
    CustomButton yb;
    CustomButton nb;
    DiceUI dice;
    Player player;


    //Coordinates
    int x;
    int y;

    int next;

    public TouchableSurface(final Context context, Gamescreen activity, DiceUI dice, Player player, CustomButton ytb, CustomButton yb, CustomButton nb) {
        super(context);
        this.activity = activity;
        this.player = player;
        this.dice = dice;
        this.btnYourTurn = ytb;
        this.yb = yb;
        this.nb = nb;
        this.setOnTouchListener(handleTouch);
        this.next = activity.getCurrentPlayer().getWitchesAtHome();

    }

    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            x = (int) event.getX();
            y = (int) event.getY();


            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    activity.playGame();
                    return false;
            }

            return true;
        }
    };


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

    public boolean isYourTurnButtonVisible() {
        return activity.getState() == GameState.MY_TURN;
    }


    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public boolean clickedYesButton() {
        return (x > yb.getLeftPosition() &&
                x < yb.getLeftPosition() + yb.getBitmapWidth() &&
                y > yb.getTopPosition() &&
                y < yb.getTopPosition() + yb.getBitMapHeight());
    }


    public void setNextPlayer() {
        activity.setState(GameState.NOT_MY_TURN);
        nb.setVisibility(INVISIBLE);
        yb.setVisibility(INVISIBLE);
        btnYourTurn.setVisibility(INVISIBLE);
        activity.findViewById(R.id.TestDisplay).setVisibility(INVISIBLE);
        NetworkLogic.getInstance().sendMessageToHost(new EndTurnMessage());
    }

    public void itsMyTurn() {
        activity.setState(GameState.MY_TURN);
        nb.setVisibility(INVISIBLE);
        yb.setVisibility(INVISIBLE);
        btnYourTurn.setVisibility(VISIBLE);
        activity.findViewById(R.id.TestDisplay).setVisibility(INVISIBLE);
    }

    public boolean clickedNoButton() {
        return (x > nb.getLeftPosition() &&
                x < nb.getLeftPosition() + nb.getBitmapWidth() &&
                y > nb.getTopPosition() &&
                y < nb.getTopPosition() + nb.getBitMapHeight());
    }



    public void changeSelectedWitch() {
        yb.setVisibility(INVISIBLE);
        nb.setVisibility(INVISIBLE);
        activity.returnToWitchSelection();
    }

    public boolean clickedYourTurnButton() {
        return (x > btnYourTurn.getLeftPosition() &&
                x < btnYourTurn.getLeftPosition() + btnYourTurn.getBitmapWidth() &&
                y > btnYourTurn.getTopPosition() &&
                y < btnYourTurn.getTopPosition() + btnYourTurn.getBitMapHeight());
    }

}