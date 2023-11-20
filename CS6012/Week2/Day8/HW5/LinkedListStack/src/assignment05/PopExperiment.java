package assignment05;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PopExperiment {
    //Same file that was provided to us on our experiement lab
    private static final int ITER_COUNT = 100;
    //opens file writer to we can write to file
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("pop_linked_list.csv"))) {

            int element;

            for (int exp = 10; exp <= 20; exp++) {
                int size = (int) Math.pow(2, exp);
                long totalTime = 0;
                //sorts in increasing time
                for (int iter = 0; iter < ITER_COUNT; iter++) {
//                    ArrayStack<Integer> arrayStack = new ArrayStack<>();
                    LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();
                    // SET UP!
                    //element to push
                    element = 5;
                    //
                    for (int i = 0; i < size; i++) {
//                        arrayStack.push(element);
                        linkedListStack.push(element);
                    }
                    long start = System.nanoTime();
                    for (int i = 0; i < size; i++) {
                        linkedListStack.pop();
                    }
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
}//End of class bracket
