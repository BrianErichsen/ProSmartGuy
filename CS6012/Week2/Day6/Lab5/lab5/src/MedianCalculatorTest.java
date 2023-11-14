import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

class MedianCalculatorTest {
    private Integer[] intArray;
    private Double[] doubleArray;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        intArray = new Integer[]{5, 2, 9, 1, 7, 6};
        doubleArray = new Double[]{3.5, 1.2, 6.7, 2.8, 9.1};
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void findMedianComparable() {
        //after sorting intArray; it should be 5 the median
        assertEquals(Integer.valueOf(5), MedianCalculator.findMedian(intArray));
        assertEquals(3.5, MedianCalculator.findMedian(doubleArray));
    }
    @Test
    void findMedianComparator() {
        //9, 7, 6, 5, 2, 1 -- > leading to 6 to be the median
        Comparator<Integer> intComparator = Comparator.reverseOrder();//reverse natural order
        assertEquals(Integer.valueOf(6), MedianCalculator.findMedian(intArray, intComparator));
    }
}