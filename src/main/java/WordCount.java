/*
This class is used as part of the getMedianWord function of the Input Text class
The object stores a word and corresponding count.
The class implements the Comparable interface so that WordCount objects can be sorted in a custom way
In the getMedianWord function they are sorted in ascending order by count value.
For this reason the middle index of a sorted list of WordCount objects would contain the median word
 */
public class WordCount implements  Comparable<WordCount>{
    private String word;
    private int count;

    public WordCount(String word) {
        this.word = word;
        this.count = 1;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int incrementCount(){
        this.count++;
        return this.count;
    }

    public int compareTo(WordCount wordCount){
        return this.getCount() - wordCount.getCount();
    }

    @Override
    public String toString(){
        return this.getWord() + "," + this.getCount();
    }
}
