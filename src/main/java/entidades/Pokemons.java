/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author belli
 */
public class Pokemons {
    private int numPokedex;
    private String nome;
    private String url;
    private int tipoPrimario;
    private int tipoSecundario;
    
    public int getNumPokedex() {
        return numPokedex;
    }

    public void setNumPokedex(int numPokedex) {
        this.numPokedex = numPokedex;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        String retorno = nome + ";" + tipoPrimario + "," + tipoSecundario;
        return retorno;
    }

    public int getTipoPrimario() {
        return tipoPrimario;
    }

    public void setTipoPrimario(int tipoPrimario) {
        this.tipoPrimario = tipoPrimario;
    }

    public int getTipoSecundario() {
        return tipoSecundario;
    }

    public void setTipoSecundario(int tipoSecundario) {
        this.tipoSecundario = tipoSecundario;
    }
    
}
