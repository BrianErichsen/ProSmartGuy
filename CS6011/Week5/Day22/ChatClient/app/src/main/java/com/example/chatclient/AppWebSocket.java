package com.example.chatclient;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class AppWebSocket extends WebSocketAdapter {
    @Override
    public void onConnected(WebSocket webSocket, Map<String, List<String>> headers) {
        Log.d("CC:AppWebSocket", "WebSocket connected");
    }
    @Override
    public void onConnectError(WebSocket webSocket, WebSocketException e) {
        Log.d("CC:AppWebSocket", "WebSocket connect failed!");
    }
    public void onError(WebSocket webSocket, WebSocketException cause) {
        Log.d("CC:AppWebSocket", "An error happened");
    }
    @Override
    public void onTextMessage(WebSocket webSocket, String message) throws JSONException {
        Log.d("CC:AppWebSocket", message);
        JSONObject jsonObject = new JSONObject(message);
        String type = jsonObject.getString("type");
        String username = jsonObject.getString("user");
        String roomname = jsonObject.getString("room");

        if (type.equals("message")) {
            ChatRoom.messages.add(username + ": " + jsonObject.getString("message"));
        } else if (type.equals("join")) {
            ChatRoom.messages.add(username + ": " + " joins " + roomname);
            ChatRoom.clients.add(username);
        } else if (type.equals("leave")) {
            ChatRoom.messages.add(username + " leaves " + roomname);
            ChatRoom.clients.remove(username);
        }
        ChatRoom.messageListView.post(new Runnable() {
            @Override
            public void run() {
                //notifyDataSetChanged method of ArrayAdapter - informs ListView and updates it
                ChatRoom.messageAdapter.notifyDataSetChanged();
                //makes the vision of items in list; it makes position visible for the user
                //.getCount takes the total number of items in the adapter's data set
                //Gets the total item count, which has new msg
                ChatRoom.messageListView.smoothScrollToPosition(ChatRoom.messageAdapter.getCount());
            }
        });
        ChatRoom.userListView.post(new Runnable() {
            @Override
            public void run() {
                ChatRoom.userAdapter.notifyDataSetChanged();
                ChatRoom.messageListView.smoothScrollToPosition(ChatRoom.userAdapter.getCount());

            }
        });
    }
}//End of class bracket
