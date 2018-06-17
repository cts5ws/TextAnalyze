import com.google.gson.*;
import com.google.gson.stream.MalformedJsonException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;


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

    public double getAverageWordLength(){

        // variable to hold total character count
        int totalLengthCount = 0;

        // create array of words, splitting on any amount of space
        // replacing acceptable punctuation with nothing
        String[] words = this.text.replaceAll("[?!.,:;\"]", "").split("\\s+");

        // record word count before processing
        int wordCount = words.length;

        System.out.println(Arrays.toString(words));

        // iterate over each word in the inputted string
        for(String word : words){

            //  if word doesn't contain characters besides letters, apostrophe, and dash
            //  add word length to total character count
            if(word.matches("^[A-Za-z\\'-]*$")){
                totalLengthCount += word.length();

            // if word is illegal (see README for explanation), decrease word count
            // since invalid words don't count towards the average
            } else{
                System.out.println("Mismatch: " + word);
                wordCount--;
            }
        }

        // determine unrounded average by dividing character count by the number of valid words
        double averageUnrounded = totalLengthCount / (double)wordCount;

        return averageUnrounded;
    }

    public String getMostCommonWord(){

        // create array of words, splitting on any amount of space
        // replacing acceptable punctuation with nothing
        String[] words = this.text.replaceAll("[?!.,:;\"]", "")
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

            System.out.println(mostCommonWords.toString());
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

    public static void main(String[] args) {
        String text = "\"te456456456xt1\":\"the the dog is a big 1Dog the dog\"}";
        //InputText inputText = new Gson().fromJson(text, InputText.class);
        //double averageLength = inputText.getAverageWordLength();
        //System.out.println(averageLength);

        //String common = inputText.getMostCommonWord();
        //System.out.println(common);

        JsonParser parser = new JsonParser();
        JsonElement responseData = parser.parse(text);
        if(!responseData.isJsonObject())
            System.out.println(new Gson().toJson(new StringResponse("Bad input: Malformed JSON")));

        JsonObject obj = responseData.getAsJsonObject();
        if(!obj.has("text"))
            System.out.println(new Gson().toJson(new StringResponse("Bad input: Expecting single string element(text)")));


        //System.out.println(inputText.getAverageWordLength());

    }
}
