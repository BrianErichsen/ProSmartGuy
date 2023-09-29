import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WebServer {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            //continously
            while (true) {
                //accepts incoming client connections
                Socket client = server.accept();
                /*Create separate thread to handle client request
                Lambda creates new object for Thread class
                Thread represents different threads of execution
                Here the lambda takes no arguments and executes in newly created
                thread*/
                Thread clientThread = new Thread(() -> handleClient(client));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket client) {
        try (
            Scanner scanner = new Scanner(client.getInputStream());
            PrintWriter outStream = new PrintWriter(client.getOutputStream(), true)
        ) {
            //reads the request line to extract HTTP and URI
            if (scanner.hasNextLine()) {
                String requestLine = scanner.nextLine();
                String[] requestParts = requestLine.split(" ");
                String httpMethod = requestParts[0];
                String requestURI = requestParts[1];

                if ("GET".equals(httpMethod)) {
                    //cheks if the client requested the root path "/"
                    if ("/".equals(requestURI)) {
                        //Sets default file to index.html
                        requestURI = "/index.html";
                    }
                    //serve the requested file or default file
                    serveFile(outStream, requestURI);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Closes the client socket
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void serveFile(PrintWriter outStream, String requestURI) throws IOException {
        String rootDirectory = "/Users/brianerichsenfagundes/myGithubRepo/CS6011/Week1/Day4/resources/";
        String requestedFilePath = rootDirectory + requestURI;
        File file = new File(requestedFilePath);

        if (file.exists() && file.isFile()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                String line;

                // Determine the content type based on the file extension
                String contentType = determineContentType(requestedFilePath);

                // Send the HTTP response headers
                outStream.print("HTTP/1.1 200 OK\r\n");
                outStream.print("Content-type: " + contentType + "\r\n");
                outStream.print("\r\n"); // End of headers

                // Send the file content line by line
                while ((line = fileReader.readLine()) != null) {
                    outStream.println(line);
                }//Flush the output stream
                outStream.flush();
            }
        } else {
            // If the requested file does not exist, return a 404 Not Found response
            outStream.print("HTTP/1.1 404 Not Found\r\n");
            outStream.print("\r\n"); // End of headers
            outStream.print("File not found");
            outStream.flush();
        }
    }
    //This determines the content type of the HTTP response
    private static String determineContentType(String filePath) {
        if (filePath.endsWith(".html")) {
            //Determine the content based on file extension
            return "text/html";
        } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filePath.endsWith(".png")) {
            return "image/png";
        } else {
            return "application/octet-stream"; // Default to binary data
        }
    }

// //In case client did request any file
// private static void serveDefaultMessage(PrintWriter outStream) {
//     //Message that I want to appear on localhost:8080
//     String defaultMessage = "<html><body><h1>Welcome to my server!!69</h1></body></html>";
//     //Send the HTTP response headers
//     outStream.print("Content-type: text/html\r\n");
//     outStream.print("\r\n"); // End of headers
//     //Sends message
//     outStream.print(defaultMessage);
//     outStream.flush();
// }
}
