/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.DAO;

import apoio.ConexaoBD;
import entidades.Pokemons;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author belli
 */
public class PokemonsDAO {

    private ResultSet resultadoQ = null;

    public void salvar(Pokemons p) throws SQLException {
        String sql = ""
                + "INSERT INTO pokemons (nome, tipo_primario, tipo_secundario) VALUES ("
                + "'" + p.getNome() + "',"
                + "'" + p.getTipoPrimario() + "',"
                + "'" + p.getTipoSecundario() + "'" + ")";

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<Pokemons> recuperarTodos() throws SQLException {
        ArrayList<Pokemons> pokemons = new ArrayList();

        String sql = ""
                + "SELECT * FROM pokemons ";

        resultadoQ = ConexaoBD.executeQuery(sql);

        while (resultadoQ.next()) {
            Pokemons p = new Pokemons();

            p.setNumPokedex(resultadoQ.getInt("num_pokedex"));
            p.setNome(resultadoQ.getString("nome"));
            p.setTipoPrimario(resultadoQ.getInt("tipo_primario"));
            p.setTipoSecundario(resultadoQ.getInt("tipo_secundario"));
                    
            pokemons.add(p);
        }

        return pokemons;
    }
     public Pokemons recuperarUm(int id) throws SQLException {

        String sql = ""
                + "SELECT * FROM pokemons WHERE num_pokedex = " + id;

        resultadoQ = ConexaoBD.executeQuery(sql);

        if (resultadoQ.next()) {
            Pokemons p = new Pokemons();

            p.setNumPokedex(resultadoQ.getInt("num_pokedex"));
            p.setNome(resultadoQ.getString("nome"));
            p.setTipoPrimario(resultadoQ.getInt("tipo_primario"));
            p.setTipoSecundario(resultadoQ.getInt("tipo_secundario"));
            return p;
        }

        return null;
    }

      public void editar(Pokemons p) throws SQLException {
        String sql = ""
                + "UPDATE pokemons SET "
                + "nome = '" + p.getNome() + "',"
                + "tipo_primario = '" + p.getTipoPrimario() + "',"
                + "tipo_secundario = '" + p.getTipoSecundario() + "' "
                + "WHERE num_pokedex = " + p.getNumPokedex();

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }
    public void excluir(int id) throws SQLException {
        String sql = ""
                + "DELETE FROM pokemons WHERE num_pokedex = " + id;

        System.out.println("sql: " + sql);

        ConexaoBD.executeUpdate(sql);
    }
}
