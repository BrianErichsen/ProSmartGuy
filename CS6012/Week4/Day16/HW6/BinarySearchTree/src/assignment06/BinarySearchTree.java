package assignment06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet {
    private TreeNode<T> root;
    private int size;

    //No parameter constructor which creates a empty BST
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    public BinarySearchTree(TreeNode node) {
        //will come back to this later to have the root of any given node instead to be the root
        this.root = node;
        size++;
    }
    /**
     * Ensures that this set contains the specified item.
     *
     * @param item
     *          - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually inserted); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean add(Comparable item) {
        if (item == null) {
            //I could change exception to be a return stament of false if I wanted program not to quit
            throw new NullPointerException("Item cannot be empty to be inserted in the tree node.");
        }
        //if root is empty, then creates a new treeNode that it's element will be set as the root
        if (root == null) {
            root = new TreeNode<>(item);
            size++;
            return true;
            //else use the insert method
        } else {
            root.insert((T) item);
            size++;
            return true;
        }
    }//end of add method bracket
    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually inserted); otherwise,
     *         returns false
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean addAll(Collection items) {
        //if collections is null; it should quit the program
        if (items == null) {
            throw new NullPointerException("Collecttion cannot be null");
        }
        //boolean to track if item was added or not
        boolean changed = false;

        for (Object item : items) {
            if (item == null) {
                throw new NullPointerException("Item cannot be empty to be inserted in the Tree!");
            }
            if (add((Comparable) item)) {
                changed = true;
            }
        }
        return changed;
    }
    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        //After setting the root to be null; the garbage collector will remove the other pointers (nodes)
        //that were associated with the root node
        root = null;
    }
    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item
     *          - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     *         otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean contains(Comparable item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        //calls for the search method within TreeNode class
        return root.search((T) item);
    }
    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     *         in this set that is equal to it; otherwise, returns false
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean containsAll(Collection items) {
        if (items == null) {
            throw new NullPointerException("Collection cannot be empty");
        }
        //Iterates through each item in the collection
        for (Object item : items) {
            //if any item is not present in the tree; then it returns false
            if (!contains((Comparable) item)) {
                return false;
            }
        }
        //else returns true
        return true;
    }
    /**
     * Returns the first (i.e., smallest) item in this set.
     *
     * @throws NoSuchElementException
     *           if the set is empty
     */
    @Override
    public Comparable first() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Set is empty");
        }
        return findMin(root).data;
    }
    /** helper method to find the minimun node in a subtree */
    protected TreeNode<T> findMin(TreeNode<T> node) {
        //if node is null; return null
        if (node == null) {
            return null;
        }
        //transverse tree until the left most node is reached
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }
    /**
     * Returns the last (i.e., largest) item in this set.
     *
     * @throws NoSuchElementException
     *           if the set is empty
     */
    @Override
    public Comparable last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Set is empty");
        }
        //find and returns the max element in the set
        return findMax(root).data;
    }
    /** helper method to find the max element in a subtree */
    protected TreeNode<T> findMax(TreeNode<T> node) {
        if (node == null) {
            return null;
        }
        //Transverse the right subtree until rightmost node is reached
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean remove(Comparable item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null to be removed");
        }
        //to check if the set is changed or not
        int initialSize = size();
        root = root.deleteNode(root, (T) item);

        //checks if the set was changed
        return size() < initialSize;
    }

    @Override
    public boolean removeAll(Collection items) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ArrayList toArrayList() {
        return null;
    }
}

