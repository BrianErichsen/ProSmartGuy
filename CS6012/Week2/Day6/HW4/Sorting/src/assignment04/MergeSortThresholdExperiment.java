package assignment04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MergeSortThresholdExperiment {
    //Same file that was provided to us on our experiement lab
    private static final int ITER_COUNT = 100;
    //opens file writer to we can write to file
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("mergesort_threshold_MAX.csv"))) {

            //sets which thresholds to test including a large number
            //{5, 10, 15, 20, Integer.MAX_VALUE
            int threshold = Integer.MAX_VALUE;

                for (int exp = 10; exp <= 20; exp++) {
                    int size = (int) Math.pow(2, exp);
                    long totalTime = 0;
                    //sorts in increasing time
                    for (int iter = 0; iter < ITER_COUNT; iter++) {
                        // SET UP!
                        ArrayList<Integer> orginalList = generatePermutedList(size);
                        //Copy for each test
                        ArrayList<Integer> tempList = new ArrayList<>(orginalList);
                        long start = System.nanoTime();
                        //Using natural order comparator for method
                        SortUtil.mergesortWithThreshold(tempList, threshold, Comparator.naturalOrder());
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
    private static ArrayList<Integer> generatePermutedList(int size) {
        //Creates sorted list from 1 to size
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        //shuffles the sorted list to created a permuted list
        Collections.shuffle(list);
        return list;
    }
}//End of class bracket
