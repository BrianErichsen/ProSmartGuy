package resources;

import java.util.ArrayList;
public class RoomContainer {
    private static ArrayList<Room> rooms = new ArrayList<>();
    public synchronized static Room getRoom(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        Room newRoom = new Room(name);
        rooms.add(newRoom);
        return newRoom;
    }
}
