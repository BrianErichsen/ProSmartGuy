
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) {
        try {
            startServer();
        } catch (IOException e) {
            //Handles critical error: Unable to start the server
            e.printStackTrace();
            System.exit(1); // terminates the program
        }
    }
    private static void startServer() throws IOException {
        try (ServerSocket server = new ServerSocket(8080)) {
            //continously
            while (true) {
                System.out.println("Waiting for client...");
                //accepts incoming client connections
                Socket client = server.accept();
                System.out.println("Got connection");
                ConnectionHandler ch = new ConnectionHandler(client);
                Thread clienThread = new Thread(ch);
                clienThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}