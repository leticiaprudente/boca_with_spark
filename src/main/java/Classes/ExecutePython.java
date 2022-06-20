package Classes;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Base64;

public class ExecutePython {

    public static String runScript(SourceCode sourceCode, ExpectedAnswer expectedAnswer){
        String s;

        File dirSourceCode = new File("Files\\SourceCode\\"+sourceCode.problem+"\\");
        if(dirSourceCode.exists()){
            dirSourceCode.delete();
            System.out.println("deletou");
        }
        dirSourceCode.mkdirs();


        try {
            String dir = "Files\\SourceCode\\"+sourceCode.problem+"\\"+sourceCode.author+"_"+sourceCode.filename;

            byte[] decodedSourceCode = Base64.getDecoder().decode(sourceCode.source_code);
            FileWriter sourceCodeFile = new FileWriter(dir, false);
            sourceCodeFile.write(new String(decodedSourceCode));
            sourceCodeFile.close();

            ExecutePython execute = new ExecutePython() ;

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process process = Runtime.getRuntime().exec("python.exe "+dir);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()));
            writer.write(expectedAnswer.inputContent);
            writer.close();

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));


            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            FileWriter outputFileSourceCode = null;

            File deleteFile =  new File("Files\\SourceCode\\"+sourceCode.problem+"\\"+sourceCode.author+"_"+expectedAnswer.testCase+"_output.txt");
            if (deleteFile.exists()) deleteFile.delete();
            while ((s = stdInput.readLine()) != null) {
                System.out.println("ENTROU NO OUTPUT");
                //colocar caminho onde o arquivo sera salvo temporariamente
                outputFileSourceCode = new FileWriter("Files\\SourceCode\\"+sourceCode.problem+"\\"+sourceCode.author+"_"+expectedAnswer.testCase+"_output.txt", true);
                outputFileSourceCode.write(s);

                System.out.println(s);
            }
            String dirOutputFile = "Files\\SourceCode\\"+sourceCode.problem+"\\"+sourceCode.author+"_"+expectedAnswer.testCase+"_output.txt";
            String outputSourceCode = null;

            if (outputFileSourceCode != null) {
                outputFileSourceCode.close();

                outputSourceCode = execute.readFile(dirOutputFile);
            }





            while ((s = stdError.readLine()) != null) {
                // read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                System.out.println(s);
            }
            System.out.println("expected answer, output:" +expectedAnswer.outputContent);

            System.out.println("output SourceCode:" +outputSourceCode);


            //comparar
            if(! (expectedAnswer.outputContent).equals(outputSourceCode)){
                System.out.println("É DIFERENTE");
                return "FAIL";
            }
            outputFileSourceCode.close();
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }



        return "SUCCESS";

    }

    public String readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();

        } finally {
            br.close();
        }
    }

}

