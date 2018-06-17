import java.util.ArrayList;

/*
This class is used to construct the list response body
 */

public class ListResponse {

    private ArrayList<String> output;

    public ListResponse(ArrayList<String> output) {
        this.output = output;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }
}
