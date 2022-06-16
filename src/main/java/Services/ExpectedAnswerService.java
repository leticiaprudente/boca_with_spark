package Services;

import Classes.ExpectedAnswer;
import Classes.Problem;
import Classes.StatementSQLite;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static Services.ProblemService.searchProblemByID;

public class ExpectedAnswerService {
    public Integer beforeAddExpectedAnswer(ExpectedAnswer expectedAnswer) {
        JsonObject jsonObject;
        try {
            if  ( (expectedAnswer.inputFilename.trim().length() == 0) || (expectedAnswer.inputContent.trim().length() == 0) || (expectedAnswer.outputFilename.trim().length() == 0) || (expectedAnswer.outputContent.trim().length() == 0) || (expectedAnswer.problem.trim().length() == 0 ) ) {
                System.out.println("Null field.");
                return null;
            }
            Problem problem = new Problem();
            problem.problem = expectedAnswer.problem;
            jsonObject = searchProblemByID(problem.problem);
            if( jsonObject == null ){
                System.out.println("Problem doesn't exists.");
                return 0;
            }

        }catch (Exception e){
            e.getStackTrace();
        }
        return 1;
    }

    public static ExpectedAnswer addExpectedAnswer(ExpectedAnswer expectedAnswer) throws SQLException{

        String insertIntoTableExpectedAnswer = "INSERT INTO expectedAnswer (inputFilename, inputContent, outputFilename, outputContent, problem ) VALUES (?, ? , ? , ? , ?)";

        StatementSQLite transaction = new StatementSQLite();

        try {
            Boolean verifyPersistence = transaction.prepareStatementTransactionExpectedAnswer(expectedAnswer, insertIntoTableExpectedAnswer);

            if ( !verifyPersistence ) {
                return null;
            }

            File dir = new File("Files\\ExpectedAnswer\\"+expectedAnswer.problem);
            if (!dir.exists()){
                dir.mkdirs();
            }
            byte[] decodedInputContent = Base64.getDecoder().decode(expectedAnswer.inputContent);
            FileWriter inputFile = new FileWriter(dir+"\\"+expectedAnswer.inputFilename, false);
            inputFile.write(new String(decodedInputContent));
            inputFile.close();

            byte[] decodedOutputContent = Base64.getDecoder().decode(expectedAnswer.outputContent);
            FileWriter outputFile = new FileWriter(dir+"\\"+expectedAnswer.outputFilename, false);
            outputFile.write(new String(decodedOutputContent));
            outputFile.close();

            System.out.println("Successfully wrote contents in files.");

        } catch (IOException e) {
            expectedAnswer = null;
            e.printStackTrace();
        }
        return expectedAnswer;
    }


    public static JsonObject searchAnswersByProblemID(String problemID) throws SQLException {

        String selectAllAnswers = "SELECT rowid, inputFilename, inputContent, outputFilename, outputContent FROM expectedAnswer WHERE problem = '"+problemID.trim().toUpperCase()+"' ORDER BY rowid;"  ;
        StatementSQLite statement = new StatementSQLite();

        JsonObject jsonObject = new JsonObject();
        ResultSet resultSet;

        try {
            resultSet = statement.selectTable(selectAllAnswers);

            JsonArray jsonArray = new JsonArray();
            Boolean validation = false;

            //SQLException "A TYPE_FORWARD_ONLY": ResultSet only supports next() for navigation
            //IF ACCESSING THE RESULT SET, EVEN IF IN SYSOUT, RETURN TO beforefirst()
            int ord = 1;
            while (resultSet.next()) {
                JsonObject record = new JsonObject();

                byte[] decodedInputContent = Base64.getDecoder().decode(resultSet.getString("inputContent"));
                byte[] decodedOutputContent = Base64.getDecoder().decode(resultSet.getString("outputContent"));

                //row id increments for any new row

                //Inserting key-value pairs into the json object
                record.addProperty("Test Case", ord);
                record.addProperty("Input", new String(decodedInputContent));
                record.addProperty("Output", new String(decodedOutputContent));
                jsonArray.add(record);

                ord += 1;

                validation = true;
            }
            jsonObject.add("Expected Answers for Problem " +problemID, jsonArray);

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


}
