/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pokedex2000;

import apoio.ConexaoBD;
import javax.swing.JOptionPane;
import telas.TelaPokedex;

/**
 *
 * @author belli
 */
public class Pokedex2000 {

    public static void main(String[] args) {
        try {
            ConexaoBD.getInstance().getConnection();
       TelaPokedex tp = new TelaPokedex();
       tp.setVisible(true);
       } catch(Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro de conexão com banco de dados");
       } finally {
            ConexaoBD.getInstance().shutdown();
        }
    }
}
