package assignment09;


import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.assertNull;


public class BSPTreeTest {


    // Entry point of the program
    public static void main(String[] args) {
        // Use a fixed seed for reproducibility
        Random random = new Random(42);


        // Create a list to store horizontal segments with different y values
        ArrayList<Segment> segments = new ArrayList<>();


        // Generate 5 random horizontal segments with different y values
        for (int i = 0; i < 5; i++) {
            double y = random.nextDouble(); // Generate a random y value
            Segment segment = new Segment(0.1, y, 0.8, y); // Create a horizontal segment
            segments.add(segment); // Add the segment to the list
        }


        // Create a BSP tree using the list of segments
        BSPTree bspTree = new BSPTree(segments);


        // Test the traverseFarToNear method
        System.out.println("Traversing segments far to near:");
        bspTree.traverseFarToNear(0.5, 0, new SegmentCallback() {
            @Override
            public void callback(Segment segment) {
                System.out.println(segment); // Print each segment during traversal
            }
        });


        // Test the collision method with a query segment
        Segment querySegment = new Segment(0.3, 0.3, 0.6, 0.3);
        System.out.println("\nCollision test with query segment: " + querySegment);


        // Detect collision and print the result
        Segment collisionSegment = bspTree.collision(querySegment);
        if (collisionSegment != null) {
            System.out.println("Collision detected with segment: " + collisionSegment);
        } else {
            System.out.println("No collision/ No splits detected.");
        }
    }


    @Test
    public void testNoCollision() {
        // Test collision detection with non-intersecting segments
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(0.0, 0.0, 1.0, 1.0));
        segments.add(new Segment(2.0, 2.0, 3.0, 3.0));


        BSPTree bspTree = new BSPTree(segments);


        // Test collision with a query segment
        Segment querySegment = new Segment(1.0, 0.0, 2.0, 1.0);
        Segment collisionSegment = bspTree.collision(querySegment);


        // Check if null is returned when there is no collision
        assertNull(collisionSegment);
    }




    @Test
    public void testTraverseFarToNear() {
        BSPTree bspTree = new BSPTree();


        // Create a list of segments to insert
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(0.0, 0.0, 1.0, 1.0));
        segments.add(new Segment(2.0, 2.0, 3.0, 3.0));


        // Insert the segments into the tree
        bspTree = new BSPTree(segments);


        // Define a callback for testing
        SegmentCallback callback = new SegmentCallback() {
            @Override
            public void callback(Segment segment) {
                // You can add custom callback logic for testing
                System.out.println("Visited segment: " + segment);
            }
        };


        // Perform traversal
        bspTree.traverseFarToNear(1.5, 1.5, callback);
    }


    @Test
    public void testCollision() {
        BSPTree bspTree = new BSPTree();


        // Create a list of segments to insert
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(0.0, 0.0, 1.0, 1.0));
        segments.add(new Segment(2.0, 2.0, 3.0, 3.0));


        // Insert the segments into the tree
        bspTree = new BSPTree(segments);


        // Test collision with a query segment
        Segment querySegment = new Segment(1.0, 0.0, 2.0, 1.0);
        Segment collisionSegment = bspTree.collision(querySegment);


        // Check if null is returned when there is no collision
        assertNull(collisionSegment);
    }


}
