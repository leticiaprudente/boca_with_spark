import Classes.StatementSQLite;
import Controllers.*;

import java.sql.SQLException;
import java.util.Base64;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        get("/hello","application/json", (req, res) -> "{\"message\": \"\"Hello World (get Spark)\"} ");


        StatementSQLite executeCreateTable = new StatementSQLite();

        String dropTableProblem="DROP TABLE IF EXISTS problem;";
        String createTableProblem = "CREATE TABLE IF NOT EXISTS problem ( " +
                "problem TEXT NOT NULL PRIMARY KEY, " +
                "filename TEXT NOT NULL, " +
                "lps TEXT NOT NULL );";
        executeCreateTable.createTable(dropTableProblem);
        executeCreateTable.createTable(createTableProblem);

        String dropTableExecutionHistory="DROP TABLE IF EXISTS executionHistory;";
        String createTableExecutionHistory = "CREATE TABLE IF NOT EXISTS executionHistory ( " +
                "problem TEXT NOT NULL, " +
                "filename TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "source_code TEXT NOT NULL, " +
                "datetime TEXT NOT NULL, " +
                "status TEXT NOT NULL CHECK (status IN ('SUCCESS', 'FAIL')), "+
                "PRIMARY KEY(problem, datetime), " +
                "FOREIGN KEY (problem) " +
                    "REFERENCES problem (problem)" +
                    "ON UPDATE CASCADE" +
                    "ON DELETE CASCADE );";
        executeCreateTable.createTable(dropTableExecutionHistory);
        executeCreateTable.createTable(createTableExecutionHistory);


        Problems problem = new Problems();
        problem.createProblemRoutes();


        ExpectedAnswers expectedAnswers = new ExpectedAnswers();
        expectedAnswers.createExpectedAnswersRoutes();
/*
        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        System.out.println("Original:" +originalInput);
        System.out.println("Encoded:" +encodedString);
        byte[] decoded = Base64.getDecoder().decode(encodedString);
        System.out.println("Decoded:" + new String(decoded));

        String createTableAnswer = "CREATE TABLE IF NOT EXISTS answer ( " +
                "problem TEXT NOT NULL PRIMARY KEY, " +
                "filename TEXT NOT NULL, " +
                "content TEXT NOT NULL, " +
                "FOREIGN KEY (problem) " +
                "REFERENCES problem (problem) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE);"
        tab.statementeSQLite(truncateTableProblem);
        tab.statementeSQLite(truncateTableAnswer);
        tab.statementeSQLite(createTableAnswer);
        */

    }
}
