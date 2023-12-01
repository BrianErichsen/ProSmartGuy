package assignment07;
/**
 * The MediocreHashFunctor class implements a hash function for strings that
 * is considered mediocre in terms of its distribution characteristics. This
 * hash function calculates the hash code by summing the ASCII values of each
 * character in the input string. While it provides a slightly better distribution
 * compared to a simplistic length-based hash, it still has shortcomings that
 * may lead to collisions, especially for strings with similar character compositions.
 *
 */
public class MediocreHashFunctor implements HashFunctor {
    /**
     * Calculates the hash code by summing the ASCII values of characters in the input string.
     *
     * @param item the input string for which to generate the hash code
     * @return the computed hash code based on the sum of ASCII values
     */
    @Override
    public int hash(String item) {
        // A mediocre hash function (example: sum of ASCII values)
        int hash = 0;
        for (char c : item.toCharArray()) {
            hash += c;
        }
        return hash;
    }
}

