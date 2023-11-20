package assignment05;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class SinglyLinkedList<E> implements List<E> {
    private Node<E> head;  // Reference to the first node in the linked list
    Node<E> tail;  // Reference to the last node in the linked list (used in the ArrayList constructor)
    private int size;  // Number of elements in the linked list


    /**
     * Constructs an empty SinglyLinkedList.
     *
     * This constructor creates an empty SinglyLinkedList with a null head and a size of 0.
     */


    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }


    //===============================================================================================================//


    /**
     * Constructs a SinglyLinkedList with elements from an ArrayList.
     *
     * This constructor initializes a SinglyLinkedList using the elements from the provided ArrayList.
     * It iterates over each element in the ArrayList, creating a new node for each element. If the
     * linked list is empty, the new node becomes both the head and the tail. If the linked list is not
     * empty, the new node is appended to the tail, and the tail reference is updated. The size of the
     * linked list is set to the size of the ArrayList.
     *
     * @param arr The ArrayList containing elements to initialize the linked list.
     */
    // Constructor to initialize a SinglyLinkedList with elements from an ArrayList
    public SinglyLinkedList(ArrayList<E> arr) {
        tail = head;  // Initialize tail to the current head of the linked list


        // Iterate over each element in the ArrayList
        for (var x : arr) {
            // If the linked list is empty, create a new node and set it as both head and tail
            if (head == null) {
                head = new Node(x);
                tail = head;
            } else {
                // If the linked list is not empty, append a new node to the tail and update the tail reference
                tail.next = new Node(x);
                tail = tail.next;
            }
        }


        size = arr.size();  // Set the size of the linked list to the size of the ArrayList
    }


    //===============================================================================================================//


    /**
     * Inserts an element at the beginning of the list.
     * O(1) for a singly-linked list.
     *
     * @param element - the element to add
     */
    // Inserts a new node with the given element at the beginning of the linked list
    @Override
    public void insertFirst(E element) {
        Node<E> newNode = new Node<>(element);  // Create a new node with the given element
        newNode.next = head;  // Set the next reference of the new node to the current head
        head = newNode;  // Update the head to the new node, making it the first node in the list
        size++;  // Increment the size of the linked list
    }


    //===============================================================================================================//


    /**
     * Inserts an element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @param element - the element to add
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
     */
    @Override
    // Inserts a new node with the given element at the specified index in the linked list
    public void insert(int index, E element) throws IndexOutOfBoundsException {
        // Check if the index is out of range
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }


        // If the index is 0, call insertFirst to insert the element at the beginning of the list
        if (index == 0) {
            insertFirst(element);
        } else {
            Node<E> newNode = new Node<>(element);  // Create a new node with the given element
            Node<E> current = head;  // Start from the head of the list


            // Move to the node just before the specified index
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }


            // Insert the new node into the list
            newNode.next = current.next;
            current.next = newNode;
            size++;  // Increment the size of the linked list
        }
    }


    //===============================================================================================================//


    /**
     * Gets the first element in the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    // Returns the data of the first node in the linked list
    @Override
    public E getFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }


    //===============================================================================================================//


    /**
     * Gets the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    // Returns the element at the specified index in the linked list
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        // Check if the index is out of range
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }


        Node<E> current = head;  // Start from the head of the list


        // Move to the node at the specified index
        for (int i = 0; i < index; i++) {
            current = current.next;
        }


        // Check if the current node is unexpectedly null
        if (current == null) {
            throw new IllegalStateException("Node is unexpectedly null");
        }


        return current.data;  // Return the data of the node at the specified index
    }


    //===============================================================================================================//


    /**
     * Deletes and returns the first element from the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    // Deletes and returns the element at the beginning of the linked list
    @Override
    public E deleteFirst() throws NoSuchElementException {
        // Check if the list is empty
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }


        E deletedData = head.data;  // Store the data of the first node to be deleted
        head = head.next;  // Update the head to the next node, effectively removing the first node
        size--;  // Decrement the size of the linked list
        return deletedData;  // Return the data of the deleted node
    }


    //===============================================================================================================//


    /**
     * Deletes and returns the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */


    // Deletes and returns the element at the specified index in the linked list
    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        // Check if the index is out of range
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }


        // If the index is 0, call deleteFirst to delete the element at the beginning of the list
        if (index == 0) {
            return deleteFirst();
        } else {
            Node<E> current = head;  // Start from the head of the list


            // Move to the node just before the specified index
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }


            E deletedData = current.next.data;  // Store the data of the node to be deleted
            current.next = current.next.next;  // Update the next reference, effectively removing the node
            size--;  // Decrement the size of the linked list
            return deletedData;  // Return the data of the deleted node
        }
    }


    //===============================================================================================================//


    /**
     * Determines the index of the first occurrence of the specified element in the list,
     * or -1 if this list does not contain the element.
     * O(N) for a singly-linked list.
     *
     * @param element - the element to search for
     * @return the index of the first occurrence; -1 if the element is not found
     */
    // Returns the index of the first occurrence of an element in the linked list, -1 if not found
    @Override
    public int indexOf(E element) {
        Node<E> current = head;  // Start from the head of the list
        int index = 0;  // Initialize the index to 0


        // Iterate through the list
        while (current != null) {
            // Check if the data of the current node equals the specified element
            if (current.data.equals(element)) {
                return index;  // Return the current index if a match is found
            }
            current = current.next;  // Move to the next node in the list
            index++;  // Increment the index
        }


        return -1;  // Return -1 if the element is not found in the list
    }


    //===============================================================================================================//


    // Inserts an element at the end of the linked list
    public void insertLast(E element) {
        Node<E> newNode = new Node<>(element);  // Create a new node with the given element


        if (isEmpty()) {
            head = newNode;  // If the list is empty, set the new node as the head
        } else {
            Node<E> current = head;  // Start from the head of the list


            // Iterate through the list to find the last node
            while (current.next != null) {
                current = current.next;
            }


            current.next = newNode;  // Set the next reference of the last node to the new node
        }


        size++;  // Increment the size of the linked list
    }


    //===============================================================================================================//


    /**
     * O(1) for a singly-linked list.
     *
     * @return the number of elements in this list
     */
    // Returns the number of elements in the linked list
    @Override
    public int size() {
        return size;
    }


    //===============================================================================================================//


    /**
     * O(1) for a singly-linked list.
     *
     * @return true if this collection contains no elements; false, otherwise
     */
    // Checks if the linked list is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    //===============================================================================================================//


    /**
     * Removes all of the elements from this list.
     * O(1) for a singly-linked list.
     */
    // Clears the linked list by setting the head to null and size to 0
    @Override
    public void clear() {
        head = null;
        size = 0;
    }


    //===============================================================================================================//


    /**
     * Generates an array containing all of the elements in this list in proper sequence
     * (from first element to last element).
     * O(N) for a singly-linked list.
     *
     * @return an array containing all of the elements in this list, in order
     */
    // Converts the linked list to an array
    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[size];  // Create an array with the size of the linked list
        Node<E> current = head;  // Start from the head of the list
        int index = 0;  // Initialize the index to 0


        // Iterate through the list
        while (current != null) {
            array[index++] = current.data;  // Copy the data of each node to the array
            current = current.next;  // Move to the next node in the list
        }


        return array;  // Return the resulting array containing the elements of the linked list
    }


    //===============================================================================================================//


    /**
     * @return an iterator over the elements in this list in proper sequence (from first
     * element to last element)
     */
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }


    // Node class represents a node in the linked list
    private static class Node<E> {
        private E data;  // Data of the node
        private Node<E> next;  // Reference to the next node in the list


        // Constructor to create a node with the given data
        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }


    // Iterator class for iterating through the linked list
    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current;  // Reference to the current node in the iteration
        private Node<E> lastReturned;  // Reference to the last node returned in the iteration


        // Constructor initializes the iterator with the head of the linked list
        public LinkedListIterator() {
            this.current = head;
            this.lastReturned = null;
        }


        // Checks if there is another node in the iteration
        @Override
        public boolean hasNext() {
            return current != null;
        }


        // Returns the data of the next node in the iteration
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current; // Update lastReturned before moving to the next node
            E data = current.data;
            current = current.next;
            return data;
        }


        // Removes the last returned node from the linked list
        // Removes the last returned element from the list
        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("remove() cannot be called before calling next()");
            }


            // Remove lastReturned element from the list
            if (lastReturned == head) {
                head = head.next;  // If lastReturned is the head, update the head to the next node
            } else {
                Node<E> current = head;  // Start from the head of the list


                // Iterate through the list to find the node before lastReturned
                while (current.next != lastReturned) {
                    current = current.next;
                }


                current.next = lastReturned.next;  // Update the next reference, effectively removing lastReturned
            }


            lastReturned = null;  // Reset lastReturned after removal
            size--;  // Decrement the size of the linked list
        }
    }
}

