package Classes;

public class SourceCodeHistory extends ExpectedAnswer{

    public String problem, filename, author, content, datetime, status;

    public SourceCodeHistory(String problem, String filename, String author, String content, String datetime, String status) {
        this.problem = problem;
        this.filename = filename;
        this.author = author;
        this.content = content; //source_code
        this.datetime = datetime;
        this.status = status;
    }

    public SourceCodeHistory() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
