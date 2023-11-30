package assignment07;


/**
 * The GoodHashFunctor class implements a hash function for strings inspired
 * by Java's default hashCode method. This hash function aims to provide
 * a well-distributed set of hash codes to minimize collisions in hash tables.
 */
class GoodHashFunctor implements HashFunctor {


    /**
     * Generates a hash code for the given string using a hash function inspired
     * by Java's default hashCode method. The algorithm iterates through each
     * character of the input string, combining the current hash with the ASCII
     * value of the character using the formula hash = 31 * hash + c. The multiplier
     * 31 is chosen for its effectiveness in achieving a good distribution of hash codes.
     * Null values are appropriately handled by returning 0 when the input item is null.
     *
     * @param item the input string for which to generate the hash code
     * @return the computed hash code for the given string
     * @throws NullPointerException if the input string is null
     *
     * @see HashFunctor
     */
    @Override
    public int hash(String item) {
        // Handle null values by returning 0
        if (item == null) {
            return 0;
        }


        // Initialize the hash value
        int hash = 0;


        // Iterate through each character of the input string
        for (char c : item.toCharArray()) {
            // Update the hash using the multiplier 31 and ASCII value of the character
            hash = 31 * hash + c;
        }


        // Return the final hash code
        return hash;
    }
}

