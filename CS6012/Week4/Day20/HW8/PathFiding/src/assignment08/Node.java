package assignment08;


import java.util.Objects;


/**
 * Represents a node in a graph.
 */
public class Node {
    private final int row;        // Row index of the node in a grid
    private final int col;        // Column index of the node in a grid
    private char value;           // Value of the node, representing the content of a maze cell
    private boolean visited;      // Flag indicating whether the node has been visited during traversal
    private Node previous;        // Reference to the previous node in the traversal path


    /**
     * Constructor to create a Node with specified row, column, and value.
     *
     * @param row    The row index of the node.
     * @param col    The column index of the node.
     * @param value  The value of the node representing the content of a maze cell.
     */
    public Node(int row, int col, char value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }


    /**
     * Gets the row index of the node.
     *
     * @return The row index.
     */
    public int getRow() {
        return row;
    }


    /**
     * Gets the column index of the node.
     *
     * @return The column index.
     */
    public int getCol() {
        return col;
    }


    /**
     * Gets the value of the node representing the content of a maze cell.
     *
     * @return The value of the node.
     */
    public char getValue() {
        return value;
    }


    /**
     * Sets the value of the node.
     *
     * @param value The new value to be set.
     */
    public void setValue(char value) {
        this.value = value;
    }


    /**
     * Checks if the node has been visited during traversal.
     *
     * @return True if the node has been visited, false otherwise.
     */
    public boolean isVisited() {
        return visited;
    }


    /**
     * Sets the visited flag for the node.
     *
     * @param visited True to mark the node as visited, false otherwise.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    /**
     * Gets the previous node in the traversal path.
     *
     * @return The previous node.
     */
    public Node getPrevious() {
        return previous;
    }


    /**
     * Sets the previous node in the traversal path.
     *
     * @param previous The previous node to be set.
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }


    /**
     * Overrides the equals method to compare nodes based on their row, column, and value.
     *
     * @param obj The object to compare with.
     * @return True if the nodes are equal, false otherwise.
     */
    public boolean equals(Object obj) {
        // Check if the reference is to the same object
        if (this == obj) return true;


        // Check if the object is null or belongs to a different class
        if (obj == null || getClass() != obj.getClass()) return false;


        // Cast the object to a Node
        Node node = (Node) obj;


        // Compare the row, column, and value of the current node with the other node
        return row == node.row && col == node.col && value == node.value;
    }


    /**
     * Overrides the hashCode method to generate a hash code based on the row and column.
     *
     * @return The hash code of the node.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

