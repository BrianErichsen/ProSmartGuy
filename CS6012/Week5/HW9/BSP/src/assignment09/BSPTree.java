package assignment09;
import java.util.ArrayList;
import java.util.Random;


public class BSPTree {


    private Node root;


//=================================================   Node Class ===================================================//


    private static class Node {
        Segment segment;
        Node left;
        Node right;


        public Node(Segment segment) {
            this.segment = segment;
            this.left = null;
            this.right = null;
        }
    }
//============================================   Default Constructor ==============================================//
    /**
     * Constructs an empty Binary Space Partitioning (BSP) tree.
     * This default constructor initializes the BSP tree with a null root,
     * indicating that the tree is empty and contains no segments.
     */
    public BSPTree() {
        this.root = null;
    }


    //============================================   Bulk Constructor ==============================================//


    /**
     * Constructs a Binary Space Partitioning (BSP) tree using the given list of line segments.
     * If the provided list is not null and not empty, the constructor builds a BSP tree
     * to organize and efficiently handle geometric data represented by line segments.
     *
     * @param segments The list of line segments used to construct the BSP tree.
     *                 It should contain the geometric data to be organized in the tree.
     */
    public BSPTree(ArrayList<Segment> segments) {
        // Check if the provided list is not null and contains segments
        if (segments != null && !segments.isEmpty()) {
            // Build the BSP tree recursively with the provided segments
            root = buildBSPTree(segments);
        }
    }


    //===========================================   buildBSPTree Method ==========================================//


    /**
     * Recursively builds a Binary Space Partitioning (BSP) tree from the given list of line segments.
     * The method randomly selects a segment as the root, partitions the remaining segments into left
     * and right subtrees, and continues the process until the tree is fully constructed.
     *
     * @param segments The list of line segments to be organized into the BSP tree.
     * @return The root node of the constructed BSP tree.
     */
    private Node buildBSPTree(ArrayList<Segment> segments) {
        // Check if the list of segments is empty
        if (segments.isEmpty()) {
            return null;  // Base case: return null for an empty list
        }


        // Randomly choose an index to select a segment from the list
        int index = (int) (Math.random() * segments.size());


        // Get the randomly selected segment and create a node for it
        Segment randomSegment = segments.get(index);
        Node node = new Node(randomSegment);


        // Remove the selected segment from the list to avoid duplicates
        segments.remove(index);


        // Separate segments into those on the left and right of the selected segment
        ArrayList<Segment> leftSegments = new ArrayList<>();
        ArrayList<Segment> rightSegments = new ArrayList<>();


        for (Segment loopSegment : segments) {
            int side = node.segment.whichSide(loopSegment);
            if (side < 0) {
                leftSegments.add(loopSegment);
            } else if (side > 0) {
                rightSegments.add(loopSegment);
            } else {
                // Segments straddling, split and add to both sides
                Segment[] split = node.segment.split(loopSegment);
                leftSegments.add(split[0]);
                rightSegments.add(split[1]);
            }
        }


        // Recursively build left and right subtrees
        node.left = buildBSPTree(leftSegments);
        node.right = buildBSPTree(rightSegments);


        return node;  // Return the root of the constructed subtree
    }


    //==============================================   insert Method ===============================================//


    /**
     * Inserts a segment into the Binary Space Partitioning (BSP) tree.
     * The method recursively traverses the tree, determining the appropriate location for the new segment,
     * and inserts it accordingly. If the new segment straddles the current node's segment, it splits
     * the current node's segment and inserts the split segments into both subtrees.
     *
     * @param segment The segment to be inserted into the tree.
     */
    public void insert(Segment segment) {
        this.root = insertRecursive(this.root, segment);
    }


    //=========================================   insert recursive Method ==========================================//


    /**
     * Recursive method to insert a segment into a Binary Space Partitioning (BSP) tree.
     * The method determines which side the segment is on relative to the current node's segment
     * and recursively inserts it into the corresponding subtree. If the new segment straddles
     * the current node's segment, it is split, and the split segments are inserted into both subtrees.
     *
     * @param root    The current root node of the tree or subtree.
     * @param segment The segment to be inserted.
     * @return The modified root node after insertion.
     */
    private Node insertRecursive(Node root, Segment segment) {
        // If the current node is null, create a new node with the given segment
        if (root == null) {
            return new Node(segment);
        }


        // Determine which side of the current node's segment the new segment is on
        int side = root.segment.whichSide(segment);


        // Recursively insert into the corresponding subtree
        if (side > 0) {
            root.right = insertRecursive(root.right, segment);
        } else if (side < 0) {
            root.left = insertRecursive(root.left, segment);
        } else {
            // Segments straddling, split and insert into both sides
            Segment[] splitResult = root.segment.split(segment);
            root.left = insertRecursive(root.left, splitResult[0]);
            root.right = insertRecursive(root.right, splitResult[1]);
        }


        return root;  // Return the modified root node after insertion
    }


    //=========================================  traverseFarToNear  Method =========================================//


    /**
     * Traverses the segments in "far to near" order relative to a specified point (x, y).
     * The method applies a callback function to each visited segment.
     *
     * @param x        The x-coordinate of the reference point.
     * @param y        The y-coordinate of the reference point.
     * @param callback The callback function to be applied to each visited segment.
     */
    public void traverseFarToNear(double x, double y, SegmentCallback callback) {
        traverseFarToNearRecursive(this.root, x, y, callback);
    }


    //===================================  traverseFarToNear Recursive Method ======================================//


    /**
     * Recursive method to perform the "far to near" traversal of segments relative to a specified point.
     * The segments are processed in a specific order based on their position relative to the reference point.
     * A callback function is applied to each visited segment.
     *
     * @param node     The current root node of the tree or subtree.
     * @param x        The x-coordinate of the reference point.
     * @param y        The y-coordinate of the reference point.
     * @param callback The callback function to be applied to each visited segment.
     */
    private void traverseFarToNearRecursive(Node node, double x, double y, SegmentCallback callback) {
        if (node != null) {
            int side = node.segment.whichSidePoint(x, y);


            if (side > 0) {
                // Include equal case to maintain correct ordering
                traverseFarToNearRecursive(node.left, x, y, callback);
                callback.callback(node.segment);
                traverseFarToNearRecursive(node.right, x, y, callback);
            } else {
                traverseFarToNearRecursive(node.right, x, y, callback);
                callback.callback(node.segment);
                traverseFarToNearRecursive(node.left, x, y, callback);
            }
        }
    }


    //===============================================  collision  Method ===========================================//


    /**
     * Finds any intersecting segment in the BSP tree with a given query segment.
     *
     * @param query The query segment for collision detection.
     * @return The intersecting segment, or null if no collision is detected.
     */
    public Segment collision(Segment query) {
        return collisionRecursive(root, query);
    }


    //=========================================  traverseFarToNear  Method =========================================//


    /**
     * Recursive method for collision detection within the BSP tree.
     *
     * @param node  The current root node of the tree or subtree.
     * @param query The query segment for collision detection.
     * @return The intersecting segment, or null if no collision is detected.
     */
    private Segment collisionRecursive(Node node, Segment query) {
        //Base case
        if (node == null) {
            return null;
        }


        int side = query.whichSide(node.segment);


        if (side == 0) {
            if (node.segment.intersects(query)) {
                // Intersection detected, return the intersecting segment
                return node.segment;
            } else {
                // Check both subtrees
                Segment result = collisionRecursive(node.left, query);
                if (result != null) {
                    return result;
                }
                return collisionRecursive(node.right, query);
            }
        } else if (side > 0) {
            // Segment is on the + side, so check only the left subtree
            return collisionRecursive(node.left, query);
        } else {
            // Segment is on the - side, so check only the right subtree
            return collisionRecursive(node.right, query);
        }
    }
}
//==================================================    END       =================================================//


