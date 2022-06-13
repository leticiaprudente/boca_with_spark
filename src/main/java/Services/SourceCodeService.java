package Services;


import Classes.Problem;
import Classes.SourceCodeHistory;
import com.google.gson.JsonObject;

import static Services.ProblemService.searchProblemByID;

public class SourceCodeService {
    public Integer beforeAddSourceCode(SourceCodeHistory sourceCode) {
        JsonObject jsonObject;
        try {
            if  ( (sourceCode.filename.trim().length() == 0) || (sourceCode.problem.trim().length() == 0) || (sourceCode.content.trim().length() == 0 ) || (sourceCode.author.trim().length() == 0 ) || (sourceCode.datetime.trim().length() == 0 ) ) {
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
}
