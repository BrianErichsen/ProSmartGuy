import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Response {
    private Request request_;
    private OutputStream outPut;
    private InputStream input;

    public Response(Request request, OutputStream outputStream, InputStream inputStream) {
        request_ = request;
        outPut = outputStream;
        input = inputStream;
    }

    public void sendResponse() throws Exception {
        // Check if the incoming request is a WebSocket request
        if (request_.isWebSocketRequest()) {
            // If it is a WebSocket request, perform the WebSocket handshake
            handleWebSocketHandshake();

            // Enter into a continuous loop for WebSocket communication
            while (true) {
                // Handle WebSocket communication and receive a decoded message
                byte[] decodedMsg = handleWebSocketCommunication();

                // Process the decoded WebSocket message
                handleMessage(decodedMsg);
            }
        } else {
            // If the incoming request is not a WebSocket request, send an HTTP response
            sendHTTPResponse();
        }
    }


    public void sendHTTPResponse() throws IOException, InterruptedException {
        // Set the default path to the current directory
        String path = ".";

        // Get the requested filename from the HTTP request
        String filename = request_.getFilename();

        // If the requested filename is "/", set it to "/index.html"
        if ("/".equals(filename)) {
            filename = "/index.html";
        }
        // Concatenate the filename to the path
        path += filename;

        // Print the path for debugging purposes
        System.out.println("path = " + path);

        // Create a File object with the specified path
        File file = new File(path);

        // Check if the file exists
        if (file.exists()) {
            // Print the name of the existing file for debugging purposes
            System.out.println(file.getName());

            // Send the contents of the file as the HTTP response
            sendFile(file);
        } else {
            // If the file doesn't exist, send a 404 Not Found response
            send404();
        }
    }


    private void sendFile(File file) throws IOException, InterruptedException {
        // Determine the content type based on the file extension
        String contentType = getContentType(file.getName());

        // Open a FileInputStream to read the content of the file
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            // Write the HTTP response header indicating a successful response (200 OK)
            outPut.write("HTTP/1.1 200 OK\r\n".getBytes());

            // Write the Content-Type header specifying the type of content in the response
            outPut.write(("Content-Type: " + contentType + "\r\n").getBytes());

            // Write the Content-Length header indicating the size of the content
            outPut.write(("Content-Length: " + file.length() + "\r\n").getBytes());

            // Write a blank line to indicate the end of the headers and the start of the content
            outPut.write("\r\n".getBytes());

            // Transfer the content of the file directly to the output stream (efficiently)
            fileInputStream.transferTo(outPut);

            // Flush the output stream to ensure all data is sent
            outPut.flush();

            // Close the output stream after sending the file
            outPut.close();
        }
    }


    private void send404() throws IOException {
        outPut.write("HTTP/1.1 404 Not Found\r\n".getBytes());
        outPut.write("Content-Type: text/html\r\n".getBytes());
        outPut.write("\r\n".getBytes());
        outPut.write("404 Not Found - The requested resource does not exist.".getBytes());
        outPut.flush();
        outPut.close();
    }


    private String getContentType(String fileName) {
        // Check if the file name ends with ".html"
        if (fileName.endsWith(".html"))
            // If true, return the content type for HTML files
            return "text/html";

        // Check if the file name ends with ".css"
        if (fileName.endsWith(".css"))
            // If true, return the content type for CSS files
            return "text/css";

        // Check if the file name ends with ".js"
        if (fileName.endsWith(".js"))
            // If true, return the content type for JavaScript files
            return "application/javascript";

        // Check if the file name ends with ".jpg" or ".jpeg"
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
            // If true, return the content type for JPEG images
            return "image/jpeg";

        // Check if the file name ends with ".png"
        if (fileName.endsWith(".png"))
            // If true, return the content type for PNG images
            return "image/png";

        // If none of the above conditions match, return the default content type for binary streams
        return "application/octet-stream";
    }


    private void handleWebSocketHandshake() throws NoSuchAlgorithmException, IOException {
        // Extract the value of the "Sec-WebSocket-Key" header from the WebSocket request
        String key = request_.requestHeaders.get("Sec-WebSocket-Key");

        // The WebSocket handshake requires a predefined string
        String magicString = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

        // Concatenate the WebSocket key with the predefined string and hash the result using SHA-1
        String acceptVal = Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-1").digest((key + magicString).getBytes("UTF-8")));

        // Compose the WebSocket handshake response including the calculated accept value
        String handshakeResponse = "HTTP/1.1 101 Switching Protocols\r\n" +
                "Upgrade: websocket\r\n" +
                "Connection: Upgrade\r\n" +
                "Sec-WebSocket-Accept: " + acceptVal + "\r\n\r\n";

        // Write the handshake response to the output stream
        outPut.write(handshakeResponse.getBytes());

        // Flush the output stream to ensure the response is sent immediately
        outPut.flush();
    }


    private byte[] handleWebSocketCommunication() throws Exception {
        DataInputStream in = new DataInputStream(input);

        byte b0 = in.readByte();
        byte b1 = in.readByte();

        int opcode = b0 & 0x0F;
        int len = b1 & 0x7F;
        boolean isMasked = (b1 & 0x80) != 0;

        if(!isMasked){
            System.out.println("ERROR!!!");
            throw new Exception("unmasked msg from client");
        }
        System.out.println("opcode: " + opcode +", len " + len);
        byte [] mask = in.readNBytes(4);
        byte [] payload = in.readNBytes(len);

        //unmask the message
        byte [] DECODED = payload;
        for (int i = 0; i < payload.length; i++){
            DECODED[i] = (byte) (payload[i]^mask[i%4]);
        }
        return DECODED;
    }

    private void handleMessage(byte[] DECODED) throws ParseException, IOException {
        // Convert the byte array to a String message
        String msg = new String(DECODED);

        // Create a JSONParser to parse the JSON message
        JSONParser parser = new JSONParser();

        // Parse the String message into a JSONObject
        JSONObject jsonObject = (JSONObject) parser.parse(msg);

        // Extract the "type" and "room" fields from the JSON message
        String type = (String) jsonObject.get("type");
        String roomname = (String) jsonObject.get("room");

        // Get the Room object corresponding to the specified room name
        Room room = Room.getRoom(roomname);

        // Use a switch statement to handle different message types
        switch (type) {
            // If the message type is "join"
            case "join":
                // Send previous messages to the joining client
                sendPreviousMsg(Room.messages_);

                // Add the client to the room
                room.addClient(outPut);

                // Broadcast the join message to all clients in the room
                room.broadcastMessage(msg);
                break;

            // If the message type is "message"
            case "message":
                // Broadcast the message to all clients in the room
                room.broadcastMessage(msg);
                break;

            // If the message type is "leave"
            case "leave":
                // Broadcast the leave message to all clients in the room
                room.broadcastMessage(msg);

                // Remove the client from the room
                room.removeClient(outPut);

                // Close the output stream for the leaving client
                outPut.close();
                break;
        }
    }


    private void sendPreviousMsg(ArrayList<String> messages) throws IOException {
        DataOutputStream out = new DataOutputStream(outPut);
        for(String msg: messages) {
            //1st byte of header: fin/opcode => 1000 0001
            out.writeByte(0x81);
            //2nd byte of header: mask/len => 0000 len
            out.writeByte(msg.length());
            out.writeBytes(msg);
            out.flush();
        }
    }
}
