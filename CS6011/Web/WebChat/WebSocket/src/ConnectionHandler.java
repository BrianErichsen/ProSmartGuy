//Class that heeps track of user's name, room and handles webSockets communication
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
      handleWebSocket();
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
  private void handleWebSocket() throws Exception {
    try (Scanner sc = new Scanner (client.getInputStream())) {

      String line = sc.nextLine();
      System.out.println("line" + line);
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
}//End of class bracket

