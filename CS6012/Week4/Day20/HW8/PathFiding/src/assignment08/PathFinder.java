package assignment08;
//by Brian Erichsen Fagundes

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;


public class PathFinder {


    public static void solveMaze(String inputFile, String outputFile) {
        try (BufferedReader input = new BufferedReader(new FileReader(inputFile));
             PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {


            String[] dimensions = input.readLine().split(" ");
            int height = Integer.parseInt(dimensions[0]);
            System.out.println(height);
            int width = Integer.parseInt(dimensions[1]);
            System.out.println(width);


            Graph graph = new Graph(height, width);


            System.out.println("Original Maze:");
            printOriginalMaze(inputFile);


            for (int row = 0; row < height; row++) {
                String line = input.readLine();
                for (int col = 0; col < width; col++) {
                    char cell = line.charAt(col);
                    if (cell == 'S' || cell == 'G' || cell == ' ') {
                        graph.addNode(new Node(row, col, cell));
                    }
                }
            }




            for (Node node : graph.getNodes()) {
                int row = node.getRow();
                int col = node.getCol();


                if (row - 1 >= 0 && graph.containsNode(row - 1, col)) {
                    graph.addEdge(node, graph.getNode(row - 1, col)); // Up
                }
                if (col + 1 < width && graph.containsNode(row, col + 1)) {
                    graph.addEdge(node, graph.getNode(row, col + 1)); // Right
                }
                if (row + 1 < height && graph.containsNode(row + 1, col)) {
                    graph.addEdge(node, graph.getNode(row + 1, col)); // Down
                }
                if (col - 1 >= 0 && graph.containsNode(row, col - 1)) {
                    graph.addEdge(node, graph.getNode(row, col - 1)); // Left
                }
            }


            Node startNode = graph.getStartNode();
            Node goalNode = graph.getGoalNode();
            Queue<Node> queue = new LinkedList<>();
            queue.add(startNode);


            boolean goalReached = false;  // Add a flag to check if the goal is reached


            // Inside the try block
            while (!queue.isEmpty()) {
                Node current = queue.poll();
                if (current.equals(goalNode)) {
                    graph.markShortestPath(startNode, goalNode);
                    goalReached = true;
                    break;
                }


                for (Node neighbor : graph.getNeighbors(current)) {
                    if (!neighbor.isVisited()) {
                        neighbor.setVisited(true);
                        neighbor.setPrevious(current);
                        queue.add(neighbor);
                    }
                }
            }


            // After the BFS loop
            if (!goalNode.isVisited()) {
                System.out.println("Goal is unreachable. Printing original maze.");
            }


            System.out.println("Solved Maze: ");
            graph.markShortestPath(startNode, goalNode);
            graph.printPath(inputFile);






            output.println(height + " " + width);
            for (int row = 0; row < height; row++) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < width; col++) {
                    Node current = graph.getNode(row, col);
                    char cell = current.getValue();
                    line.append(cell);
                }
                output.println(line.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


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
    public static void main(String[] args) {
        PathFinder.solveMaze("unsolvable.txt", "output.txt");
    }
}//end of class bracket

