package assignment07;
public class BadHashFunctor implements HashFunctor {
    @Override
    public int hash(String item) {
        // Return the length of the string as the hash code
        return item.length();
    }
}

