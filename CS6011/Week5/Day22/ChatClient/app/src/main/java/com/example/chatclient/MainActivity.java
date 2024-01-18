package com.example.chatclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static WebSocket ws;
    Button login;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Loads Username and Password fields to be edited along with room name to join in
        setContentView(R.layout.login);

        //Starts the websocket thread
        try {
            ws = new WebSocketFactory().createSocket("ws://10.0.2.2:8080", 1000);
        } catch (IOException e) {
            Log.e("Dd:", "WS error");
        }
        ws.addListener(new AppWebSocket());
        ws.connectAsynchronously();

        //defines what login means
        login = (Button) findViewById(R.id.Login);
        exit = (Button) findViewById(R.id.Exit);

        //tells what do to do when login is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
            });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //------------------------------------------------------------------------------------------

    }//End of onCreate method bracket
    public void login(View view) {
        TextView roomNameTV = findViewById(R.id.roomNameEditText);
        String room = roomNameTV.getText().toString();
        TextView userNameTV = findViewById(R.id.usernameEditText);
        //it says that is currently null
        String user = userNameTV.getText().toString();
        ws.sendText("join " + user + " " + room);

        //When login happens; loads new class
        Intent intent = new Intent(MainActivity.this, ChatRoom.class);
        //Adds room and user data
        intent.putExtra("room", room);
        intent.putExtra("user", user);
        startActivity(intent);//Launches it
    }
}//End of MainActivity bracket