package assignment07;

import org.junit.jupiter.api.Test;

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
//        assertTrue(hashTable.add("Julia"));
//        //should not add duplicate
//        assertFalse(hashTable.add("Brian"));
//        assertEquals(2, hashTable.size());
    }
}//end of class bracket