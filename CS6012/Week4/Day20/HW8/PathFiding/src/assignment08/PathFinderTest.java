package assignment08;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    private static String INPUT_FILE = "input.txt";
    private static String OUTUT_FILE = "output.txt";
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void solveMaze() {
        //soveable maze for testing
        createMazeFile("5 5\nXXXXX\nXS  X\nX   X\nX  GX\nXXXXX", INPUT_FILE);
        //call the solveMaze method
        PathFinder.solveMaze(INPUT_FILE, OUTUT_FILE);
        //check the output for correctness
        assertTrue(checkSolution("5 5\nXXXXX\nXS  X\nX.. X\nX .GX\nXXXXX", OUTUT_FILE));
    }
    @Test
    public void testSolveMaze_UnsolvableMaze() {
        //creates a unsolvable maze for testing
        createMazeFile("5 5\nXXXXX\nXSXXX\nXX  X\nX  GX\nXXXXX", INPUT_FILE);
        PathFinder.solveMaze(INPUT_FILE, OUTUT_FILE);
        assertTrue(checkSolution("5 5\nXXXXX\nXSXXX\nXX  X\nX  GX\nXXXXX", OUTUT_FILE));
    }
    private static void createMazeFile(String content, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean checkSolution(String expected, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder actual = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                actual.append(line).append("\n");
            }
            return expected.equals(actual.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}//end of class bracket