package spark.routes;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import Classes.Problem;

public class Problems {
    private static Map<String, Problem> problems = new HashMap<String, Problem>();

    public static void main(String[] args) {
        post("/addProblem", (req, res) -> {
            String filename = req.queryParams("filename") ;
            String problem = req.queryParams("problem") ;
            String lps = req.queryParams("lps") ;  /*java|c|c++|python*/

            try {
                Problem newProblem = new Problem(filename, problem, lps) ;

                System.out.println("teste");

                problems.put(String.valueOf(problem), newProblem);

                res.status(201); // Created
                return("OK");

            }
            catch(Exception e) {
                e.getStackTrace();
                res.status(404); // Not found
                return("Error");
            }
        });

        get("/searchAllProblems", (request, response) -> {
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