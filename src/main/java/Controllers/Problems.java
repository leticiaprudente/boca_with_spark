package Controllers;

import static spark.Spark.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Classes.JsonTransformer;
import Classes.ObjectTransformer;
import Classes.Problem;
import Classes.StatementSQLite;
import Services.ProblemService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.xml.transform.Result;

public class Problems {
    private static Map<String, Problem> problems = new HashMap<String, Problem>();

    public static void createRoutes(){
        /*errors*/
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"error_code\":\"404\"," +
                    "\"error_message\":\"Sorry, URL Not Found\"}";
        });

        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"error_code\":\"500\"," +
                    "\"error_message\":\"Ops! Internal Server Error\"}";
        });
/*
        get("/throwexception", (request, response) -> {
            throw new SQLException();
        });

        exception(spark.HaltException.class, (ex, req, res) -> {
            res.status(204);
            res.type("application/json");
            halt("{\"return_code\":\"204\"," +
                    "\"error_message\":\"The server successfully processed the request but the Problem are not found.\"}");
        });
*/

        /*problem*/
        path("/problem", () -> {
            //filter: all fields are mandatory
            before("/addProblem", (req, res) ->
            {
                String bodyContent = req.body();
                JsonTransformer jsonTransformer = new JsonTransformer();
                Problem problem = jsonTransformer.stringToObject(bodyContent);

                if  ( !(problem.filename.trim().length() != 0) || !(problem.problem.trim().length() != 0) || !(problem.lps.trim().length() != 0 ) ) {
                    res.type("application/json");
                    halt(400, "{\"error_code\":\"400\"," +
                            "\"error_message\":\"What do you say to empty fields? Not today! All fields are required!!!\"}");

                }
            });

            post("/addProblem", "application/json", (req, res) -> {
                //RECEBER, TRANSFORMAR E ENVIAR PARA O SERVICE
                //RETORNO DA REQUISIÇÃO

                String bodyContent = req.body();
                JsonTransformer jsonTransformer = new JsonTransformer();
                Problem problem = jsonTransformer.stringToObject(bodyContent);

                ProblemService problemservice = new ProblemService();

                Problem problemCreated;
                try {
                    problemCreated = problemservice.createProblem(problem);

                } catch (Exception e){
                    res.type("application/json");
                    res.status(500);
                    return "{\"error_msg\":\"500 - errow\"}";
                    //ajustar para tratar throws expections expecificas
                };

                ObjectTransformer objectTransformer = new ObjectTransformer();
                res.status(201);
                res.type("application/json");
                return objectTransformer.objectToString(problemCreated);

            });

            get("/searchAllProblems", (req, res) -> {

                String selectProblem = "SELECT filename, problem, lps FROM problem ORDER BY problem; " ;
                StatementSQLite statement = new StatementSQLite();

                ResultSet resultSet = statement.selectTable(selectProblem);

                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();

                while(resultSet.next()) {
                    JsonObject record = new JsonObject();
                    //Inserting key-value pairs into the json object
                    record.addProperty("filename", resultSet.getString("filename"));
                    record.addProperty("problem", resultSet.getString("problem"));
                    record.addProperty("lps", resultSet.getString("lps"));
                    jsonArray.add(record);
                }
                jsonObject.add("Problems", jsonArray);
                res.type("application/json");
                return jsonObject ;
            });


            get("/searchProblem/:problem", (req, res) -> {
                String selectProblemById = "SELECT filename, problem, lps FROM problem WHERE problem = '" +req.params(":problem")+ "';" ;
                StatementSQLite statement = new StatementSQLite();
                ResultSet resultSet = statement.selectTable(selectProblemById);
                JsonObject jsonObject = new JsonObject();
                if(resultSet.next()){
                    jsonObject.addProperty("filename", resultSet.getString("filename"));
                    jsonObject.addProperty("problem", resultSet.getString("problem"));
                    jsonObject.addProperty("lps", resultSet.getString("lps"));
                } else {
                    res.status(204);
                    res.type("application/json");
                    halt("{\"return_code\":\"204\"," +
                            "\"error_message\":\"The server successfully processed the request but the Problem does not exists in the database.\"}");
                }
                res.status(200);
                res.type("application/json");
                return jsonObject;
            });

            before("/deleteProblem/:problem", (req, res) ->
            {
                String selectCountProblemById = "SELECT * FROM problem WHERE problem = '" +req.params(":problem").toUpperCase()+ "';" ;
                System.out.println("before, problem: " +req.params(":problem").toUpperCase());
                StatementSQLite statement = new StatementSQLite();
                statement.statementeSQLite(selectCountProblemById);

                ResultSet resultSet = statement.selectTable(selectCountProblemById);
                JsonObject jsonObject = new JsonObject();

                if  ( !(resultSet.next())) {
                    res.status(204);
                    res.type("application/json");
                    halt("{\"return_code\":\"204\"," +
                            "\"error_message\":\"The server successfully processed the request but the Problem does not exists in the database.\"}");
                }
            });

            delete("/deleteProblem/:problem", (req, res) -> {
                String problem = req.params(":problem");
                String deleteProblemById = "DELETE FROM problem WHERE problem = '" +req.params(":problem")+ "';" ;
                StatementSQLite deleteProblem = new StatementSQLite();
                deleteProblem.statementeSQLite(deleteProblemById) ;
                res.status(200);
                res.type("application/json");
                return("{\"msg\":\"Problem removed\"}");
            });

        });
    }

}