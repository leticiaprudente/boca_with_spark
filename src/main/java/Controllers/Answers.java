package Controllers;

import static spark.Spark.*;

public class Answers {
    public static void main(String[] args) {
        post("/answer", (req, res) -> {
            String filename = req.queryParams("filename") ;
            String problem = req.queryParams("problem") ;
            String content = req.queryParams("content") ;

            return ("OK");
        });
            /*
            b. O sitema deve possibilitar o registro de resultados esperados em arquivos.
            Ficará a critério do aluno definir a estrutura de diretórios a ser criada para a
            criação dos arquivos de acordo com o POST realizado, sendo que o
            processamento deverá considerar a comparação com todas os resultados
            existentes:
            HTTP Request – Method: POST – application/json – Contexto: /activity/response –
            HTTP Code: 201
            {
                ‘filename’ : ‘respostaA.txt’,
                ‘problem’ : <id_do_problema_em_que_a_resposta_sera_associada>,
                ‘content’ : ‘<conteudo_do_arquivo>’ -- conteúdo do arquivo a ser criado
            }
            Observação: Todos os campos são obrigatórios e caso um não seja fornecido uma
            resposta com o seguinte contrato deve ser retornado
                HTTP Request – Method: POST - application/json – Contexto: /activity/response –
                HTTP Code: 400
                {
                    ‘error_code’ : <codigo_elaborado_pelo_aluno>,
                    ‘error_msg : ‘<msg_erro_elaborada_pelo_aluno>’
                }
            */

    }
}