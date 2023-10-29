import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
                //accepts incoming client connections
                Socket client = server.accept();
                String requestLine = getRequestLine(client);
                if (requestLine.startsWith("GET /websocket")) {
                
                ConnectionHandler ch = new ConnectionHandler(client);
                Thread clientThread = new Thread(ch);
                clientThread.start();
                } else {
                    Thread clientThread2 = new Thread(() -> handleClient(client));
                    clientThread2.start();
                }
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
                    requestURI = sanitizeURI(requestURI);
                    //checks if the client requested websocket

                // if (requestLine.contains ("Upgrade: websocket")) {
                //     handleWebSocketCommunication(client);
                // }

                     //cheks if the client requested the root path "/"
                    if ("/".equals(requestURI)) {
                    //cheks if the client requested the root path "/"if ("/".equals(requestURI)) {
                        //Sets default file to index.html
                        requestURI = "/index.html";
                    }
                    //serve the requested file or default file
                    serveFile(client.getOutputStream(), requestURI);
                }
            }
        } catch (IOException e) {
            //Handles non critical error: Log error and continue handling other clients
            e.printStackTrace();
        } finally {
            try {
                //Closes the client socket
                client.close();
            } catch (IOException e) {
                //Handle non-critical error: Log the error and continue
                e.printStackTrace();
            }
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

    // private static void handleWebSocketHandShake(PrintWriter outStream) {
    //     outStream.write("HTTP/1.1 101 Switching Protocols\r\n");
    //     outStream.write("Upgrade: websocket\r\n");
    //     outStream.write("Connection: Upgrade\r\n");
    //     //
    //     outStream.write("\r\n");
    //     outStream.flush();
    // }
    // private static void handleWebSocketCommunication(Socket client) throws IOException {
    //     try (
    //         InputStream inputStream = client.getInputStream();
    //         OutputStream outputStream = client.getOutputStream();
    //     ) {
    //         byte[] buffer = new byte[1024];
    //         int bytesRead;
    //         ByteBuffer messageBuffer = ByteBuffer.allocate(1024);

    //         while ((bytesRead = inputStream.read(buffer)) != -1) {
    //             for (int i = 0; i < bytesRead; i++) {
    //                 byte b = buffer[1];
    //                 messageBuffer.put(b);

    //                 if (b == (byte) 0x88) {

    //                 } else if (b == (byte) 0x81) {
    //                     System.out.println("sure thing");
    //                     // parseWebSocket(messageBuffer.array());
    //                     messageBuffer.clear();
    //                 }
    //             }
    //         }
    //     }
    // }
    // private static void parseWebSocket(byte[] frameBytes) throws IOException {
    //     WebSocket.parseWebSocket(frameBytes);
    // }

    private static String sanitizeURI(String requestURI) {
        //remove parent directory (e.g. ..) from URI - prevents path manipulation from client
        //removes backslashes
        String sanitizeURI = requestURI.replaceAll("\\.\\.", "");
        //Normalize URI by replacing backslahses with forward slashes
        sanitizeURI = requestURI.replace("\\", "/");

        return sanitizeURI;
    }


    private static void serveFile(OutputStream outStream, String requestURI) throws IOException {
        String rootDirectory = "/Users/brianerichsenfagundes/myGithubRepo/CS6011/Week4/Day18/HW10/resources/";
        String requestedFilePath = rootDirectory + requestURI;
        File file = new File(requestedFilePath);

        if (file.exists() && file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream socketOut = outStream) {

                // Determine the content type based on the file extension
                String contentType = determineContentType(requestedFilePath);

                // Send the HTTP response headers
                outStream.write(("HTTP/1.1 200 OK\r\n").getBytes());
                outStream.write(("Content-type: " + contentType + "\r\n").getBytes());
                outStream.write("\r\n".getBytes()); // End of headers

                //Sends the file content as bytes
                byte[] buffer = new byte[1024];
                int bytesRead;

                /*Alternative one for testing */
                while((bytesRead = fileInputStream.read(buffer)) != -1) {
                    for (int i = 0; i < bytesRead; i++) {
                        socketOut.write(buffer[i]);
                        socketOut.flush();
                        Thread.sleep(10);
                    }
                }
                //Sets bytesRead to be = fileInputStream.read taking the bytes array
                //reads until there is no more data to read
            //     while((bytesRead = fileInputStream.read(buffer)) != -1) {
            //         outStream.write(buffer, 0, bytesRead);
            //     }
            //    //Flush the output stream
            //     outStream.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            // If the requested file does not exist, return a 404 Not Found response
            outStream.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
            outStream.write(("\r\n").getBytes()); // End of headers
            outStream.write(("File not found").getBytes());
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
}