package assignment07;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

public class Experiment {
    //Same file that was provided to us on our experiement lab
    private static final int ITER_COUNT = 100;
    //opens file writer to we can write to file
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("runtime_good.csv"))) {

            String element;

            for (int exp = 10; exp <= 20; exp++) {
                int size = (int) Math.pow(2, exp);
                long totalTime = 0;
                //sorts in increasing time
                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    HashFunctor hash = new GoodHashFunctor();
                    ChainingHashTable hashTable = new ChainingHashTable(size, hash);
                    // SET UP!
                    //element to push
                    element = generateRandom();
                    //
                    long start = System.nanoTime();
                    //
                    for (int i = 0; i < size; i++) {
                        hashTable.add(element);
                    }
                    //start here
//                    int collision = Collisions(hashTable, element);

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
    private static String generateRandom() {
        //could implement this over the loops size if wanted
        Random randomL = new Random();
        //from 1 to 10
        int randomLength = randomL.nextInt(10) + 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < randomLength; i++) {
            char randomChar = (char) (randomL.nextInt(26) + 'a');
            builder.append(randomChar);
        }
        return builder.toString();
    }
//    private int Collisions(ChainingHashTable hashTable, )
}//End of class bracket
