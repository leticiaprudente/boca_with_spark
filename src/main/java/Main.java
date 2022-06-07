import Classes.ConnectSQLite;

import Classes.StatementSQLite;
import Controllers.*;

import java.sql.SQLException;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectSQLite con = new ConnectSQLite();
        con.connect() ;
        //SQL command for drop table
        String truncateTableProblem = "DROP TABLE IF EXISTS problem" ;

        String truncateTableAnswer = "DROP TABLE IF EXISTS answer" ;

        // SQL command for creating a new table
        String createTableProblem = "CREATE TABLE IF NOT EXISTS problem ( " +
                "problem TEXT NOT NULL PRIMARY KEY, " +
                "filename TEXT NOT NULL, " +
                "lps TEXT NOT NULL );";

        String createTableAnswer = "CREATE TABLE IF NOT EXISTS answer ( " +
                "problem TEXT NOT NULL PRIMARY KEY, " +
                "filename TEXT NOT NULL, " +
                "content TEXT NOT NULL, " +
                "FOREIGN KEY (problem) " +
                "REFERENCES problem (problem) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE);" ;

        StatementSQLite tab = new StatementSQLite();
        tab.statementeSQLite(truncateTableAnswer);
        tab.statementeSQLite(truncateTableProblem);

        tab.statementeSQLite(createTableProblem);
        tab.statementeSQLite(createTableAnswer);

        get("/hello","application/json", (req, res) -> "{\"message\": \"\"Hello World (get Spark)\"} ");


        Problems problem = new Problems();
        problem.createRoutes();


    }
}
