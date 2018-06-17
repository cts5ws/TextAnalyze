import java.util.ArrayList;

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
