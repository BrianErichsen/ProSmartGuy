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


    public Graph(int height, int width) {
        this.adjacencyList = new HashMap<>();
        this.height = height; // Set the height
        this.width = width;   // Set the width


    }

    public void addNode(Node node) {
        adjacencyList.put(node, new ArrayList<>());


        if (node.getValue() == 'S') {
            startNode = node;
        } else if (node.getValue() == 'G') {
            goalNode = node;
        } else if (node.getValue() == 'X') {
            wall = node;
        }
    }

    public void addEdge(Node source, Node destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }


    public boolean containsNode(int row, int col) {
        return adjacencyList.keySet().stream()
                .anyMatch(node -> node.getRow() == row && node.getCol() == col);
    }




    public Node getNode(int row, int col) {
        return adjacencyList.keySet().stream()
                .filter(node -> node.getRow() == row && node.getCol() == col)
                .findFirst()
                .orElse(null);
    }


    public List<Node> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }


    public List<Node> getNeighbors(Node node) {
        return adjacencyList.get(node);
    }


    public Node getStartNode() {
        return startNode;
    }


    public Node getGoalNode() {
        return goalNode;
    }


    public void markShortestPath(Node startNode, Node goalNode) {
        Node current = goalNode;
        while (current != null && current != startNode) {
            if (current != wall && current.getValue() != 'X' && current.getValue() != 'G') {
                current.setValue('.');
            }
            current = current.getPrevious();
        }
    }


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
                    Node current = getNode(row, col);
                    char cell = current.getValue();


                    // Print path ('.') on top of the original maze
                    if (cell == '.' || cell == 'X') {
                        System.out.print(cell);
                    } else {
                        System.out.print(cell);
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

