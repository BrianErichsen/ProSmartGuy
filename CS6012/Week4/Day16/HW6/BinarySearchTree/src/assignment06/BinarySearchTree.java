package assignment06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet {
    private TreeNode root;
    private int size;

    public class TreeNode<T extends Comparable<? super T>> {
        TreeNode right, left;
        T data;
        //Public constructor that takes data as a parameter
        public TreeNode(T data) {
            this.data = data;
        }
        /**Insert method inserts new values to binary tress**/
        public boolean insert(T value) {
            //if value is lower than itself, child is inserted in the left
            if (value.compareTo (this.data) <= 0) {
                //if left node is empty, then sets value to be the value of the left node;
                if (left == null) {
                    left = new TreeNode<>(value);
                    return true;
                    //else insert value in itself
                } else {
                    return left.insert(value);
                }
                //use same logic for the right side and if value is not lower than it's higher and after
            } else {
                if (right == null) {
                    right = new TreeNode<>(value);
                    return true;
                } else {
                    return right.insert(value);
                }
            }
        }//end of insert method bracket
        /**Delete method to remove nodes from tree*/
        TreeNode deleteNode(TreeNode root, T value) {
            //base case
            if (root == null) {
                return root;
            }
            //recursive call for ancerstors of node to be deleted
            if (root.data.compareTo(value) > 0) {
                root.left = deleteNode(root.left, value);
                return root;
            } else if (root.data.compareTo(value) < 0) {
                root.right = deleteNode(root.right, value);
                return root;
            }
            //if one child is empty
            if (root.left == null) {
                TreeNode temp = root.right;
                return temp;
            } else if (root.right == null) {
                TreeNode temp = root.left;
                return temp;
                //if both child exists
            } else {
                TreeNode sParent = root;
                TreeNode successor = root.right;
                while (successor.left != null) {
                    sParent = successor;
                    successor = successor.left;
                }
                //Delete successor; since succ is always left we can safely make successor right right child
                // as left of it's parent, if no succ, then assign succ.right to be sParente.right
                if (sParent != root) {
                    sParent.left = successor.right;
                } else
                    sParent.right = successor.right;
                //Copies successor data to root
                root.data = successor.data;
                return root;
            }
        }
        public boolean search(T value) {
            if (data.equals(value)) {
                return true;
            }
            boolean foundInLeft = false;
            boolean foundInRight = false;
            if (left != null) {
                foundInLeft = left.search(value);
            }
            if (right != null) {
                foundInRight = right.search(value);
            }
            return foundInLeft || foundInRight;
        }
    }//end of inner class bracket

    //No parameter constructor which creates a empty BST
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    public BinarySearchTree(TreeNode node) {
        //will come back to this later to have the root of any given node instead to be the root
        if (root == null) {
            this.root = node;
            size++;
        } else {
            node.insert(node.data);
        }
    }
    /**public helper method for testing purposes */
    public TreeNode<T> getRoot() {
        return root;
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
            return true;
            //else use the insert method
        } else {
            root.insert((T) item);
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
        //if root is empty; then item is not present
        if (root == null) {
            return false;
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
        //Check if the tree is empty
        if (root == null) {
            return false;
        }
        //to check if the set is changed or not
        int initialSize = size();
        root = root.deleteNode(root, (T) item);
        //checks if the set was changed
        //returns true if current size is less than initial size; false otherwise
        return size() < initialSize;
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
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean removeAll(Collection items) {
        if (items == null) {
            throw new NoSuchElementException("Items cannot be empty to be removed");
        }
        //Flag to track if any changes are made
        boolean changed = false;
        //Iterates through set of items
        for (Object item : items) {
            //if the item is removed; then changes the flag
            if (remove((Comparable) item)) {
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
        //I am keeping track of size within the add & remove methods hence just call this.size
        return countNodes(root);
    }
    /**Helper method that counts the number of nodes */
    private int countNodes(TreeNode<T> node) {
        //if node is empty; hence returns 0
        if (node == null) {
            return 0;
        }
        //Recursively count nodes in the left and right subtrees and add 1 for the current node
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    /**
     * Returns an ArrayList containing all of the items in this set, in sorted
     * order.
     */
    @Override
    public ArrayList toArrayList() {
        ArrayList<T> result = new ArrayList<>();
        //Starts in order transversal from the root
        inOrderTransversal(root, result);
        return result;
    }
    public void inOrderTransversal(TreeNode<T> node, ArrayList<T> result) {
        if (node != null) {
            //Recursively transverse the left subtree, visit the current node, and then
            //transverse the right subtree
            inOrderTransversal(node.left, result);
            result.add(node.data);
            inOrderTransversal(node.right, result);
        }
    }
}//end of class bracket

