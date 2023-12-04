package assignment08;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(5, 5);
    }
    @AfterEach
    void tearDown() {
        graph = null;
    }

    @org.junit.jupiter.api.Test
    void addNode() {
        Node node = new Node (1, 2, 'S');
        graph.addNode(node);

        assertTrue(graph.containsNode(1, 2));
        assertEquals(node, graph.getNode(1, 2));
        assertEquals(node, graph.getStartNode());
    }

    @org.junit.jupiter.api.Test
    void addEdge() {
        Node node1 = new Node(1, 2, 'S');
        Node node2 = new Node(1, 3, ' ');

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);

        assertTrue(graph.getNeighbors(node1).contains(node2));
        assertTrue(graph.getNeighbors(node2).contains(node1));
    }

    @org.junit.jupiter.api.Test
    void containsNode() {
        Node node = new Node(1, 2, 'S');
        graph.addNode(node);

        assertTrue(graph.containsNode(1, 2));
        assertFalse(graph.containsNode(3, 4));
    }

    @org.junit.jupiter.api.Test
    void getNode() {
        Node node = new Node(1, 2, 'S');
        graph.addNode(node);

        assertEquals(node, graph.getNode(1, 2));
        assertNull(graph.getNode(3, 4));
    }

    @org.junit.jupiter.api.Test
    void getNodes() {
        Node node1 = new Node(1, 2, 'S');
        Node node2 = new Node(3, 4, ' ');
        graph.addNode(node1);
        graph.addNode(node2);

        List<Node> nodes = graph.getNodes();
        assertEquals(2, nodes.size());
        assertTrue(nodes.contains(node1));
        assertTrue(nodes.contains(node2));
    }

    @org.junit.jupiter.api.Test
    void getNeighbors() {
        Node node1 = new Node(1, 2, 'S');
        Node node2 = new Node(3, 4, ' ');
        Node node3 = new Node( 2, 2, 'G');

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);

        List<Node> neighbors = graph.getNeighbors(node1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(node2));
        assertTrue(neighbors.contains(node3));
    }

    @org.junit.jupiter.api.Test
    void getStartNode() {
        Node startNode = new Node(1, 2, 'S');
        graph.addNode(startNode);

        assertEquals(startNode, graph.getStartNode());
    }

    @org.junit.jupiter.api.Test
    void getGoalNode() {
        Node goalNode = new Node(2, 3, 'G');
        graph.addNode(goalNode);

        assertEquals(goalNode, graph.getGoalNode());
    }

    @org.junit.jupiter.api.Test
    void markShortestPath() {
        Node startNode = new Node(1, 2, 'S');
        Node goalNode = new Node(3, 4, 'G');
        graph.addNode(startNode);
        graph.addNode(goalNode);
        graph.markShortestPath(startNode, goalNode);
        assertEquals('S', graph.getNode(1, 2).getValue()); // Start node
        assertEquals('G', graph.getNode(3, 4).getValue()); // Goal node
    }
}//end of class bracket