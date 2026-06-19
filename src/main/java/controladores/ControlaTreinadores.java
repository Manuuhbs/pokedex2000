/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidades.DAO.TreinadoresDAO;
import entidades.Treinadores;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author manuela.carlesso
 */
public class ControlaTreinadores {

    private TreinadoresDAO trDAO = new TreinadoresDAO();

    public boolean salvar(Treinadores tr) {
        try {
            trDAO.salvar(tr);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar treinadores: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Treinadores> recuperarTodos() {
        try {
            return trDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar treinadores: " + ex.getMessage());
            return null;
        }
    }

    public boolean excluir(int id) {
        try {
            trDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir treinadores: " + ex.getMessage());
            return false;
        }
    }

    public boolean editar(Treinadores ts) {
        try {
            trDAO.editar(ts);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao editar treinadores: " + ex.getMessage());
            return false;
        }
    }

    public Treinadores recuperarUm(int id) {
        try {
            return trDAO.recuperarUm(id);
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar treinadores: " + ex.getMessage());
            return null;
        }
    }

}
