package Classes;

import java.sql.*;

public class ConnectSQLite {
    public static void connect() {
        Connection connection = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:javasparkbocadb.db";

            // create a connection to the database
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            Statement statement = connection.createStatement();

            //create table 'problem'
            statement.execute("CREATE TABLE IF NOT EXISTS problem( problem TEXT NOT NULL PRIMARY KEY, filename TEXT NOT NULL, lps TEXT NOT NULL, CHECK( lps IN ('java', 'c', 'c++','python')) )");
            System.out.println("Table 'problem' created.");

            //create table 'answer'
            statement.execute("CREATE TABLE IF NOT EXISTS answer(problem TEXT NOT NULL PRIMARY KEY, filename TEXT, content TEXT NOT NULL, FOREIGN KEY (problem) REFERENCES problem (problem) ON UPDATE CASCADE ON DELETE CASCADE)");
            System.out.println("Table 'answer' created.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } /*finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();


    }
}
