package com.example.chatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
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
//        setContentView(R.layout.activity_main);
        //Loads Username and Password fields to be edited
        setContentView(R.layout.login);

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
                    setContentView(R.layout.chat);
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

        //WebSocket ws = new WebSocketAdpater();
        //ws.connect(); initiates the handshake


    }//End of onCreate method bracket

}//End of MainActivity bracket