package cpen221.mp1.searchterm;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PublicTests {
    @Test
    void testFirstCharacterDifferent() {

        SearchTerm s1 = new SearchTerm("abcde", 1);
        SearchTerm s2 = new SearchTerm("xyz", 1);

        assertTrue(s1.compareTo(s2) < 0);
    }

    @Test
    void testFirstFourCharactersEqual() {

        SearchTerm s1 = new SearchTerm("abcd", 1);
        SearchTerm s2 = new SearchTerm("abcd", 1);

        assertTrue(s1.compareTo(s2) == 0);
    }

    @Test
    void testFirstFourCharactersEqualDifferentLength() {

        SearchTerm s1 = new SearchTerm("abcde", 1);
        SearchTerm s2 = new SearchTerm("abcd", 1);

        assertTrue(s1.compareTo(s2) > 0);
    }

    @Test
    void testNumbers() {

        SearchTerm s1 = new SearchTerm("123", 1);
        SearchTerm s2 = new SearchTerm("129", 1);

        assertTrue(s1.compareTo(s2) < 0);
    }

    @Test
    void testNumbersAndLetters() {

        SearchTerm s1 = new SearchTerm("a1b", 1);
        SearchTerm s2 = new SearchTerm("a3b", 1);

        assertTrue(s1.compareTo(s2) < 0);
    }

}
