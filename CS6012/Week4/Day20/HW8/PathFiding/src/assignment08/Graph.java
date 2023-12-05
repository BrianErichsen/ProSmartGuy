package assignment08;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {
    private final Map<Node, List<Node>> adjacencyList;
    private Node startNode;
    private Node goalNode;


    private Node wall;


    private int height;
    private int width;


    //==========================================       Constructor       ==============================================//
    /**
     * Constructs a new Graph with a specified height and width.
     *
     * @param height The height of the maze (number of rows).
     * @param width  The width of the maze (number of columns).
     */
    public Graph(int height, int width) {
        // Initialize the adjacencyList as a HashMap, where nodes are keys and neighbors are values (adjacent nodes)
        this.adjacencyList = new HashMap<>();
        // Set the height of the maze
        this.height = height;
        // Set the width of the maze
        this.width = width;
    }


    //========================================       addNode Method      ==============================================//


    /**
     * Adds a node to the graph's adjacency list.
     *
     * @param node The node to be added to the graph.
     */
    public void addNode(Node node) {
        // Add the node to the adjacency list with an empty list of neighbors
        adjacencyList.put(node, new ArrayList<>());


        // Check if the node represents the start ('S'), goal ('G'), or a wall ('X')
        if (node.getValue() == 'S') {
            // If the node is the start node, set it as the startNode reference
            startNode = node;
        } else if (node.getValue() == 'G') {
            // If the node is the goal node, set it as the goalNode reference
            goalNode = node;
        } else if (node.getValue() == 'X') {
            // If the node is a wall, set it as the wall reference
            wall = node;
        }
    }


    //========================================       addEdge Method      ==============================================//


    /**
     * Adds an undirected edge between two nodes in the graph.
     *
     * @param source      The source node of the edge.
     * @param destination The destination node of the edge.
     */
    public void addEdge(Node source, Node destination) {
        // Add the destination node to the list of neighbors for the source node
        adjacencyList.get(source).add(destination);
        // Add the source node to the list of neighbors for the destination node
        adjacencyList.get(destination).add(source);
    }


    //======================================       containsNode Method      ===========================================//


    /**
     * Checks if the graph contains a node with the specified row and column.
     *
     * @param row The row index of the node to check.
     * @param col The column index of the node to check.
     * @return True if the node is found, false otherwise.
     */
    public boolean containsNode(int row, int col) {
        // Check if any node in the key set has the specified row and column
        return adjacencyList.keySet().stream()
                .anyMatch(node -> node.getRow() == row && node.getCol() == col);
    }


    //========================================       getNode Method      ==============================================//


    /**
     * Retrieves a node from the graph based on the specified row and column.
     * If the node is not found, a new node with default values ('X') is created.
     *
     * @param row The row index of the node to retrieve.
     * @param col The column index of the node to retrieve.
     * @return The node found or a new node with default values if not found.
     */
    public Node getNode(int row, int col) {
        // Filter nodes to find the one with the specified row and column
        return adjacencyList.keySet().stream()
                .filter(node -> node.getRow() == row && node.getCol() == col)
                .findFirst()
                // If no matching node is found, create a new node with default values ('X')
                .orElse(new Node(row, col, 'X'));
    }


    //========================================       getNode Method      ==============================================//


    /**
     * Retrieves a list of all nodes in the graph.
     *
     * @return A list containing all nodes in the graph.
     */
    public List<Node> getNodes() {
        // Create a new ArrayList containing all nodes in the graph's key set
        return new ArrayList<>(adjacencyList.keySet());
    }


    //======================================       getNeighbors Method      ===========================================//


    /**
     * Retrieves a list of neighboring nodes for a given node in the graph.
     *
     * @param node The node for which neighbors are to be retrieved.
     * @return A list containing neighboring nodes of the specified node.
     */
    public List<Node> getNeighbors(Node node) {
        // Retrieve the list of neighbors for the given node from the adjacency list
        return adjacencyList.get(node);
    }


    //======================================       getStartNode Method      ===========================================//


    /**
     * Retrieves the start node of the graph.
     *
     * @return The start node of the graph.
     */
    public Node getStartNode() {
        // Return the reference to the start node
        return startNode;
    }


    //========================================       getGoalNode Method      ==========================================//


    /**
     * Retrieves the goal node of the graph.
     *
     * @return The goal node of the graph.
     */
    public Node getGoalNode() {
        // Return the reference to the goal node
        return goalNode;
    }


    //=====================================       markShortestPath Method      ========================================//


    /**
     * Marks the shortest path in the graph between the start and goal nodes.
     *
     * @param startNode The starting node of the path.
     * @param goalNode  The goal node of the path.
     */
    public void markShortestPath(Node startNode, Node goalNode) {
        // Initialize the current node with the goal node
        Node current = goalNode;


        // Traverse the path from the goal to the start node
        while (current != null && current != startNode) {
            // Check if the current node is not a wall and not the goal node
            if (current != wall && current.getValue() != 'X' && current.getValue() != 'G') {
                // Mark the current node as part of the shortest path
                current.setValue('.');
            }


            // Move to the previous node in the path
            current = current.getPrevious();
        }
    }


    //========================================       printPath Method      ============================================//


    /**
     * Prints the maze with the marked shortest path to the console.
     *
     * @param fileName The name of the input file containing the original maze.
     */
    public void printPath(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Read the first line containing the number of rows and columns
            String[] dimensions = br.readLine().split(" ");
            int numRows = Integer.parseInt(dimensions[0]);
            int numCols = Integer.parseInt(dimensions[1]);


            // Print the numbers
            System.out.println(numRows + " " + numCols);


            // Read and print the pattern
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    // Retrieve the current node in the graph
                    Node current = getNode(row, col);
                    // Retrieve the value (character) of the cell in the current node
                    char cell = current.getValue();


                    // Print the marked path ('.') on top of the original maze
                    if (cell == '.' || cell == 'X') {
                        System.out.print(cell);
                    } else {
                        // Print non-path cells as they are
                        System.out.print(cell);
                    }
                }
                // Move to the next line after printing each row
                System.out.println();
            }
        } catch (IOException e) {
            // Print stack trace in case of an exception during file reading
            e.printStackTrace();
        }
    }
}
//================================================
