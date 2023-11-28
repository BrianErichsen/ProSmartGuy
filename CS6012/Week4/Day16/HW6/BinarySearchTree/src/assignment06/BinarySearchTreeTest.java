package assignment06;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;


class BinarySearchTreeTest {


    //========================================  Add Method Tests ================================================//
    @Test
    void addElementToEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.add(42));
        assertEquals(1, tree.size());
    }


    @Test
    void addElementToNonEmptyTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("apple");
        assertTrue(tree.add("banana"));
        assertEquals(2, tree.size());
    }


    @Test
    void addDuplicateElement() {
        BinarySearchTree<Double> tree = new BinarySearchTree<>();
        assertTrue(tree.add(3.14));
        assertFalse(tree.add(3.14)); // Adding the same element should return false
        assertEquals(1, tree.size());
    }


    @Test
    void addNullElement() {
        BinarySearchTree<Character> tree = new BinarySearchTree<>();
        assertThrows(NullPointerException.class, () -> tree.add(null));
        assertEquals(0, tree.size());
    }


    @Test
    void addElementsOfDifferentTypes() {
        BinarySearchTree<ComparableObject> tree = new BinarySearchTree<>();
        assertTrue(tree.add(new ComparableObject(10)));
        assertTrue(tree.add(new ComparableObject(5)));
        assertEquals(2, tree.size());
    }


    private static class ComparableObject implements Comparable<ComparableObject> {
        private final int value;


        ComparableObject(int value) {
            this.value = value;
        }


        @Override
        public int compareTo(ComparableObject other) {
            return Integer.compare(this.value, other.value);
        }
    }


    //========================================  Add Method Tests ================================================//


    @Test
    void addAllEmptyCollection() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.addAll(Arrays.asList()));
        assertTrue(tree.isEmpty());
    }


    @Test
    void addAllUniqueItems() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.addAll(Arrays.asList(5, 3, 7, 2, 4, 6, 8)));
        assertEquals(7, tree.size());
        assertTrue(tree.containsAll(Arrays.asList(2, 3, 4, 5, 6, 7, 8)));
    }


    @Test
    void addAllDuplicateItems() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.addAll(Arrays.asList(5, 3, 7, 3, 2, 5, 4, 6, 8, 7)));
        assertEquals(7, tree.size());
        assertTrue(tree.containsAll(Arrays.asList(2, 3, 4, 5, 6, 7, 8)));
    }




    @Test
    void addAllSomeExistingItems() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.addAll(Arrays.asList(5, 3, 7, 2, 4, 6, 8)));
        assertFalse(tree.addAll(Arrays.asList(2, 4, 6, 8)));
        assertEquals(7, tree.size());
        assertTrue(tree.containsAll(Arrays.asList(2, 3, 4, 5, 6, 7, 8)));
    }


    @Test
    void addAllEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.addAll(Arrays.asList(2, 4, 6, 8)));
        assertEquals(4, tree.size());
        assertTrue(tree.containsAll(Arrays.asList(2, 4, 6, 8)));
    }


    //========================================  Add Method Tests ================================================//


    @Test
    void containsInEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.contains(42));
    }


    @Test
    void containsInTreeWithOneElement() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(5);
        assertTrue(tree.contains(5));
        assertFalse(tree.contains(42));
    }


    @Test
    void containsInTreeWithMultipleElements() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(5, 3, 7, 2, 4, 6, 8));


        assertTrue(tree.contains(5));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(6));
        assertTrue(tree.contains(8));


        assertFalse(tree.contains(42));
    }


    @Test
    void containsNullItem() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertThrows(NullPointerException.class, () -> tree.contains(null));
    }








    @Test
    void removeAllFromEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.removeAll(Arrays.asList(1, 2, 3)));
        assertTrue(tree.isEmpty());
    }


    @Test
    void removeAllNonexistentElements() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(5, 3, 7, 2, 4, 6, 8));
        assertFalse(tree.removeAll(Arrays.asList(1, 9, 10)));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(6));
        assertTrue(tree.contains(8));
    }


    @Test
    void removeAllFromTreeWithOneElement() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(5);
        assertTrue(tree.removeAll(Arrays.asList(5)));
        assertTrue(tree.isEmpty());
    }


    @Test
    void removeAllFromTreeWithMultipleElements() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(5, 3, 7, 2, 4, 6, 8));
        assertTrue(tree.removeAll(Arrays.asList(3, 2, 6, 8)));
        assertTrue(tree.contains(5));
        assertFalse(tree.contains(3));
        assertTrue(tree.contains(7));
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(4));
        assertFalse(tree.contains(6));
        assertFalse(tree.contains(8));
    }


    @Test
    void removeNullItem() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertThrows(NullPointerException.class, () -> tree.remove(null));
    }






    @Test
    void containsAllEmptyTreeAndEmptyCollection() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.containsAll(Arrays.asList()));
    }


    @Test
    void containsAllEmptyTreeAndNonEmptyCollection() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.containsAll(Arrays.asList(1, 2, 3)));
    }


    @Test
    void containsAllNonEmptyTreeAndEmptyCollection() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        assertTrue(tree.containsAll(Arrays.asList()));
    }


    @Test
    void containsAllNonEmptyTreeAndNonEmptyCollectionAllPresent() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertTrue(tree.containsAll(Arrays.asList(1, 2, 3)));
    }


    @Test
    void containsAllNonEmptyTreeAndNonEmptyCollectionNotAllPresent() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertFalse(tree.containsAll(Arrays.asList(1, 2, 6)));
    }


    @Test
    void containsAllStringTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList("apple", "banana", "orange"));
        assertTrue(tree.containsAll(Arrays.asList("apple", "orange")));
        assertFalse(tree.containsAll(Arrays.asList("apple", "grape")));
    }


    @Test
    void addSingleNode() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.add(42));
        assertEquals(1, tree.size());
        assertEquals(Collections.singletonList(42), tree.toArrayList());
    }


    @Test
    void addDuplicateNode() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.add(42));
        assertFalse(tree.add(42)); // Adding the same element again
        assertEquals(1, tree.size());
        assertEquals(Collections.singletonList(42), tree.toArrayList());
    }


    @Test
    void addMultipleNodes() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.add(42));
        assertTrue(tree.add(20));
        assertTrue(tree.add(60));
        assertEquals(3, tree.size());
        assertEquals(Arrays.asList(20, 42, 60), tree.toArrayList());
    }


    @Test
    void addAll() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.addAll(Arrays.asList(42, 20, 60)));
        assertEquals(3, tree.size());
        assertEquals(Arrays.asList(20, 42, 60), tree.toArrayList());
    }




    @Test
    void contains() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.contains(42));
        tree.addAll(Arrays.asList(42, 20, 60));
        assertTrue(tree.contains(42));
        assertFalse(tree.contains(30));
    }


    @Test
    void removeAll() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.removeAll(Arrays.asList(42, 20, 60))); // Nothing to remove
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertTrue(tree.removeAll(Arrays.asList(42, 20, 60)));
        assertEquals(3, tree.size());
        assertEquals(Arrays.asList(30, 50, 70), tree.toArrayList());
    }


    @Test
    void remove() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertFalse(tree.remove(42)); // Nothing to remove
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertTrue(tree.remove(42));
        assertEquals(5, tree.size());
        assertEquals(Arrays.asList(20, 30, 50, 60, 70), tree.toArrayList());
    }


    @Test
    void clear() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertFalse(tree.isEmpty());
        tree.clear();
        assertTrue(tree.isEmpty());
    }


    @Test
    void first() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertThrows(NoSuchElementException.class, tree::first); // Empty tree
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertEquals(20, tree.first());
    }


    @Test
    void last() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertThrows(NoSuchElementException.class, tree::last); // Empty tree
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertEquals(70, tree.last());
    }


    @Test
    void isEmpty() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertTrue(tree.isEmpty());
        tree.add(42);
        assertFalse(tree.isEmpty());
    }


    @Test
    void size() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertEquals(0, tree.size());
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertEquals(6, tree.size());
    }


    @Test
    void toArrayList() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertEquals(Collections.emptyList(), tree.toArrayList());
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertEquals(Arrays.asList(20, 30, 42, 50, 60, 70), tree.toArrayList());
    }


//    @Test
//    void findMin() {
//        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//        assertNull(tree.findMin(tree.getRoot())); // Empty tree
//        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
//        assertEquals(20, tree.findMin(tree.getRoot()).getData());
//    }


//    @Test
//    void findMax() {
//        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//        assertNull(tree.findMax(tree.getRoot())); // Empty tree
//        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
//        assertEquals(70, tree.findMax(tree.getRoot()).getData());
//    }




//    @Test
//    void inOrderTraversal() {
//        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//        ArrayList<Integer> result = new ArrayList<>();
//        tree.inOrderTraversal(tree.getRoot(), result);
//        assertEquals(Collections.emptyList(), result);
//
//
//        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
//        result.clear();
//        tree.inOrderTraversal(tree.getRoot(), result);
//        assertEquals(Arrays.asList(20, 30, 42, 50, 60, 70), result);
//    }


    @Test
    void containsAll() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(42, 20, 60, 30, 50, 70));
        assertTrue(tree.containsAll(Arrays.asList(20, 30, 50)));
        assertFalse(tree.containsAll(Arrays.asList(20, 30, 99)));
    }


}
