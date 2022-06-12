package Classes;

import com.google.gson.Gson;

public class JsonToExpectedAnswerTransformer {
    public static ExpectedAnswer stringToObject(String bodyContent) throws Exception {
        Gson gson = new Gson();
        ExpectedAnswer expectedAnswer = gson.fromJson(bodyContent, ExpectedAnswer.class);

        return expectedAnswer;

    }
}
