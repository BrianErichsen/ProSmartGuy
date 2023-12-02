package assignment07;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;

public class QuadProbeHashTable implements Set<String>{
    private String[] table_;
    private int capacity_;
    private HashFunctor functor_;
    private int size_;
    public LinkedList<String>[] storage_;
    /** Constructs a hash table of the given capacity that uses the hashing function
     * specified by the given functor.
     */
    public QuadProbeHashTable(int capacity, HashFunctor functor) {
       if (capacity <= 0) {
           throw new IllegalArgumentException("The capacity must be higher than 0");
       }
       //capacity is initial table size; if it is not a prime number; then find next prime number
       capacity_ = nextPrime(capacity);
       functor_ = functor;
       table_ = new String[capacity_];
       size_ = 0;
       storage_ = (LinkedList<String>[]) new LinkedList[capacity_];
    }
    /** Helper method to find the next prime for proper/suitable table size*/
    private int nextPrime(int n) {
        BigInteger bigInteger = BigInteger.valueOf(n);
        return Integer.parseInt(bigInteger.nextProbablePrime().toString());
    }
    private int quadraticProbe(String item, int i) {
        int index = (functor_.hash(item) + i * i) % capacity_;
        return (index < 0) ? index + capacity_ : index;
    }

    @Override
    public boolean add(String item) {
            if (item == null) {
                throw new IllegalArgumentException("Item cannot be null to be added");
            }
            //Finds the index based on the compressHash function
            int index = compressHash(item);
            //If there is no LinkedList at index, then creates a new one
            if (storage_[index] == null) {
                storage_[index] = new LinkedList<>();
            }
            int i = 0;
            int probeIndex = index;
            //While current probing index is occupied, keep probing quadratically
            while(storage_[probeIndex] != null && contains(item)) {
                i++;
                probeIndex = (index + i * i) % capacity_;
            }
            //Adds the new node
            storage_[index].add(item);
            size_++;//increases size

            //if loadfactor is > than 0.5 then rehash
            double loadFactor = (double) size_ / capacity_;
            if (loadFactor > 0.5) {
                rehash();
            }
            //if reached to this point; item was successfully added into the set
            return true;
        }

    private void rehash() {
        //doubles the capacity
        int newCapacity = capacity_ * 2;
        //creates a new storage array with the new Capacity
        LinkedList<String>[] newStorage = (LinkedList<String>[]) new LinkedList[newCapacity];
        int newSize = 0;
        //Rehashes the elements from the old storage into the new one
        for (LinkedList<String> item : storage_) {
            if (item != null) {
                //for each element, add the chain to the new one
                for (String chain : item) {
                    int hashCode = functor_.hash(chain);
                    int newIndex = Math.abs(hashCode % newCapacity);
                    if (newStorage[newIndex] == null) {
                        newStorage[newIndex] = new LinkedList<>();
                    }
                    newStorage[newIndex].add(chain);
                    newSize++;
                }
            }
        }
        //sets the new storage, new capacity and size to the new values
        storage_ = newStorage;
        capacity_ = newCapacity;
        size_ = newSize;
    }
    private int compressHash(String item) {
        int hashCode = functor_.hash(item);
        int index = Math.abs(hashCode % capacity_);
        return index;
    }
    @Override
    public boolean addAll(Collection<? extends String> items) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(String item) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<? extends String> items) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean remove(String item) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<? extends String> items) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
