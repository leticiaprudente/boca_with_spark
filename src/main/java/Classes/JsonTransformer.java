package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer {

    public static Problem stringToObject(String bodyContent) throws Exception{
        Gson gson = new Gson();
        Problem problem = gson.fromJson(bodyContent, Problem.class);
        System.out.println(problem);

        return problem;

}


    public static void main(String[] args) {

        Gson gson = new Gson();

        try {

            BufferedReader br = new BufferedReader(new FileReader("Files\\file.json"));

            //Converte String JSON para objeto Java
            Problem object = gson.fromJson(br, Problem.class);

            System.out.println(object);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
