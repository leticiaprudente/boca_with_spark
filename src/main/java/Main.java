import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World (get Spark)");
    }
}

/*class Main {
    public static void main(String[] args) {

        try {
            System.out.println("Hello World!!! (Main.java)");
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }
}*/