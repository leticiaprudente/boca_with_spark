import Classes.ConnectSQLite;
import spark.routes.Problems;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        //ConnectSQLite con = new ConnectSQLite();
        //con.connect() ;

        get("/hello", (req, res) -> "Hello World (get Spark)");

        //Problems problem = new Problems();


    }
}
