//Class that heeps track of user's name, room and handles webSockets communication
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;



public class ConnectionHandler implements Runnable {
private final Socket client;

Boolean masked;

public ConnectionHandler(Socket client) {
    this.client = client;
}

@Override
public void run() {
    try {
        System.out.println("Hi from thread");
        // String requestLine = getRequestLine(client);
        // if (requestLine.startsWith("GET")) {
        //     handleClientRequest();
        // }
        handleClientRequest();
        // else if (requestLine.startsWith("upgrade")) {
            handleWebSocket();
        // }
    } catch (Exception e) {
        e.printStackTrace();
        } finally {
            try {
            client.close();
        } catch (IOException e) {
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

  private void handleWebSocket() throws Exception {
    try (Scanner sc = new Scanner (client.getInputStream())) {

      String line = sc.nextLine();
      System.out.println("line " + line);
      HashMap<String, String> headers = new HashMap<>();
      line = sc.nextLine();

      while (!line.isEmpty()) {
        // System.out.println("line: " + line);
        String[] pieces = line.split(" ");
        String key = pieces[0];
        String value = pieces[1];
        headers.put(key, value);
        line = sc.nextLine();
      }
      boolean isWsRequest = headers.containsKey("Sec-WebSocket-Key");
        //then do the hand-shake
      if (isWsRequest) {
        String key = headers.get("Sec-WebSocket-Key");
        key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

        String secret = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest(key.getBytes("UTF-8")));
        // Socket HandShake
        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.print("HTTP/1.1 101 Switching Protocols\r\n");
        out.print("Upgrade: websocket\r\n");
        out.print("Connection: Upgrade\r\n");
        out.print("Sec-WebSocket-Accept" + secret + "\r\n");
        out.print("\r\n");//blank line means end of headers...
        out.flush();
        
        DataInputStream in = new DataInputStream(client.getInputStream());
        byte b0 = in.readByte();
        byte b1 = in.readByte();

        int opcode = b0 & 0x0F;
        int len = b1 & 0x7F;// 7 == 0111 F == 1111 7F == 0111 1111
        boolean isMasked = (b1 & 0x80) != 0; // 1000 0000; if masked read 4 more bytes

        if (!isMasked) {
          System.out.println("ERROR!!!");
          throw new Exception("unmasked msg from client");
        }
        System.out.println("opcode: " + opcode + ", len: " + len);
        byte[] mask = in.readNBytes(4);
        byte[] payload = in.readNBytes(len);

        byte[] DECODED = payload;
        for (int i = 0; i < payload.length; i++) {
          //check byte
          payload[i] = (byte) ( payload[i] ^ mask[i % 4]);

      }
      String message = new String(payload);
      System.out.println("Just got this message: " + message);

      //Sends back the message
      DataOutputStream dataout = new DataOutputStream(client.getOutputStream());
      dataout.writeByte(0X81);
      dataout.writeByte(len);
      dataout.writeBytes(message);

      while(true) {}
    }

  }
}
private void handleClientRequest() throws IOException {
    //Creates a scanner to read the client's request from input stream
    //create a outputstream to send back the response to client
    try (
        Scanner scanner = new Scanner(client.getInputStream());
        OutputStream outStream = client.getOutputStream()
    ) {
        if (scanner.hasNextLine()) {
            String requestLine = scanner.nextLine();
            String[] requestParts = requestLine.split(" ");
            String httpMethod = requestParts[0];
            String requestURI = requestParts[1];

            if ("GET".equals(httpMethod)) {
                requestURI = sanitizeURI(requestURI);
                //cheks if the client requested the root path "/"
                if ("/".equals(requestURI)) {
                    //Sets default file to index.html
                    requestURI = "/index.html";
                }
                //serve the requested file or default file
                serveFile(outStream, requestURI);
            }
        }
        }
    }
    
        private static String sanitizeURI(String requestURI) {
            //remove parent directory (e.g. ..) from URI - prevents path manipulation from client
            //removes backslashes
            String sanitizeURI = requestURI.replaceAll("\\.\\.", "");
            //Normalize URI by replacing backslahses with forward slashes
            sanitizeURI = requestURI.replace("\\", "/");
    
            return sanitizeURI;
        }
    
        private static void serveFile(OutputStream outStream, String requestURI) throws IOException {
            String rootDirectory = "/Users/brianerichsenfagundes/myGithubRepo/CS6011/Week4/Day18/HW10/";
            String requestedFilePath = rootDirectory + requestURI;
            File file = new File(requestedFilePath);
    
            if (file.exists() && file.isFile()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
    
                    // Determine the content type based on the file extension
                    String contentType = determineContentType(requestedFilePath);
    
                    // Send the HTTP response headers
                    outStream.write(("HTTP/1.1 200 OK\r\n").getBytes());
                    outStream.write(("Content-type: " + contentType + "\r\n").getBytes());
                    outStream.write("\r\n".getBytes()); // End of headers
    
                    //Sends the file content as bytes
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    //Sets bytesRead to be = fileInputStream.read taking the bytes array
                    //reads until there is no more data to read
                    while((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                   //Flush the output stream
                    outStream.flush();
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
}//End of class bracket
    

