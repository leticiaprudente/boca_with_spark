package Controllers;

import Classes.ExpectedAnswer;
import Classes.ExpectedAnswerToJsonTransformer;
import Classes.JsonToExpectedAnswerTransformer;
import Classes.Problem;
import Services.ExpectedAnswerService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ExpectedAnswers {
    private static Map<String, Problem> problems = new HashMap<String, Problem>();

    public static void createExpectedAnswersRoutes() {
        /*errors*/
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"error_code\":\"404\"," +
                    "\"error_msg\":\"Sorry, URL Not Found\"}";
        });

        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"error_code\":\"500\"," +
                    "\"error_msg\":\"Ops! Internal Server Error\"}";
        });
        //expected Answer routes
        path("/expectedAnswer", () -> {
            before("/addExpectedAnswer", (req, res) -> {
                JsonToExpectedAnswerTransformer jsonToExpectedAnswerTransformer = new JsonToExpectedAnswerTransformer();
                ExpectedAnswer expectedAnswer = jsonToExpectedAnswerTransformer.stringToObject(req.body());
                Boolean status = null;
                try {
                    ExpectedAnswerService expectedAnswerService = new ExpectedAnswerService();
                    status = expectedAnswerService.beforeAddExpectedAnswer(expectedAnswer);
                } catch (Exception e) {
                    e.getStackTrace();
                }

                if (status == false) {
                    res.type("application/json");
                    halt(400, "{\"error_code\":\"400\"," +
                            "\"error_msg\":\"What do you say to empty fields? Not today! All fields are required!!!\"}");
                }
            });


            post("/addExpectedAnswer", "application/json", (req, res) -> {

                String bodyContent = req.body();
                JsonToExpectedAnswerTransformer jsonToEA = new JsonToExpectedAnswerTransformer();
                ExpectedAnswer expectedAnswer = jsonToEA.stringToObject(bodyContent);

                ExpectedAnswerService expectedAnswerService = new ExpectedAnswerService();
                ExpectedAnswerToJsonTransformer expectedAnswerToJson = new ExpectedAnswerToJsonTransformer();
                ExpectedAnswer expectedAnswerCreated = new ExpectedAnswer();

                try {
                    expectedAnswerCreated = expectedAnswerService.addExpectedAnswer(expectedAnswer);
                    res.status(201);
                    res.type("application/json");

                } catch (SQLException e){
                    e.getStackTrace();

                };
                return expectedAnswerToJson.objectToString(expectedAnswerCreated);

            });

        });
    }
}