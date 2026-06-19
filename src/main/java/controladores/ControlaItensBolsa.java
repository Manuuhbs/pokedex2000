/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidades.DAO.ItensBolsaDAO;
import entidades.ItensBolsa;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author manuela.carlesso
 */
public class ControlaItensBolsa {
    private ItensBolsaDAO IbDAO = new ItensBolsaDAO();

    public boolean salvar(ItensBolsa ib) {
        try {
            IbDAO.salvar(ib);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar itens: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<ItensBolsa> recuperarTodos() {
        try {
            return IbDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar itens: " + ex.getMessage());
            return null;
        }
    }

    public boolean excluir(int id) {
        try {
            IbDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir itens: " + ex.getMessage());
            return false;
        }
    }

    public boolean editar(ItensBolsa ib) {
        try {
            IbDAO.editar(ib);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao editar tipos: " + ex.getMessage());
            return false;
        }
    }
     public ItensBolsa recuperarUm(int id){
        try {
            return IbDAO.recuperarUm(id);
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar tipos: " + ex.getMessage());
            return null;
        }
    }
}
