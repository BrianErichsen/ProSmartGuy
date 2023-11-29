package assignment06;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {
    private SpellChecker spellChecker;

    @BeforeEach
    void setUp() {
        try {
            Path tempDictionary = Files.createTempFile("tempDictionary", ".txt");
            List<String> dictionaryWords = Arrays.asList("Brian", "John", "Julia");
            Files.write(tempDictionary, dictionaryWords);
            spellChecker = new SpellChecker(tempDictionary.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        spellChecker = null;
    }

    @Test
    public void addToDictionary() {
        spellChecker.addToDictionary("Lorena");
        assertTrue(spellChecker.dictionary.contains("lorena"));

    }

    @Test
    public void removeFromDictionary() {
        spellChecker.removeFromDictionary("John");
        assertFalse(spellChecker.dictionary.contains("john"));
    }
    @Test
    public void spellCheck() {
        try {
            Path tempDocument = Files.createTempFile("tempDocument", ".txt");
            List<String> documentWords = Arrays.asList("Brian", "John", "orange", "kiwi");
            Files.write(tempDocument, documentWords);
            System.out.println("Document is: " + documentWords);
            List<String> misspelledWords = spellChecker.spellCheck(tempDocument.toFile());
            System.out.println("Misspelled words: " + misspelledWords);

            assertEquals(2, misspelledWords.size());
            assertEquals("orange", misspelledWords.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}//end of class bracket