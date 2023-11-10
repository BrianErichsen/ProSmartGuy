import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Room {
    //Store rooms in a hashmap
    public static Map<String, Room> rooms_ = new HashMap<>();
    ArrayList<OutputStream> clients_;
    private final String rommName_;
    public static ArrayList<String> messages_ = new ArrayList<>();
    private Room(String roomName){
        rommName_ = roomName;
        clients_ = new ArrayList<>();
    }



    public synchronized static Room getRoom(String name) {
        //Create a new room
        Room room = rooms_.get(name);
        if (room == null){
            room = new Room(name);
            rooms_.put(name, room);
        }
        return room;
    }



    public synchronized void addClient(OutputStream client) {
        clients_.add(client);
    }



    public synchronized void broadcastMessage(String message) throws IOException {
        for (OutputStream client : clients_) {
            DataOutputStream out = new DataOutputStream(client);
            //1st byte of header: fin/opcode => 1000 0001
            out.writeByte(0x81);
            //2nd byte of header: mask/len => 0000 len
            out.writeByte(message.length());
            out.writeBytes(message);
            out.flush();
        }
        messages_.add(message);
    }

    public synchronized void removeClient(OutputStream client) {
        clients_.remove(client);
    }

}
