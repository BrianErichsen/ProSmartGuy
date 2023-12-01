package assignment07;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChainingHashTableTest {
    private ChainingHashTable hashTable;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        hashTable = new ChainingHashTable(10, new GoodHashFunctor());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        hashTable = null;
    }
    @Test
    public void testAdd() {
        assertTrue(hashTable.add("Brian"));
        assertTrue(hashTable.add("Julia"));
        //should not add duplicate
        assertFalse(hashTable.add("Brian"));
        assertEquals(2, hashTable.size());
    }
    @Test
    public void testAddAll() {
        List<String> items = Arrays.asList("Brian", "Julia", "Mina");
        assertTrue(hashTable.addAll(items));
        //duplicates should be false
        assertFalse(hashTable.addAll(items));
        assertEquals(3, hashTable.size());
    }
    @Test
    public void testClear() {
        hashTable.add("Brian");
        hashTable.add("Julia");
        hashTable.clear();
        assertTrue(hashTable.isEmpty());
        assertEquals(0, hashTable.size());
    }
    @Test
    public void testContains() {
        hashTable.add("Brian");
        hashTable.add("Julia");
        assertTrue(hashTable.contains("Brian"));
        assertFalse(hashTable.contains("Dota"));
    }
    @Test
    public void testContainsAll() {
        hashTable.add("Brian");
        hashTable.add("Lisa");
        List<String> items = Arrays.asList("Brian", "Lisa");
        assertTrue(hashTable.containsAll(items));
        items = Arrays.asList("Brian", "orange");
        assertFalse(hashTable.containsAll(items));
    }
    @Test
    public void testIsEmpty() {
        assertTrue(hashTable.isEmpty());
        hashTable.add("Brian");
        assertFalse(hashTable.isEmpty());
    }
    @Test
    public void testRemove() {
        hashTable.add("Brian");
        hashTable.add("Lisa");
        assertTrue(hashTable.remove("Brian"));
        //item is not present; should not remove it
        assertFalse(hashTable.remove("Julia"));
        assertEquals(1, hashTable.size());
    }
    @Test
    public void testRemoveAll() {
        hashTable.add("Brian");
        hashTable.add("Lisa");
        List<String> items = Arrays.asList("Brian", "Julia");
        assertTrue(hashTable.removeAll(items));
        assertFalse(hashTable.removeAll(items));
        assertEquals(1, hashTable.size());
    }
}//end of class bracket