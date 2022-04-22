package SQLite;
import java.sql.*;

public class SQLiteTables {
    public static void main( String args[] ) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:boca.db");
            System.out.println("Opened database successfully");

            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String createTableLanguages = "CREATE TABLE LANGUAGES " +
                                 "(ID               INT     NOT NULL    PRIMARY KEY," +
                                 "LANGUAGE_NAME     TEXT    NOT NULL)";
            statement.executeUpdate(createTableLanguages);

            String createTableProblems = "CREATE TABLE PROBLEMS " +
                    "(PROBLEM          CHAR     NOT NULL    PRIMARY KEY ," +
                    "FILENAME          TEXT    NOT NULL," +
                    "LPS          VARCHAR(5));";
            statement.executeUpdate(createTableProblems);

            String createTableResults = "CREATE TABLE RESULTS " +
                    "(PROBLEM          CHAR    NOT NULL    PRIMARY KEY," +
                    "FILENAME          TEXT    NOT NULL," +
                    "CONTENT           TEXT    NOT NULL);";
            statement.executeUpdate(createTableResults);

            //INSERTS
            String insertProblems = "INSERT INTO PROBLEMS (PROBLEM,FILENAME,LPS) " +
                    "VALUES ();";

            String insertResults = "INSERT INTO RESULTS (PROBLEM,FILENAME,CONTENT) " +
                    "VALUES ();";


            statement.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Tables created successfully");
    }
}

