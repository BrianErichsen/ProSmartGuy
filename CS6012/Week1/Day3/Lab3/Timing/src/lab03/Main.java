package lab03;

import java.util.SortedSet;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        int[] setSizes = {1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576};

        for (int size : setSizes) {
            SortedSet<Integer> sortedSet = new TreeSet<>();
            for (int i = 0; i < size; i++) {
                sortedSet.add(i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < 10000; i++) {  // Perform contains() 10,000 times
                sortedSet.contains(size - 1);
            }
            long endTime = System.nanoTime();

            double averageRuntime = (endTime - startTime) / 1e6 / 10000.0;  // Convert to milliseconds
            System.out.println("Size: " + size + ", Average Runtime (ms): " + averageRuntime);
        }
    }
}