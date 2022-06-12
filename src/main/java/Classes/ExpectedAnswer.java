package Classes;

public class ExpectedAnswer {

    public String filename, problem, content;

    public ExpectedAnswer(String filename, String problem, String content) {
        this.filename = filename;
        this.problem = problem;
        this.content = content;
    }

    public ExpectedAnswer() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
