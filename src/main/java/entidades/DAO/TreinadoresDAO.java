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
                + "SELECT * FROM tipos WHERE id_tipo = " + id;

        resultadoQ = ConexaoBD.executeQuery(sql);

        if (resultadoQ.next()) {
            Treinadores t = new Treinadores();

            t.setIdTipo(resultadoQ.getInt("id_tipo"));
            t.setNome(resultadoQ.getString("nome"));
            t.setVantagem(resultadoQ.getString("vantagem"));
            t.setDesvantagem(resultadoQ.getString("desvantagem"));
            return t;
        }
        //ajeitar

        return null;
    }
       public void editar(Tipos t) throws SQLException {
        String sql = ""
                + "UPDATE tipos SET "
                + "nome = '" + t.getNome() + "',"
                + "vantagem = '" + t.getVantagem() + "',"
                + "desvantagem = '" + t.getDesvantagem() + "',"
                +"WHERE id = " + t.getIdTipo();

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
        //ajeitar
    }
}
