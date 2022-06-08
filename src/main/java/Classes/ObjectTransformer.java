package Classes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ObjectTransformer {
    public static String objectToString(Problem problem){
        Gson gson = new Gson();
        return gson.toJson(problem);
    }
    public static String jsonObjectToString(JsonObject jsonObject){
        Gson gson = new Gson();
        return gson.toJson(jsonObject);
    }
}