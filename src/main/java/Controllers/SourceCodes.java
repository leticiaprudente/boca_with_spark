package Controllers;

import Classes.*;
import Services.ExpectedAnswerService;
import Services.ProblemService;
import Services.SourceCodeService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.Base64;

import static spark.Spark.*;

public class SourceCodes {
    public static void createSourceCodesRoutes() {
        //execution Source Code History routes
        path("/sourceCode", () -> {
            before("/addSourceCode", (req, res) -> {
                JsonToObjectTransformer jsonToSourceCodeTransformer = new JsonToObjectTransformer();
                SourceCode sourceCode = jsonToSourceCodeTransformer.stringToSourceCode(req.body());

                Integer verify = null;
                try {
                    SourceCodeService sourceCodeService = new SourceCodeService();
                    verify = sourceCodeService.beforeAddSourceCode(sourceCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (verify == null) {
                    res.status(400);
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

            post("/addSourceCode", "application/json", (req, res) -> {
                String bodyContent = req.body();
                JsonToObjectTransformer jsonToSourceCodeTransformer = new JsonToObjectTransformer();
                SourceCode sourceCode = jsonToSourceCodeTransformer.stringToSourceCode(bodyContent);

                SourceCodeService sourceCodeService = new SourceCodeService();
                ObjectToJsonTransformer sourceCodeToJsonTransformer = new ObjectToJsonTransformer();

                SourceCode sourceCodeCreated = new SourceCode();

                try {
                    sourceCodeCreated = sourceCodeService.addSourceCode(sourceCode);
                    res.status(201);
                    res.type("application/json");

                } catch (SQLException e){
                    e.printStackTrace();

                };

                if( sourceCodeCreated == null ){
                    res.status(406);
                    res.type("application/json");
                    return("{\"error_code\":\"406\"," +
                            "\"error_msg\":\"Oh no :(\"}");
                }

                return sourceCodeToJsonTransformer.sourceCodeToString(sourceCodeCreated);

            });
        });

    }
}
