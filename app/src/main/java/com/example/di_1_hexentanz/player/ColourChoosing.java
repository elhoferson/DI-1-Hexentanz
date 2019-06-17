package com.example.di_1_hexentanz.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private Button btnBlue;
    private Button btnGreen;
    private Button btnYellow;
    private Button btnRed;

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

                final PlayerColor pickedColor = msg.getPlayerColor();
                ColourChoosing.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Button btnDisable = null;
                        switch (pickedColor) {
                            case BLUE:
                                btnDisable = ColourChoosing.this.btnBlue;
                                break;
                            case GREEN:
                                btnDisable = ColourChoosing.this.btnGreen;
                                break;
                            case YELLOW:
                                btnDisable = ColourChoosing.this.btnYellow;
                                break;
                            case RED:
                                btnDisable = ColourChoosing.this.btnRed;
                                break;
                            default:
                                Log.e("MSG: ", "undefined colour received");
                                break;
                        }
                        btnDisable.setEnabled(false);
                        btnDisable.setVisibility(View.INVISIBLE);
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

        btnBlue = findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.BLUE);
            }
        });

        btnGreen = findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.GREEN);
            }
        });

        btnYellow = findViewById(R.id.btnYellow);
        btnYellow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.YELLOW);
            }
        });

        btnRed = findViewById(R.id.btnRed);
        btnRed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startColoredActivity(PlayerColor.RED);
            }
        });
        WifiP2pLogic.instance().getDiscoverPeerThread().interrupt();
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
