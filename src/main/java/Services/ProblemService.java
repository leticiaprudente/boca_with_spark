package Services;

import Classes.JsonTransformer;
import Classes.ObjectTransformer;
import Classes.Problem;
import Classes.StatementSQLite;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemService {
    public static Integer beforeAddProblem(Problem problem) throws Exception {

        JsonObject jsonObject = null;
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
        //VERIFICAÇÃO E REGRAS DE NEGÓCIO NO SERVICE
        //logica
        try {

            String insertIntoTableProblem = "INSERT INTO problem (problem, filename, lps) " +
                    "VALUES ('" + problem.problem.toUpperCase() + "' , '" + problem.filename + "' , '" + problem.lps + "' );";

            StatementSQLite insertIntoTable = new StatementSQLite();
            Boolean createdOrNot = insertIntoTable.statementeSQLite(insertIntoTableProblem);
            if (createdOrNot == false) {
                return null;
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return problem;
    }

    public static JsonObject searchAllProblems() throws SQLException {

        System.out.println("entreii");

        String selectAllProblems = "SELECT filename, problem, lps FROM problem ORDER BY problem;"  ;
        StatementSQLite statement = new StatementSQLite();
        JsonObject jsonObject = new JsonObject();

        try {

            ResultSet resultSet = statement.selectTable(selectAllProblems);

            //System.out.println("result is before first? " +resultSet.isBeforeFirst());

            if(! (resultSet.isBeforeFirst()) ){
                jsonObject = null;
                System.out.println("Problem table is empty.");
            }
            else {
                JsonArray jsonArray = new JsonArray();
                while (resultSet.next()) {
                    JsonObject record = new JsonObject();
                    //Inserting key-value pairs into the json object
                    record.addProperty("filename", resultSet.getString("filename"));
                    record.addProperty("problem", resultSet.getString("problem"));
                    record.addProperty("lps", resultSet.getString("lps"));
                    jsonArray.add(record);
                } ;
                jsonObject.add("Problems", jsonArray);
                //}
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return  jsonObject ;

    }

    public static JsonObject searchProblemByID(String problemID) throws SQLException{
        String selectProblemById = "SELECT filename, problem, lps FROM problem WHERE problem = '" +problemID.toUpperCase()+ "';" ;

        StatementSQLite statement = new StatementSQLite();
        ResultSet resultSet = statement.selectTable(selectProblemById);
        JsonObject jsonObject = new JsonObject(); //não pode ser null por causa do add property! precisa estar instanciado

        if(! (resultSet.isBeforeFirst())){
            System.out.println("o bagulho eh nulo");
            jsonObject = null;

        } else {
            //resultSet.getString("problem").trim().toUpperCase().equals(problemID.trim().toUpperCase()) ){
            jsonObject.addProperty("filename", resultSet.getString("filename"));
            jsonObject.addProperty("problem", resultSet.getString("problem"));
            jsonObject.addProperty("lps", resultSet.getString("lps"));
        }

        return jsonObject;
    }

    public static JsonObject beforeDeleteProblemByID(String problemID) throws SQLException{
        JsonObject jsonObjectVerify = new JsonObject();
        JsonObject jsonObjectDelete = new JsonObject();

        try {
            jsonObjectVerify = searchProblemByID(problemID);

            if( jsonObjectVerify != null ){
                System.out.println("retornou pq existe, AMENO");

                jsonObjectDelete.addProperty("problem", problemID);
            }else{
                System.out.println("nulo SIM");
                jsonObjectDelete = null;
            }
        }catch (SQLException e){
            e.getStackTrace();
        }

        return jsonObjectDelete;
    }

    public static String deleteProblemByID(String problemID) throws SQLException{
        String deleteProblemById = "DELETE FROM problem WHERE problem = '" +problemID+ "';" ;
        StatementSQLite statement = new StatementSQLite();

        Boolean deletedOrNot = statement.statementeSQLite(deleteProblemById);
        if (deletedOrNot == false) {
            return null;
        }else{
            return problemID;
        }

    }

}