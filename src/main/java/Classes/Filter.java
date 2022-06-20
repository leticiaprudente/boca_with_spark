package Classes;

public class Filter {
    public  String problems, startDateTime,endDateTime, status;

    public Filter(String problems, String startDateTime, String endDateTime,  String status) {
        this.problems = problems;
        this.startDateTime = startDateTime ;
        this.endDateTime = endDateTime ;
        this.status = status;
    }

    public Filter() {
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
