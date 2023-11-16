package assignment04;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;

public class SortUtilTest {
    private ArrayList<Integer> originalList;
    private Comparator<Integer> integerComparator;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        // Common setup for the tests
        originalList = new ArrayList<>();
        integerComparator = Comparator.naturalOrder();
    }
    @org.junit.jupiter.api.AfterEach
    public void tearDown() {
        // Clean up after each test if necessary
        originalList = null;
        integerComparator = null;
    }
    @Test
    public void testMergeSort() {
        originalList = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5));
        ArrayList<Integer> sortedList = new ArrayList<>(originalList);
        sortedList.sort(integerComparator);

        SortUtil.mergesort(originalList, integerComparator);

        assertEquals(sortedList, originalList);
    }
    @Test
    public void testQuickSort() {
        originalList = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5));
        ArrayList<Integer> sortedList = new ArrayList<>(originalList);
        sortedList.sort(integerComparator);

        SortUtil.quicksort(originalList, integerComparator);

        assertEquals(sortedList, originalList);
    }
    @Test
    public void testInsertionSort() {
        originalList = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5));
        ArrayList<Integer> sortedList = new ArrayList<>(originalList);
        sortedList.sort(integerComparator);

        // Assuming you add an insertionSort method to SortUtil
        SortUtil.insertionSort(originalList, integerComparator);

        assertEquals(sortedList, originalList);
    }
    @Test
    public void testGenerateBestCase() {
        int size = 10;
        ArrayList<Integer> bestCase = SortUtil.generateBestCase(size);

        for (int i = 0; i < size - 1; i++) {
            assertTrue(bestCase.get(i) <= bestCase.get(i + 1));
        }
    }
    @Test
    public void testGenerateAverageCase() {
        int size = 10;
        ArrayList<Integer> averageCase = SortUtil.generateAverageCase(size);

        // Check if the list is not in sorted order
        boolean isNotSorted = false;
        for (int i = 0; i < size - 1; i++) {
            if (averageCase.get(i) > averageCase.get(i + 1)) {
                isNotSorted = true;
                break;
            }
        }
        assertTrue(isNotSorted);
    }
    @Test
    public void testGenerateWorstCase() {
        int size = 10;
        ArrayList<Integer> worstCase = SortUtil.generateWorstCase(size);

        for (int i = 0; i < size - 1; i++) {
            assertTrue(worstCase.get(i) >= worstCase.get(i + 1));
        }
    }
}//end of class bracket
