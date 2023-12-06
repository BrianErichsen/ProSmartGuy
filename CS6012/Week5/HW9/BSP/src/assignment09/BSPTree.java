package assignment09;

import java.util.ArrayList;
import java.util.function.Consumer;

//by Brian Erichsen Fagundes
public class BSPTree {
    private Node root;
    //Default constructor that makes an empty tree
    public BSPTree() {
        this.root = null;
    }
    //Constructor that takes ArrayList<Segment> for bulk construction
    public BSPTree(ArrayList<Segment> segments) {
        for (Segment segment : segments) {
            insert(segment);
        }
    }
    //Insert a segment into an existing tree
    public void insert(Segment segment) {
        this.root = insert(root, segment);
    }
    private Node insert(Node node, Segment segment) {
        if (node == null) {
            return new Node(segment);
        }
        int side = segment.whichSide(node.segment);

        if (side < 0) {
            node.left = insert(node.left, segment);
        } else {
            node.right = insert(node.right, segment);
        }
        return node;
    }
    public void transverseFarToNear(double x, double y, Consumer<Segment> callback) {
        transverseFarToNear(root, x, y, callback);
    }
    private void transverseFarToNear(Node node, double x, double y, Consumer<Segment> callback) {
        //if current node is null; reached leaf node or empty subtree
        if (node == null) {
            return;
        }

        int side = node.segment.whichSidePoint(x, y);
        //checks if query segment intersects with current segment ; returns segment as collision
        //which side the current segment lies on
        //less than 0 means query segment is on the negative side, recursively search in the right subtree
        if (side < 0) {
            transverseFarToNear(node.right, x, y, callback);
            callback.accept(node.segment);
            transverseFarToNear(node.left, x, y , callback);
        } else {
            transverseFarToNear(node.left, x, y, callback);
            callback.accept(node.segment);
            transverseFarToNear(node.right, x, y, callback);
        }
    }

    //Retuns any segment in the tree which intersects query and returns null if there isn't any
    public Segment collision(Segment query) {
        return collision(root, query);
    }
    //query = // pick a segment to test with, you decide how
    //boolean collisionFound = false;
    //tree.traverseFarToNear(x, y, //they don't matter
    //(segment) -> {
    //	if(segment.intersects(query)){
    //		collisionFound = true;
    //	}
    //}
    private Segment collision(Node node, Segment query) {
        if (node == null) {
            return null;
        }
        if (query.intersects(node.segment)) {
            return node.segment;
        }
        int side = query.whichSide(node.segment);

        if (side < 0) {
            return collision(node.right, query);
        } else {
            return collision(node.left, query);
        }
    }
    private static class Node {
        private final Segment segment;
        private Node left;
        private Node right;

        public Node(Segment segment) {
            this.left = null;
            this. right = null;
            this.segment = segment;
        }
    }//end of inner class Node
}//end of class bracket
