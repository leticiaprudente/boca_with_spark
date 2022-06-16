package Services;


import Classes.Problem;
import Classes.SourceCode;
import Classes.StatementSQLite;

import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    public static SourceCode addSourceCode(SourceCode sourceCode) throws SQLException {
        try {

            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp = date.format(new Date());
            sourceCode.datetime = timeStamp;
            sourceCode.status = "Teste";


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

}
