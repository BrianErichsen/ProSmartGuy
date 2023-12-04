package assignment08;


import java.util.Objects;


public class Node {
    private final int row;
    private final int col;
    private char value;
    private boolean visited;
    private Node previous;


    public Node(int row, int col, char value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.visited = false;
        this.previous = null;
    }


    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }


    public char getValue() {
        return value;
    }


    public void setValue(char value) {
        this.value = value;
    }


    public boolean isVisited() {
        return visited;
    }


    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    public Node getPrevious() {
        return previous;
    }


    public void setPrevious(Node previous) {
        this.previous = previous;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return row == node.row && col == node.col && value == node.value;
    }


    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

