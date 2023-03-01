package cpen221.mp1.searchterm;

import java.util.Comparator;
import java.util.Objects;

public class SearchTerm implements Comparable<SearchTerm> {

    private String query;
    private long   weight;

    /**
     * Initializes a term with the given query string and weight.
     *
     * @param query the query for the search, is not empty
     * @param weight the weight associated with the query string
     *
     */
    public SearchTerm(String query, long weight) {
        this.query  = query;
        this.weight = weight;
    }

    /**
     * Obtain the query of a search term.
     *
     * @return the query as it is.
     */
    public String getQuery() {
        return this.query;
    }

    /**
     * Obtain the weight of a search term.
     * @return the weight as it is.
     */
    public long getWeight() {
        return this.weight;
    }

    /**
     * Obtain a comparator for comparing two search terms based on weight.
     *
     * @return a comparator that compares two search terms using their weight
     */
    public static Comparator<SearchTerm> byReverseWeightOrder() {
        return new Comparator<SearchTerm>() {
            @Override
            public int compare(SearchTerm o1, SearchTerm o2) {
                // Standard order semantics
                // return value > 0: o1 > o2 (o2 comes first since it has lower weight "smaller")
                // return value = 0: o1 = o2
                // return value < 0: o1 < o2 (o1 comes first since it has lower weight "smaller")
                // Weight would be "smaller" to "larger" sorted {1,10,100}

                // To reverse, we swap o1 and o2
                long temp = o2.weight - o1.weight;
                return temp < 0 ? -1 : (temp > 0 ? 11 : 0);
            }
        };
    }

    /**
     * Obtain a comparator for lexicographic ordering.
     *
     * @return a comparator that compares two search terms lexicographically
     */
    public static Comparator<SearchTerm> byPrefixOrder() {
        // Iterate through each character of the array and compare ASCII values
        return new Comparator<SearchTerm>() {
            @Override
            // return value > 0: o1 > o2 (o1 comes after o2 in a dictionary)
            // return value = 0: o1 = o2
            // return value < 0: o1 < o2 (o1 comes before o2 in a dictionary)
            public int compare(SearchTerm o1, SearchTerm o2) {

                /**
                 * Deprecated: code that corresponds with calling Arrays.sort() twice
                 *             where the second call is a sort that occurs if the first
                 *             sort resulted in a tie, hence we would need a conditional
                // Only compare when weights are equal
                if (o1.weight != o2.weight) {
                    return 0;
                }
                 */

                //TODO: use string compareTo() which does this all automatically

                // Clean up input to only have characters and spaces
                String str1 = o1.query;
                String str2 = o2.query;

                for (int i = 0; i < str1.length() && i < str2.length(); i++) {
                    if (!((int)str1.charAt(i) == (int)str2.charAt(i))) {
                        return (int)str1.charAt(i) - (int)str2.charAt(i);
                    }
                }

                // Case: if strings are not the same length, but all characters have been equal so far
                //       the string with fewer characters comes first in a dictionary
                if (str1.length() < str2.length()) {
                    return (str1.length()-str2.length());
                }
                else if (str1.length() > str2.length()) {
                    return (str1.length()-str2.length());
                }

                // Strings are equal if all cases above did not return
                else {
                    return 0;
                }
            }
        };
    }

    /**
     * Compares the two terms in lexicographic order by query.
     */
    @Override
    public int compareTo(SearchTerm other) {
        return SearchTerm.byPrefixOrder().compare(this, other);
    }

    /**
     * Returns a string representation of this SearchTerm.
     *
     * @return a string representation of this term in the following format:
     * the weight, followed by a tab, followed by the query.
     */
    public String toString() {
        return String.format("%-10d\t%s", this.weight, this.query);
    }

    /**
     * SearchTerm equality override using Intellij auto generation based on all instance fields
     * @param other object to compare with
     * @return true if all fields of two objects are equal
     */
    @Override
    /* Deprecated: use equals() provided by teaching team
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchTerm that = (SearchTerm) o;
        return weight == that.weight && Objects.equals(query, that.query);
    }
    */
    public boolean equals(Object other) {
        if (other instanceof SearchTerm) {
            SearchTerm otherST = (SearchTerm) other;
            return (this.query.equals(otherST.query) && this.weight == otherST.weight);
        }
        else {
            return false;
        }
    }
}