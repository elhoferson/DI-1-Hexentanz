package com.example.di_1_hexentanz.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.gameboard.Gamescreen;
import com.example.di_1_hexentanz.gameplay.GameConfig;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.logic.std.WifiP2pLogic;
import com.example.di_1_hexentanz.network.messages.listener.AbstractClientMessageReceivedListener;
import com.example.di_1_hexentanz.network.messages.std.ColorPickMessage;
import com.example.di_1_hexentanz.network.messages.std.ColorPickResultMessage;
import com.example.di_1_hexentanz.network.mordechaim_server.Client;

public class ColourChoosing extends AppCompatActivity {

    private AbstractClientMessageReceivedListener<ColorPickResultMessage> cprm = new AbstractClientMessageReceivedListener<ColorPickResultMessage>() {
        @Override
        public void handleReceivedMessage(Client client, ColorPickResultMessage msg) {
            if (msg.isSuccessful()) {
                NetworkLogic.getInstance().getClient().removeClientListener(cprm);
                if (NetworkLogic.getInstance().isHost()) {
                    GameConfig.getInstance().gameStarted();
                }
                Intent intent = new Intent(getApplicationContext(), Gamescreen.class);
                intent.putExtra("playerColor", msg.getPlayerColor());
                startActivity(intent);
            } else {
                // TODO disable colour button
                final PlayerColor pickedColor = msg.getPlayerColor();
                ColourChoosing.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast. makeText(ColourChoosing.this, "Color "+pickedColor+" can't be picked", Toast. LENGTH_LONG). show();
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colourchoosing);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        Button btnBlue = findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.BLUE);
            }
        });

        Button btnGreen = findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.GREEN);
            }
        });

        Button btnYellow = findViewById(R.id.btnYellow);
        btnYellow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.YELLOW);
            }
        });

        Button btnRed = findViewById(R.id.btnRed);
        btnRed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.RED);
            }
        });
        NetworkLogic.getInstance().getClient().addClientListener(cprm);
    }

    private void startColoredActivity(PlayerColor color) {
        NetworkLogic.getInstance().sendMessageToHost(new ColorPickMessage(color));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WifiP2pLogic.instance().disconnect();
        NetworkLogic.getInstance().close();
    }
}
