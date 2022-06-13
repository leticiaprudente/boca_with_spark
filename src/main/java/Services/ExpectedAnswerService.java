package Services;

import Classes.ExpectedAnswer;
import Classes.Problem;
import com.google.gson.JsonObject;

import java.io.*;
import java.sql.SQLException;

import static Services.ProblemService.searchProblemByID;

public class ExpectedAnswerService {
    public Integer beforeAddExpectedAnswer(ExpectedAnswer expectedAnswer) {
        JsonObject jsonObject;
        try {
            if  ( (expectedAnswer.filename.trim().length() == 0) || (expectedAnswer.problem.trim().length() == 0) || (expectedAnswer.content.trim().length() == 0 ) ) {
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

    public static ExpectedAnswer addExpectedAnswer(ExpectedAnswer expectedAnswer) throws Exception{
        File dir = new File("Files\\ExpectedAnswer\\"+expectedAnswer.problem);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            FileWriter fileWriter = new FileWriter(dir+"\\"+expectedAnswer.filename, false);
            fileWriter.write(expectedAnswer.content);
            fileWriter.close();
            System.out.println("Successfully wrote content in the file.");
        } catch (IOException e) {
            expectedAnswer = null;
            e.printStackTrace();
        }
        return expectedAnswer;
    }


}
