package Services;

import Classes.Problem;
import Classes.StatementSQLite;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemService {
    public static Integer beforeAddProblem(Problem problem) throws Exception {

        JsonObject jsonObject;
        try {
            if  ( (problem.filename.trim().length() == 0) || (problem.problem.trim().length() == 0) || (problem.lps.trim().length() == 0 ) ) {
                System.out.println("Null field.");
                return null;
            }
            jsonObject = searchProblemByID(problem.problem);

            if( jsonObject != null ){
                System.out.println("Problem already exists.");
                return 0;
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return 1;
    }

    public static Problem addProblem(Problem problem) throws SQLException{
        try {
            String insertIntoTableProblem = "INSERT INTO problem (problem, filename, lps) VALUES (?, ? ,?)";

            StatementSQLite transaction = new StatementSQLite();
            Boolean verifyPersistence = transaction.prepareStatementTransactionProblem(problem, insertIntoTableProblem);

            if ( !verifyPersistence ) {
                return null;
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
        return problem;
    }

    public static JsonObject searchAllProblems() throws SQLException {

        String selectAllProblems = "SELECT filename, problem, lps FROM problem ORDER BY problem;"  ;
        StatementSQLite statement = new StatementSQLite();

        JsonObject jsonObject = new JsonObject();
        ResultSet resultSet;

        try {
            resultSet = statement.selectTable(selectAllProblems);

            JsonArray jsonArray = new JsonArray();
            Boolean validation = false;

            //SQLException "A TYPE_FORWARD_ONLY": ResultSet only supports next() for navigation
            //IF ACCESSING THE RESULT SET, EVEN IF IN SYSOUT, RETURN TO beforefirst()
            while (resultSet.next()) {
                JsonObject record = new JsonObject();

                //Inserting key-value pairs into the json object
                record.addProperty("filename", resultSet.getString("filename"));
                record.addProperty("problem", resultSet.getString("problem"));
                record.addProperty("lps", resultSet.getString("lps"));
                jsonArray.add(record);
                validation = true;
            }
            jsonObject.add("Problems", jsonArray);

            if (!validation){
                jsonObject = null;
                System.out.println("Problem table is empty.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getMessage();
        }
        return  jsonObject ;

    }

    public static JsonObject searchProblemByID(String problemID) throws SQLException{
        String selectProblemById = "SELECT filename, problem, lps FROM problem WHERE problem = '" +problemID.trim().toUpperCase()+ "'" ;

        StatementSQLite select = new StatementSQLite();

        ResultSet resultSet = select.selectTable(selectProblemById);

        JsonObject jsonObject = new JsonObject(); //it can't be null because of the add property! it must be instantiated

        if(!resultSet.next()){
            jsonObject = null;

        } else {
            jsonObject.addProperty("filename", resultSet.getString("filename"));
            jsonObject.addProperty("problem", resultSet.getString("problem"));
            jsonObject.addProperty("lps", resultSet.getString("lps"));
        }
        resultSet.close();
        return jsonObject;
    }

    public static Boolean beforeDeleteProblemByID(String problemID) throws SQLException{
        JsonObject jsonObjectVerify;

        try {
            jsonObjectVerify = searchProblemByID(problemID.trim().toUpperCase());

            if( jsonObjectVerify == null ) {
                return false;
            }
        }catch (SQLException e){
            e.getStackTrace();
        }

        return true;
    }

    public static Boolean deleteProblemByID(String problemID) throws SQLException{
        String deleteProblemById = "DELETE FROM problem WHERE problem = ?;" ;
        Problem problemObj = new Problem();
        problemObj.problem = problemID.trim().toUpperCase();
        problemObj.filename = null;
        problemObj.lps = null;

        StatementSQLite transaction = new StatementSQLite();
        Boolean verifyPersistence = transaction.prepareStatementTransactionProblem(problemObj, deleteProblemById);

        File dir = new File("Files\\ExpectedAnswer\\"+problemObj.problem);
        if (dir.exists()){
            dir.delete();
        }

        return verifyPersistence;

    }

}