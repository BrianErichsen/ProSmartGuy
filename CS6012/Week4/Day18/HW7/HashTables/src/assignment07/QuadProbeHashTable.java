package assignment07;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class QuadProbeHashTable implements Set<String> {

    private int capacity_;
    private String[] table;
    private int size_;
    private HashFunctor functor_;

    public QuadProbeHashTable(int capacity, HashFunctor functor) {
        capacity_ = capacity;
        table = new String[capacity];
        functor_ = functor;
        size_ = 0;
    }

    public void insert(String key) {
        int index = functor_.hash(key);
        int i = 0;
        while (table[index] != null) {
            index = (index + (i * i)) % capacity_;
            i++;
        }
        table[index] = key;
    }

    public boolean search(String key) {
        int index = functor_.hash(key);
        int i = 0;
        while (table[index] != null && !Objects.equals(table[index], key)) {
            index = (index + (i * i)) % capacity_;
            i++;
        }
        return Objects.equals(table[index], key);
    }

    public boolean delete(String key) {
        boolean changed = false;
        int index = functor_.hash(key);
        int i = 0;
        while (table[index] != null && !Objects.equals(table[index], key)) {
            index = (index + (i * i)) % capacity_;
            i++;
        }
        if (Objects.equals(table[index], key)) {
            table[index] = null;
             changed = true;
             size_--;
        }
        return changed;
    }

    @Override
    public boolean add(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null to be added");
        }
        //item already is present in the data structure
        if (search(item)) {
            return false;
        }
        double loadFactor = (double) size_ / capacity_;
        if (loadFactor > 0.5) {
            rehash();
        }
        insert(item);
        size_++;
        return true;
    }
    public void rehash() {
        // Double the capacity
        int newCapacity = capacity_ * 2;

        // Find the next prime number greater than or equal to the new capacity
        newCapacity = nextPrime(newCapacity);

        // Create a new table with the new capacity
        String[] newTable = new String[newCapacity];
        int newSize = 0;
        // Rehash the elements from the old table into the new one
        for (String key : table) {
            if (key != null) {
                int newIndex = findEmptySlot(newTable, functor_.hash(key), key);
                newTable[newIndex] = key;
                newSize++;
            }
        }

        // Update the capacity and table
        capacity_ = newCapacity;
        table = newTable;
        size_ = newSize;
    }
    private int nextPrime(int n) {
        BigInteger bigInteger = BigInteger.valueOf(n);
        return Integer.parseInt(bigInteger.nextProbablePrime().toString());
    }
    private int findEmptySlot(String[] table, int index, String key) {
        int i = 0;
        while (table[index] != null) {
            index = (index + (i * i)) % table.length;
            i++;
        }
        return index;
    }

    @Override
    public boolean addAll(Collection<? extends String> items) {
        if (items == null) {
            throw new IllegalArgumentException("Collectionss of items cannot be null");
        }
        //flag to keep track if a item has been added
        boolean added = false;
        //iterates through whole collection and uses the add method to add each item;
        for (String item : items) {
            if (add(item)) {
                added = true;
            }
        }
        return added;
    }

    @Override
    public void clear() {
        size_ = 0;
        for (int i = 0; i < capacity_; i++) {
            table[i] = null;
        }
    }

    @Override
    public boolean contains(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null to be searched");
        }
        return search(item);
    }

    @Override
    public boolean containsAll(Collection<? extends String> items) {
        if (items == null) {
            throw new IllegalArgumentException("Collections cannot be null");
        }
        //iterates through whole collection and if contains method returns false in any item
        for (String item : items) {
            if (!contains(item)) {
                return false; //returns false then
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }

    @Override
    public boolean remove(String item) {
        if (item == null || isEmpty()) {
            throw new IllegalArgumentException("Item cannot be null or empty to be deleted");
        }
        return delete(item);
    }
    @Override
    public boolean removeAll(Collection<? extends String> items) {
        if (items == null) {
            throw new IllegalArgumentException("Collections of items to be removed cannot be null");
        }
        //flag to keep track either an item has been removed or not
        boolean changed = false;
        //Iterates through whole collection of items; if a item from collection is removed; changed flag
        for (String item : items) {
            if (remove(item)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public int size() {
        return size_;
    }
}
