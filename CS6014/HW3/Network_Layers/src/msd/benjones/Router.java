package msd.benjones;

import java.util.HashMap;
import java.util.Set;

public class Router {

    private HashMap<Router, Integer> distances;
    private String name;
    public Router(String name) {
        this.distances = new HashMap<>();
        this.name = name;
    }

    public void onInit() throws InterruptedException {

        distances.clear();
        for (Router router : Network.getRouters()) {
            if (!router.equals(this)) {
                distances.put(router, Integer.MAX_VALUE);
            }
        }
        //since distance to itself is 0
        distances.put(this, 0);

        HashMap<Router, Integer> initialDistances = new HashMap<>(distances);
        for (Neighbor neighbor : Network.getNeighbors(this)) {
            Message message = new Message(this, neighbor.router, initialDistances);
            Network.sendDistanceMessage(message);
        }
        //As soon as the network is online,
        //fill in your initial distance table and broadcast it to your neighbors
    }

    public void onDistanceMessage(Message message) throws InterruptedException {
        boolean updated = false;

        for (Router router : message.distances.keySet()) {
            int distanceBySender = message.distances.get(router) + message.distances.get(message.sender);
            if (distanceBySender < distances.getOrDefault(router, Integer.MAX_VALUE)) {
                distances.put(router, distanceBySender);
                updated = true;
            }
        }
        if (updated) {
            for (Neighbor neighbor : Network.getNeighbors(this)) {
                Message updatedMsg = new Message(this, neighbor.router, distances);
                Network.sendDistanceMessage(updatedMsg);
            }
        }
    }


    public void dumpDistanceTable() {
        System.out.println("router: " + this);
        for(Router r : distances.keySet()){
            System.out.println("\t" + r + "\t" + distances.get(r));
        }
    }

    @Override
    public String toString(){
        return "Router: " + name;
    }
}
