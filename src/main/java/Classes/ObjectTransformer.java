package Classes;

import com.google.gson.Gson;

public class ObjectTransformer {
    public static String objectToString(Problem problem){
        Gson gson = new Gson();
        return gson.toJson(problem);
    }
}