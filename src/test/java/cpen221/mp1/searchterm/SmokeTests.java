package cpen221.mp1.searchterm;

import cpen221.mp1.autocompletion.AutoCompletor;
import cpen221.mp1.cities.DataAnalyzer;
import cpen221.mp1.searchterm.SearchTerm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.List;

public class SmokeTests {

    private static String citiesData = "data/cities.txt";
    private static DataAnalyzer cityAnalyzer;
    private static AutoCompletor ac;

    @BeforeAll
    public static void setupTests() {
        cityAnalyzer = new DataAnalyzer(citiesData);
        ac = new AutoCompletor(cityAnalyzer.getSearchTerms());
    }

    @Test
    public void test_San_3() {
        SearchTerm[] st = ac.topKMatches("San", 3);

        SearchTerm santiago = new SearchTerm("Santiago, Chile", 4837295);
        SearchTerm santoDomingo = new SearchTerm("Santo Domingo, Dominican Republic", 2201941);
        SearchTerm sanaa = new SearchTerm("Sanaa, Yemen", 1937451);
        SearchTerm[] expectedST = new SearchTerm[] { santiago, santoDomingo, sanaa };

        Assertions.assertArrayEquals(expectedST, st);
    }

    @Test
    public void testCities2() {
        SearchTerm[] st = ac.topKMatches("Saint Petersburg", 3);

        SearchTerm russia = new SearchTerm("Saint Petersburg, Russia", 4039745);
        SearchTerm usa = new SearchTerm("Saint Petersburg, Florida, United States", 244769);
        SearchTerm[] expectedST = new SearchTerm[] { russia, usa };

        Assertions.assertArrayEquals(expectedST, st);
    }
}