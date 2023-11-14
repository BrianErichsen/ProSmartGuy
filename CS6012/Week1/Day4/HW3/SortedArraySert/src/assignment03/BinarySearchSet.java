package assignment03;
//Author:Brian Erichsen Fagundes
import java.util.*;

import static java.util.Collections.binarySearch;

public class BinarySearchSet<E> implements SortedSet<E>{
    //remove capacity method to call .length
    private int capacity_;
    private int size_;
    private Comparator<? super E> comparator_;
    private E[] set_;

    //creates the sorted set; sorted in natural order
    public BinarySearchSet() {
        capacity_ = 10;
        this.set_ = (E[]) new Object[capacity_];
        comparator_ = null;
        size_ = 0;
    }
    //sorts the set with specific comparator
    public BinarySearchSet(Comparator<? super E> comparator) {
        capacity_ = 10;
        this.set_ = (E[]) new Object[capacity_];
        comparator_ = comparator;
        size_ = 0;

    }
    //Returns the comparator used to sort elements in array
    @Override
    public Comparator<? super E> comparator() {
        return comparator_;
    }
    //Returns first (lowest, smallest) element in array in this set
    @Override
    public E first() throws NoSuchElementException {
        //Checks if size is 0; and throws an exception if so
        if (size_ == 0) {
            throw new NoSuchElementException("Set is empty");
        }
        //else returns the set data at lowest index
        else
            return set_[0];
    }

    @Override
    public E last() throws NoSuchElementException {
        if (size_ == 0) {
            throw new NoSuchElementException("Set is empty");
        }
        else
            return set_[size_ - 1];
    }
    /**
     * Adds all of the elements in the specified collection to this set if they
     * are not already present and not set to null.
     *
     *  elements collection containing elements to be added to this set
     *  true if this set changed as a result of the call
     */
    @Override
    public boolean add(E element) {
        boolean added = false;
        //If set is null, then creates new array and adds element to it
        if (set_==null) {
            set_ = (E[]) new Object[1];
            set_[0] = element;
            size_ = 1;
            added = true;
            return added;
        }
        //call constains methods once
        //If the set does not contain specific element; then it adds it
        if (!contains(element)) {
            //Calls for the binarySearch to find insertion point
            int insertionPoint = binarySearch(element);
            //If insertion is greater than 0 then it is itself, if negative then insertion + 1
            //changes to a positive value
            insertionPoint = insertionPoint >= 0 ? insertionPoint : -(insertionPoint + 1);
            //If size is greater or equal than array capacity, then call for the growArray method
            if (size_ >= capacity_) {
                growArray();
            }
            //handles case ...
            //Shifts contents to the right, starting from the insertion point, to make space for the new element
            System.arraycopy(set_, insertionPoint, set_, insertionPoint + 1, size_ - insertionPoint);
            set_[insertionPoint] = element;
            size_++;
            added = true;
        }
        return added;
    }
    @Override
    public boolean addAll(Collection<? extends E> elements) {
        //get the original collection size
        int originalSize = this.size();
        //Iterates through all elements / if element is not already in the set; then adds to set
        for (E obj : elements) {
                this.add(obj);
        }
        //Gets the new size of the collection
        int newSize = this.size();
        //returns true if new size is greather than previous size indicating that a element was added into it
        return newSize > originalSize;
    }
    //Come back to this later
    @Override
    public void clear() {
        size_ = 0;
    }

    @Override
    public boolean contains(E element) {
        //searches in the array with binary search
        int index = binarySearch(element);
        //if index >= 0 it means that element already exists in array hence returning true
        return index >= 0;
    }

    @Override
    public boolean containsAll(Collection<? extends E> elements) {
        //Iterate through all elements in collection, if any of the elements is not present then returns false
        for (E obj : elements) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        //returns true only if all elements were present in the collection
        return true;
    }
    //Returns true if this set has size 0 (no elements)
    @Override
    public boolean isEmpty() {
        return (size_ == 0);
    }
    //Removes specified element from set; returns true if this set contains the specified element
    @Override
    public boolean remove(E element) {
        //finds the index of the element
        int index = binarySearch(element);
        //if element is found, then remove it from array
            if (contains(element)) {
                System.arraycopy(set_, index + 1, set_, index, size_ - index - 1);
                //sets the last element to null
                    set_[--size_] = null;
                //returns true that element was removed
                return true;
            }
            return false;
    }
    //Removes from this set all its elements that are contained in the specific collection
    @Override
    //remove new array list no chekc for contains
    public boolean removeAll(Collection<? extends E> elements) {
        List<E> elementsToRemove = new ArrayList<>(elements);
        boolean ifRemoved = false;
        //Iterates through whole list of items to be removed and remove one by one if original set had specified element
        for (E element : elementsToRemove) {
            if (this.contains(element)) {
                this.remove(element);
                ifRemoved = true;
            }
        }
        return ifRemoved;
    }
    @Override
    public Iterator iterator() {
        return new InnerIterator(this);
    }

    @Override
    public int size() {
        return size_;
    }

    @Override
    public E[] toArray() {
        return Arrays.copyOf(set_, size_, (Class<? extends E[]>) set_.getClass());
    }
    //Helper methods
    public void growArray() {
        //if this method is called; then it doubles the capacity
        capacity_ = 2 * capacity_;
        //copy it's content from set to this temp array
        E[] tempArray = Arrays.copyOf(set_, capacity_);
        //changes the pointer to the temp array
        set_ = tempArray;
    }
    //Algorithm to find specific element from sorted list of items // repeatedly divides by 2 the portion that possibly
    //contains item until we narrow the possible location to just one // repeat until low is less than or equal to high
    private int binarySearch(E element) {
        int low = 0;//low is the lowest index of the array
        int high = size_ - 1;//as the highest index of the array
        int cmp;
        //Performs the binary search which narrows down the search range until low >= high
        while (low <= high) {
            //calculate the middle index; the >>> operator (right shift with 0 fills in, hence divides by 2;
            int mid = (low + high) >>> 1;
            //if the comparator is provided then compares elements at the middle index and the target element
            if (comparator_ != null) {
                cmp = comparator_.compare(set_[mid], element);
                //if no comparator; then uses Comparable(compareTo method)
            } else {
                @SuppressWarnings("unchecked")
                        Comparable<? super E> midVal = (Comparable<? super E>) set_[mid];
                //if midval is null, then it stops the comparison and it indicates that the elements are not comparable
                if (midVal == null) {
                    break;
                }
                //Compares the middle value with the given element
                cmp = midVal.compareTo(element);
            }
            //if the result of the comparison is negative; then it means that element is in the upper half of current range
            if (cmp < 0) {
                low = mid + 1;//updates the search range
                //If comparison is positive, updates the search range
            } else if (cmp > 0) {
                high = mid - 1;
                //else element found at mid index
            } else
                return mid;
        }//
        //if element is not found in array; the method returns the negative of the insertion point -low and -1;
        //which is where the element should be inserted
        return -(low + 1);
    }
    //retuns current set_
    public E[] getSet_() {
        if (set_.equals(null)) {
            throw new NullPointerException("No set");
        }
        //returns set if set is not null
        return set_;
    }
    public E get(int i) {
        if (i < 0 || i >= size_) {
            throw new IndexOutOfBoundsException("Illegal index, non existing");
        }
        //returns specified element at index i
        return set_[i];
    }
    class InnerIterator implements Iterator<E> {
        E[] set_;
        int position;
        public InnerIterator(BinarySearchSet<E> set) {
            this.set_ = set.getSet_();
        }
        //Returns true if there are more elements in the array
        @Override
        public boolean hasNext() {
            if (position < 0) {
                throw new IllegalArgumentException("Position should be a positive number");
            }
            return position < size_;
        }//remove check
        //Returns next Element and advance the position
        @Override
        public E next() {
            if (position < 0) {
                throw new IllegalArgumentException("Position should be a positive number");
            }
            E obj = get(position);
            position++;
            return obj;
        }
        //removes the element that the most recent call to next returned
        @Override
        public void remove() {
            E obj = get(position);
            BinarySearchSet.this.remove(obj);
        }//add bolean
    }//end of InnerIterator class bracket
}//end of class bracket
