package cpen221.mp1.ngrams;


import cpen221.mp1.ngrams.NGrams;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class PublicTests {

    @Test
    void blue_cow_repeated() {
        String[] text = {"The blue cow jumps over the blue cow moon", "The blue cow jumps over the blue cow moon"};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(3);
        assertEquals(sum, 18);
    }

    @Test
    void blue_repeats() {
        String[] text = {"blue blue blue, blue"};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(4);
        assertEquals(sum, 4);
    }

    @Test
    void blue_cow_spaces_punctuation() {
        String[] text = {"    The blue cow,      jumps over the blue. cow moon"};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(3);
        assertEquals(sum, 18);
    }

    @Test
    void blue_cow_5_grams() {
        String[] text = {"The blue cow jumps over the blue cow moon", "The blue cow jumps over the blue cow moon"};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(5);
        assertEquals(sum, 29);

        List<Map<String, Long>> nGramList = new ArrayList<>();
        HashMap<String, Long> oneGramMap = new HashMap<>();
        oneGramMap.put("the", (long)4);
        oneGramMap.put("blue", (long)4);
        oneGramMap.put("cow", (long)4);
        oneGramMap.put("jumps", (long)2);
        oneGramMap.put("over", (long)2);
        oneGramMap.put("moon", (long)2);
        nGramList.add(oneGramMap);

        HashMap<String, Long> twoGramMap = new HashMap<>();
        twoGramMap.put("the blue", (long)4);
        twoGramMap.put("blue cow", (long)4);
        twoGramMap.put("cow jumps", (long)2);
        twoGramMap.put("jumps over", (long)2);
        twoGramMap.put("over the", (long)2);
        twoGramMap.put("cow moon", (long)2);
        nGramList.add(twoGramMap);

        HashMap<String, Long> threeGramMap = new HashMap<>();
        threeGramMap.put("the blue cow", (long)4);
        threeGramMap.put("blue cow jumps", (long)2);
        threeGramMap.put("cow jumps over", (long)2);
        threeGramMap.put("jumps over the", (long)2);
        threeGramMap.put("over the blue", (long)2);
        threeGramMap.put("blue cow moon", (long)2);
        nGramList.add(threeGramMap);

        HashMap<String, Long> fourGramMap = new HashMap<>();
        fourGramMap.put("the blue cow jumps", (long)2);
        fourGramMap.put("blue cow jumps over", (long)2);
        fourGramMap.put("cow jumps over the", (long)2);
        fourGramMap.put("jumps over the blue", (long)2);
        fourGramMap.put("over the blue cow", (long)2);
        fourGramMap.put("the blue cow moon", (long)2);
        nGramList.add(fourGramMap);

        HashMap<String, Long> fiveGramMap = new HashMap<>();
        fiveGramMap.put("the blue cow jumps over", (long)2);
        fiveGramMap.put("blue cow jumps over the", (long)2);
        fiveGramMap.put("cow jumps over the blue", (long)2);
        fiveGramMap.put("jumps over the blue cow", (long)2);
        fiveGramMap.put("over the blue cow moon", (long)2);
        nGramList.add(fiveGramMap);

        HashMap<String, Long> sixGramMap = new HashMap<>();
        sixGramMap.put("the blue cow jumps over the", (long)2);
        sixGramMap.put("blue cow jumps over the blue", (long)2);
        sixGramMap.put("cow jumps over the blue cow", (long)2);
        sixGramMap.put("jumps over the blue cow moon", (long)2);
        nGramList.add(sixGramMap);

        HashMap<String, Long> sevenGramMap = new HashMap<>();
        sevenGramMap.put("the blue cow jumps over the blue", (long)2);
        sevenGramMap.put("blue cow jumps over the blue cow", (long)2);
        sevenGramMap.put("cow jumps over the blue cow moon", (long)2);
        nGramList.add(sevenGramMap);

        HashMap<String, Long> eightGramMap = new HashMap<>();
        eightGramMap.put("the blue cow jumps over the blue cow", (long)2);
        eightGramMap.put("blue cow jumps over the blue cow moon", (long)2);
        nGramList.add(eightGramMap);

        HashMap<String, Long> nineGramMap = new HashMap<>();
        nineGramMap.put("the blue cow jumps over the blue cow moon", (long)2);
        nGramList.add(nineGramMap);

        assertEquals(nGramList,nGrams.getAllNGrams());
    }

    @Test
    void short_long_string() {
        String[] text = {"blue", "hello world", "hello world hi"};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(3);
        assertEquals(sum, 7);
    }

    @Test
    void one_empty_input() {
        String[] text = {"", "The blue cow jumps over the blue cow moon"};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(3);
        assertEquals(sum, 18);
    }

    @Test
    void extra_space_input() {
        String[] text = {"     ", "The blue     cow jumps over the blue cow moon ", "   "};
        NGrams nGrams = new NGrams(text);
        long sum = nGrams.getTotalNGramCount(3);
        assertEquals(sum, 18);
    }

    @Test
    void only_punctuation_getList() {
        String[] text = {"  .   ", ""};
        assertThrows(AssertionError.class, () -> { NGrams nGrams = new NGrams(text);});
    }

    @Test
    void only_punctuation_getSum() {
        String[] text = {".,", ""};
        assertThrows(AssertionError.class, () -> { NGrams nGrams = new NGrams(text);});
    }

    @Test
    void negative_getSum() {
        String[] text = {"     hello", ""};
        NGrams nGrams = new NGrams(text);
        assertThrows(AssertionError.class, () -> nGrams.getTotalNGramCount(-5));
    }

    @Test
    void invalid_n_entry_too_high() {
        String[] text = {"  cow jumps over the blue cow moon"};
        NGrams nGrams = new NGrams(text);
        assertThrows(AssertionError.class, () -> nGrams.getTotalNGramCount(500));
    }

    @Test
    void entry_is_spaces() {
        String[] text = {"       "};
        assertThrows(AssertionError.class, () -> { NGrams nGrams = new NGrams(text);});
    }

    @Test
    void entry_is_empty() {
        String[] text = {"       "};
        assertThrows(AssertionError.class, () -> { NGrams nGrams = new NGrams(text);});
    }

    @Test
    public void simpleTestCount() {
        String text1 = "the blue cow jumped over the blue cow moon!";
        String text2 = "The Blue Period of Picasso is the period between 1900 and 1904, when he painted essentially monochromatic paintings in shades of blue and blue-green, only occasionally warmed by other colors.";

        long expectedCount = 130;

        NGrams ng = new NGrams(new String[]{text1, text2});

        assertEquals(expectedCount, ng.getTotalNGramCount(4));
    }

    @Test
    public void simpleTestGetNGrams() {
        String text1 = "great class";
        String text2 = "good textbook written by him";

        List<Map<String, Long>> expectedNGrams = List.of(
                Map.of("great", 1L, "class", 1L, "good", 1L, "textbook", 1L, "written", 1L, "by", 1L, "him", 1L),
                Map.of("great class", 1L, "good textbook", 1L, "textbook written", 1L, "written by", 1L, "by him", 1L),
                Map.of("good textbook written", 1L, "textbook written by", 1L, "written by him", 1L),
                Map.of("good textbook written by", 1L, "textbook written by him", 1L),
                Map.of("good textbook written by him", 1L)
        );

        NGrams ng = new NGrams(new String[]{text1, text2});
        assertEquals(expectedNGrams, ng.getAllNGrams());
    }


}
