package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer {

    public static Problem stringToObject(String bodyContent) throws Exception {
        Gson gson = new Gson();
        Problem problem = gson.fromJson(bodyContent, Problem.class);

        return problem;

    }
}