package com.example.chatclient;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.neovisionaries.ws.client.WebSocket;

public class MainActivity extends AppCompatActivity {
    static WebSocket ws;
    Boolean isLoginSuccessful = false;
    EditText username;
    EditText password;
    Button login;
    Button exit;
    TextView tx1;
    //to keep track of total attempts to log in
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Loads Username and Password fields to be edited
        setContentView(R.layout.login);

//        Starts the websocket thread
//        try {
//            ws = new WebSocketFactory().createSocket("ws//10.0.2.2:8080/", 1000);
//        } catch (IOException e) {
//            Log.e("Dd:", "WS error");
//        }
//        ws.addListener(new AppWebSocket());
//        ws.connectAsynchronously();

        //------------------------------------------------------------------------------------------

        login = (Button) findViewById(R.id.Login);
        username = (EditText) findViewById((R.id.usernameEditText));
        password = (EditText) findViewById((R.id.passwordEditText));

        exit = (Button) findViewById(R.id.Exit);
        tx1 = (TextView) findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText()
                        .toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Redirecting ...", Toast.LENGTH_LONG).show();
                    isLoginSuccessful = true;
//                    setContentView(R.layout.chat);
//                    login(v);
                    //------------------------------------------------------------------------------
                    TextView roomNameTV = findViewById(R.id.roomNameEditText);
                    String room = roomNameTV.getText().toString();
                    TextView userNameTV = findViewById(R.id.usernameEditText);
                    String user = userNameTV.getText().toString();
                    Intent intent = new Intent(MainActivity.this, ChatRoom.class);
                    //Adds room and user data
                    intent.putExtra("room", room);
                    intent.putExtra("user", user);
                    startActivity(intent);//Launches it

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_LONG).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        login.setEnabled(false);
                    }
                }
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