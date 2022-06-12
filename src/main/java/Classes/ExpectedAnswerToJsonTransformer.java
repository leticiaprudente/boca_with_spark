package Classes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ExpectedAnswerToJsonTransformer {
    public static String objectToString(ExpectedAnswer expectedAnswer){
        Gson gson = new Gson();
        return gson.toJson(expectedAnswer);
    }
    public static String jsonObjectToString(JsonObject jsonObject){
        Gson gson = new Gson();
        return gson.toJson(jsonObject);
    }
}
