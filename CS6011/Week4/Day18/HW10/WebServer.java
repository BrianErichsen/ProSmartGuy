import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
                // String requestLine = getRequestLine(client);
                // if (requestLine.startsWith("GET /websocket")) {
                
                // ConnectionHandler ch = new ConnectionHandler(client);
                // Thread clientThread = new Thread(ch);
                // clientThread.start();
                // } else {
                //     Thread clientThread2 = new Thread(() -> handleClient(client));
                //     clientThread2.start();
                // }
                ConnectionHandler ch = new ConnectionHandler(client);
                Thread clienThread = new Thread(ch);
                clienThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getRequestLine(Socket client) {
        try (Scanner scanner = new Scanner(client.getInputStream())) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}