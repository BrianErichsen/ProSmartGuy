package lab06;

public interface PriorityQueue<T extends Comparable<T>> {
    /**
     * Adds an element to the priority queue.
     *
     * @param element the element to be added
     */
    void add(T element);


    /**
     * Removes and returns the minimum element from the priority queue.
     *
     * @return the minimum element
     */
    T removeMin();


    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the priority queue is empty, false otherwise
     */
    boolean isEmpty();
}

