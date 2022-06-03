package Services;

import Classes.Problem;
import Classes.StatementSQLite;

import java.rmi.server.ExportException;
import java.util.Locale;

import static spark.Spark.get;

public class ProblemService {
    public static Problem createProblem(Problem problem) throws Exception{
        //VERIFICAÇÃO E REGRAS DE NEGÓCIO NO SERVICE
        try {

            String insertIntoTableProblem = "INSERT INTO problem (problem, filename, lps) " +
                    "VALUES ('" + problem.problem.toUpperCase() + "' , '" + problem.filename + "' , '" + problem.lps + "' );";
            StatementSQLite tab = new StatementSQLite();
            tab.executeSQL(insertIntoTableProblem);

            System.out.println("Row inserted - Problem: " +problem.problem+ "\nFilename: " +problem.filename+ "\nLPS: " + problem.lps);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return problem;
    }
}