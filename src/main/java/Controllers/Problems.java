package Controllers;

import static spark.Spark.*;

import java.io.File;
import java.sql.SQLException;

import Classes.JsonToObjectTransformer;
import Classes.ObjectToJsonTransformer;
import Classes.Problem;
import Services.ProblemService;

import com.google.gson.JsonObject;


public class Problems {

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
                JsonToObjectTransformer jsonToProblemTransformer = new JsonToObjectTransformer();
                Problem problem = jsonToProblemTransformer.stringToProblem(req.body());
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
                String bodyContent = req.body();
                JsonToObjectTransformer jsonToProblemTransformer = new JsonToObjectTransformer();
                Problem problem = jsonToProblemTransformer.stringToProblem(bodyContent);

                ProblemService problemservice = new ProblemService();
                ObjectToJsonTransformer problemToJsonTransformer = new ObjectToJsonTransformer();
                Problem problemCreated = new Problem();

                try {
                    problemCreated = problemservice.addProblem(problem);
                    res.status(201);
                    res.type("application/json");

                } catch (SQLException e){
                    e.getStackTrace();

                };
                return problemToJsonTransformer.problemToString(problemCreated);

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
                Boolean verify = false;
                try{
                    ProblemService problemService = new ProblemService();
                    verify = problemService.beforeDeleteProblemByID(req.params(":problem"));

                    //AJUSTAR
                    if( (req.params(":problem").trim().length()) == 0 ){
                        res.type("application/json");
                        halt(205,"{\"error_code\":\"205\"," +
                                "\"error_msg\":\"The server has successfully processed the request but it is necessary to send the Problem ID.\"}");
                    }

                    if(!verify){
                        res.type("application/json");
                        halt(205,"{\"error_code\":\"205\"," +
                                "\"error_msg\":\"The server successfully processed the request but the Problem doesn't exists in the database.\"}");
                    }
                }catch (SQLException e){
                    e.getStackTrace();
                }
            });

            delete("/deleteProblemByID/:problem", (req, res) -> {
                try{
                    ProblemService problemService = new ProblemService();
                    Boolean problemVerify = problemService.deleteProblemByID(req.params(":problem"));

                    if( !problemVerify ){
                        res.status(406);
                        res.type("application/json");
                        return("{\"error_msg\":\"Oh no :(\"}");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                File dir = new File("Files\\ExpectedAnswer\\"+req.params(":problem").trim().toUpperCase());
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