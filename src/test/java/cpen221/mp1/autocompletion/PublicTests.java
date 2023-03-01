package cpen221.mp1.autocompletion;


import cpen221.mp1.searchterm.SearchTerm;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class PublicTests {
    @Test
    void simpleTestCountMatches1() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long)3);
        n3.put("hello there sally", (long)2);
        n3.put("hello there jack", (long)1);

        ngrams.add(n3);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        int matches = autoCompletor.numberOfMatches("hello");

        assertEquals(3, matches);
    }

    @Test
    void simpleTestCountMatches2() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long)3);
        n3.put("hello there sally", (long)2);
        n3.put("hello there jack", (long)1);

        HashMap<String, Long> n4 = new HashMap<>();
        n4.put("hello there bob baily", (long)99);
        n4.put("hello there sally samson", (long)2);

        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        int matches = autoCompletor.numberOfMatches("hello there");

        assertEquals(5, matches);
    }

    @Test
    void simpleTestCountMatchesGeneralConstructor1() {

        SearchTerm[] inputSearchTerms = {
                new SearchTerm("hello there bob", (long)3),
                new SearchTerm("hello there yes", (long)13),
                new SearchTerm("hello there no", (long)2)
        };

        AutoCompletor autoCompletor = new AutoCompletor(inputSearchTerms);
        int matches = autoCompletor.numberOfMatches("hello th");

        assertEquals(3, matches);
    }

    @Test
    void simpleTestAllMatches() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long)3);
        n3.put("hello there sally", (long)2);
        n3.put("hello there jack", (long)1);

        HashMap<String, Long> n4 = new HashMap<>();
        n3.put("hello there bob dylan", (long)3);
        n3.put("hello there sally jackson", (long)2);
        n3.put("hello there jack carlson", (long)1);

        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.allMatches("hello");

        SearchTerm[] expected = {
                new SearchTerm("hello there bob", (long)3),
                new SearchTerm("hello there bob dylan", (long)3),
                new SearchTerm("hello there sally", (long)2),
                new SearchTerm("hello there sally jackson", (long)2),
                new SearchTerm("hello there jack", (long)1),
                new SearchTerm("hello there jack carlson", (long)1)
        };

        assertArrayEquals(expected, matches);
    }

    @Test
    void simpleTestAllMatchesEqualWeight1() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long)10);
        n3.put("hello there box", (long)10);
        n3.put("hello there a", (long)1);

        HashMap<String, Long> n4 = new HashMap<>();
        n3.put("hello there bob samuel", (long)10);
        n3.put("hello there sally zamuel", (long)10);
        n3.put("hello there bob samson", (long)1);

        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.allMatches("hello ther");

        SearchTerm[] expected = {
                new SearchTerm("hello there bob", (long)10),
                new SearchTerm("hello there bob samuel", (long)10),
                new SearchTerm("hello there box", (long)10),
                new SearchTerm("hello there sally zamuel", (long)10),
                new SearchTerm("hello there a", (long)1),
                new SearchTerm("hello there bob samson", (long)1)
        };

        assertArrayEquals(expected, matches);
    }

    @Test
    void simpleTestAllMatchesEqualWeight2() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bobb", (long)10);
        n3.put("hello there bob", (long)10);
        n3.put("hello there bobbb", (long)10);
        n3.put("hello there bobbbbb", (long)10);
        n3.put("hello there bobbbb", (long)10);
        n3.put("hello there a", (long)1);

        HashMap<String, Long> n4 = new HashMap<>();
        n3.put("hello there sally zamuel", (long)10);
        n3.put("hello there bob samson", (long)1);

        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.allMatches("hello ther");

        SearchTerm[] expected = {
                new SearchTerm("hello there bob", (long)10),
                new SearchTerm("hello there bobb", (long)10),
                new SearchTerm("hello there bobbb", (long)10),
                new SearchTerm("hello there bobbbb", (long)10),
                new SearchTerm("hello there bobbbbb", (long)10),
                new SearchTerm("hello there sally zamuel", (long)10),
                new SearchTerm("hello there a", (long)1),
                new SearchTerm("hello there bob samson", (long)1)
        };

        assertArrayEquals(expected, matches);
    }

    @Test
    void simpleTestTopKMatchesEqualWeight1() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long) 10);
        n3.put("hello there box", (long) 10);
        n3.put("hello there a", (long) 1);

        HashMap<String, Long> n4 = new HashMap<>();
        n3.put("hello there bob samuel", (long) 10);
        n3.put("hello there sally zamuel", (long) 10);
        n3.put("hello there bob samson", (long) 1);

        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.topKMatches("hello ther");

        SearchTerm[] expected = {
                new SearchTerm("hello there bob", (long) 10),
                new SearchTerm("hello there bob samuel", (long) 10),
                new SearchTerm("hello there box", (long) 10),
                new SearchTerm("hello there sally zamuel", (long) 10),
                new SearchTerm("hello there a", (long) 1),
                new SearchTerm("hello there bob samson", (long) 1)
        };
        assertArrayEquals(expected, matches);
    }

    @Test
    void simpleTestTopKMatchesEqualWeight2() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n2 = new HashMap<>();
        n2.put("hello a", (long)1000);
        n2.put("hello b", (long)1);
        n2.put("hello c", (long)1);
        n2.put("hello d", (long)1);
        n2.put("hello e", (long)1);

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long) 10);
        n3.put("hello there box", (long) 10);
        n3.put("hello there a", (long) 1);

        HashMap<String, Long> n4 = new HashMap<>();
        n3.put("hello there bob samuel", (long) 10);
        n3.put("hello there sally zamuel", (long) 10);
        n3.put("hello there bob samson", (long) 1);

        ngrams.add(n2);
        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.topKMatches("hell");

        SearchTerm[] expected = {
                new SearchTerm("hello a", (long) 1000),
                new SearchTerm("hello there bob", (long) 10),
                new SearchTerm("hello there bob samuel", (long) 10),
                new SearchTerm("hello there box", (long) 10),
                new SearchTerm("hello there sally zamuel", (long) 10),
                new SearchTerm("hello b", (long) 1),
                new SearchTerm("hello c", (long) 1),
                new SearchTerm("hello d", (long) 1),
                new SearchTerm("hello e", (long) 1),
                new SearchTerm("hello there a", (long) 1)
        };
        assertArrayEquals(expected, matches);
    }

    @Test
    void noMatches() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n3 = new HashMap<>();
        n3.put("hello there bob", (long) 10);
        n3.put("hello there box", (long) 10);
        n3.put("hello there a", (long) 1);

        HashMap<String, Long> n4 = new HashMap<>();
        n3.put("hello there bob samuel", (long) 10);
        n3.put("hello there sally zamuel", (long) 10);
        n3.put("hello there bob samson", (long) 1);

        ngrams.add(n3);
        ngrams.add(n4);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.allMatches("goodbye");

        SearchTerm[] expected = new SearchTerm[0]; // same as expected = {}

        assertArrayEquals(expected, matches);
    }

    // TODO: fix this test
    @Test
    void specialCharacterTest1() {
        List<Map<String, Long>> ngrams = new ArrayList<>();

        HashMap<String, Long> n1 = new HashMap<>();
//        n1.put("abc1", (long)1);
//        n1.put("abc12", (long)1);
//        n1.put("abc2", (long)1);

        n1.put("abc&a", (long)1);
//        n1.put("abc-a", (long)1);
        n1.put("abc0a", (long)1);

//        n1.put("x&y", (long)1);
//        n1.put("xy", (long)1);

        n1.put("xyz", (long)1);
        n1.put("Xyz", (long)1);


        ngrams.add(n1);

        AutoCompletor autoCompletor = new AutoCompletor(ngrams);
        SearchTerm[] matches = autoCompletor.allMatches("");

        for(SearchTerm s : matches) {
            System.out.println(s.toString());
        }

        SearchTerm[] expected = {
                new SearchTerm("hello there jack", (long)1),
                new SearchTerm("hello there jack carlson", (long)1),
                new SearchTerm("hello there sally", (long)2),
                new SearchTerm("hello there sally jackson", (long)2),
                new SearchTerm("hello there bob", (long)3),
                new SearchTerm("hello there bob dylan", (long)3)
        };

        fail();
    }

    @Test
    void getWeightTest() {
        SearchTerm s = new SearchTerm("hello there jack", (long)1);
        long weight = s.getWeight();
        assertEquals(1, weight);
    }

    @Test
    void equalityDifferentWeightTest() {
        SearchTerm o1 = new SearchTerm("hello there jack", (long)1);
        SearchTerm o2 = new SearchTerm("hello there jack", (long)2);
        assertNotEquals(o1, o2);
    }

    @Test
    void equalitySameWeightTest() {
        SearchTerm o1 = new SearchTerm("hello there jack", (long)100);
        SearchTerm o2 = new SearchTerm("hello there jack", (long)100);
        assertEquals(o1, o2);
    }

    @Test
    void equalityDifferentQueryTest() {
        SearchTerm o1 = new SearchTerm("hello there jacky", (long)1);
        SearchTerm o2 = new SearchTerm("hello there jack", (long)1);
        assertNotEquals(o1, o2);
    }

    @Test
    void equalitySameQueryTest() {
        SearchTerm o1 = new SearchTerm("hello there jack", (long)1);
        SearchTerm o2 = new SearchTerm("hello there jack", (long)1);
        assertEquals(o1, o2);
    }

    @Test
    void equalityDifferentObjectTest() {
        String o1 = "Hello";
        SearchTerm o2 = new SearchTerm("hello there jack", (long)1);
        boolean b = o2.equals(o1);
        assertEquals(b, false);
    }

    @Test
    void compareToTest() {
        SearchTerm o1 = new SearchTerm("b $ a", (long)1);
        SearchTerm o2 = new SearchTerm("b $ z", (long)1);
        int i = o1.compareTo(o2);
        // Return <0 because o1 comes before o2
        assert(i < 0);
    }

    @Test
    void notPossibleEqualQueryAndWeight() {
        SearchTerm[] inputSearchTerms = {
            new SearchTerm("b $ z", (long) 1),
            new SearchTerm("b $ z", (long) 1),
            new SearchTerm("b $ zz", (long) 1)
        };

        AutoCompletor autoCompletor = new AutoCompletor(inputSearchTerms);
        SearchTerm[] matches = autoCompletor.topKMatches("b");

        SearchTerm[] expected = {
                new SearchTerm("b $ z", (long) 1),
                new SearchTerm("b $ z", (long) 1),
                new SearchTerm("b $ zz", (long) 1)
        };

        assertArrayEquals(matches, expected);
    }

    @Test
    void prefixNotMatch() {
        SearchTerm[] inputSearchTerms = {
                new SearchTerm("x $ z", (long) 1),
                new SearchTerm("b $ z", (long) 1),
                new SearchTerm("b $ zz", (long) 1)
        };

        AutoCompletor autoCompletor = new AutoCompletor(inputSearchTerms);
        SearchTerm[] matches = autoCompletor.topKMatches("x");

        SearchTerm[] expected = {
                new SearchTerm("x $ z", (long) 1),
        };

        assertArrayEquals(matches, expected);
    }

}
