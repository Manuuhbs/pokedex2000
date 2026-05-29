package apoio;

import java.sql.Connection;
import java.sql.ResultSet;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("Testando conexão com o banco de dados...");

        try {
            Connection con = ConexaoBD.getInstance().getConnection();
            System.out.println("Conexão estabelecida com sucesso!");
            System.out.println("URL: " + con.getMetaData().getURL());
            System.out.println("Usuário: " + con.getMetaData().getUserName());

            ResultSet rs = ConexaoBD.executeQuery("SELECT version()");
            if (rs.next()) {
                System.out.println("Versão do PostgreSQL: " + rs.getString(1));
            }

            ConexaoBD.getInstance().shutdown();
            System.out.println("Conexão encerrada.");

        } catch (Exception e) {
            System.err.println("Falha na conexão: " + e.getMessage());
        }
    }

}
