package spark.routes;

import Classes.Problem;
import static spark.Spark.*;


public class Problems {
    public static void main(String[] args) {
        post("/problem", (req, res) -> {
            String filename = req.queryParams("filename") ;
            String problem = req.queryParams("problem") ;
            String lps = req.queryParams("lps") ;  /*java|c|c++|python*/

            try {
                Problem newProblem = new Problem(filename, problem, lps) ;

                System.out.println("teste");
                res.status(201); // Created
                return("OK");

            }
            catch(Exception e) {
                e.getStackTrace();
                res.status(404); // Not found
                return("Error");
            }
        });

        /*before("/problem", (req, res) -> {

            if (!(lps != null && lps.equals(dbLps))) {
                halt(400, "Is not a valid language!");
            }
        });*/

        /*
                HTTP Request – Method: POST – application/json – Contexto: /activity – HTTP Code: 201
                {
                    ‘filename’ : ‘pradinho.py’,
                    ‘problem’ : ‘A’,
                    ‘lps’ : ‘java|c|c++|python’ -- linguagens de programação válidas
                }
                Observação: Todos os campos são obrigatórios e caso um não seja fornecido uma
                resposta com o seguinte contrato deve ser retornado

                    HTTP Request – Method: POST - application/json – Contexto: /activity – HTTP Code:  400
                    {
                        ‘error_code’ : <codigo_elaborado_pelo_aluno>,
                        ‘error_msg : ‘<msg_erro_elaborada_pelo_aluno>’
                    }
            */


    }

}