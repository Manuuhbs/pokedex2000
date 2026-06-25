/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.DAOTest;

import apoio.ConexaoBD;
import entidades.DAO.PokemonsDAO;
import entidades.Pokemons;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author belli
 */
public class PokemonsDAOTest {

    private static final String SQL_CREATE_TABLE
            = "CREATE TABLE IF NOT EXISTS pokemons ( "
            + "  num_pokedex     SERIAL PRIMARY KEY, "
            + "  nome   VARCHAR(200)  NOT NULL, "
            + "  url      VARCHAR(255) NOT NULL, "
            + "  tipo_primario VARCHAR(100) NOT NULL, "
            + "  tipo_secundario VARCHAR(100) NOT NULL "
            + ");";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS pokemons;";

    private PokemonsDAO dao;

    @BeforeClass
    public static void setUpClass() {
        try {
            System.setProperty("db.config", "db-test.properties");
            ConexaoBD.executeUpdate(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @BeforeClass: falha ao criar tabela 'pokemons'.\n" + e.getMessage(), e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConexaoBD.executeUpdate(SQL_DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @AfterClass: falha ao dropar tabela 'pokemons'.\n" + e.getMessage(), e);
        }
    }

    @Before
    public void setUp() {
        ConexaoBD.getInstance().shutdown();
        dao = new PokemonsDAO();

        try {
            ConexaoBD.executeUpdate("TRUNCATE TABLE pokemons RESTART IDENTITY;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @Before: falha ao truncar tabela 'pokemons'.\n" + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        ConexaoBD.getInstance().shutdown();
    }

    /**
     * Testa a inserção de um planeta: 1) Insere um planeta via dao.salvar 2)
     * Recupera todos e valida que existe somente 1 3) Recupera via
     * dao.recuperarUm(1) e valida os dados
     */
    @Test
    public void testInserirERecuperarPokemons() throws SQLException {
        // 1) Cria objeto e insere no banco
        Pokemons p = new Pokemons();
        p.setNome("Raichu");
        p.setTipoPrimario(5);
        p.setTipoSecundario(2);
        dao.salvar(p);

        // 2) Recupera todos, deve haver 1
        ArrayList<Pokemons> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 1 planeta na tabela", 1, lista.size());

        // 3) Recupera por ID e valida os dados
        Pokemons recuperado = dao.recuperarUm(1);
        assertNotNull("recuperarUm(id) não deve retornar null", recuperado);
        assertEquals(1, recuperado.getNumPokedex());
        assertEquals(p.getNome(), recuperado.getNome());
        assertEquals(p.getTipoPrimario(), recuperado.getTipoPrimario());
        assertEquals(p.getTipoSecundario(), recuperado.getTipoSecundario());
    }

    /**
     * Testa a edição de um pokemon: 1) Insere um pokemon inicial 2) Altera
     * campos via dao.editar(...) 3) Recupera via dao.recuperarUm(1) e valida as
     * alterações
     */
    @Test
    public void testEditarPokemons() throws SQLException {
        // 1) Insere um registro inicial
        Pokemons p = new Pokemons();
        p.setNome("Raichu");
        p.setTipoPrimario(5);
        p.setTipoSecundario(2);
        dao.salvar(p);

        // 2) Edita com novos dados
        Pokemons modificado = new Pokemons();
        modificado.setNumPokedex(1);
        modificado.setNome("Raichu Shiny");
        modificado.setTipoPrimario(3);
        modificado.setTipoSecundario(6);
        dao.editar(modificado);

        // 3) Recupera e valida os campos atualizados
        Pokemons recuperado = dao.recuperarUm(1);
        assertNotNull("Pokemon com ID 1 deveria existir após edição", recuperado);
        assertEquals(1, recuperado.getNumPokedex());
        assertEquals("Raichu Shiny", recuperado.getNome());
        assertEquals(3, recuperado.getTipoPrimario());
        assertEquals(6, recuperado.getTipoSecundario());
    }

    /**
     * Testa a exclusão de um pokemon: 1) Insere um pokemon 2) Chama
     * dao.excluir(1) 3) Verifica que dao.recuperarUm(1) retorna null 4)
     * Verifica que dao.recuperarTodos() retorna lista vazia
     */
    @Test
    public void testExcluirPokemons() throws SQLException {
        // 1) Insere um registro
        Pokemons p = new Pokemons();
        p.setNome("Wobbuffet");
        p.setTipoPrimario(4);
        p.setTipoSecundario(3);
        dao.salvar(p);

        // Confirma que existe antes da exclusão
        Pokemons antes = dao.recuperarUm(1);
        assertNotNull("Planeta com ID 1 deveria existir antes da exclusão", antes);

        // 2) Exclui o planeta de ID = 1
        dao.excluir(1);

        // 3) Verifica que recuperarUm(1) retorna null
        Pokemons depois = dao.recuperarUm(1);
        assertNull("recuperarUm(1) deve retornar null após exclusão", depois);

        // 4) Verifica que recuperarTodos() retorna lista vazia
        ArrayList<Pokemons> todos = dao.recuperarTodos();
        assertNotNull("Lista retornada por recuperarTodos não deve ser null", todos);
        assertTrue("Lista deve estar vazia após exclusão do pokemon", todos.isEmpty());
    }

    /**
     * Testa a inserção de múltiplos pokemons e a recuperação de todos: 1)
     * Insere 3 pokemons 2) Recupera todos e valida a quantidade
     */
    @Test
    public void testRecuperarTodosComMultiplosPokemons() throws SQLException {
        // 1) Insere 3 pokemons
        String[] nomes = {"Chimchar", "Piplup", "Turtwig"};
        for (String nome : nomes) {
            Pokemons p = new Pokemons();
            p.setNome(nome);
            p.setTipoPrimario(5);
            p.setTipoSecundario(2);
            dao.salvar(p);
        }

        // 2) Recupera todos e valida
        ArrayList<Pokemons> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 3 pokemons na tabela", 3, lista.size());
    }

    @Test
    public void testSalvarDeveGerarId() throws SQLException {
        Pokemons p = new Pokemons();
        p.setNome("Pikachu");
        p.setTipoPrimario(1);
        p.setTipoSecundario(2);

        dao.salvar(p);

        assertTrue(p.getNumPokedex() > 0);
    }

    @Test
    public void testRecuperarTodosQuandoVazio() throws SQLException {
        ArrayList<Pokemons> lista = dao.recuperarTodos();

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    public void testExcluirPokemonInexistenteNaoDeveQuebrar() throws SQLException {
        dao.excluir(9999);

        ArrayList<Pokemons> lista = dao.recuperarTodos();

        assertNotNull(lista);
    }
}
