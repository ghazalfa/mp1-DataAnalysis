package cpen221.mp1.sentimentanalysis;

import cpen221.mp1.datawrapper.DataWrapper;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;


import static cpen221.mp1.ngrams.NGrams.getWords;
import static cpen221.mp1.ratemyprofessor.DataAnalyzer.checkRating;


public class SentimentAnalyzer {
    private ArrayList<HashMap<String,Integer>> bagsOfWords;
    private ArrayList<Integer> numberOfRatings;
    double totalNumberOfReviews;
    private HashMap<String,Integer > allWords;


    /**
     * A sentiment analysis model to predict the most likely rating for any given comment
     * @param filename which contains the training data with on which to build this prediction model
     */
    public SentimentAnalyzer(String filename) throws FileNotFoundException {
        initializeBagsOfWords(filename);
    }

    /**
     *
     * @param filename
     * modifies: bagOfWords , a list of bags-of-words associated with a roting,
     * {1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5} and their associated counts,
     * with the i-th entry being (rating - 1) * 2
     * @throws FileNotFoundException
     */
    private void initializeBagsOfWords(String filename) {
        bagsOfWords = new ArrayList<>();
        numberOfRatings = new ArrayList<>();

        for(int i = 0; i<9;i++){
            numberOfRatings.add(i,0);
        }

        //instantiates a hashmap at each index
        for(int i = 0; i<9; i++){
            bagsOfWords.add(i, new HashMap<>() );
        }

        DataWrapper dw = null;
        try {
            dw = new DataWrapper(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File is invalid");
        }

        String text;
        String rating;
        String review;
        String [] reviewArray;
        boolean ratingValidity;
        double currentRating;


        while(dw.dataReader.hasNext()){
            double correspondingIndex;
            text = dw.dataReader.nextLine();
            rating = text.substring(0,3);
            review = text.substring(5);
            ratingValidity = checkRating(rating);
            reviewArray = getWords(review);


            if(ratingValidity){
                totalNumberOfReviews++;
                currentRating = Double.parseDouble(rating);

                for(int i = 0; i<9; i++){
                    correspondingIndex =(0.5*i)+1;

                    if(currentRating<=(correspondingIndex)&&currentRating>(correspondingIndex-0.5)){
                        addToMap(bagsOfWords.get(i), reviewArray);
                        numberOfRatings.set(i,(numberOfRatings.get(i)+1));
                    }

                }

            }

        }
    }

    /**
     * Predicting the most likely rating for a given comment
     * @param reviewText, is not null and not empty
     * @return the lowest rating with the highest conditional probability for
     * the bag-of-words associated with the review being considered
     */
    public float getPredictedRating(String reviewText) {

        String[] reviewTextWords = getWords(reviewText);
        float[] ratings = {1.0F, 1.5F, 2.0F, 2.5F, 3.0F, 3.5F, 4.0F, 4.5F, 5.0F};

        double highestProbability = pRatingBagOfWords(reviewTextWords, 1.0F);
        float predictedRating = 1.0F;

        for (int i = 1; i<ratings.length; i++) {
            float rating = ratings[i];
            double probability = pRatingBagOfWords(reviewTextWords, rating);

            if (probability > highestProbability) {
                highestProbability = probability;
                predictedRating = rating;
            }
        }

        return predictedRating;
    }


    /**
     * Calculates the number of occurrences of a rating relative to the total number of review entries
     * @param ratingOriginal (should be float)
     * @return probability of the rating occurring or returns 0 if rating is invalid
     */
    public double pRating(float ratingOriginal) {

        String rating = Float.toString(ratingOriginal);
        double probability;
        double currentRating;
        int index;
        int occurrencesOfRating = 0;
        HashMap<String, Integer> currentHashMap;

            if (rating.charAt(2) == '5') {
                rating = rating.replace('5', '4');
            }
            currentRating = Double.parseDouble(rating);
            currentRating = Math.round(currentRating);
            index = (int) ((currentRating - 1) * 2);

            probability = this.numberOfRatings.get(index) / this.totalNumberOfReviews;
            return probability;

    }

    /**
     * get pWord
     * @param word
     * @return probability of the single word occuring
     */
    public double pWord(String word) {
        if (!allWords.containsKey(word)) {
            //insignificant decimal place
        }

        double occurences = allWords.get(word);
        double totalWords = allWords.size();

        return occurences / totalWords;

    }

    public int getRatingIndex(float rating) {
        float n = (rating - 1) *2;
        int index = (int)n;

        return index;
    }

    /**
     * get pWord at a certain rating
     * @param word
     * @return conditional probability of the word occuring at a certain rating
     */
    public double pWord(String word, float rating) {
        int ratingIndex = getRatingIndex(rating);
        if (!bagsOfWords.get(ratingIndex).containsKey(word)) {
            //some insignificant decimal place
        }

        double occurences = bagsOfWords.get(ratingIndex).get(word);
        double totalWords = bagsOfWords.get(ratingIndex).size();

        return occurences / totalWords;
    }

    /**
     * Get the pBagOfWords
     * @param reviewTextWords
     * @return probability of a bag-of-words occuring
     */
    public double pBagOfWords(String[] reviewTextWords) {
        double pBagOfWords = pWord(reviewTextWords[0]);

        for (int i = 1; i < reviewTextWords.length; i++) {
            pBagOfWords *= pWord(reviewTextWords[i]);
        }
        return pBagOfWords;

    }

    /**
     * Get the conditional probability, based on rating, of a bag-of-words
     * @param reviewTextWords the bag of words
     * @return conditional probability, based on rating, of a bag-of-words occuring
     */
    public double pBagOfWordsRating(String[] reviewTextWords, float rating) {
        double pBagOfWords = pWord(reviewTextWords[0], rating);

        for (int i = 1; i < reviewTextWords.length; i++) {
            pBagOfWords *= pWord(reviewTextWords[i], rating);
        }
        return pBagOfWords;
    }

    /**
     *
     * @param map
     * @param reviews
     */
    public void addToMap(HashMap map, String[] reviews){
        int count;
        String key;
        for(int i = 0; i<reviews.length; i++){
            key = reviews[i];
            if(map.containsKey(reviews[i])){
                count = (int) map.get(key);
                map.replace(key,count+1);

            } else {
                map.put(key,1);

            }
        }
    }

    /**
     * Get the probability that a bag of words occurs given a rating
     * @param reviewTextWords bag-of-words
     * @param rating
     * @return the probability that a bag of words occurs given a rating
     */
    public double pRatingBagOfWords(String[] reviewTextWords, float rating) {
        double pBagOfWordsRating = pBagOfWordsRating(reviewTextWords, rating);
        double pRating = pRating(rating);
        double pBagOfWords = pBagOfWords(reviewTextWords);

        double pRatingBagOfWords = (pBagOfWordsRating * pRating) / pBagOfWords;

        return pRatingBagOfWords;
    }

    /**
     * creates a map of all the words in the text file that we are building the model with,
     * and their associated counts
     * modifies: allWords
     */
    public void getAllWords() {
        allWords = new HashMap<>();

        for (Map map: bagsOfWords) {
            Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry<String, Integer> entry = iterator.next();
                String word = entry.getKey();
                int count = entry.getValue();

                // add entry to hash map of all words
                if (allWords.containsKey(word)) {
                    allWords.put(word, allWords.get(word) + count);
                } else {
                    allWords.put(word, count);
                }
            }
        }
    }


}
