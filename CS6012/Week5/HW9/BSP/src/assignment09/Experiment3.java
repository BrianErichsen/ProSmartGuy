package assignment09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Experiment3 {
    //Same file that was provided to us on our experiement lab
    private static final int ITER_COUNT = 100;
    //opens file writer to we can write to file
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("runtime_bulk.csv"))) {

            int element;

            for (int exp = 10; exp <= 20; exp++) {
                int size = (int) Math.pow(2, exp);
                long totalTime = 0;
                //sorts in increasing time
                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    ArrayList<Segment> segments = generateVerticalSegments(size);
                    long start = System.nanoTime();
                    BSPTree tree = new BSPTree(segments);
                    long stop = System.nanoTime();
                    totalTime += stop - start;
                }

                double averageTime = totalTime / (double) ITER_COUNT;
                System.out.println(size + "\t" + averageTime); // print to console
                fw.write(size + "\t" + averageTime + "\n"); // write to file.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//End of Main bracket

    private static ArrayList<Segment> generateVerticalSegments(int size) {
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //making sure that  it creates vertical segments that overlap leading
            //to a worst case scenario for testing purposes
            Segment segment = new Segment(i, 0, i, i +1);
            segments.add(segment);
        }
        return segments;
    }

}//End of class bracket
