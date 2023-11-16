package assignment04;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;


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

    // Public method for quicksort
    public static <T> void quicksort(ArrayList<T> list, Comparator<? super T> comparator) {
        // Call the helper method to perform quicksort on the entire list
        quicksortHelperMethod(list, 0, list.size() - 1, comparator);
    }


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

    // Private method to partition the list and return the pivot index
    private static <T> int partition(ArrayList<T> list, int low, int high, Comparator<? super T> comparator) {
        // Choose the last element as the pivot
//        T pivot = list.get(high);
//        T pivot = list.get(low);
//        for getting pivot at the middle
        int middle = low + (high - low) / 2;
        T pivot = list.get(middle);

        // Initialize the index of the smaller element
        int i = low - 1;

        // Traverse the elements of the list
        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (comparator.compare(list.get(j), pivot) <= 0) {
                // Swap the elements and move the smaller element index
                i++;
                swap(list, i, j);
            }
        }

        // Swap the pivot element with the element at the (i + 1) position
        swap(list, i + 1, middle);

        // Return the index where the pivot element is now located
        return i + 1;
    }

    // Private method to swap elements in the list
    private static <T> void swap(ArrayList<T> list, int i, int j) {
        // Swap elements at positions i and j in the list
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Public method for generating best-case input which has objects in sorted order
    public static ArrayList<Integer> generateBestCase(int size) {
        // Create a sorted list from 1 to size
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        return list;
    }

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

