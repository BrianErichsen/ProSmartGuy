package assignment09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Experiment4 {
    //Same file that was provided to us on our experiement lab
    private static final int ITER_COUNT = 100;
    //opens file writer to we can write to file
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("collisions_alternative.csv"))) {
            for (int exp = 10; exp <= 20; exp++) {
                int size = (int) Math.pow(2, exp);
                long totalTime = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    ArrayList<Segment> segments = generateRadomSegments(size);
                    BSPTree tree = new BSPTree();
                    for (Segment segment : segments) {
                        tree.insert(segment);
                    }
                    //Using the optimized collision method
                    Segment query = new Segment(0.5, 0, 0.5, 1.0);
                    //start the timer (get the start time)
                    int numCollisions = 0;
                    long start = System.nanoTime();
                    numCollisions = newCollision(query, tree);
                    //stop the timer (get the end time)
                    long stop = System.nanoTime();
                    //get the total time (stop time - start time = execution time)
                    totalTime += stop - start;
                    count = 0;
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
    private static ArrayList<Segment> generateRadomSegments(int size) {
        ArrayList<Segment> segments = new ArrayList<>();
        Random rand = new Random(42);
        double y = 0;
        for (int i = 0; i < size; i++) {
            segments.add(new Segment(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
            y += 0.1;
        }
        return segments;
    }
    static int count;
    public static int newCollision(Segment query, BSPTree tree) {

        tree.traverseFarToNear(0.5, 0.5, //they don't matter
                (segment) -> {
                    if(segment.intersects(query)){
                        count++;
                    }
                });
        return count;
    }
}//End of class bracket
