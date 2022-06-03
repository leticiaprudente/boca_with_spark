package Classes;

import java.util.List;
import java.io.*;

public class Problem {

    public String filename, problem, lps;

    public Problem(String filename, String problem, String lps) {
        this.filename = filename;
        this.problem = problem;
        this.lps = lps;
    }

    public Problem() {
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

    //public List<Problem> getAllProblems() ;
}