package Classes;

import com.google.gson.Gson;

public class JsonToObjectTransformer {
    public static ExpectedAnswer stringToExpectedAnswer(String bodyContent) throws Exception {
        Gson gson = new Gson();
        ExpectedAnswer expectedAnswer = gson.fromJson(bodyContent, ExpectedAnswer.class);

        return expectedAnswer;

    }

    public static SourceCodeHistory stringToSourceCode(String bodyContent) throws Exception {
        Gson gson = new Gson();
        SourceCodeHistory sourceCode = gson.fromJson(bodyContent, SourceCodeHistory.class);

        return sourceCode;

    }

    public static Problem stringToProblem(String bodyContent) throws Exception {
        Gson gson = new Gson();
        Problem problem = gson.fromJson(bodyContent, Problem.class);

        return problem;

    }


}
