package Classes;

import com.google.gson.Gson;

public class JsonToObjectTransformer { //criar interface (POO)
    public static ExpectedAnswer stringToExpectedAnswer(String bodyContent) throws Exception {
        Gson gson = new Gson();
        ExpectedAnswer expectedAnswer = gson.fromJson(bodyContent, ExpectedAnswer.class);

        return expectedAnswer;
    }

    public static Problem stringToProblem(String bodyContent) throws Exception {
        Gson gson = new Gson();
        Problem problem = gson.fromJson(bodyContent, Problem.class);

        return problem;
    }

    public static SourceCode stringToSourceCode(String bodyContent) throws Exception {
        Gson gson = new Gson();
        SourceCode sourceCode = gson.fromJson(bodyContent, SourceCode.class);

        return sourceCode;
    }


}
