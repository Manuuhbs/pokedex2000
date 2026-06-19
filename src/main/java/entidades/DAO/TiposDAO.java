/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.DAO;

import apoio.ConexaoBD;
import apoio.FileManager;
import entidades.Tipos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author manuela.carlesso
 */
public class TiposDAO {

    private ResultSet resultadoQ = null;

    public void salvar(Tipos t) throws SQLException {
        String sql = ""
                + "INSERT INTO tipos (nome, vantagem, desvantagem) VALUES ("
                + "'" + t.getNome() + "',"
                + "'" + t.getVantagem() + "',"
                + "'" + t.getDesvantagem() + "'" + ")";

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<Tipos> recuperarTodos() throws SQLException {
        ArrayList<Tipos> tipos = new ArrayList();

        String sql = ""
                + "SELECT * FROM tipos ";

        resultadoQ = ConexaoBD.executeQuery(sql);

        while (resultadoQ.next()) {
            Tipos t = new Tipos();

            t.setIdTipo(resultadoQ.getInt("id_tipo"));
            t.setNome(resultadoQ.getString("nome"));
            t.setVantagem(resultadoQ.getString("vantagem"));
            t.setDesvantagem(resultadoQ.getString("desvantagem"));

            tipos.add(t);
        }

        return tipos;
    }

    public Tipos recuperarUm(int id) throws SQLException {

        String sql = ""
                + "SELECT * FROM tipos WHERE id_tipo = " + id;

        resultadoQ = ConexaoBD.executeQuery(sql);

        if (resultadoQ.next()) {
            Tipos t = new Tipos();

            t.setIdTipo(resultadoQ.getInt("id_tipo"));
            t.setNome(resultadoQ.getString("nome"));
            t.setVantagem(resultadoQ.getString("vantagem"));
            t.setDesvantagem(resultadoQ.getString("desvantagem"));
            return t;
        }

        return null;
    }

    public void excluir(int id) throws SQLException {
        String sql = ""
                + "DELETE FROM tipos WHERE id_tipo = " + id;

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }

    public void editar(Tipos t) throws SQLException {
        String sql = ""
                + "UPDATE tipos SET "
                + "nome = '" + t.getNome() + "',"
                + "vantagem = '" + t.getVantagem() + "',"
                + "desvantagem = '" + t.getDesvantagem() + "' "
                + "WHERE id_tipo = " + t.getIdTipo();

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }
}
