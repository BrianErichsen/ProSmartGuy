package assignment07;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class HashFunctionExperiment {
    public static void main(String[] args) {
        // Change the number of random strings to generate
        int numStrings = 1000;


        // Generate an array of random strings
        String[] strings = generateRandomStrings(numStrings);


        // Array of hash table sizes to be used in the experiment
        int[] tableSizes = {1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576};


        // Run the experiment for each hash function
        runExperiment(new GoodHashFunctor(), strings, tableSizes);
        runExperiment(new MediocreHashFunctor(), strings, tableSizes);
        runExperiment(new BadHashFunctor(), strings, tableSizes);
    }


    /**
     * Runs the experiment for a given hash function, array of strings, and hash table sizes.
     *
     * @param hashFunctor The hash function to be evaluated.
     * @param strings An array of strings to be inserted into the hash table.
     * @param tableSizes An array of hash table sizes to be used in the experiment.
     */
    private static void runExperiment(HashFunctor hashFunctor, String[] strings, int[] tableSizes) {
        // Number of trials to be averaged
        int numTrials = 10;


        // Arrays to store cumulative collision times and running times for each table size
        long[] collisionTimes = new long[tableSizes.length];
        long[] runningTimes = new long[tableSizes.length];


        // Perform the experiment for multiple trials
        for (int trial = 0; trial < numTrials; trial++) {
            // Measure collisions and running times for each table size
            for (int i = 0; i < tableSizes.length; i++) {
                collisionTimes[i] += measureCollisions(hashFunctor, strings, tableSizes[i]);
                runningTimes[i] += measureRunningTime(hashFunctor, strings, tableSizes[i]);
            }
        }

        //System.out.println(size + "\t" + averageTime); // print to console
        //                fw.write(size + "\t" + averageTime + "\n");
        // Display the average collisions for the hash function
        System.out.println("Average Collisions for " + hashFunctor.getClass().getSimpleName());
        for (int i = 0; i < tableSizes.length; i++) {
            long avgCollisions = collisionTimes[i] / numTrials;
            System.out.println(tableSizes[i] + "\t" + avgCollisions);
        }


        System.out.println();


        // Display the average running times for the hash function
        System.out.println("Average Running Time for " + hashFunctor.getClass().getSimpleName());
        for (int i = 0; i < tableSizes.length; i++) {
            long avgRunningTime = runningTimes[i] / numTrials;
            System.out.println(tableSizes[i] + "\t" + avgRunningTime);
        }


        System.out.println();
    }




    /**
     * Measures the number of collisions in a hash table using the specified hash function.
     *
     * @param hashFunctor The hash function to be evaluated.
     * @param strings An array of strings to be inserted into the hash table.
     * @param tableSize The size of the hash table.
     * @return The number of collisions in the hash table.
     */
    private static int measureCollisions(HashFunctor hashFunctor, String[] strings, int tableSize) {
        // Map to store the count of strings at each hash code
        Map<Integer, Integer> hashTable = new HashMap<>();


        // Insert each string into the hash table and count collisions
        for (String str : strings) {
            int hash = Math.abs(hashFunctor.hash(str)) % tableSize;
            hashTable.put(hash, hashTable.getOrDefault(hash, 0) + 1);
        }


        // Count total collisions in the hash table
        int collisions = 0;
        for (int count : hashTable.values()) {
            if (count > 1) {
                collisions += count - 1;
            }
        }


        return collisions;
    }


    /**
     * Measures the running time to obtain hash indices for a set of strings using the specified hash function.
     *
     * @param hashFunctor The hash function to be evaluated.
     * @param strings An array of strings for which hash indices are obtained.
     * @param tableSize The size of the hash table.
     * @return The running time in nanoseconds.
     */
    private static long measureRunningTime(HashFunctor hashFunctor, String[] strings, int tableSize) {
        long startTime = System.nanoTime();


        // Measure the time to get the index using the hash function
        for (String str : strings) {
            int hash = Math.abs(hashFunctor.hash(str)) % tableSize;
        }


        long endTime = System.nanoTime();
        return endTime - startTime;
    }




    /**
     * Generates an array of random strings with specified length.
     *
     * @param numStrings The number of random strings to generate.
     * @return An array of random strings.
     */
    private static String[] generateRandomStrings(int numStrings) {
        Random random = new Random();
        String[] strings = new String[numStrings];


        for (int i = 0; i < numStrings; i++) {
            // Varying length from 1 to 10
            int length = random.nextInt(10) + 1;
            strings[i] = generateRandomString(length);
        }


        return strings;
    }


    /**
     * Generates a random string with the specified length.
     *
     * @param length The length of the random string to generate.
     * @return A random string of the specified length.
     */
    private static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();


        for (int i = 0; i < length; i++) {
            // Generating a random lowercase character
            char randomChar = (char) (random.nextInt(26) + 'a');
            builder.append(randomChar);
        }


        return builder.toString();
    }
}

