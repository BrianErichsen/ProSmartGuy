import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Set the server port to 8080
        final int PORT = 8080;

        try (ServerSocket ss = new ServerSocket(PORT)) {
            // Print a message indicating that the server is running on the specified port
            System.out.println("Server is running on port: " + PORT);

            // Enter into a continuous loop to accept incoming client connections
            while (true) {
                // Accept a client connection and obtain the client socket
                Socket client = ss.accept();

                // Create a new ConnectionHandler for the accepted client socket
                ConnectionHandler ch = new ConnectionHandler(client);

                // Create a new thread for the ConnectionHandler and start the thread
                Thread t = new Thread(ch);
                t.start();
            }
        } catch (IOException e) {
            // Print the stack trace if an exception occurs during server startup
            e.printStackTrace();

            // Print an error message indicating an issue with starting the server
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }
}