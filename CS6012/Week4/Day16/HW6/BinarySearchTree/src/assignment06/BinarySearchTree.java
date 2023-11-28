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
        //will come back to this later to have the root of any given node.
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
            throw new NullPointerException("Item cannot be empty to be inserted in the tree node.");
        }
        if (root == null) {
            root = new TreeNode<>(item);
            size++;
            return true;
        } else {
            root.insert((T) item);
            size++;
            return true;
        }
    }//end of add method bracket

    @Override
    public boolean addAll(Collection items) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Comparable item) {
        return false;
    }

    @Override
    public boolean containsAll(Collection items) {
        return false;
    }

    @Override
    public Comparable first() throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Comparable last() throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean remove(Comparable item) {
        return false;
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

