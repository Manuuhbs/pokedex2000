/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.DAO;

import apoio.ConexaoBD;
import entidades.ItensBolsa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author manuela.carlesso
 */
public class ItensBolsaDAO {
      private ResultSet resultadoQ = null;

    public void salvar(ItensBolsa ib) throws SQLException {
        String sql = ""
                + "INSERT INTO itens_bolsa (nome, tipo, qtd_item, id_treinador) VALUES ("
                + "'" + ib.getNome() + "',"
                + "'" + ib.getTipo() + "',"
                + "'" + ib.getQtdItem() + "',"
                + "'" + ib.getIdTreinador() + "'" + ")";

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<ItensBolsa> recuperarTodos() throws SQLException {
        ArrayList<ItensBolsa> itensbolsa = new ArrayList();

        String sql = ""
                + "SELECT * FROM itens_bolsa ";

        resultadoQ = ConexaoBD.executeQuery(sql);

        while (resultadoQ.next()) {
            ItensBolsa ib = new ItensBolsa();

            ib.setIdItem(resultadoQ.getInt("id_item"));
            ib.setNome(resultadoQ.getString("nome"));
            ib.setTipo(resultadoQ.getString("tipo"));
            ib.setQtdItem(resultadoQ.getInt("qtd_item"));
            ib.setIdTreinador(resultadoQ.getInt("id_treinador"));

            itensbolsa.add(ib);
        }

        return itensbolsa;
    }

    public ItensBolsa recuperarUm(int id) throws SQLException {

        String sql = ""
                + "SELECT * FROM itens_bolsa WHERE id_item = " + id;

        resultadoQ = ConexaoBD.executeQuery(sql);

        if (resultadoQ.next()) {
            ItensBolsa ib = new ItensBolsa();

            ib.setIdItem(resultadoQ.getInt("id_item"));
            ib.setNome(resultadoQ.getString("nome"));
            ib.setTipo(resultadoQ.getString("tipo"));
            ib.setQtdItem(resultadoQ.getInt("qtd_item"));
            ib.setIdTreinador(resultadoQ.getInt("id_treinador"));
            
            return ib;
        }

        return null;
    }

    public void excluir(int id) throws SQLException {
        String sql = ""
                + "DELETE FROM itens_bolsa WHERE id_item = " + id;

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }

    public void editar(ItensBolsa ib) throws SQLException {
        String sql = ""
                + "UPDATE itens_bolsa SET "
                + "nome = '" + ib.getNome() + "',"
                + "tipo = '" + ib.getTipo() + "',"
                + "qtd_item = '" + ib.getQtdItem() + "',"
                + "id_treinador = '" + ib.getIdTreinador() + "' "
                + "WHERE id_item = " + ib.getIdItem();

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }
}
