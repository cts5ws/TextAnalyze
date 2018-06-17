import com.google.gson.*;
import java.util.*;


public class InputText {
    private String text;

    public InputText(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*
      - determines average word length of a text string
      - assumptions and decisions discussed in README
     */
    public double getAverageWordLength(){

        // handle empty string
        if(this.text.length() == 0){
            return 0.0;
        }

        // variable to hold total character count
        int totalLengthCount = 0;

        // create array of words, splitting on any amount of space
        // replacing acceptable punctuation with nothing
        String[] words = this.text.trim().replaceAll("[?!.,:;\"]", "").split("\\s+");

        // record word count before processing
        int wordCount = words.length;

        // iterate over each word in the inputted string
        for(String word : words){

            //  if word doesn't contain characters besides letters, apostrophe, and dash
            //  add word length to total character count
            if(word.matches("^[A-Za-z\\'-]*$")){
                totalLengthCount += word.length();

            // if word is illegal (see README for explanation), decrease word count
            // since invalid words don't count towards the average
            } else{
                wordCount--;
            }
        }

        // return 0.0 if all words were invalid
        if(wordCount == 0){
            return 0.0;
        }

        // determine unrounded average by dividing character count by the number of valid words
        double averageUnrounded = totalLengthCount / (double)wordCount;

        // round output to 2 decimal places
        return Math.round(averageUnrounded * 100D) / 100D;
    }

    /*
    - determines most common word in text string
    - assumptions and decisions discussed in README
    */
    public String getMostCommonWord(){

        // handle empty string, most common word would be empty string
        if(this.text.length() == 0){
            return "";
        }

        // create array of words, splitting on any amount of space
        // replacing acceptable punctuation with nothing
        // converting everything to lower case
        String[] words = this.text.trim().replaceAll("[?!.,:;\"]", "")
                .toLowerCase().split("\\s+");

        // create Hashmap to store counts of words
        HashMap<String, Integer> wordCount = new HashMap<>();

        // create list to hold max words
        LinkedList<String> mostCommonWords = new LinkedList<>();
        // create variable to hold max frequency of a word
        int maxFreq = 0;

        // iterate over each word in the inputted string
        for(String word : words){

            //  if word doesn't contain characters besides letters, apostrophe, and dash
            //  add word length to total character count
            if(word.matches("^[A-Za-z\\'-]*$")){

                // store updated count for each iteration
                int count;

                // increment value if word is already a key
                if(wordCount.containsKey(word)){
                    wordCount.put(word, wordCount.get(word) + 1);
                    count = wordCount.get(word);

                // if key doesn't exist put with value 1
                } else {
                    wordCount.put(word, 1);
                    count = 1;
                }

                // if count is greater than current max
                // create new list contain new most common word
                if(count > maxFreq){
                    mostCommonWords = new LinkedList<>();
                    mostCommonWords.add(word);

                    // update max frequency variable
                    maxFreq = count;

                // if count is equal to max, add word to list
                } else if(count == maxFreq){
                    mostCommonWords.add(word);
                }
            }
        }

        // if no valid words, return empty string as it is the most common
        if(mostCommonWords.size() == 0)
            return "";

        // if there are multiple most common words, sort alphabetically
        if(mostCommonWords.size() > 1){
            Collections.sort(mostCommonWords);
        }

        // return the first element in the list
        return mostCommonWords.get(0);
    }

    /*
    - determines median words in frequency distribution in text string
    - assumptions and decisions discussed in README
    */
    public ArrayList<String> getMedianWord(){

        // handle empty string, median list would be empty
        if(this.text.length() == 0){
            return new ArrayList<>();
        }

        // create array of words, splitting on any amount of space
        // replacing acceptable punctuation with nothing
        // convert everything to lower case
        String[] words = this.text.trim().replaceAll("[?!.,:;\"]", "")
                .toLowerCase().split("\\s+");

        // map strings to WordCount class
        // this mapping allows for words to mapped to a count
        // once processing is complete WordCount objects will be used to determine median
        HashMap<String, WordCount> wordMap = new HashMap<>();

        // iterate over each word in the inputted string
        for(String word : words){

            //  if word doesn't contain characters besides letters, apostrophe, and dash
            //  add word length to total character count
            if(word.matches("^[A-Za-z\\'-]*$")){

                // if maps contains word, increment count within the object
                if(wordMap.containsKey(word)){
                    wordMap.get(word).incrementCount();

                // otherwise create a new object, with count set to 1 upon initialization
                } else {
                    wordMap.put(word, new WordCount(word));
                }
            }
        }

        // handle no valid words
        if(wordMap.keySet().size() == 0){
            return new ArrayList<>();
        }

        // after calculating all the counts, store the value set in an arraylist
        ArrayList<WordCount> counts = new ArrayList<>(wordMap.values());

        // sort the Arraylist, using Comparable
        // sorts in ascending order based on count of word
        Collections.sort(counts);

        // create list to hold return value
        ArrayList<String> medianList = new ArrayList<>();

        // variable to store frequency of median word
        int medianFreq;
        // set default value to -1 in the case that there is an odd number of words and only one media
        int secondMedianFreq = -1;

        //determine median values
        // even number of elements, two median counts
        if(counts.size() % 2 == 0){

            // determine median count value
            medianFreq = counts.get(counts.size() / 2).getCount();
            // determine the second median count
            secondMedianFreq = counts.get((counts.size() / 2) - 1).getCount();

        // one median count
        } else {
            // determine median count value
            medianFreq = counts.get(counts.size() / 2).getCount();
        }

        // collect all words with either of the median counts
        // this handles the situation when the two median counts are the  same and differ
        for(WordCount wordCount : counts){
            if(wordCount.getCount() == medianFreq || (secondMedianFreq != -1 && wordCount.getCount() == secondMedianFreq)){
                medianList.add(wordCount.getWord());
            }
        }

        return medianList;
    }

    public static void main(String[] args) {
    }
}
