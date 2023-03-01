package cpen221.mp1.ngrams;

import java.text.BreakIterator;
import java.util.*;

public class NGrams {
    private String[] text;
    private List<Map<String, Long>> nGramList;

    /**
     * Create an NGrams object
     *
     * @param text all the text to analyze and create n-grams from;
     *             is not null and is not empty.
     */
    public NGrams(String[] text) {
        this.text = text;
        this.nGramList = assignAllNGrams();
    }

    /**
     * Get the n-grams, as a List, with the i-th entry being
     * all the (i+1)-grams and their counts.
     *
     * @return a list of n-grams, n = 1, 2, ... until n equals the number of words
     * in the longest string in this.text, and their associated counts,
     * with the i-th entry being all the (i+1)-grams and their counts
     */
    private List<Map<String, Long>> assignAllNGrams() {
        assert (longestSentence() != 0) : "the input text is empty";

        int n = longestSentence(); // maximum number of n-grams
        this.nGramList = new ArrayList<>();

        for (int count = 1; count <= n; count++) {
            HashMap<String, Long> nMap = new HashMap<>();

            for (int sentence = 0; sentence < text.length; sentence++) {
                String[] wordsArray = getWords(text[sentence]);

                for (int i = 0; i <= wordsArray.length - count; i++) {
                    StringBuilder nGramKey = new StringBuilder();

                    for (int j = 0; j < count; j++) {
                        nGramKey.append(wordsArray[i + j]);
                        if (j < (count - 1)) {
                            nGramKey.append(" ");
                        }
                    }
                    if (nMap.containsKey(nGramKey.toString())) {
                        nMap.put(nGramKey.toString(), nMap.get(nGramKey.toString()) + 1);
                    } else {
                        nMap.put(nGramKey.toString(), 1L);
                    }
                }
            }
            nGramList.add(nMap);
        }
        return nGramList;
    }

    /**
     * Obtain the total number of unique 1-grams,
     * 2-grams, ..., n-grams.
     *
     * Specifically, if there are m_i i-grams,
     * obtain sum_{i=1}^{n} m_i.
     *
     * @param n the number of n-grams to count to, n >= 0
     *
     * @return the total number of 1-grams,
     * 2-grams, ..., n-grams
     *
     */
    public long getTotalNGramCount(int n) {
        assert (n >= 0 && n <= longestSentence()) : "value of n is out of bounds";

        long sum = 0; //returns 0 if the value of n is zero
        List<Map<String, Long>> nGramList = getAllNGrams();

        for (int i = 0; i < n; i++) {
            sum += nGramList.get(i).size();
        }
        return sum;
    }

    /**
     * Returning the instance variable this.nGramList
     *
     * @return a list of n-grams, n = 1, 2, ... until n equals the number of words
     * in the longest string in this.text, and their associated counts,
     * with the i-th entry being all the (i+1)-grams and their counts
     */
    public List<Map<String, Long>> getAllNGrams() {
        return this.nGramList;
    }

    /**
     * Get the number of words in the longest entry in this.text.
     * @return the largest count of words in any element in this.text
     */
    public int longestSentence() {
        int longest = 0;

        for (int i = 0; i < text.length; i++) {
            if (getWords(text[i]).length > longest) {
                longest = getWords(text[i]).length;
            }
        }
        return longest;
    }

    /**
     * Get the words in a string of text as an array. Ignores extra white spaces and punctuation.
     * @param text string input
     * @return the words in a string of text in lowercase.
     */
    public static String[] getWords(String text) {
        ArrayList<String> words = new ArrayList<>();
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int start = wb.first();
        for (int end = wb.next();
             end != BreakIterator.DONE;
             start = end, end = wb.next()) {
            String word = text.substring(start, end).toLowerCase();
            word = word.replaceAll("^\\s*\\p{Punct}+\\s*", "").replaceAll("\\s*\\p{Punct}+\\s*$", "");
            if (!(word.contains(" ") || word.equals(""))) {
                words.add(word);
            }
        }
        String[] wordsArray = new String[words.size()];
        words.toArray(wordsArray);
        return wordsArray;
    }

}

