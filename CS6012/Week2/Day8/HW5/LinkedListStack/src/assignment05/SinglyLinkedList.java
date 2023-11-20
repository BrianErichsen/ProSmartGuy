//Author Brian Erichsen Fagundes
package assignment05;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class SinglyLinkedList<E> implements List<E> {
    private Node<E> head;
    Node<E> tail;

    private int size;

//Default constructor
    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    //For creating empty linkedList, populate it and use for other methods
    public SinglyLinkedList(ArrayList<E> arr) {
        tail = head;
        for (var x : arr) {
            //if this is empty; sets the head and tail
            if (head == null) {
                head = new Node(x);
                tail = head;
            } else {
                tail.next = new Node(x);
                tail = tail.next;
            }
        }//sets size per arr size
        size = arr.size();
    }
    @Override
    public void insertFirst(E element) {
        Node<E> newNode = new Node<>(element);
        newNode.next = head;
        head = newNode;
        size++;
    }
    @Override
    public void insert(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }


        if (index == 0) {
            insertFirst(element);
        } else {
            Node<E> newNode = new Node<>(element);
            Node<E> current = head;


            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }


            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
    }


    @Override
    public E getFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }


    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");

        }


        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }


        if (current == null) {
            throw new IllegalStateException("Node is unexpectedly null");
        }


        return current.data;
    }




    @Override
    public E deleteFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }


        E deletedData = head.data;
        head = head.next;
        size--;
        return deletedData;
    }


    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }


        if (index == 0) {
            return deleteFirst();
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }


            E deletedData = current.next.data;
            current.next = current.next.next;
            size--;
            return deletedData;
        }
    }


    @Override
    public int indexOf(E element) {
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }


    public void insertLast(E element) {
        Node<E> newNode = new Node<>(element);


        if (isEmpty()) {
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }


        size++;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        head = null;
        size = 0;
    }


    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[size];
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }




    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }


    private static class Node<E> {
        private E data;
        private Node<E> next;


        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }


    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current;
        private Node<E> lastReturned;


        public LinkedListIterator() {
            this.current = head;
            this.lastReturned = null;
        }


        @Override
        public boolean hasNext() {
            return current != null;
        }


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


        //        @Override
//        public void remove() {
//            if (lastReturned == null) {
//                throw new IllegalStateException("remove() cannot be called before calling next()");
//            }
//            lastReturned = null; // Reset lastReturned after removal
//        }
        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("remove() cannot be called before calling next()");
            }
            // Remove lastReturned element from the list
            if (lastReturned == head) {
                head = head.next;
            } else {
                Node<E> current = head;
                while (current.next != lastReturned) {
                    current = current.next;
                }
                current.next = lastReturned.next;
            }
            lastReturned = null; // Reset lastReturned after removal
            size--;
        }


    }
}

