/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesTest;

import entidades.Pokemons;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author belli
 */
public class PokemonsTest {

    private Pokemons pokemon;

    @Before
    public void setUp() {
        pokemon = new Pokemons();
        pokemon.setNome("oshawott");
        pokemon.setTipoPrimario(6);
        pokemon.setTipoSecundario(10);
    }

    @Test
    public void testNomeNaoPodeSerNulo() {
        assertNotNull("o nome não pode ser nulo", pokemon.getNome());
    }

    @Test
    public void testTipoPrimarioAtribuido() {
        Pokemons p = new Pokemons();
        p.setTipoPrimario(1);
        assertEquals(1, p.getTipoPrimario());
    }

    @Test
    public void testPokemonNaoNulo() {
        Pokemons p = new Pokemons();
        assertNotNull(p);
    }

    @Test
    public void testSetNomePokemon() {
        Pokemons p = new Pokemons();
        p.setNome("Pikachu");
        assertEquals("Pikachu", p.getNome());
    }

    @Test
    public void testTiposPokemon() {
        Pokemons p = new Pokemons();
        p.setTipoPrimario(1);
        p.setTipoSecundario(2);
        assertEquals(1, p.getTipoPrimario());
        assertEquals(2, p.getTipoSecundario());
    }
}
