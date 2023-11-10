import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {
    private Socket client_;

    public ConnectionHandler(Socket client) {
        client_ = client;
    }

    @Override
    public void run() {
        try {
            // Create a Request object to parse the incoming request from the client
            Request request = new Request(client_);

            // Create a Response object, providing the request and client's input/output streams
            Response response = new Response(request, client_.getOutputStream(), client_.getInputStream());

            // Send the appropriate response based on the type of request (HTTP or WebSocket)
            response.sendResponse();

            // client_.close();
        } catch (Exception e) {
            // Print the stack trace ifs an exception occurs during request handling
            e.printStackTrace();

            // Print an error message indicating an issue with handling the client
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

}
