package msd.benjones;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Network.makeSimpleNetwork(); //use this for testing/debugging
        //Network.makeProbablisticNetwork(5); //use this for the plotting part
        Network.dump();

        Network.startup();
        Network.runBellmanFord();

        System.out.println("done building tables!");
        for (Router r : Network.getRouters()) {
            r.dumpDistanceTable();
        }
        System.out.println("total messages: " + Network.getMessageCount());

        List<Integer> networkSizes = new ArrayList<>();
        List<Integer> messageCounts = new ArrayList<>();

        // Try different network sizes and record the number of messages required to converge
        for (int size = 2; size <= 50; size++) {
            int totalMessages = 0;
            int iterations = 5; // Number of iterations per network size

            for (int i = 0; i < iterations; i++) {
                Network.makeProbablisticNetwork(size);
                Network.startup();
                Network.runBellmanFord();
                totalMessages += Network.getMessageCount();
            }

            int averageMessages = totalMessages / iterations;
            networkSizes.add(size);
            messageCounts.add(averageMessages);

            System.out.println("Network size: " + size + ", Average messages: " + averageMessages);
        }

        // Write data to an output file
        writeDataToFile("output.txt", networkSizes, messageCounts);
    }//end of class bracket

    private static void writeDataToFile(String filename, List<Integer> xData, List<Integer> yData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < xData.size(); i++) {
                writer.write(xData.get(i) + "," + yData.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}//end of helper method bracket