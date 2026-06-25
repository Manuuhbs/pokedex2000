/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesTest;

import entidades.Pokemons;
import entidades.Tipos;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author belli
 */
public class TiposTest {
     private Tipos tipo;
    @Before
     public void setUp() {
        tipo = new Tipos();
        tipo.setNome("fogo");
    }
     @Test
     public void testNomeNaoPodeSerNulo() {
        assertNotNull("o nome não pode ser nulo", tipo.getNome());
    }
     @Test
     public void testVantagemTipo() {
        Tipos t = new Tipos();
        t.setVantagem("agua");
         assertEquals("agua", t.getVantagem());
     }
}
