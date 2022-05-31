package Classes;

public class Error {
    private String error_msg;
    private int error_code;

    public Error(String message, String error_code ) {
        this.error_msg = String.format(message, error_code);
    }

    public Error(Exception e) {
        this.error_msg = e.getMessage();
    }

    public String getMessage() {
        return this.error_msg;
    }
}
