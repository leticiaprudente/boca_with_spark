package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementSQLite {
    public static void executeSQL(String sqlCommand) throws SQLException {
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
}
