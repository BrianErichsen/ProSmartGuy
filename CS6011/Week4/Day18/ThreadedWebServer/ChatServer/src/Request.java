import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Request {
    private Socket client_;
    Map<String, String> requestHeaders;
    private String filename;
    Request(Socket client) throws IOException {
        // Set the client socket for the request
        client_ = client;

        // Obtain input and output streams from the client socket
        InputStream inputStream = client_.getInputStream();
        OutputStream outputStream = client_.getOutputStream();

        // Create a Scanner to read the input stream from the client
        Scanner scanner = new Scanner(inputStream);

        // Read the request line from the client's input
        String requestLine = scanner.nextLine();
        System.out.println("RequestLine: " + requestLine);

        // Split the request line into parts using space as a delimiter
        String[] requestParts = requestLine.split(" ");

        // Extract the filename from the request line
        filename = requestParts[1];

        // Read and parse the headers of the HTTP request
        requestHeaders = new HashMap<>();
        String line = scanner.nextLine();
        while (!line.equals("")) {
            // Split each header line into key-value pairs and add them to the requestHeaders map
            String[] header = line.split(": ");
            requestHeaders.put(header[0], header[1]);
            // Read the next line
            line = scanner.nextLine();
        }
    }


    boolean isWebSocketRequest() {
        return requestHeaders.containsKey("Sec-WebSocket-Key");
    }

    public String getFilename() {
        return filename;
    }

}
