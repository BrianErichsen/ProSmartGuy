package assignment05;


import java.util.NoSuchElementException;


public class LinkedListStack<E> implements Stack<E> {
    private SinglyLinkedList<E> list;


    public LinkedListStack() {
        this.list = new SinglyLinkedList<>();
    }


    @Override
    public void clear() {
        list.clear();
    }


    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }


    @Override
    public E peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.getFirst();
    }


    @Override
    public E pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.deleteFirst();
    }


    @Override
    public void push(E element) {
        list.insertFirst(element);
    }


    @Override
    public int size() {
        return list.size();
    }
}

