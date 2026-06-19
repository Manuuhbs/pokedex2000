/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.DAO;

import apoio.ConexaoBD;
import apoio.FileManager;
import entidades.Treinadores;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author manuela.carlesso
 */
public class TreinadoresDAO {

    private ResultSet resultadoQ = null;

    public void salvar(Treinadores t) throws SQLException {
        String sql = ""
                + "INSERT INTO treinadores (nome, idade, sexo, genero) VALUES ("
                + "'" + t.getNome() + "',"
                + "'" + t.getIdade() + "',"
                + "'" + t.getSexo() + "',"
                + "'" + t.getGenero() + "'" + ")";

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<Treinadores> recuperarTodos() throws SQLException {
        ArrayList<Treinadores> treinadores = new ArrayList();

        String sql = ""
                + "SELECT * FROM treinadores ";

        resultadoQ = ConexaoBD.executeQuery(sql);

        while (resultadoQ.next()) {
            Treinadores tr = new Treinadores();

            tr.setIdTreinador(resultadoQ.getInt("id_treinador"));
            tr.setNome(resultadoQ.getString("nome"));
            tr.setIdade(resultadoQ.getString("idade"));
            tr.setSexo(resultadoQ.getString("sexo"));
            tr.setGenero(resultadoQ.getString("genero"));

            treinadores.add(tr);
        }

        return treinadores;
    }
       public void excluir(int id) throws SQLException {
        String sql = ""
                + "DELETE FROM treinadores WHERE id_treinador = " + id;

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }
       public Treinadores recuperarUm(int id) throws SQLException {

        String sql = ""
                + "SELECT * FROM treinadores WHERE id_treinador = " + id;

        resultadoQ = ConexaoBD.executeQuery(sql);

        if (resultadoQ.next()) {
            Treinadores t = new Treinadores();

            t.setIdTreinador(resultadoQ.getInt("id_treinador"));
            t.setNome(resultadoQ.getString("nome"));
            t.setIdade(resultadoQ.getString("idade"));
            t.setSexo(resultadoQ.getString("sexo"));
            t.setGenero(resultadoQ.getString("genero"));
            return t;
        }
        

        return null;
    }
       public void editar(Treinadores t) throws SQLException {
        String sql = ""
                + "UPDATE treinadores SET "
                + "nome = '" + t.getNome() + "',"
                + "idade = '" + t.getIdade() + "',"
                + "sexo = '" + t.getSexo() + "',"
                + "genero = '" + t.getGenero() + "'"
                +"WHERE id_treinador = " + t.getIdTreinador();

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
        
    }
}
