package assignment08;
//Author: Brian Erichsen Fagundes

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;


public class PathFinder {


    /**
     * Solves a maze provided in an input file and writes the solution to an output file.
     * The maze is represented as a graph, and the solution is found using Breadth-First Search (BFS).
     *
     * @param inputFile  The path to the input file containing the maze.
     * @param outputFile The path to the output file where the solved maze will be written.
     */
    public static void solveMaze(String inputFile, String outputFile) {
        //Use printWriter and BufferReader to write and read the output and input files.
        try (BufferedReader input = new BufferedReader(new FileReader(inputFile));
             PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {


            // Read dimensions of the maze
            String[] dimensions = input.readLine().split(" ");
            //The first one is the number of rows/height of the maze...
            int height = Integer.parseInt(dimensions[0]);
            //The first one is the number of columns/width of the maze...
            int width = Integer.parseInt(dimensions[1]);
            // Print the original maze
            System.out.println("Original Maze:");
            printOriginalMaze(inputFile);

            // Create a graph to represent the maze
            Graph graph = new Graph(height, width);
            // Populate the graph with nodes based on the maze cells

            // Iterate over each row in the maze
            for (int row = 0; row < height; row++) {
                // Read the current row from the input file
                String line = input.readLine();
                // Iterate over each column in the current row
                for (int col = 0; col < width; col++) {
                    // Retrieve the character representing the cell at the current row and column
                    char cell = line.charAt(col);

                    // Check if the cell is ('S', 'G', or empty space ' ')
                    if (cell == 'S' || cell == 'G' || cell == ' ') {
                        // Add a new node to the graph with the current row, column, and cell value
                        graph.addNode(new Node(row, col, cell));
                    }
                }
            }
            // Connect nodes in the graph to represent the maze structure
            // Iterate over each node in the graph
            for (Node node : graph.getNodes()) {
                // Retrieve the row and column of the current node
                int row = node.getRow();
                int col = node.getCol();
                //x-rows y-col
                // Check if there is a node above the current node and add an edge if it exists
                if (row - 1 >= 0 && graph.containsNode(row - 1, col)) {
                    graph.addEdge(node, graph.getNode(row - 1, col)); // Up
                }


                // Check if there is a node to the right of the current node and add an edge if it exists
                if (col + 1 < width && graph.containsNode(row, col + 1)) {
                    graph.addEdge(node, graph.getNode(row, col + 1)); // Right
                }


                // Check if there is a node below the current node and add an edge if it exists
                if (row + 1 < height && graph.containsNode(row + 1, col)) {
                    graph.addEdge(node, graph.getNode(row + 1, col)); // Down
                }


                // Check if there is a node to the left of the current node and add an edge if it exists
                if (col - 1 >= 0 && graph.containsNode(row, col - 1)) {
                    graph.addEdge(node, graph.getNode(row, col - 1)); // Left
                }
            }

            // Retrieve start and goal nodes
            Node startNode = graph.getStartNode();
            Node goalNode = graph.getGoalNode();

            // Initialize a queue for BFS traversal
            Queue<Node> queue = new LinkedList<>();
            //starts the transversal from the beginning
            queue.add(startNode);

            // Add a flag to check if the goal is reached
            boolean goalReached = false;
            //process nodes in breadth-first order
            // Inside the try block: Perform BFS traversal to find the shortest path
            while (!queue.isEmpty()) {
                // Dequeue the current node from the front of the queue
                Node current = queue.poll();

                // Check if the current node is the goal node
                if (current.equals(goalNode)) {
                    // Mark the shortest path in the graph
                    graph.markShortestPath(startNode, goalNode);

                    // Set the flag to indicate that the goal is reached
                    goalReached = true;

                    // Exit the loop since the goal has been reached
                    break;
                }

                // Explore neighbors of the current node and enqueue them if not visited
                for (Node neighbor : graph.getNeighbors(current)) {
                    // Check if the neighbor has not been visited
                    if (!neighbor.isVisited()) {
                        // Mark the neighbor as visited, set its previous node, and enqueue it
                        neighbor.setVisited(true);
                        neighbor.setPrevious(current);
                        queue.add(neighbor);
                    }
                }
            }


            // After the BFS loop: Check if the goal is unreachable
            if (!goalReached) {
                System.out.println("Goal is unreachable. Printing original maze.");
            }


            // Print the solved maze
            System.out.println("Solved Maze: ");
            graph.markShortestPath(startNode, goalNode);
            graph.printPath(inputFile);


            // Write the solved maze to the output file
            output.println(height + " " + width);
            // Iterate over each row in the maze
            for (int row = 0; row < height; row++) {
                // Create a StringBuilder to build the line for the current row
                StringBuilder line = new StringBuilder();
                // Iterate over each column in the current row
                for (int col = 0; col < width; col++) {
                    // Retrieve the node at the current row and column
                    Node current = graph.getNode(row, col);
                    // Get the value (character) of the cell in the current node
                    char cell = current.getValue();
                    // Append the cell value to the line
                    line.append(cell);
                }
                // Write the line to the output file
                output.println(line.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //========================================   Print Solved Maze Function ==============================================//


    /**
     * Prints the original maze pattern from the specified file.
     *
     * @param fileName The path to the file containing the original maze.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private static void printOriginalMaze(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Read the first line containing the number of rows and columns
            String[] dimensions = br.readLine().split(" ");
            int numRows = Integer.parseInt(dimensions[0]);
            int numCols = Integer.parseInt(dimensions[1]);


            // Print the numbers
            System.out.println(numRows + " " + numCols);


            // Read and print the pattern
            for (int i = 0; i < numRows; i++) {
                String line = br.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //==============================================   Main Method ======================================================//


    public static void main(String[] args) {
        PathFinder.solveMaze("unsolvable.txt", "output.txt");
    }//end of main method bracket
}//end of class bracket