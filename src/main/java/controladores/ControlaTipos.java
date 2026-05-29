/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidades.DAO.TiposDAO;
import entidades.Tipos;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author manuela.carlesso
 */
public class ControlaTipos {

    private TiposDAO tDAO = new TiposDAO();

    public boolean salvar(Tipos t) {
        try {
            tDAO.salvar(t);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar tipos: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Tipos> recuperarTodos() {
        try {
            return tDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar tipos: " + ex.getMessage());
            return null;
        }
    }

    public boolean excluir(int id) {
        try {
            tDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir tipos: " + ex.getMessage());
            return false;
        }
    }

    public boolean editar(Tipos t) {
        try {
            tDAO.editar(t);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao editar tipos: " + ex.getMessage());
            return false;
        }
    }
     public Tipos recuperarUm(int id){
        try {
            return tDAO.recuperarUm(id);
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar tipos: " + ex.getMessage());
            return null;
        }
    }
    
}

