package cpen221.mp1.ratemyprofessor;

import cpen221.mp1.datawrapper.DataWrapper;
import cpen221.mp1.histogram.Histogram;

import static cpen221.mp1.ngrams.NGrams.getWords;



import java.io.FileNotFoundException;
import java.util.*;


public class DataAnalyzer {

     ArrayList<String[]> reviews;
     ArrayList<Character> gender;
     ArrayList<Double> rating;

    /**
     * Create an object to analyze a RateMyProfessor dataset
     *
     * @param dataSourceFileName the name of the file that contains the data
     * @throws FileNotFoundException if the file does not exist or is not found
     */
    public DataAnalyzer(String dataSourceFileName) throws FileNotFoundException {
        DataWrapper dw = new DataWrapper(dataSourceFileName);

        gender = new ArrayList<Character>();
        rating = new ArrayList<Double>();
        reviews = new ArrayList<String[]>();

        boolean genderValidity;
        boolean ratingValidity;
        String review;
        String[] reviewArray;
        char currentGender;
        double currentRating;
        String r;
        String text = dw.dataReader.nextLine();

        while (dw.dataReader.hasNext()) {

            text = dw.dataReader.nextLine();
            currentGender = text.charAt(4);
            r = text.substring(0, 3);
            review = text.substring(5);
            reviewArray = getWords(review);

            genderValidity = checkGender(currentGender);
            ratingValidity = checkRating(r);

            if (genderValidity && ratingValidity) {
                currentRating = Double.parseDouble(r);
                gender.add(currentGender);
                rating.add(currentRating);
                reviews.add(reviewArray);

            }

        }

    }

    /**
     * Obtain a histogram with the number of occurrences of the
     * query term in the RMP comments, categorized as men-low (ML),
     * women-low (WL), men-medium (MM), women-medium (WM),
     * men-high (MH), and women-high (WH).
     *
     * @param query the search term, which contains between one and three words
     * @return the histogram with the number of occurrences of the
     * query term in the RMP comments, categorized as men-low (ML),
     * women-low (WL), men-medium (MM), women-medium (WM),
     * men-high (MH), and women-high (WH)
     */
    public Map<String, Long> getHistogram(String query) {

        Map<String, Long> histogram = new HashMap<String, Long>();
        String text;
        String [] queryNoJunk;
        String[] currentReviews;
        char woman = 'W';
        int count;
        int stateCount;
        int numOfOccurences;
        long WL = 0;
        long WM = 0;
        long WH = 0;
        long ML = 0;
        long MM = 0;
        long MH = 0;

        queryNoJunk = getWords(query);

        //scans through every review in the reviews array
        for (int i = 0; i < this.reviews.size(); i++) {
            currentReviews = this.reviews.get(i);
            numOfOccurences=0;

            //checks to see if review contains query
           for(int p = 0; p< currentReviews.length;p++){
               count = p;
               stateCount = 0;

               for (int m = 0; m< queryNoJunk.length; m++){

                   if(count<currentReviews.length){


                       if(currentReviews[count].equals(queryNoJunk[m])){
                           stateCount++;}
                   }
                   count++;
               }

               if(stateCount == queryNoJunk.length){
                    numOfOccurences++;
               }
               stateCount = 0;


           }

           //partitions into hashmap review if review contains query
            //will add review in multiple times in the case of multiple occurrences of query
            for(int b = 0;b<numOfOccurences;b++) {


                    if (this.gender.get(i) == woman) {

                        if (this.rating.get(i) <= 2) {
                            WL++;
                        } else if (this.rating.get(i) <= 3.5) {
                            WM++;
                        } else {
                            WH++;
                        }

                    } else {

                        if (this.rating.get(i) <= 2) {
                            ML++;
                        } else if (this.rating.get(i) <= 3.5) {
                            MM++;
                        } else {
                            MH++;
                        }

                    }


            }

        }


        histogram.put("WL", WL);
        histogram.put("ML", ML);
        histogram.put("WM", WM);
        histogram.put("MM", MM);
        histogram.put("WH", WH);
        histogram.put("MH", MH);

        return histogram;
    }

    public static void showHistogramChart(Map<String, Long> histogram) {

        Histogram h = new Histogram("Ratings by Gender in Rate my Professor", "Rating & Gender", "Number of Ratings");
        List<String> xData = Arrays.asList("0-2", "2.1-3.5", "3.6-5");
        List<Long> yData = Arrays.asList(histogram.get("WL"), histogram.get("WM"), histogram.get("WH"));
        h.addSeries("Women", xData, yData);

        xData = Arrays.asList("0-2", "2.1-3.5", "3.6-5");
        yData = Arrays.asList(histogram.get("ML"), histogram.get("MM"), histogram.get("MH"));
        h.addSeries("Men", xData, yData);

        h.showChart();

    }

    /**
     * Display the histogram data as a chart
     *
     * @param histogram with entries for men-low (ML),
     *                  women-low (WL), men-medium (MM), women-medium (WM),
     *                  men-high (MH), and women-high (WH)
     */


    /**
     * Checks to see if the rating is valid
     * @param rating that is made of three characters
     */
    public static boolean checkRating(String rating) {
        boolean state = true;
        if (rating.charAt(0) != '0' && rating.charAt(0) != '1' && rating.charAt(0) != '2' && rating.charAt(0) != '3' && rating.charAt(0) != '4' && rating.charAt(0) != '5') {
            state = false;
        }

        if (rating.charAt(1) != '.') {
            state =  false;
        }

        if (rating.charAt(2) != '0' && rating.charAt(2) != '1' && rating.charAt(2) != '2' && rating.charAt(2) != '3' && rating.charAt(2) != '4' && rating.charAt(2) != '5' && rating.charAt(2) != '6' && rating.charAt(2) != '7' && rating.charAt(2) != '8' && rating.charAt(2) != '9') {
            state =  false;
        }
        return state;

    }

    /**
     * Checks to see if the gender is valid
     * @param gender that is made of one character
     */
    public boolean checkGender(char gender) {
        if (gender != 'W' && gender != 'M') {
            return false;
        }

        return true;
    }


}