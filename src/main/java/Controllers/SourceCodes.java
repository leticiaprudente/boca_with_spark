package Controllers;

import Classes.JsonToObjectTransformer;
import Classes.SourceCodeHistory;
import Services.SourceCodeService;

import static spark.Spark.*;

public class SourceCodes {
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

        //execution History routes
        path("/sourceCode", () -> {
            before("", (req, res) -> {
                JsonToObjectTransformer jsonToSourceCodeTransformer = new JsonToObjectTransformer();
                SourceCodeHistory sourceCode = jsonToSourceCodeTransformer.stringToSourceCode(req.body());
                Integer verify = null;
                try {
                    SourceCodeService sourceCodeService = new SourceCodeService();
                    verify = sourceCodeService.beforeAddSourceCode(sourceCode);
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


        });
    }
}
