# TextAnalyze

## Overview
This API was built using the Spark Framework and Maven. I've used SpringBoot in the past, but wanted to try something new with this. I was able to implement 3 of the 5 endpoints; average length, most common word, and median word(s).

## Assumptions
 * As stated in the assignment description, each endpoint is expecting a JSON body comprised of one tag, "text." If this tag is not provided in the JSON body or Malformed JSON syntax is supplied as input, the endpoint will return, but with an error message.
 * **Word Definition**: a valid word contains characters a-z and it may also contain a dash(-) or an apostrophe('). I'm allowing these characters because words like "sugar-free" and "doesn't" are valid. In words like "sugar-free" and "doesn't" I am including the special character(' or -) in the length of the word. I considered using an external dictionary API to determine if a word was valid because I don't want to make any assumptions about the language of the user. If this library was exclusively for the english language adding this feature would easily filter out invalid words. Currently jibberish like "asdfjkl" would pass since it contains valid characters.
 * **Invalid Words**: Based on my definition above an invalid word is a token that contains any numbers or special characters besides dash and apostrophe. When parsing through input I was left with the decision on how to handle invalid words. The two options I thought of were to not process any text that contains an invalid word or to only process valid words within a provided text. I chose to go with the latter option. In all 3 of my endpoints invalid words skipped, and the output is as if those words were not included to begin with.
 Example: average length on "the dog is1 fun" would return 3.0 because "is1" is an invalid word, thus it wasn't included in the processing.
 * **Tokenization**: 
    * Tokenization Approach:
      * trim leading and trailing whitespace
      * replace all forms of acceptable punction(?!.,:;") with nothing
      * to lowercase (except for average length because it doesn't matter here)
      * split on any amount of whitespace
    * One issue with this approach is that an invalid word like "d.og" would be turned into a valid word. I'm assuming here that the user will be doing their best to use proper punctuation. This could be resolved by putting more effort into tokenization
    
## Approach
 * **avg_len**
    * tokenize input into an array of strings
    * iterate over each word, if it's valid add length to a variable storing sum of characters
    * after iterating divide the sum of characters by the number of valid words
    * round the output to 2 decimal places
 * **most_com**
    * tokenize input into an array of strings
    * iterate over each words, and update HashMap that maps a word to a count
    * track max frequency throughout the iteration by updating a list that contains the most common word(s)
    * if length of the list is greater than 1, sort the list and return the first element
 * **median**
    * tokenize input into an array of strings
    * iterate over each word, and update a HashMap that maps a word to a WordCount object
      * WordCount has 2 fields, String word and int count.
      * if the word is a key in the HashMap, call the increment function on the WordCount object mapped to by the word
      * if the word is not in the HashMap, map the word to a new Instance of the WordCount object
    * after the iteration is complete create a list that contains all WordCount objects in the value set
    * use Collections.sort() to sort the list. WordCount implements Comparable and sorts based on the count of each word.
      * after sorting the list will be in ascending order based on the count field of each WordCount object
    * determine the median count(s) by looking at the middle index of the list. If the list has an even number of elements there may be two median values. 
    * once the median values have been determined, iterate over the list one final time, storing each work that has the same frequency of the median
      * Example: 
        * text = "cat cat dog dog horse horse horse cow cow cow"
        * after sorting the WordCount objects: (cat,2), (dog,2), (horse,3), (cow,3)
        * the median elements here are (dog,2) and (horse,3), but because cat and cow have median frequencies the output would include all 4 words; cat, dog, horse, and cow
        
        
**Code level details can be found in the comments of the source code.**

## Source Code Details
 * **WordsAnalyze.java** contains all 3 endpoints that read in the request, perform the necessary logic, and return a JSON body
 * **InputText.java** is class that corresponds to the input. If valid JSON is passed in the request body, an InputText object is created. The class has single field **text**. This class has 3 functions **getAverageWordLength()**, **getMostCommonWord()**, and **getMedianWord()**. These functions contain the core processing logic.
 * **WordCount.java** is a custom class used as part of the getMedianWord() function. The class allows for mapping between a word and it's count and allows for custom sorting by count by implementing the Comparable interface.
 * **NumberResponse.java**, **StringResponse.java**, and **ListResponse.java** are used to return output. Each class has one field, output, which is either a double, String, or ArrayList depending on the class.
 
## Future Work
If I was able to spend more time in this I would focused on the following areas:
 * build out robust unit testing cases
     * I unit tested this code thoroughly in the main method, but did not write custom unit tests
 * perfect the tokenization, this would involve spending more time understanding syntax and structure of the english language
 * find a better way to determine the validity of a word, possibly by using an external dictionary API
 * create better organizational structure of the different classes that are part of the source code
## Web Server Details
 * The web server is hosted at this endpoint: **18.208.165.136:4567**
 * Example commands below:
    * curl -X POST -H "Content-type: application/json" -d '{"text":"This sentence is sample input."}' http://18.208.165.136:4567/words/avg_len
    * curl -X POST -H "Content-type: application/json" -d '{"text":"This sentence is sample input."}' http://18.208.165.136:4567/words/most_com
    * curl -X POST -H "Content-type: application/json" -d '{"text":"This sentence is sample input."}' http://18.208.165.136:4567/words/median
