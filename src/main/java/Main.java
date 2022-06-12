import Classes.StatementSQLite;
import Controllers.*;

import java.sql.SQLException;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        get("/hello","application/json", (req, res) -> "{\"message\": \"\"Hello World (get Spark)\"} ");

        String dropTableProblem="DROP TABLE IF EXISTS problem;";

        String createTableProblem = "CREATE TABLE IF NOT EXISTS problem ( " +
                "problem TEXT NOT NULL PRIMARY KEY, " +
                "filename TEXT NOT NULL, " +
                "lps TEXT NOT NULL );";

        StatementSQLite executeCreateTable = new StatementSQLite();
        executeCreateTable.createTable(dropTableProblem);
        executeCreateTable.createTable(createTableProblem);

        Problems problem = new Problems();
        problem.createProblemRoutes();


        ExpectedAnswers expectedAnswers = new ExpectedAnswers();
        expectedAnswers.createExpectedAnswersRoutes();

        /*String createTableAnswer = "CREATE TABLE IF NOT EXISTS answer ( " +
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
