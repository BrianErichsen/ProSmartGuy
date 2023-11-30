package lab06;




import lab06.PriorityQueue;

import java.util.TreeSet;


/**
 * A priority queue implementation using a TreeSet as the underlying data structure.
 *
 * @param <T> the type of elements in the priority queue, must be Comparable
 */
public class TreeSetPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {
    private TreeSet<T> treeSet; // The TreeSet to store elements in a sorted order.


    /**
     * Constructs an empty TreeSetPriorityQueue.
     */
    public TreeSetPriorityQueue() {
        treeSet = new TreeSet<>();
    }


    /**
     * Adds the specified element to the priority queue.
     *
     * @param element the element to be added
     */
    @Override
    public void add(T element) {
        treeSet.add(element);
    }


    /**
     * Removes and returns the minimum element from the priority queue.
     *
     * @return the minimum element, or null if the priority queue is empty
     */
    @Override
    public T removeMin() {
        if (isEmpty()) {
            return null; // Return null if the priority queue is empty
        }
        return treeSet.pollFirst(); // Remove and return the first (minimum) element
    }


    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the priority queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }
}
