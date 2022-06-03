package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SelectSQLite {
    public void selectAll(String selectString) throws SQLException {
        String sql = selectString;
        String url = "jdbc:sqlite:javasparkbocadb.db";

        Connection con = DriverManager.getConnection(url);
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println("filename: " + rs.getString("filename") + "\t" +
                        "problem: " + rs.getString("problem") + "\t" +
                        "lps: " + rs.getString("lps"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}