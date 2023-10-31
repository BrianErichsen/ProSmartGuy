package resources;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Room {
    private String name;
    //Not static?
    private List<Socket> clients;
    private List<String> messages;

    public Room(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public synchronized void addClient(Socket client) {
        clients.add(client);
    }

    public synchronized void removeClient(Socket client) {
        clients.remove(client);
    }

    public synchronized void sendMessageToAllClients(String message) {
        for (Socket client : clients) {
            try {
                PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);

                // Send the message to the client
                clientOut.println("message " + message);

            } catch (IOException e) {
                // Handle any potential errors when sending the message
                e.printStackTrace();
            }
        }

        // Optionally, add the message to the room's message history.
        messages.add(message);
    }

    // public List<String> synchronized List<String> getMessages() {
    //     return messages;
    // }

    public String getName() {
        return name;
    }

    public  boolean isUserPresent() {
        return true;
    }

}