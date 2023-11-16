package assignment04;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
/**
 * Author: Brian Erichsen Fagundes
 * The {@code SortUtil} class provides static methods for performing various sorting algorithms
 * on ArrayLists, generating best-case, average-case, and worst-case inputs, and implementing
 * insertion sort for small subarrays.


 * The class includes implementations of merge sort, quicksort, and insertion sort.
 * It also provides methods to generate ArrayLists representing different input scenarios for testing
 * the performance of sorting algorithms.


 * The sorting methods support a generic type {@code <T>} with a comparator for flexible sorting
 * based on different criteria. Additionally, the class includes private helper methods for
 * partitioning, merging, swapping elements, and performing insertion sort.


 * To use the sorting methods, create an instance of {@code SortUtil}, or call the methods directly
 * on the class using the class name. The sorting methods modify the input ArrayList in-place.


 * Usage Examples:
 * ArrayList<Integer> myList = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5));
 * SortUtil.quicksort(myList, Comparator.naturalOrder());
 * System.out.println(myList); // Output: [1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]
 *
 */
public class SortUtil {

    // Public method for mergesort
    public static <T> void mergesort(ArrayList<T> list, Comparator<? super T> comparator) {
        // Get the size of the input list
        int size = list.size();

        // Base case: if the size is less than 2, the list is already sorted
        if (size < 2) {
            return;
        }

        // Find the middle index of the list
        int mid = size / 2;

        // Create two new ArrayLists representing the left and right halves of the input list
        ArrayList<T> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<T> right = new ArrayList<>(list.subList(mid, size));


        // Recursively call mergesort on the left and right halves
        mergesort(left, comparator);
        mergesort(right, comparator);

        // Merge the sorted left and right halves back into the original list
        merge(list, left, right, comparator);
    }
    public static <T> void mergesortWithThreshold(ArrayList<T> list, int threshold, Comparator<? super T> comparator) {
        //if the size of the list is below or equal the threshold, then switch to insertion sort
        if (list.size() <= threshold) {
            insertionSort(list, comparator);
        } else {
            //otherwise performs regular mergesort
            mergesort(list, comparator);
        }
    }
    /**
     * Merges two sorted subarrays into a single sorted ArrayList.
     * <p>
     * This method combines the elements of the left and right subarrays into the original ArrayList,
     * maintaining the sorted order based on the provided comparator. It is a crucial step in the
     * mergesort algorithm.
     * </p>
     *
     * @param <T>        the type of elements in the ArrayList
     * @param list       the ArrayList to be merged into
     * @param left       the left sorted subarray to be merged
     * @param right      the right sorted subarray to be merged
     * @param comparator the comparator to determine the order of the elements
     */


    // Private method for merging two sorted lists
    private static <T> void merge(ArrayList<T> list, ArrayList<T> left, ArrayList<T> right, Comparator<? super T> comparator) {
        // Initialize indices for the left, right, and original lists
        int i = 0, j = 0, k = 0;

        // Iterate through both left and right lists until one of them is exhausted
        while (i < left.size() && j < right.size()) {
            // Compare elements at the current indices of the left and right lists
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                // If the element in the left list is smaller or equal, update the original list
                list.set(k++, left.get(i++));
            } else {
                // If the element in the right list is smaller, update the original list
                list.set(k++, right.get(j++));
            }
        }
        // Copy any remaining elements from the left list to the original list
        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }

        // Copy any remaining elements from the right list to the original list
        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
    }
    /**
     * Sorts the specified ArrayList using the quicksort algorithm.
     * <p>
     * This method applies the quicksort algorithm to the entire input ArrayList. It utilizes a helper method
     * for recursively sorting subarrays. The order of elements is determined by the provided comparator.
     * </p>
     * <p>
     * The quicksort algorithm is an efficient sorting algorithm with an average time complexity of O(n log n).
     * It divides the ArrayList into smaller subarrays, recursively sorts them, and then combines them to achieve
     * the final sorted result.
     * </p>
     *
     * @param <T>        the type of elements in the ArrayList
     * @param list       the ArrayList to be sorted
     * @param comparator the comparator to determine the order of the elements
     */

    // Public method for quicksort
    public static <T> void quicksort(ArrayList<T> list, Comparator<? super T> comparator) {
        // Call the helper method to perform quicksort on the entire list
        quicksortHelperMethod(list, 0, list.size() - 1, comparator);
    }
    /**
     * Helper method for performing quicksort on the specified range of elements in the ArrayList.
     * <p>
     * This method recursively applies the quicksort algorithm to the subarray defined by the given
     * range of indices. It continues the sorting process until the base case is reached, where the
     * range has only one or zero elements.
     * </p>
     *
     * @param <T>        the type of elements in the ArrayList
     * @param list       the ArrayList to be sorted
     * @param low        the starting index of the range to be sorted
     * @param high       the ending index of the range to be sorted
     * @param comparator the comparator to determine the order of the elements
     */
    // Private helper method for quicksort
    private static <T> void quicksortHelperMethod(ArrayList<T> list, int low, int high, Comparator<? super T> comparator) {
        // Check if there are elements to be sorted
        if (low < high) {
            // Find the partition index
            int partitionIndex = partition(list, low, high, comparator);

            // Recursively call quicksort on the sublists before and after the partition
            quicksortHelperMethod(list, low, partitionIndex - 1, comparator);
            quicksortHelperMethod(list, partitionIndex + 1, high, comparator);
        }
    }
    /**
     * Partitions the specified range of elements in the ArrayList using the middle element as the pivot.
     * <p>
     * This method is a crucial step in the quicksort algorithm. It chooses the middle element in the given
     * range as the pivot, rearranges the elements in the range so that elements smaller than or equal to
     * the pivot are on the left, and elements greater than the pivot are on the right. The final position
     * of the pivot is determined, and its index is returned.
     * </p>
     *
     * @param <T>        the type of elements in the ArrayList
     * @param list       the ArrayList to be partitioned
     * @param low        the starting index of the range to be partitioned
     * @param high       the ending index of the range to be partitioned
     * @param comparator the comparator to determine the order of the elements
     * @return the index where the pivot element is now located after partitioning
     */
    // Private method to partition the list and return the pivot index
    private static <T> int partition(ArrayList<T> list, int low, int high, Comparator<? super T> comparator) {
        // Choose the last element as the pivot
//        T pivot = list.get(high);
//        T pivot =     list.get(low);
//        for getting pivot at the middle
        int middle = (high + low) / 2;
        T pivot = list.get(middle);
        swap(list, middle, high);
        // Initialize the index of the smaller element
        int i = low - 1;

        // Traverse the elements of the list
        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (comparator.compare(list.get(j), pivot) <= 0) {
                // Swap the elements and move the smaller element index
                i++;
                swap(list, i, j);
//                i++;
            }
        }

        // Swap the pivot element with the element at the (i + 1) position
        swap(list, i + 1, high);

        // Return the index where the pivot element is now located
        return i + 1;
    }
    /**
     * Swaps elements at the specified positions in the given ArrayList.
     * <p>
     * This method exchanges the elements at indices 'i' and 'j' in the provided ArrayList.
     * It is a private utility method used internally for various sorting algorithms.
     * </p>
     *
     * @param <T>  the type of elements in the ArrayList
     * @param list the ArrayList in which elements are to be swapped
     * @param i    the index of the first element to be swapped
     * @param j    the index of the second element to be swapped
     */
    // Private method to swap elements in the list
    private static <T> void swap(ArrayList<T> list, int i, int j) {
        // Swap elements at positions i and j in the list
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    /**
     * Generates an ArrayList representing the best-case input for sorting algorithms.
     * <p>
     * This method creates a sorted ArrayList with elements in ascending order from 1 to 'size'.
     * The generated list is intended for testing the performance of sorting algorithms in scenarios
     * where input elements are already in the desired sorted order.
     * </p>
     *
     * @param size the number of elements in the generated ArrayList
     * @return a sorted ArrayList with elements from 1 to 'size'
     */
    // Public method for generating best-case input which has objects in sorted order
    public static ArrayList<Integer> generateBestCase(int size) {
        // Create a sorted list from 1 to size
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        return list;
    }
    /**
     * Generates an ArrayList representing an average-case input for sorting algorithms.
     * <p>
     * This method creates a sorted ArrayList with elements in ascending order from 1 to 'size'.
     * It then shuffles the list to introduce randomness and create a permuted input.
     * The generated list is suitable for testing the performance of sorting algorithms in scenarios
     * where input elements are partially ordered or in a random order.
     * </p>
     *
     * @param size the number of elements in the generated ArrayList
     * @return a shuffled ArrayList with elements from 1 to 'size'
     */
    // Public method for generating average-case input which returns permuted order
    public static ArrayList<Integer> generateAverageCase(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }

        // Shuffling the sorted list to create a permuted list
        Collections.shuffle(list);

        return list;
    }
    /**
     * Generates an ArrayList representing the worst-case input for sorting algorithms.
     * <p>
     * This method creates a reverse-sorted ArrayList with elements in descending order from 'size' to 1.
     * The generated list is intended for testing the performance of sorting algorithms in scenarios
     * where input elements are in the opposite order of the desired sorting order.
     * </p>
     *
     * @param size the number of elements in the generated ArrayList
     * @return a reverse-sorted ArrayList with elements from 'size' to 1
     */
    // Public method for generating worst-case input
    public static ArrayList<Integer> generateWorstCase(int size) {
        // Create a reverse-sorted list from size to 1
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = size; i >= 1; i--) {
            list.add(i);
        }
        //return list in reverse order for worse case
        return list;
    }
    /**
     * Sorts the specified ArrayList using the insertion sort algorithm.
     * <p>
     * The insertion sort algorithm iterates through the input ArrayList, considering one element at a time
     * and repeatedly moving elements greater than the current element to the right, until finding the correct
     * position for the current element in the sorted part of the list.
     * </p>
     * <p>
     * This method operates in-place, modifying the input ArrayList directly. The sorting is performed based on
     * the provided comparator, allowing for customization of the sorting criteria.
     * </p>
     * <p>
     * The time complexity of the insertion sort algorithm is O(n^2) in the worst case, where 'n' is the number
     * of elements in the ArrayList. However, it performs well for small arrays or partially sorted arrays.
     * </p>
     *
     * @param <T>        the type of elements in the ArrayList
     * @param list       the ArrayList to be sorted
     * @param comparator the comparator to determine the order of the elements
     */
    // Private method for insertion sort
    static <T> void insertionSort(ArrayList<T> list, Comparator<? super T> comparator) {
        // Iterate through the list starting from the second element
        for (int i = 1; i < list.size(); i++) {
            // Store the current element to be inserted
            T key = list.get(i);

            // Initialize an index for the previous element
            int j = i - 1;

            // Move elements greater than the key to the right
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            // Insert the key at the correct position in the sorted part of the list
            list.set(j + 1, key);
        }
    }
}//end of class bracket

