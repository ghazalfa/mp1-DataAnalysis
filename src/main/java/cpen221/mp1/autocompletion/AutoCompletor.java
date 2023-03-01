package cpen221.mp1.autocompletion;

import cpen221.mp1.autocompletion.gui.AutoCompletorGUI;
import cpen221.mp1.cities.DataAnalyzer;
import cpen221.mp1.searchterm.SearchTerm;

import javax.swing.*;
import java.util.*;

public class AutoCompletor {

    private static final int DEFAULT_SEARCH_LIMIT = 10;
    private SearchTerm[] searchTermArray;

     /**
     * Create an object that recommends n-grams that contain a prefix based on occurrences of that n-gram in a data set.
     * @param searchTerms are the complete search terms that will be used as recommendations based on a given prefix
     *                   searchTerms is not null and not empty
     */
    public AutoCompletor(SearchTerm[] searchTerms) {
        this.searchTermArray = new SearchTerm[searchTerms.length];
        for(int i = 0; i < searchTerms.length; i++) {
            this.searchTermArray[i] = searchTerms[i];
        }
    }

    /**
     * Create an object that recommends n-grams that contain a prefix based on occurrences of that n-gram in a data set.
     * @param searchTerms the output from Task 1, a list of pairs of n-grams and counts, used for creating recommendations
     *                    searchTerms is not null and not empty
     *                    searchTerms is exactly in the format as indicated in Task 1
     */
    public AutoCompletor(List<Map<String, Long>> searchTerms) {
        // Extract all <String, Long> pairs and save as new SearchTerm objects in an array of SearchTerms
        ArrayList<SearchTerm> searchTermArrayList = new ArrayList<>();
        for(int i = 0; i < searchTerms.size(); i++) {
            for(Map.Entry<String, Long> rowOfMap : searchTerms.get(i).entrySet()) {
                SearchTerm s = new SearchTerm(rowOfMap.getKey(), rowOfMap.getValue());
                searchTermArrayList.add(s);
            }
        }
        // Convert ArrayList to array
        this.searchTermArray = new SearchTerm[searchTermArrayList.size()];
        this.searchTermArray = searchTermArrayList.toArray(this.searchTermArray);
    }

    /**
     * Find all the recommendations from the set of n-grams data that starts with a given prefix.
     * @param prefix must be the start of a recommendation
     * @return return all recommendations that start with the prefix provided
     */
    public SearchTerm[] allMatches(String prefix) {
        int totalMatches = numberOfMatches(prefix);
        // No matches returns an empty array
        if (totalMatches == 0) {
            return new SearchTerm[0];
        }
        else {
            int index = 0;
            SearchTerm[] matchesArray = new SearchTerm[totalMatches];
            for (SearchTerm searchTerm : this.searchTermArray) {
                if (searchTerm.getQuery().startsWith(prefix)) {
                    matchesArray[index] = searchTerm;
                    index++;
                }
            }

            /**
             * Deprecated: code that corresponds with calling Arrays.sort() twice
             *             where the second call is a sort that occurs if the first
             *             sort resulted in a tie, hence we would need a conditional
            Arrays.sort(matchesArray,SearchTerm.byReverseWeightOrder());
            Arrays.sort(matchesArray,SearchTerm.byPrefixOrder());
             */

            // Chain comparator instances to sort based on multiple fields
            // Sort first by weight in reverse order
            // Then sort by lexicographic ordering
            Comparator<SearchTerm> complexComparator = SearchTerm.byReverseWeightOrder();
            complexComparator = complexComparator.thenComparing(SearchTerm.byPrefixOrder());
            Arrays.sort(matchesArray, complexComparator);

            return matchesArray;
        }
    }

    /**
     * Find the top k recommendations from the set of n-grams data that starts with a given prefix.
     * @param prefix must be the start of a recommendation
     * @param limit is the number of recommendations to return
     * @return the top k recommendations where k is the limit provided
     */
    public SearchTerm[] topKMatches(String prefix, int limit) {
        SearchTerm[] allRecommendations = allMatches(prefix);
        // If there are fewer matches than the limit, use the lower limit
        int maxIndex = Math.min(allRecommendations.length, limit);
        SearchTerm[] topKRecommendations = new SearchTerm[maxIndex];
        for(int i = 0; i < maxIndex ; i++) {
            topKRecommendations[i] = allRecommendations[i];
        }
        return topKRecommendations;
    }

    /**
     * Find the default amount of recommendations from the set of n-grams data that starts with a given prefix.
     * @param prefix must be the start of a recommendation
     * @return the top n recommendations where n is the default search limit
     */
    public SearchTerm[] topKMatches(String prefix) {
        return topKMatches(prefix, DEFAULT_SEARCH_LIMIT);
    }

    /**
     * Count the number of search terms that start with a provided prefix.
     * @param prefix must be the start of a match
     * @return the number of matches to the prefix
     */
    public int numberOfMatches(String prefix) {
        int totalMatches = 0;
        for (SearchTerm searchTerm : this.searchTermArray) {
            if (searchTerm.getQuery().startsWith(prefix)) {
                totalMatches++;
            }
        }
        return totalMatches;
    }

    /* For testing purposes, GUI feature
    public static void main(String[] args) {
        final String citiesData = "data/cities.txt";
        DataAnalyzer da = new DataAnalyzer(citiesData);
        SearchTerm[] searchTerms = da.getSearchTerms();
        AutoCompletor ac = new AutoCompletor(searchTerms);
        final int k = 10;
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        new AutoCompletorGUI(searchTerms, k).setVisible(true);
                    }
                }
        );
    }
    */

}
