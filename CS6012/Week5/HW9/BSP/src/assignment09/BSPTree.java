package assignment09;
import java.util.ArrayList;
/**
 * The BSPTree class represents a Binary Space Partitioning (BSP) tree data structure
 * used for efficient spatial organization of line segments in a 2D space. It supports
 * segment insertion, traversal in a specific order, and collision detection.
 *
 */
public class BSPTree {


    private Node root;


    //==========================================   Empty Constructor    ===========================================//


    /**
     * Constructs a new instance of the BSPTree class with an empty tree.
     *
     * <p>This constructor initializes the tree with a null root, creating an empty BSP tree.
     * It is useful when you want to start with an empty tree and later insert segments into it
     * using the insert method.
     *
     */
    public BSPTree() {
        //Make an empty tree
        this.root = null;
    }

    //===========================================    Constructor    ===============================================//


    /**
     * Constructs a new instance of the BSPTree class and inserts a list of segments.
     *
     * <p>This constructor takes an ArrayList of segments and inserts each segment into the
     * binary space partitioning (BSP) tree using the insert method. It is a convenient way
     * to construct a BSP tree with a set of initial segments.
     *
     * @param segments An ArrayList of Segment objects to be inserted into the BSP tree.
     */
    public BSPTree(ArrayList<Segment> segments){
        if(segments != null && !segments.isEmpty()){
            root = buildBSPTree(segments);
        }
    }
    private Node buildBSPTree(ArrayList<Segment> segments){
        if(segments.isEmpty()) {
            return null;
        }
        int index = (int) (Math.random() * segments.size());
        Segment randomSegment = segments.get(index);
        Node node = new Node(randomSegment);
        segments.remove(index);
        ArrayList<Segment> leftSegments = new ArrayList<>();
        ArrayList<Segment> rightSegments = new ArrayList<>();
        for(Segment loopSegment : segments) {
            int side = node.segment.whichSide(loopSegment);
            if (side < 0) {
                leftSegments.add(loopSegment);
            } else if (side > 0) {
                rightSegments.add(loopSegment);
            } else {
                Segment[] split = node.segment.split(loopSegment);
                leftSegments.add(split[0]);
                rightSegments.add(split[1]);
            }
        }
        node.left = buildBSPTree(leftSegments);
        node.right = buildBSPTree(rightSegments);
        return node;
    }


    //========================================   Insert  Method    ===============================================//


    /**
     * Inserts a new segment into the binary space partitioning (BSP) tree.
     *
     * @param segment The new segment to be inserted into the BSP tree.
     */
    public void insert(Segment segment) {
        root = insert(root, segment);
    }


    /**
     * This method recursively inserts a new segment into the BSP tree. If the current
     * node is null, a new node containing the segment is created and returned. If the new
     * segment is on the right side, it is inserted into the right subtree; if on the left
     * side, it is inserted into the left subtree. If the new segment intersects with the
     * current node's segment, the current node's segment is split into two segments using
     * the split method, and the insertion process is repeated for each split segment.
     *
     * @param node The current node being processed during the insertion.
     * @return The updated node after inserting the new segment.
     */
    private Node insert(Node node, Segment segment) {
        //Base Case
       /*If the current node is null, it means we've found an empty spot in the tree where
       we can insert the new segment. In this case, we create a new node containing the
       segment and return it.*/
        if (node == null) {
            return new Node(segment);
        }
       /*Determine on which side of the current tree node's segment the new segment should be
       placed (left or right).
       The whichSide method is used for this purpose, and it returns:
           1 if the new segment is on the right side.
           -1 if the new segment is on the left side.
           0 if the new segment intersects with the current segment.*/
        int side = segment.whichSide(node.segment);


        if (side == 1) {
            node.right = insert(node.right, segment);
        } else if (side == -1) {
            node.left = insert(node.left, segment);
        } else {
           /*If the new segment intersects with the current node's segment, it means the new
           segment straddles the current node.


           Split the current node's segment using the split method to create two new segments.
           For each split segment, create a new node and decide whether it should be inserted
           into the left or right subtree based on its spatial relationship.*/


            // Handle cases where the segment straddles the current node
            Segment[] splitSegments = node.segment.split(segment);//[0]
            node.left = insert(node.left, splitSegments[0]);
            node.right = insert(node.right, splitSegments[1]);

        }
//        System.out.println("Inserted segment: " + segment);
        return node;
    }


    //======================================   traverseFarToNear Method    ========================================//


    /**
     * Traverses the BSP tree from the farthest to the nearest segments based on a given point.
     *
     * <p>This method initiates the traversal by calling the private recursive method with the
     * root node. The traversal is performed based on the relative position of each segment to
     * the specified point (x, y). Segments are visited in a top-down order, starting from the
     * farthest ones to the nearest ones. The callback function is invoked for each visited segment.</p>
     *
     * @param x The x-coordinate of the point for traversal reference.
     * @param y The y-coordinate of the point for traversal reference.
     * @param callback The callback function to be invoked for each visited segment.
     */
    public void traverseFarToNear(double x, double y, SegmentCallback callback) {
        traverseFarToNear(root, x, y, callback);
    }


    /**
     * Recursive helper method for traversing the BSP tree from far to near.
     *
     * <p>This method recursively traverses the tree based on the relative position of each
     * segment to the specified point (x, y). Segments on the right side of the current node
     * are traversed first, followed by the current node's segment, and then the segments on
     * the left side. The callback function is invoked for each visited segment.</p>
     *
     * @param node The current node being processed during the traversal.
     * @param x The x-coordinate of the point for traversal reference.
     * @param y The y-coordinate of the point for traversal reference.
     * @param callback The callback function to be invoked for each visited segment.
     */
    private void traverseFarToNear(Node node, double x, double y, SegmentCallback callback) {
        // Base case: If the current node is null (leaf or empty subtree), return.
        if (node == null) {
            return;
        }


        // Determine on which side of the current tree node's segment the point (x, y) lies.
        int side = node.segment.whichSidePoint(x, y);


        // Recursively traverse the right subtree for points on the right side.
        if (side == 1) {
            traverseFarToNear(node.right, x, y, callback);
            // Invoke the callback for the current segment.
            callback.callback(node.segment);
            // Recursively traverse the left subtree for points on the left side.
            traverseFarToNear(node.left, x, y, callback);
        } else {
            // Recursively traverse the left subtree for points on the left side.
            traverseFarToNear(node.left, x, y, callback);
            // Invoke the callback for the current segment.
            callback.callback(node.segment);
            // Recursively traverse the right subtree for points on the right side.
            traverseFarToNear(node.right, x, y, callback);
        }
    }


    //===========================================   Collision Method    ============================================//


    /**
     * Detects collision between the given query segment and the segments in the BSP tree.
     *
     * <p>This method initiates the collision detection process by calling the private
     * recursive method with the root node of the BSP tree. The collision detection is based
     * on the relative position of the query segment to the segments in the tree. If a collision
     * is detected, the collided segment is returned; otherwise, null is returned.</p>
     *
     * @param query The segment for which collision detection is performed.
     * @return The collided segment if a collision is detected, or null otherwise.
     */
    public Segment collision(Segment query) {
        return collision(root, query);
    }


    /**
     * Recursive helper method for collision detection in the BSP tree.
     *
     * <p>This method recursively traverses the BSP tree based on the relative position of the
     * query segment to the segments in the tree. If a collision is detected, the collided
     * segment is returned; otherwise, the method continues to search for a collision in
     * the appropriate subtree (left or right).</p>
     *
     * @param node The current node being processed during collision detection.
     * @param query The segment for which collision detection is performed.
     * @return The collided segment if a collision is detected, or null otherwise.
     */
    private Segment collision(Node node, Segment query) {
       /*If the current node is null, it means we've reached a leaf node or an empty subtree.
        In this case, we return null since there's no collision to be found.*/
        if (node == null) {
            return null;
        }


        //Determine on which side of the current tree node's segment the query segment lies (left, right, or intersects).
       /*The whichSide method is used for this purpose, and it returns:
           1 if the query is on the right side.
           -1 if the query is on the left side.
           0 if the query intersects with the current segment.*/
        int side = query.whichSide(node.segment);


       /*If the side is 0 (meaning the query intersects the current segment),
       check if there is an actual intersection using the intersects method.
       If there is an intersection, print a message indicating a collision and return
       the current segment.*/
        if (side == 0 && query.intersects(node.segment)) {
//            System.out.println("Collision detected with segment: " + node.segment);
            return node.segment;
        }


       /*Based on the side information, recursively call the collision method on the
       appropriate subtree (left or right) to continue searching for a collision.*/
        Segment collisionSegment;


       /*If a collision is found in the left or right subtree, return the collided segment.
       Otherwise, return null if no collision is detected in either subtree.*/
        if (side == -1) {
            collisionSegment = collision(node.left, query);
        } else {
            collisionSegment = collision(node.right, query);
        }


        if (collisionSegment != null) {
            return collisionSegment;
        }


        if (side == 1) {
            collisionSegment = collision(node.left, query);
        } else {
            collisionSegment = collision(node.right, query);
        }


        return collisionSegment;
    }


    //=============================================   Node Class   ================================================//


    /**
     * Represents a node in the Binary Space Partitioning (BSP) tree.
     *
     * <p>Each node holds a segment and references to its left and right child nodes.</p>
     */
    private static class Node {
        Segment segment;
        Node left;
        Node right;


        /**
         * Constructs a new node with the given segment.
         *
         * @param segment The segment associated with this node.
         */
            Node(Segment segment) {
            this.segment = segment;
            this.left = null;
            this.right = null;
        }
    }
    //================================================   END    ===================================================//
}

