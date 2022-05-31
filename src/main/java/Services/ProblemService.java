package Services;

import Classes.Problem;
import spark.routes.Problems;

import static spark.Spark.get;

public class ProblemService {
    public ProblemService(final ProblemService problemService) {
        get("/problems/", (req, res) -> {
            return "osrrrrraa";
        });
    }


    // returns a list of all problems
    /*public List<Problems> getAllProblems() {
        return ;
    }
*/

    // updates an existing user
    //public Problem updateUser(String id, String name, String email) { .. }
}
