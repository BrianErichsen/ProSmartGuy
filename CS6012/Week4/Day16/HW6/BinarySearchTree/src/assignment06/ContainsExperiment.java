package assignment06;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class ContainsExperiment {
    //Same file that was provided to us on our experiement lab
    private static final int ITER_COUNT = 100;
    //opens file writer to we can write to file
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("bst_unbalanced_contains.csv")))
        {
            for (int exp = 10; exp <= 20; exp++) {
                int size = (int) Math.pow(2, exp);

//                TreeSet<Integer> balancedTree = new TreeSet<>();
                BinarySearchTree<Integer> random = new BinarySearchTree<>();
                long totalTime = 0;
                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    List<Integer> randomOrder = generateRandomOrder(size);

//                    long startRandom = System.nanoTime();
                    for (int i = 0; i < size; i++) {
                        random.add(i);
                    }

                    long startRandom = System.nanoTime();
                    for (int i = 1; i < size; i++) {
                        random.contains(i);
                    }
                    long stopRandom = System.nanoTime();
                    totalTime += stopRandom - startRandom;
                    random.clear();
                }

                double averageTimeRandom = totalTime / (double) ITER_COUNT;

                System.out.println(size + "\t" + averageTimeRandom);
                fw.write(size + "\t" + averageTimeRandom + "\n"); // write to file.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//End of Main bracket
    private static List<Integer> generateRandomOrder(int n) {
        List<Integer> randomOrder = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            randomOrder.add(i);
        }
        Collections.shuffle(randomOrder);
        return randomOrder;
    }
}//End of class bracket
