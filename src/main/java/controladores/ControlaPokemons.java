/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidades.DAO.PokemonsDAO;
import entidades.Pokemons;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author belli
 */
public class ControlaPokemons {
    private PokemonsDAO pDAO = new PokemonsDAO();

    public boolean salvar(Pokemons p) {
        try {
            pDAO.salvar(p);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar pokemons: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Pokemons> recuperarTodos() {
        try {
            return pDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar pokemons: " + ex.getMessage());
            return null;
        }
    }
      public boolean excluir(int id){
        try {
            pDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir pokemons: " + ex.getMessage());
            return false;
        }
      }
}
