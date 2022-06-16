package Classes;

public class SourceCode{

    public String problem, filename, author, source_code, datetime, status;

    public SourceCode(String problem, String filename, String author, String source_code, String datetime, String status) {
        this.problem = problem;
        this.filename = filename;
        this.author = author;
        this.source_code = source_code;
        this.datetime = datetime;
        this.status = status;
    }

    public SourceCode() {
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceCode() {
        return source_code;
    }

    public void setSourceCode(String source_code) {
        this.source_code = source_code;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
