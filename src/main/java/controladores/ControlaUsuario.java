/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidades.DAO.UsuarioDAO;
import java.sql.SQLException;

/**
 *
 * @author roveda
 */
public class ControlaUsuario {
    private UsuarioDAO uDAO = new UsuarioDAO();
    
    public boolean login(String usuario, String senha) {
        try {
            return uDAO.login(usuario, senha);
        } catch (SQLException ex) {
            System.out.println("Erro ao logar: " + ex.getMessage());
            return false;
        }
    }
}
