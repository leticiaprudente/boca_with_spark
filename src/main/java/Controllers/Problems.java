package Controllers;

import static spark.Spark.*;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Classes.JsonToProblemTransformer;
import Classes.ProblemToJsonTransformer;
import Classes.Problem;
import Services.ProblemService;

import com.google.gson.JsonObject;


public class Problems {
    private static Map<String, Problem> problems = new HashMap<String, Problem>();

    public static void createProblemRoutes(){
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

        /*problem*/
        path("/problem", () -> {
            //filter: all fields are mandatory
            before("/addProblem", (req, res) -> {
                JsonToProblemTransformer jsonToProblemTransformer = new JsonToProblemTransformer();
                Problem problem = jsonToProblemTransformer.stringToObject(req.body());
                Integer status = null;
                try{
                    ProblemService problemservice = new ProblemService();
                    status = problemservice.beforeAddProblem(problem);
                }catch (SQLException e){
                    e.getStackTrace();
                }

                if  (status == null) {
                    res.type("application/json");
                    halt(400, "{\"error_code\":\"400\"," +
                            "\"error_msg\":\"What do you say to empty fields? Not today! All fields are required!!!\"}");
                }else if(status == 0){
                    res.type("application/json");
                    halt(205,"{\"error_code\":\"205\"," +
                            "\"error_msg\":\"The server successfully processed the request but the Problem already exists.\"}");
                }

            });

            post("/addProblem", "application/json", (req, res) -> {
                //RECEBER, TRANSFORMAR E ENVIAR PARA O SERVICE
                //RETORNO DA REQUISIÇÃO

                String bodyContent = req.body();
                JsonToProblemTransformer jsonToProblemTransformer = new JsonToProblemTransformer();
                Problem problem = jsonToProblemTransformer.stringToObject(bodyContent);

                ProblemService problemservice = new ProblemService();
                ProblemToJsonTransformer problemToJsonTransformer = new ProblemToJsonTransformer();
                Problem problemCreated = new Problem();

                try {
                    problemCreated = problemservice.addProblem(problem);
                    res.status(201);
                    res.type("application/json");

                } catch (SQLException e){
                    e.getStackTrace();

                };
                return problemToJsonTransformer.objectToString(problemCreated);

            });

            get("/searchAllProblems", (req, res) -> {
                JsonObject jsonObject = null;

                try{
                    ProblemService problemservice = new ProblemService();
                    jsonObject = problemservice.searchAllProblems();
                }catch(SQLException e){
                    e.getStackTrace();
                }
                if(jsonObject==null){
                    res.status(205);
                    res.type("application/json");
                    halt(205,"{\"error_code\":\"205\"," +
                            "\"error_msg\":\"The server successfully processed the request but the table Problem is empty.\"}");
                }

                res.type("application/json");
                return jsonObject ;

            });


            get("/searchProblemByID/:problem", (req, res) -> {
                JsonObject jsonObject = null;

                try{
                    ProblemService problemservice = new ProblemService();
                    jsonObject = problemservice.searchProblemByID(req.params(":problem"));

                    if(jsonObject==null){
                        res.type("application/json");
                        halt(205,"{\"error_code\":\"205\"," +
                                "\"error_msg\":\"The server successfully processed the request but the Problem doesn't exists in the database.\"}");
                    }

                }catch(SQLException e){
                    e.getStackTrace();
                }

                res.type("application/json");
                return jsonObject;
            });

            before("/deleteProblemByID/:problem", (req, res) -> {
                JsonObject jsonObject = null;
                try{
                    ProblemService problemService = new ProblemService();
                    jsonObject = problemService.beforeDeleteProblemByID(req.params(":problem"));
                    if(jsonObject==null){
                        res.type("application/json");
                        halt(205,"{\"error_code\":\"205\"," +
                                "\"error_msg\":\"The server successfully processed the request but the Problem doesn't exists in the database.\"}");
                    }
                }catch (SQLException e){
                    e.getStackTrace();
                }
            });

            delete("/deleteProblemByID/:problem", (req, res) -> {
                JsonObject jsonObject = new JsonObject();
                try{
                    ProblemService problemService = new ProblemService();
                    String problemVerify = problemService.deleteProblemByID(req.params(":problem"));

                    if( problemVerify != req.params(":problem") ){
                        res.status(406);
                        res.type("application/json");
                        return("{\"error_msg\":\"Oh no :(\"}");
                    }

                }catch (SQLException e){
                    e.getStackTrace();
                }

                File dir = new File("Files\\ExpectedAnswer\\"+req.params(":problem").trim().toUpperCase());
                System.out.println("dir: " +dir);
                if (dir.exists()){
                    System.out.println("The directory exists");
                    dir.delete();
                }

                res.status(200);
                res.type("application/json");
                return("{\"msg\":\"Problem and expected answers have been removed.\"}");
            });

        });
    }

}