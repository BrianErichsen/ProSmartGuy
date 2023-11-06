import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
private int[] arr1, arr2, arr3, arr4, arr5;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        arr1 = new int[0];
        arr2 = new int[] { 3, 3, 3 };
        arr3 = new int[] { 52, 4, -8, 0, -17 };
        arr4= new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        arr5 = new int[] {1, 3, 5, 7, 9};
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        arr1 = null;
        arr2 = null;
        arr3 = null;
    }

    @org.junit.jupiter.api.Test
    void findSmallestDiff() {
    }
    //-------------the 3 tests
    @Test
    public void emptyArray() {
        assertEquals(-1, Main.findSmallestDiff(arr1));
    }
    @Test
    public void allArrayElementsEqual() {
        assertEquals(0, Main.findSmallestDiff(arr2));
    }
    @Test
    public void smallRandomArrayElements() {
        assertEquals(4, Main.findSmallestDiff(arr3));
    }
    @Test void test4() {
        assertEquals(-1, Main.findSmallestDiff(arr4));
    }
    @Test void test5() {
        assertEquals(-2, Main.findSmallestDiff(arr5));
    }

}