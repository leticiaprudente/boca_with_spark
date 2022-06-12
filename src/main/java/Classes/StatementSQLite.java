package Classes;

import java.sql.*;

public class StatementSQLite {

    //Refactor to avoid error> SQLException - org.sqlite.SQLiteException: [SQLITE_BUSY]  The database file is locked (database is locked)
    private Connection connect(){
        Connection connection;

        try {
            String url = "jdbc:sqlite:javasparkbocadb.db" ;
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return connection;
    }

    public Boolean createTable(String sqlCommand){
        System.out.println("createTable - sql commando que chegou: " +sqlCommand);
        Connection conn;
        Statement statement;
        try {
            conn = this.connect();
            if (conn == null) {
                return false;
            }
            statement = conn.createStatement();

            statement.executeUpdate(sqlCommand);

            System.out.println("Table created.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public Boolean prepareStatementTransaction(Problem problem, String sqlCommand) {
        //A table of data representing a database result set, which is usually generated by executing a statement that queries the database.
        ResultSet resultSet = null;

        //A connection (session) with a specific database.
        Connection conn = null;

        //Creates a default PreparedStatement object that has the capability to retrieve auto-generated keys
        PreparedStatement preparedStatement = null;

        try {
            conn = this.connect();
            if (conn == null) {
                return false;
            }
            conn.setAutoCommit(false);

            //The constant indicating that generated keys should be made available for retrieval.
            preparedStatement = conn.prepareStatement(sqlCommand,Statement.RETURN_GENERATED_KEYS);

            /*You must supply values in place of the question mark placeholders (if there are any) before you can execute a PreparedStatement object.
            * The first argument for each of these setter methods specifies the question mark placeholder. */
            preparedStatement.setString(1, problem.problem);
            preparedStatement.setString(2, problem.filename);
            preparedStatement.setString(3, problem.lps);

            //for INSERT, UPDATE or DELETE use the executeUpdate() method
            int rowAffected = preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (rowAffected != 1) {
                System.out.println("Rollback, row affected false");
                conn.rollback();
            }
            conn.commit();
        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    System.out.println("SQLException Rollback,conn != null");
                    conn.rollback();
                }
            } catch (SQLException e2) {
                e1.getStackTrace();
                System.out.println(e2.getMessage());
            }
            e1.getStackTrace();
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {

                    conn.close();
                }
            } catch (SQLException e3) {
                e3.getStackTrace();
                System.out.println(e3.getMessage());
            }
        }
        return true;
    }


    public ResultSet selectTable(String selectTable) throws SQLException {
        System.out.println("selectTable - sqlCommand  que chegou: "  +selectTable);
        ResultSet resultSet = null;
        Connection con = null;
        Statement statement = null;

        try {
            con = this.connect();
            if (con == null) {
                return null;
            }

            statement = con.createStatement();

            //for SELECT use the executeQuery() method which returns the ResultSet
            resultSet = statement.executeQuery(selectTable);
            
            System.out.println("Select command executed.");
            return resultSet;

        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            e1.getMessage();
            return null;

        }  finally {
            try {
                if(resultSet != null){
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
                e2.getStackTrace();
            }
        }

    }
    /*
    public Boolean deleteTransaction(String problemID, String sqlCommand){
        ResultSet resultSet = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = this.connect();
            if (conn == null) {
                return false;
            }
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sqlCommand,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, problemID);


            //for INSERT, UPDATE or DELETE use the executeUpdate() method
            int rowAffected = preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (rowAffected != 1) {
                System.out.println("Rollback, row affected FALSE");
                conn.rollback();
            }
            conn.commit();
        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    System.out.println("SQLException Rollback,conn != null");
                    conn.rollback();
                }
            } catch (SQLException e2) {
                e1.getStackTrace();
                System.out.println(e2.getMessage());
            }
            e1.getStackTrace();
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {

                    conn.close();
                }
            } catch (SQLException e3) {
                e3.getStackTrace();
                System.out.println(e3.getMessage());
            }
        }
        return true;
    }*/
}
