package assignment07;
//By Brian Erichsen Fagundes

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class ChainingHashTable implements Set<String> {
    public LinkedList<String>[] storage_;
    private int capacity_;
    private HashFunctor functor_;
    private int size_;
    /** Constructor
     *
     * @param capacity  size of the table
     * @param functor hashcode function*/
    public ChainingHashTable(int capacity, HashFunctor functor) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be a negative value");
        }
        capacity_ = capacity;
        functor_ = functor;
        storage_ = (LinkedList<String>[]) new LinkedList[capacity_];
        size_ = 0;
    }
    /**
     * Ensures that this set contains the specified item.
     *
     * @param item
     *          - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually inserted); otherwise, returns false
     */
    @Override
    public boolean add(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null to be added");
        }
        //Finds the index based on the compressHash function
        int index = compressHash(item);
        //If there is no LinkedList at index, then creates a new one
        if (storage_[index] == null) {
            storage_[index] = new LinkedList<String>();
        }
        //if item is already present; then returns false
        if (contains(item)) {
            return false;
        }
        //Adds the new node
        storage_[index].add(item);
        size_++;//increases size
        //if loadfactor is > than 0.8 then rehash
        double loadFactor = (double) size_ / capacity_;
        if (loadFactor > 0.8) {
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
    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually inserted); otherwise,
     *         returns false
     */
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
    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        //this makes sure that array set is null which allows the garbage collector to reclaim memory
        size_ = 0;
        for (int i = 0; i < capacity_; i++) {
            storage_[i] = null;
        }
    }
    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item
     *          - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     *         otherwise, returns false
     */
    @Override
    public boolean contains(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        //Calculates the index where item should be found
        int index = compressHash(item);
        //retrieves the calculated index from storage array
        LinkedList<String> node = storage_[index];
        //Iterates through each chain in the node
        if (node != null) {
            for (String chain : node) {
                if (Objects.equals(chain, item)) {
                    return true; //found item; returns true
                }
            }
        }
        return false; //else returns false; item not found
    }
    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     *         in this set that is equal to it; otherwise, returns false
     */
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
    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }
    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     */
    @Override
    public boolean remove(String item) {
        if (item == null || isEmpty()) {
            throw new IllegalArgumentException("Item cannot be null or empty to be removed");
        }
        //Finds the index based on the hash code
        int index = compressHash(item);
        //Eliminates the need to use contains method
        LinkedList<String> node = storage_[index];
        //Find if item is present; if item is removed; decreases size and returns true
        if (node != null) {
            if (node.remove(item)) {
                size_--;
                return true;
            }
        }
        return false;
    }
    /**
     * Ensures that this set does not contain any of the items in the specified
     * collection.
     *
     * @param items
     *          - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually removed); otherwise,
     *         returns false
     */
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
    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size_;
    }
}//end of class bracket
