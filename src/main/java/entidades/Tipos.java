/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author belli
 */
public class Tipos {
private int idTipo;
private String nome;
private String vantagem;
private String desvantagem;

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVantagem() {
        return vantagem;
    }

    public void setVantagem(String vantagem) {
        this.vantagem = vantagem;
    }

    public String getDesvantagem() {
        return desvantagem;
    }

    public void setDesvantagem(String desvantagem) {
        this.desvantagem = desvantagem;
    }
      @Override
    public String toString() {
        String retorno = nome + ";" + vantagem + ";" + desvantagem;
        return retorno;
    }
}

