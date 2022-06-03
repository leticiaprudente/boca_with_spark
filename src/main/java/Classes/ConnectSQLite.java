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

           // DatabaseMetaData meta = connection.getMetaData();

            System.out.println("Connection to SQLite has been established.");

            Statement statement = connection.createStatement();

            //return url;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

            //return null;
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
}
