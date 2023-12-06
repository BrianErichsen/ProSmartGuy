package assignment09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BSPTreeTest {
    private BSPTree tree;
    @BeforeEach
    void setUp() {
        ArrayList<Segment> segments = new ArrayList<>();
        tree = new BSPTree(segments);

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        tree = null;
    }
    @Test
    void testConstructor() {
        BSPTree defaultTree = new BSPTree();
        assertNotNull(defaultTree);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        Segment segment = new Segment(1.0, 1.1, 2.2, 2.2);
        tree.insert(segment);
    }

    @org.junit.jupiter.api.Test
    void transverseFarToNear() {
    }

    @org.junit.jupiter.api.Test
    void collision() {
    }
}