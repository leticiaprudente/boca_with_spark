package Services;


import Classes.Problem;
import Classes.SourceCodeHistory;
import Classes.StatementSQLite;
import com.google.gson.JsonObject;

import java.sql.SQLException;

import static Services.ProblemService.searchProblemByID;

public class SourceCodeService {
    public Integer beforeAddSourceCode(SourceCodeHistory sourceCode) {
        JsonObject jsonObject;
        try {
            if  ( (sourceCode.filename.trim().length() == 0) || (sourceCode.problem.trim().length() == 0) || (sourceCode.content.trim().length() == 0 ) || (sourceCode.author.trim().length() == 0 ) ) {
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
            e.getStackTrace();
        }
        return 1;
    }

    public static SourceCodeHistory addSourceCode(SourceCodeHistory sourceCode) throws SQLException {
        try {

            /*
            // 2021-03-24 16:48:05.59
            Date date = new Date();
            Timestamp timestamp2 = new Timestamp(date.getTime());*/
            String insertIntoTableSourceCodeHistory = "INSERT INTO insertIntoTableSourceCodeHistory (problem, filename, lps) VALUES (?, ? ,?)";

            StatementSQLite transaction = new StatementSQLite();
            //problem <> sourcecode
            /*Boolean verifyPersistence = transaction.prepareStatementTransaction(sourceCode, insertIntoTableSourceCodeHistory);

            if ( !verifyPersistence ) {
                return null;
            }*/

        } catch (Exception e) {
            e.getStackTrace();
        }
        return sourceCode;
    }
}
