package Classes;
import com.google.gson.Gson;

public class JsonToProblemTransformer {

    public static Problem stringToObject(String bodyContent) throws Exception {
        Gson gson = new Gson();
        Problem problem = gson.fromJson(bodyContent, Problem.class);

        return problem;

    }
}