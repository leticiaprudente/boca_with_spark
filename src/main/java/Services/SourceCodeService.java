package Services;


import Classes.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static Services.ProblemService.searchProblemByID;

public class SourceCodeService {
    public Integer beforeAddSourceCode(SourceCode sourceCode) {
        JsonObject jsonObject;
        try {
            if  ( (sourceCode.filename.trim().length() == 0) || (sourceCode.problem.trim().length() == 0) || (sourceCode.source_code.trim().length() == 0 ) || (sourceCode.author.trim().length() == 0 ) ) {
                System.out.println("Null field.");
                return null;
            }
            Problem problem = new Problem();
            problem.problem = sourceCode.problem;
            jsonObject = searchProblemByID(problem.problem);
            if( jsonObject == null ){
                System.out.println("Problem doesn't exists.");
                return 0;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    public static SourceCode addSourceCode(SourceCode sourceCode) throws SQLException, IOException {
        try {

            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp = date.format(new Date());
            sourceCode.datetime = timeStamp;

            File dir = new File("Files\\SourceCode\\"+sourceCode.problem);
            if (!dir.exists()){
                dir.mkdirs();
            }


            ExpectedAnswerService expectedAnswerService = new ExpectedAnswerService();
            JsonObject jsonObject = expectedAnswerService.searchAnswersByProblemID(sourceCode.problem) ;

            ExecutePython exec = new ExecutePython();

            JsonArray jsonArray = jsonObject.getAsJsonArray("expectedAnswers" +sourceCode.problem) ;
            boolean statusSourceCode = true ;

            for(int i = 0; i < jsonArray.size(); i++){
                System.out.println("for - I:" +i);
                System.out.println("size :" +jsonArray.size());
                JsonToObjectTransformer jsonToExpectedAnswerTransformer = new JsonToObjectTransformer();

                ExpectedAnswer expectedAnswer = null;

                try {
                    System.out.println("JsonArray get as string:" +jsonArray.get(i));
                    expectedAnswer = jsonToExpectedAnswerTransformer.stringToExpectedAnswer(jsonArray.get(i).toString());
                    //expectedAnswer.inputContent = expectedAnswer.inputContent.replace("\n",)
                    System.out.println("expected Answer problem:" +expectedAnswer.problem);

                    System.out.println("expected Answer input content decoded:" +expectedAnswer.inputContent);

                    System.out.println("expected Answer output content decoded:" +expectedAnswer.outputContent);

                    if (exec.runScript(sourceCode, expectedAnswer).equals("FAIL") ){
                        statusSourceCode = false;
                    }
                } catch (Exception exp){
                    exp.printStackTrace();
                }

            }

            if(statusSourceCode) {
                System.out.println("SUCCESS");
                sourceCode.status = "SUCCESS";
            }
            else{
                System.out.println("FAIL");
                sourceCode.status = "FAIL";
            }


            String insertIntoTableSourceCodeHistory = "INSERT INTO sourceCodeExecutionHistory (problem, filename, author, source_code, datetime, status) VALUES (?, ? , ? , ? , ? , ?)";

            StatementSQLite transaction = new StatementSQLite();
            //problem <> sourcecode
            Boolean verifyPersistence = transaction.prepareStatementTransactionSourceCode(sourceCode, insertIntoTableSourceCodeHistory);

            if ( !verifyPersistence ) {
                return null;
            }

        } catch (Exception e) {
            e.getStackTrace();
        }

        return sourceCode;
    }


    public static JsonObject searchSourceCodeByID(String problemID) throws SQLException{
        String selectSourceCodeById = "SELECT problem, filename, author, source_code, datetime, status FROM sourceCodeExecutionHistory WHERE problem = '" +problemID.trim().toUpperCase()+ "'" ;
        System.out.println("select problem by id: " +selectSourceCodeById);
        StatementSQLite select = new StatementSQLite();

        ResultSet resultSet = select.selectTable(selectSourceCodeById);

        JsonObject jsonObject = new JsonObject(); //it can't be null because of the add property! it must be instantiated

        if(!resultSet.next()){
            jsonObject = null;

        } else {
            jsonObject.addProperty("filename", resultSet.getString("filename"));
            jsonObject.addProperty("author", resultSet.getString("author"));
            jsonObject.addProperty("filename", resultSet.getString("filename"));
            jsonObject.addProperty("source_code", resultSet.getString("source_code"));
            jsonObject.addProperty("datetime", resultSet.getString("datetime"));
            jsonObject.addProperty("status", resultSet.getString("status"));
        }
        resultSet.close();
        return jsonObject;
    }

    public static JsonObject searchSourceCodeByFilter(Filter filter) throws SQLException{
        String selectSourceCode = "SELECT problem, filename, author, source_code, datetime, status FROM sourceCodeExecutionHistory " ;
        if( (filter.status != null) || (filter.startDateTime != null && filter.endDateTime != null) || (filter.problems != null)){
            String add = "WHERE " ;
            int cont = 0 ;
            if(filter.status != null){
                add += " status = '"+filter.status+"' " ;
                cont+=1;
            }
            if(filter.startDateTime != null && filter.endDateTime != null){
                if(cont>0){
                    add += " AND " ;
                }
                add += " datetime BETWEEN ('"+filter.startDateTime+"' AND '"+filter.endDateTime+"' ) " ;
                cont +=1 ;
            }
            if(filter.problems != null){
                if(cont>0){
                    add += " AND " ;
                }
                add += " problem IN (" +filter.problems+ ") ";

            }
            selectSourceCode += add ;
        }

        System.out.println("select problem by id: " +selectSourceCode);

        JsonObject jsonObject = new JsonObject(); //it can't be null because of the add property! it must be instantiated

        try{
            StatementSQLite statement = new StatementSQLite();

            ResultSet resultSet = statement.selectTable(selectSourceCode);

            JsonArray jsonArray = new JsonArray();
            Boolean validation = false;

            //SQLException "A TYPE_FORWARD_ONLY": ResultSet only supports next() for navigation
            //IF ACCESSING THE RESULT SET, EVEN IF IN SYSOUT, RETURN TO beforefirst()
            while (resultSet.next()) {
                JsonObject record = new JsonObject();

                //Inserting key-value pairs into the json object
                record.addProperty("filename", resultSet.getString("filename"));
                record.addProperty("author", resultSet.getString("author"));
                record.addProperty("filename", resultSet.getString("filename"));
                record.addProperty("source_code", resultSet.getString("source_code"));
                record.addProperty("datetime", resultSet.getString("datetime"));
                record.addProperty("status", resultSet.getString("status"));
                jsonArray.add(record);
                validation = true;
            }
            jsonObject.add("SourceCodeHistory", jsonArray);

            if (!validation){
                System.out.println("Source Code History table is empty.");
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  jsonObject ;
    }



}
