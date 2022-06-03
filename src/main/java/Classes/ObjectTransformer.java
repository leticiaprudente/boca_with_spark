package Classes;

import com.google.gson.Gson;

public class ObjectTransformer {
    public static String objectToString(Problem problem){
        Gson gson = new Gson();

        System.out.println(gson.toJson(problem));

        return gson.toJson(problem);
    }
    /*
    public static void main(String[] args) {
        
        Problem problem = new Problem("pradinho.py","A", "java|c|c++|python");
        Gson gson = new Gson();

        //receive a java Object and returns a Json as a String
        String json = gson.toJson(problem);

        try {
            //creates a file "file.json" with the converted Json
            FileWriter writer;
            writer = new FileWriter("Files\\"+problem.filename);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);

    }*/
}
