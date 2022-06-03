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
        path("/problem", () -> {

        });

        //filter: all fields are mandatory
        /*before("/addProblem", (req, res) -> {


            String filename = req.body("filename") ;
            String problem = req.queryParams("problem") ;
            String lps = req.queryParams("lps") ;  //java|c|c++|python

            if  (!( filename != "" || problem != "" || lps != "")) {
                halt(400, "Preencha todos os campos!");
            }
        });*/

        post("/addProblem", (req, res) -> {
            //RECEBER, TRANSFORMAR E ENVIAR PARA O SERVICE
            //RETORNO DA REQUISIÇÃO
            System.out.println(req.body());

            String bodyContent = req.body();
            JsonTransformer jsonTransformer = new JsonTransformer();
            Problem problem = jsonTransformer.stringToObject(bodyContent);

            ProblemService problemservice = new ProblemService();

            Problem problemCreated;
            try {
                problemCreated = problemservice.createProblem(problem);

            } catch (Exception e){
                res.status(500);
                return "errow";
            };

            ObjectTransformer objectTransformer = new ObjectTransformer();
            res.status(201);
            return objectTransformer.objectToString(problemCreated);


            /*String filename = req.queryParams("filename");
            String problem = req.queryParams("problem");
            String lps = req.queryParams("lps");  //java|c|c++|python


                try {
                    Problem newProblem = new Problem(filename, problem, lps);
                    problems.put(String.valueOf(problem), newProblem);

                    String insertIntoTableProblem = "INSERT INTO problem (problem, filename, lps) " +
                            "VALUES ('" + problem + "' , '" + filename + "' , '" + lps + "' );";
                    StatementSQLite tab = new StatementSQLite();
                    tab.executeSQL(insertIntoTableProblem);

                    System.out.println("SQL command executed.");

                    res.status(201); // Created
                    return ("OK");

                } catch (Exception e) {
                    e.getStackTrace();
                    res.status(400); //
                    return ("Error");
                }*/

        });

        get("/searchAllProblems", (request, response) -> {

            String selectProblem = "select * from problem;" ;

            SelectSQLite slct = new SelectSQLite();
            slct.selectAll(selectProblem);

            String problemList = "";
            for (String p : problems.keySet()) {
                problemList += p + " ";
            }
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
    }

}