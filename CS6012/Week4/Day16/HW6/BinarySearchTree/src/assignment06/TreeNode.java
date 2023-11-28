package assignment06;

public class TreeNode<T extends Comparable<? super T>> {
    TreeNode right, left;
    T data;
    //Public constructor that takes data as a parameter
    public TreeNode(T data) {
        this.data = data;
    }
    //create method to return these guys
    private TreeNode first;
    //go left node = node.left; until fall off tree -- node = none; return node; O (h)
    //Successor is the node next after node in tree's traversal order; O (h)
    //i give you one node, find next one; or find it's root
    //iter ((x)) x being a node -- O (n)
    //iter ((x.lfeft; itself, x.right

    private TreeNode successor;
    //if node.right: return subtree_first(node.right) .. if no right child like a leaf
    //walk up the tree node = node.parent; until up a left branch (node== node.parent.left)
    //return node
    /**
     * */
    private TreeNode after;
    private TreeNode before;
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
//    public void delete (int value) {
//
//    }
//    TreeNode deleteNode(TreeNode root, int value) {
//        //base case
//        if (root == null) {
//            return root;
//        }
//        //recursive call for ancerstors of node to be deleted
//        if (root.data > value) {
//            root.left = deleteNode(root.left, value);
//            return root;
//        } else if (root.data < value) {
//            root.right = deleteNode(root.right, value);
//            return root;
//        }
//        //if one child is empty
//        if (root.left == null) {
//            TreeNode temp = root.right;
//            return temp;
//        } else if (root.right == null) {
//            TreeNode temp = root.left;
//            return temp;
//            //if both child exists
//        } else {
//            TreeNode sParent = root;
//            TreeNode successor = root.right;
//            while (successor.left != null) {
//                sParent = successor;
//                successor = successor.left;
//            }
//            //Delete successor; since succ is always left we can safely make successor right right child
//            // as left of it's parent, if no succ, then assign succ.right to be sParente.right
//            if (sParent != root) {
//                sParent.left = successor.right;
//            } else
//                sParent.right = successor.right;
//            //Copies successor data to root
//            root.data = successor.data;
//            return root;
//        }
//    }
    public void inOrderTransversal() {
        if (left != null) {
            left.inOrderTransversal();
        }
        System.out.print(data + " ");
        if (right != null) {
            right.inOrderTransversal();
        }
    }
    public void preOrderTransversal() {
        System.out.print(data + " ");
        if (left != null) {
            left.preOrderTransversal();
        }
        if (right != null) {
            right.preOrderTransversal();
        }
    }
    public void postOrderTransversal() {
        if (left != null) {
            left.postOrderTransversal();
        }
        if (right != null) {
            right.postOrderTransversal();
        }
        System.out.print(data + " ");
    }
//    public boolean search(int value) {
//        if (data == value) {
//            return true;
//        }
//        boolean foundInLeft = false;
//        boolean foundInRight = false;
//        if (left != null) {
//            foundInLeft = left.search(value);
//        }
//        if (right != null) {
//            foundInRight = right.search(value);
//        }
//        return foundInLeft || foundInRight;
//    }
    public int height() {
        //Height of an empty tree is 0 and height of a tree with a single node is 1;
        if (this == null) {
            return 0;
        } else {
            int leftHeight = (left == null) ? -1 : left.height();
            int rightHeight = (right == null) ? -1 : right.height();

            return 1 + Math.max(leftHeight, rightHeight);
        }
    }
    public boolean isBalanced(TreeNode node) {
        //if this is null, then this is a balanced tree
        if (node == null) {
            return true;
        }
        int lh = left.height();
        int rh = right.height();
        return Math.abs(lh - rh) <= 1 && isBalanced(node.left) && isBalanced(node.right);
    }
    public void invertNode(TreeNode node) {
        if (node != null) {
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            invertNode(node.left);
            invertNode(node.right);
        }
    }
}//end of class bracket

