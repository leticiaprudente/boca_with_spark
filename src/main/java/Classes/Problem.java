package Classes;

public class Problem {

    public String filename, problem, lps;

    public Problem(String filename, String problem, String lps) {
        this.filename = filename;
        this.problem = problem;
        this.lps = lps;
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

    public String getLps() {
        return lps;
    }

    public void setLps(String lps) {
        this.lps = lps;
    }
}