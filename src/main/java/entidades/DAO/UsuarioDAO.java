/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.DAO;

import apoio.ConexaoBD;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author roveda
 */
public class UsuarioDAO {

    private ResultSet resultadoQ = null; // interface que representa o resultado de uma consulta SQL executada em um banco de dados

    public boolean login(String usuario, String senha) throws SQLException {

        String sql = ""
                + "SELECT id_usuario FROM usuarios "
                + "WHERE usuario = '" + usuario + "' "
                + "AND senha = md5('" + senha +"')";

        resultadoQ = ConexaoBD.executeQuery(sql);

        return resultadoQ.next();
    }
}
