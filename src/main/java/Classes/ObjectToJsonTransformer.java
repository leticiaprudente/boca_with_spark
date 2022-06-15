package Classes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ObjectToJsonTransformer {
    public static String problemToString(Problem problem){
        Gson gson = new Gson();
        return gson.toJson(problem);
    }

    public static String expectedAnswerToString(ExpectedAnswer expectedAnswer){
        Gson gson = new Gson();
        return gson.toJson(expectedAnswer);
    }

    public static String sourceCodeToString(SourceCode sourceCode){
        Gson gson = new Gson();
        return gson.toJson(sourceCode);
    }

    public static String jsonObjectToString(JsonObject jsonObject){
        Gson gson = new Gson();
        return gson.toJson(jsonObject);
    }



}
