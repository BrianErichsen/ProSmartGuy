package com.example.chatclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoom extends AppCompatActivity {
    Button logout;
    private String userName;
    public String roomName;
    protected static ArrayList<String> messages = new ArrayList<>();
    protected static ListView messageListView;
    protected static ArrayAdapter<String> messageAdapter;
    protected static ArrayList<String> clients = new ArrayList<>();
    protected static ListView userListView;
    protected static ArrayAdapter<String> userAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        Intent intent = getIntent();

        userName = intent.getStringExtra("user");
        roomName = intent.getStringExtra("room");

        TextView roomNameTV = findViewById(R.id.roomName);
        roomNameTV.setText(roomName);
        logout = (Button) findViewById(R.id.Logout);

        TextView sendMessageView = findViewById(R.id.messageInputEditText);
        sendMessageView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    handleMessage(v);
                    return true;
                }
                return false;
            }
        });
        //Show the message List View
        messageListView = findViewById(R.id.messageListView);
        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        messageListView.setAdapter(messageAdapter);

        //Sets the user List view
        userListView = findViewById(R.id.userListView);
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clients);
        userListView.setAdapter(userAdapter);

        logout = (Button) findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout(v);
            }
        });
    }//end of onCreate method bracket
    public void handleLogout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void handleMessage(View view) {
        TextView messageView = findViewById(R.id.messageInputEditText);
        String message = messageView.getText().toString();
        MainActivity.ws.sendText(userName + " " + message);
        messageView.setText("");
    }

}//end of class bracket
