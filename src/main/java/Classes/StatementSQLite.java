package Classes;

import javax.xml.transform.Result;
import java.sql.*;

public class StatementSQLite {
    public static Boolean statementeSQLite(String sqlCommand) throws SQLException {
        // SQLite connection string
        String url = "jdbc:sqlite:javasparkbocadb.db";

        Connection con = DriverManager.getConnection(url);
        con.setAutoCommit(false);

        try{
            Statement stmt = con.createStatement();
            stmt.execute(sqlCommand);
        } catch (SQLException e1) {
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("Rollback executed.");
                    return false;
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        }
        con.commit();
        System.out.println("SQL command executed and commited.");
        return true;
    }


    public static ResultSet selectTable(String selectTable) throws SQLException {

        String url = "jdbc:sqlite:javasparkbocadb.db";

        Connection con = DriverManager.getConnection(url);

        Statement statement = con.createStatement();

        ResultSet resultSet = statement.executeQuery(selectTable);

        System.out.println("Select command executed.");

        return resultSet;

    }

}
