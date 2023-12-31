package assignment03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchSetTest {
    BinarySearchSet<Integer> integerSet = new BinarySearchSet<>();
    BinarySearchSet<Integer> set = new BinarySearchSet<>();

    BinarySearchSet<Integer> emptySet = new BinarySearchSet<>();
    @BeforeEach
    void setUp() {
        //Add elements to array
        integerSet.add(3);
        integerSet.add(5);
        integerSet.add(2);
        integerSet.add(7);
    }
    @Test
    void first() {
        //Size of the array should be 4, the fist element should be 2
        assertEquals(integerSet.size(), 4);
        assertEquals(integerSet.first(), 2);
    }
    @Test
    void last() {
        //last element should be 7
        assertEquals(integerSet.last(), 7);

    }
    @Test
    void add() {
        //UTILITY
        integerSet.add(77);
        assertEquals(integerSet.last(), 77);

        //DUPLICATE ADD
        //Cannot add duplicate values, should return false
        assertFalse(integerSet.add(77));
    }
    @Test
    void addAll() {
        // Create a new instance of MySet
        BinarySearchSet<Integer> addAllSet = new BinarySearchSet<>();

        // Create a collection of elements to add
        List<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);

        // Call the addAll method
        boolean result = addAllSet.addAll(elements);

        // Assert that the method returns true
        assertTrue(result);

        // Assert that the set contains all the elements
        assertTrue(addAllSet.containsAll(elements));
    }

    @Test
    void contains() {
        //Test contains
        assertTrue(integerSet.contains(3));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(2));
        assertFalse(integerSet.contains(6));
    }

    @Test
    void containsAll() {
        List<Integer> collection = new ArrayList<>();
        collection.add(2);
        collection.add(3);
        collection.add(5);
        collection.add(7);

        assertTrue(integerSet.containsAll(collection));

    }
    @Test
    void isEmpty() {
        //Empty set should be empty
        assertTrue(emptySet.isEmpty());
        //test isEmpty on non-empty set
        assertFalse(integerSet.isEmpty());

    }
    @Test
    void remove() {
        //remove last element
        integerSet.add(88);
        integerSet.remove(88);
        //Last element should be 7 and the size should now be 5
        assertEquals(integerSet.last(), 7);
        assertEquals(integerSet.size(), 4);
    }
    @Test
    void removeAll() {
        BinarySearchSet<Integer> set2 = new BinarySearchSet<>();

        set2.add(1);
        set2.add(3);
        set2.add(5);

        List<Integer> elementsToRemove = new ArrayList<>();
        elementsToRemove.add(3);
        elementsToRemove.add(4);

        boolean result = set2.removeAll(elementsToRemove);

        assertTrue(result);
        assertFalse(set2.contains(3));
        assertTrue(set2.contains(1));
        assertTrue(set2.contains(5));
    }
    @Test
    void toArray() {
//        //CASE: UTILITY
//        // Call the toArray() method
//        Integer[] result = integerSet.toArray();
//        // Create an expected array with the elements in sorted order
//        Integer[] expected = {2,3,5,7,};
//
//        // Assert that the result array is equal to the expected array
//        assertArrayEquals(expected, result);
    }
    @Test
    void iterator() {
        BinarySearchSet<Integer> iteratorSet = new BinarySearchSet<>();
        Integer[] integerArray = {2,3,5,7};

        //For each loop to iterate over integerSet and copy contents
        var iterator = integerSet.iterator();
        while(iterator.hasNext()){
            var x = iterator.next();
            iteratorSet.add((Integer) x);
        }
        //IteratorSet contains the same contents as IntegerSet (which is the same as integerArray)
        assertTrue(iteratorSet.containsAll(List.of(integerArray)));
    }
    @Test
    void clear() {
        //clear set
        integerSet.clear();
        //Set should be empty
        assertTrue(integerSet.isEmpty());
    }
}