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
        //deletar arquivo do codigo do aluno + resposta do aluno
        //dar close no arquivo!!!!!!!
        FileUtils.deleteDirectory(new File("Files\\SourceCode\\"+sourceCode.problem+"\\"));

        return sourceCode;
    }

}
