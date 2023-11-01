package com.example.chatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Boolean isLoginSuccessful = true;
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


    }
    // --- prob make a class called user;

    public void onLoginClick(View view) {
        //Gets the username and password
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        EditText password = (EditText) findViewById(R.id.passwordEditText);

        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();

        if (isLoginSuccessful) {
            setContentView(R.layout.chat);
        }
    }
    public void login (View view) {
        //Gets the username and password
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        EditText password = (EditText) findViewById(R.id.passwordEditText);

        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        if(usernameStr.equals("admin") && passwordStr.equals("admin")) {
            //correct password
            isLoginSuccessful = true;
        } else {

        }
}
}//End of MainActivity bracket