package Classes;

import javax.xml.transform.Result;
import java.sql.*;

public class StatementSQLite {
    public static void statementeSQLite(String sqlCommand) throws SQLException {
        // SQLite connection string
        String url = "jdbc:sqlite:javasparkbocadb.db";

        Connection con = DriverManager.getConnection(url);
        con.setAutoCommit(false);

        try{
            Statement stmt = con.createStatement();
            stmt.execute(sqlCommand);
            con.commit();
            System.out.println("SQL command executed.");
        } catch (SQLException e1) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        }
    }


    public static ResultSet selectTable(String selectTable) throws SQLException {

        System.out.println("select into >> " +selectTable);
        String url = "jdbc:sqlite:javasparkbocadb.db";

        Connection con = DriverManager.getConnection(url);

        Statement statement = con.createStatement();
        //statement.execute(selectTable);

        ResultSet resultSet = statement.executeQuery(selectTable);

        if (!resultSet.next() ) {
            System.out.println("No problem with this id!");
        }
        return resultSet;

    }

}
