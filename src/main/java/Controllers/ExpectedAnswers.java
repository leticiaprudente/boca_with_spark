package Controllers;

import Classes.ExpectedAnswer;
import Classes.ObjectToJsonTransformer;
import Classes.JsonToObjectTransformer;
import Services.ExpectedAnswerService;

import java.sql.SQLException;
import static spark.Spark.*;

public class ExpectedAnswers {

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
                JsonToObjectTransformer jsonToExpectedAnswerTransformer = new JsonToObjectTransformer();
                ExpectedAnswer expectedAnswer = jsonToExpectedAnswerTransformer.stringToExpectedAnswer(req.body());
                Integer verify = null;
                try {
                    ExpectedAnswerService expectedAnswerService = new ExpectedAnswerService();
                    verify = expectedAnswerService.beforeAddExpectedAnswer(expectedAnswer);
                } catch (Exception e) {
                    e.getStackTrace();
                }

                if (verify == null) {
                    res.type("application/json");
                    halt(400, "{\"error_code\":\"400\"," +
                            "\"error_msg\":\"What do you say to empty fields? Not today! All fields are required!!!\"}");
                }

                if (verify == 0){
                    res.status(205);
                    res.type("application/json");
                    halt(205,"{\"error_code\":\"205\"," +
                            "\"error_msg\":\"The server successfully processed the request but Problem doesn't exists in the database.\"}");
                }
            });

            post("/addExpectedAnswer", "application/json", (req, res) -> {
                //content: enconding base64
                String bodyContent = req.body();
                JsonToObjectTransformer jsonToEA = new JsonToObjectTransformer();
                ExpectedAnswer expectedAnswer = jsonToEA.stringToExpectedAnswer(bodyContent);

                ExpectedAnswerService expectedAnswerService = new ExpectedAnswerService();
                ObjectToJsonTransformer expectedAnswerToJson = new ObjectToJsonTransformer();
                ExpectedAnswer expectedAnswerCreated = new ExpectedAnswer();

                try {
                    expectedAnswerCreated = expectedAnswerService.addExpectedAnswer(expectedAnswer);
                    res.status(201);
                    res.type("application/json");

                } catch (SQLException e){
                    e.getStackTrace();

                }
                return expectedAnswerToJson.expectedAnswerToString(expectedAnswerCreated);

            });

        });
    }
}