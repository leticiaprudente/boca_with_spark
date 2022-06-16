import Classes.StatementSQLite;
import Controllers.*;

import java.sql.SQLException;
import java.util.Base64;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        get("/hello","application/json", (req, res) -> "{\"message\": \"\"Hello World\"} ");


        StatementSQLite executeCreateTable = new StatementSQLite();

        String dropTableProblem="DROP TABLE IF EXISTS problem ;";
        String createTableProblem = "CREATE TABLE IF NOT EXISTS problem ( " +
                "problem TEXT NOT NULL PRIMARY KEY, " +
                "filename TEXT NOT NULL, " +
                "lps TEXT NOT NULL );";
        executeCreateTable.createTable(dropTableProblem);
        executeCreateTable.createTable(createTableProblem);

        String dropTableExpectedAnswer = "DROP TABLE IF EXISTS expectedAnswer ;" ;
        String createTableExpectedAnswer = "CREATE TABLE IF NOT EXISTS expectedAnswer ( " +
                "problem TEXT NOT NULL, " +
                "inputFilename TEXT NOT NULL, " +
                "inputContent TEXT NOT NULL, " +
                "outputFilename TEXT NOT NULL, " +
                "outputContent TEXT NOT NULL, " +
                "PRIMARY KEY (problem, inputFilename), " +
                "FOREIGN KEY (problem) " +
                "REFERENCES problem (problem) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE);" ;

        executeCreateTable.createTable(dropTableExpectedAnswer);
        executeCreateTable.createTable(createTableExpectedAnswer);

        String dropTableExecutionHistory="DROP TABLE IF EXISTS sourceCodeExecutionHistory ;";
        String createTableExecutionHistory = "CREATE TABLE IF NOT EXISTS sourceCodeExecutionHistory ( " +
                "problem TEXT NOT NULL, " +
                "filename TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "source_code TEXT NOT NULL, " +
                "datetime TEXT NOT NULL, " +
                "status TEXT NOT NULL CHECK (status IN ('SUCCESS', 'FAIL', 'Teste')), "+
                "PRIMARY KEY(problem, datetime), " +
                "FOREIGN KEY (problem) " +
                    "REFERENCES problem (problem) " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE );";
        executeCreateTable.createTable(dropTableExecutionHistory);
        executeCreateTable.createTable(createTableExecutionHistory);


        Problems problem = new Problems();
        problem.createProblemRoutes();

        ExpectedAnswers expectedAnswers = new ExpectedAnswers();
        expectedAnswers.createExpectedAnswersRoutes();

        SourceCodes sourceAnswers = new SourceCodes();
        sourceAnswers.createSourceCodesRoutes();
/*
        String originalInput = "1 1 0";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        System.out.println("Original:" +originalInput);
        System.out.println("Encoded:" +encodedString);
        byte[] decoded = Base64.getDecoder().decode(encodedString);
        System.out.println("Decoded:" + new String(decoded));
*/


    }
}
