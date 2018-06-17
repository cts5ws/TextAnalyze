import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.BasicConfigurator;
import java.util.ArrayList;

import static spark.Spark.*;

/*
This function houses all of the endpoints. Each function is a wrapper for the corresponding logic in the InputText class.
Each function performs input validation, calls the corresponding function of the InputText class, and finally returns a
JSON object containing the desired output
 */
public class WordsAnalyze {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        // average length endpoint
        post("words/avg_len", (request, response) -> {
            response.type("application/json");

            InputText inputText;
            try{
                // read create InputText object from request body
                // this will throw MalformedJsonException if Json is bad
                inputText = new Gson().fromJson(request.body(), InputText.class);

                JsonParser parser = new JsonParser();
                JsonElement responseData = parser.parse((request.body()));
                JsonObject obj = responseData.getAsJsonObject();

                // check that the appropriate element is passed in the request body
                if(!obj.has("text"))
                    return new Gson().toJson(new StringResponse("Bad input: Expecting single string element(text)"));

            } catch (Exception e){
                return new Gson().toJson(new StringResponse("Bad input: Malformed JSON"));
            }

            // determine average length
            double averageLength = inputText.getAverageWordLength();

            // return json object by creating a response object
            return new Gson().toJson(new NumberResponse(averageLength));
        });

        // most common word endpoint
        post("words/most_com", (request, response) -> {
            response.type("application/json");

            InputText inputText;
            try{
                // read create InputText object from request body
                // this will throw MalformedJsonException if Json is bad
                inputText = new Gson().fromJson(request.body(), InputText.class);

                JsonParser parser = new JsonParser();
                JsonElement responseData = parser.parse((request.body()));
                JsonObject obj = responseData.getAsJsonObject();

                // check that the appropriate element is passed in the request body
                if(!obj.has("text"))
                    return new Gson().toJson(new StringResponse("Bad input: Expecting single string element(text)"));

            } catch (Exception e){
                return new Gson().toJson(new StringResponse("Bad input: Malformed JSON"));
            }

            // determine most common word
            String mostCommonWord = inputText.getMostCommonWord();

            // return json object by creating a response object
            return new Gson().toJson(new StringResponse(mostCommonWord));
        });

        // median word endpoint
        post("words/median", (request, response) -> {
            response.type("application/json");

            InputText inputText;
            try{
                // read create InputText object from request body
                // this will throw MalformedJsonException if Json is bad
                inputText = new Gson().fromJson(request.body(), InputText.class);

                JsonParser parser = new JsonParser();
                JsonElement responseData = parser.parse((request.body()));
                JsonObject obj = responseData.getAsJsonObject();

                // check that the appropriate element is passed in the request body
                if(!obj.has("text"))
                    return new Gson().toJson(new StringResponse("Bad input: Expecting single string element(text)"));

            } catch (Exception e){
                return new Gson().toJson(new StringResponse("Bad input: Malformed JSON"));
            }

            // determine median word
            ArrayList<String> medianList = inputText.getMedianWord();

            // return json object by creating a response object
            return new Gson().toJson(new ListResponse(medianList));
        });
    }
}
