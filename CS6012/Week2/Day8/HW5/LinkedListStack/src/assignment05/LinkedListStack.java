package assignment05;


import java.util.NoSuchElementException;


public class LinkedListStack<E> implements Stack<E> {
    private SinglyLinkedList<E> list;  // SinglyLinkedList used as the underlying data structure for the stack


    /**
     * Constructs a LinkedListStack with an empty SinglyLinkedList.
     *
     * This constructor initializes a LinkedListStack by creating a new instance of SinglyLinkedList.
     * The stack is initially empty, and the underlying SinglyLinkedList is used for stack operations.
     */


    public LinkedListStack() {
        this.list = new SinglyLinkedList<>();
    }
    //===============================================================================================================//


    /**
     * Clears the stack by clearing the underlying SinglyLinkedList.
     *
     * This method clears the elements from the stack by delegating the operation to the clear() method
     * of the underlying SinglyLinkedList.
     */
    // Clears the stack by clearing the underlying SinglyLinkedList
    @Override
    public void clear() {
        list.clear();
    }
    //===============================================================================================================//


    /**
     * Checks if the stack is empty by delegating to the isEmpty() method of the underlying SinglyLinkedList.
     *
     * This method determines whether the stack is empty by checking if the underlying SinglyLinkedList
     * has no elements.
     *
     * @return true if the stack is empty, false otherwise.
     */
    // Checks if the stack is empty by delegating to the isEmpty() method of the underlying SinglyLinkedList
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    //===============================================================================================================//


    /**
     * Returns the element at the top of the stack without removing it.
     *
     * This method retrieves the element at the top of the stack without modifying the stack.
     *
     * @return the element at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    // Returns the element at the top of the stack without removing it
    @Override
    public E peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.getFirst();
    }
    //===============================================================================================================//


    /**
     * Removes and returns the element at the top of the stack.
     *
     * This method pops the element at the top of the stack by delegating the operation to the deleteFirst()
     * method of the underlying SinglyLinkedList.
     *
     * @return the element at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    // Removes and returns the element at the top of the stack
    @Override
    public E pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.deleteFirst();
    }
    //===============================================================================================================//


    /**
     * Inserts an element at the top of the stack by inserting it at the beginning of the underlying SinglyLinkedList.
     *
     * This method pushes an element onto the stack by inserting it at the beginning of the underlying SinglyLinkedList.
     *
     * @param element The element to be inserted at the top of the stack.
     */
    // Inserts an element at the top of the stack by inserting it at the beginning of the underlying SinglyLinkedList
    @Override
    public void push(E element) {
        list.insertFirst(element);
    }
    //===============================================================================================================//


    /**
     * Returns the size of the stack by delegating to the size() method of the underlying SinglyLinkedList.
     *
     * This method returns the number of elements in the stack by checking the size of the underlying SinglyLinkedList.
     *
     * @return the size of the stack.
     */
    // Returns the size of the stack by delegating to the size() method of the underlying SinglyLinkedList
    @Override
    public int size() {
        return list.size();
    }
    //===============================================================================================================//
}


