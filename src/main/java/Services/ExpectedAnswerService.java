package Services;

import Classes.ExpectedAnswer;
import com.google.gson.JsonObject;

import java.io.*;
import java.sql.SQLException;

public class ExpectedAnswerService {
    public Boolean beforeAddExpectedAnswer(ExpectedAnswer expectedAnswer) {
        JsonObject jsonObject = null;
        try {
            if  ( (expectedAnswer.filename.trim().length() == 0) || (expectedAnswer.problem.trim().length() == 0) || (expectedAnswer.content.trim().length() == 0 ) ) {
                System.out.println("Null field.");
                return false;
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return true;
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



    /*public static JsonObject searchExpectedAnswerByID(String problemID) throws SQLException {

    }*/

}
