package assignment08;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    private Node node;
    @BeforeEach
    void setUp() {
        node = new Node(1, 2, 'S');
    }
    @AfterEach
    void tearDown() {
        node = null;
    }
    @Test
    void testGetCol() {
        assertEquals(2, node.getCol());
    }
    @Test
    void testGetValue() {
        assertEquals('S', node.getValue());
    }
    @Test
    void testSetValue() {
        node.setValue('G');
        assertEquals('G', node.getValue());
    }
    @Test
    void testSetVisited() {
        node.setVisited(true);
        assertTrue(node.isVisited());
    }
    @Test
    void testGetPrevious() {
        assertNull(node.getPrevious());
    }
    @Test
    void testEquals() {
        Node node1 = new Node(1, 2, 'S');
        Node differentNode = new Node(1, 2, 'G');
        Node anotherLocation = new Node(3, 4, 'S');

        assertTrue(node.equals(node1));
        assertFalse(node.equals(differentNode));
        assertFalse(node.equals(anotherLocation));
    }
    @Test
    void testHashCode() {
        Node node1 = new Node(1, 2, 'S');
        assertEquals(node.hashCode(), node1.hashCode());
    }
}//end of class bracket