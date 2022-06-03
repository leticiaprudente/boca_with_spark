package Controllers;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import Classes.JsonTransformer;
import Classes.ObjectTransformer;
import Classes.Problem;
import Classes.SelectSQLite;

import Services.ProblemService;

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

        /*problem*/
        path("/problem", () -> {
            //filter: all fields are mandatory
            before("/addProblem", (req, res) ->
            {
                String bodyContent = req.body();
                JsonTransformer jsonTransformer = new JsonTransformer();
                Problem problem = jsonTransformer.stringToObject(bodyContent);

                if  ( !(problem.filename.trim().length() != 0) || !(problem.problem.trim().length() != 0) || !(problem.lps.trim().length() != 0 ) ) {
                    System.out.println("NOT TODAY!");
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

            get("/searchAllProblems", (request, response) -> {

                String selectProblem = "select * from problem;" ;

                SelectSQLite select = new SelectSQLite();
                select.selectAll(selectProblem);


                String problemList = "";
                for (String problem : problems.keySet()) {
                    problemList += problem + " ";
                }
                /*res.type("application/json");
                return objectTransformer.objectToString(problemCreated);*/

                return problemList;
            });

            get("/searchProblem/:problem", (req, res) -> {
                try{
                    Problem problem = problems.get(req.params(":problem"));
                    if (problem != null) {
                        return "File name: " + problem.getFilename() + ", Problem: " + problem.getProblem() + ", LPS: " + problem.getLps();
                    } else {
                        res.status(404); // 404 Not found
                        return "not found";
                    }
                }
                catch(Exception e) {
                    e.getStackTrace();
                    res.status(404); // Not found
                    return("Error");
                }

            });

            delete("/deleteProblem/:problem", (req, res) -> {
                String problem = req.params(":problem");
                Problem deleteProblem = problems.remove(problem);
                if (deleteProblem != null) {
                    return "Problem '" + problem + "' deleted";
                } else {
                    res.status(404); // 404 Not found
                    return "not found";
                }
            });

        });
    }

}