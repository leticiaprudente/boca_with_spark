package Services;

import Classes.Problem;
import Classes.StatementSQLite;

import static spark.Spark.get;

public class ProblemService {
    public static Problem createProblem(Problem problem) throws Exception{
        //VERIFICAÇÃO E REGRAS DE NEGÓCIO NO SERVICE
        try {

            String insertIntoTableProblem = "INSERT INTO problem (problem, filename, lps) " +
                    "VALUES ('" + problem.problem.toUpperCase() + "' , '" + problem.filename + "' , '" + problem.lps + "' );";
            StatementSQLite tab = new StatementSQLite();
            tab.statementeSQLite(insertIntoTableProblem);

            System.out.println("Row inserted - Problem: " +problem.problem+ " - Filename: " +problem.filename+ " - LPS: " + problem.lps);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return problem;
    }
}