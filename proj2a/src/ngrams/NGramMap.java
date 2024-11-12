package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    HashMap<String, TimeSeries> map;
    TimeSeries allCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        map = new HashMap<>();
        allCounts = new TimeSeries();

        In in = new In(wordsFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");

            String word = splitLine[0];
            Integer year = Integer.parseInt(splitLine[1]);
            Double number = Double.parseDouble(splitLine[2]);

            TimeSeries ts = new TimeSeries();
            ts.put(year, number);

            if (map.containsKey(word)) {
                map.get(word).put(year, number);
            } else {
                map.put(word, ts);
            }
        }

        in = new In(countsFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            allCounts.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        if (map.containsKey(word)) {
            for (Integer i : map.get(word).keySet()) {
                if (i >= startYear && i <= endYear) {
                    result.put(i, map.get(word).get(i));
                }
            }
        }
        return result;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        result.putAll(allCounts);
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        if (map.containsKey(word)) {
            for (Integer i : map.get(word).keySet()) {
                if (i >= startYear && i <= endYear) {
                    result.put(i, map.get(word).get(i) / allCounts.get(i));
                }
            }
        }
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            if (map.containsKey(word)) {
                result = result.plus(weightHistory(word, startYear, endYear));
            }
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
