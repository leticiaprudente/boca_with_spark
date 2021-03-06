package Classes;

public class ExpectedAnswer {

    public String inputFilename, inputContent, outputFilename, outputContent, problem ;
    public int testCase ;

    public ExpectedAnswer(String inputFilename, String inputContent, String outputFilename, String outputContent, String problem, int testCase) {
        this.inputFilename = inputFilename;
        this.inputContent = inputContent ;
        this.outputFilename = outputFilename;
        this.outputContent = outputContent ;
        this.problem = problem;
        this.testCase = testCase ;
    }

    public ExpectedAnswer() {
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public void setInputFilename(String inputFilename) {
        this.inputFilename = inputFilename;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public String getOutputContent() {
        return outputContent;
    }

    public void setOutputContent(String outputContent) {
        this.outputContent = outputContent;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getTestCase() {
        return testCase;
    }

    public void setTestCase(int testCase) {
        this.testCase = testCase;
    }
}
