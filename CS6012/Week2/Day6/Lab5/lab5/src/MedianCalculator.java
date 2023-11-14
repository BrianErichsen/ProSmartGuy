import java.util.Arrays;
import java.util.Comparator;

public class MedianCalculator {

    public static <T extends Comparable<T>> T findMedian(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        Arrays.sort(array);

        int middle = array.length / 2;

        if (array.length % 2 == 0) {
            // If the array length is even, return either element in the "middle"
            return array[middle - 1];
        } else {
            // If the array length is odd, return the middle element
            return array[middle];
        }
    }
    public static <T> T findMedian(T[] array, Comparator<? super T> cmp) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must have an item");
        }
        Arrays.sort(array, cmp);
        int middle = array.length / 2;

        if (array.length % 2 == 0) {
            //the return can be either middle + 1 or middle - 1; either is fine for even number
            return array[middle -1];
        } else {
            return array[middle];
        }
    }

    public static void main(String[] args) {
        // Example usage with Integer array
        Integer[] intArray = {5, 2, 9, 1, 7, 6};
        System.out.println("Median: " + findMedian(intArray));

        // Example usage with Double array
        Double[] doubleArray = {3.5, 1.2, 6.7, 2.8, 9.1};
        System.out.println("Median: " + findMedian(doubleArray));
    }
}