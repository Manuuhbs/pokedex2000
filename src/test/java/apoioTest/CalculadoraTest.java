/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apoioTest;

import apoio.Calculadora;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author belli
 */
public class CalculadoraTest {
    
    // =========================================================================
    // @BeforeClass — executa UMA VEZ antes de todos os @Test desta classe.
    // Usar para inicializações caras (abrir conexão, criar arquivos, etc.).
    // Deve ser static.
    // =========================================================================
    @BeforeClass
    public static void setUpClass() {
        System.out.println(">> @BeforeClass: roda uma vez antes de todos os testes");
    }

    // =========================================================================
    // @AfterClass — executa UMA VEZ após todos os @Test desta classe.
    // Usar para liberar recursos (fechar conexão, deletar arquivos, etc.).
    // Deve ser static.
    // =========================================================================
    @AfterClass
    public static void tearDownClass() {
        System.out.println(">> @AfterClass: roda uma vez após todos os testes");
    }

    // =========================================================================
    // @Before — executa ANTES de cada @Test.
    // Usar para deixar o ambiente limpo e previsível para cada teste.
    // =========================================================================
    @Before
    public void setUp() {
        System.out.println("-- @Before: preparando para o próximo teste");
    }

    // =========================================================================
    // @After — executa APÓS cada @Test.
    // Usar para desfazer o que o @Before preparou (ex: fechar resources).
    // =========================================================================
    @After
    public void tearDown() {
        System.out.println("-- @After: limpando após o teste");
    }

    // =========================================================================
    // @Test — marca o método como um caso de teste.
    // O JUnit executa cada @Test de forma independente.
    // =========================================================================

    /**
     * assertEquals(esperado, obtido)
     * Verifica se dois valores são IGUAIS.
     * Falha se esperado != obtido.
     */
    @Test
    public void somar_deveRetornarSomaDoisNumeros() {
        // Arrange
        int a = 3;
        int b = 4;

        // Act
        int resultado = Calculadora.somar(a, b);

        // Assert
        assertEquals(7, resultado);
    }

    /**
     * assertEquals com mensagem de erro personalizada.
     * O primeiro parâmetro é a mensagem exibida se o teste falhar.
     */
    @Test
    public void subtrair_deveRetornarDiferencaDoisNumeros() {
        // Arrange
        int a = 10;
        int b = 3;

        // Act
        int resultado = Calculadora.subtrair(a, b);

        // Assert
        assertEquals("10 - 3 deveria ser 7", 7, resultado);
    }

    /**
     * assertEquals para double: usa delta (tolerância de arredondamento).
     * Necessário porque doubles têm imprecisão de ponto flutuante.
     * assertEquals(esperado, obtido, delta)
     */
    @Test
    public void dividir_deveRetornarResultadoCorreto() {
        // Arrange
        double a = 10.0;
        double b = 3.0;

        // Act
        double resultado = Calculadora.dividir(a, b);

        // Assert — delta de 0.001 aceita diferenças menores que isso
        assertEquals(3.333, resultado, 0.001);
    }

    /**
     * assertTrue(condicao)
     * Verifica se a condição é VERDADEIRA.
     */
    @Test
    public void somar_deveRetornarValorPositivo() {
        // Act
        int resultado = Calculadora.somar(5, 3);

        // Assert
        assertTrue("Soma de positivos deve ser positiva", resultado > 0);
    }

    /**
     * assertFalse(condicao)
     * Verifica se a condição é FALSA.
     */
    @Test
    public void somar_naoDeveRetornarZeroComNumerosPositivos() {
        // Act
        int resultado = Calculadora.somar(2, 3);

        // Assert
        assertFalse("Soma de 2 + 3 não deve ser zero", resultado == 0);
    }

    /**
     * assertNotNull(objeto)
     * Verifica se o objeto NÃO é null.
     */
    @Test
    public void dividir_deveRetornarObjetoNaoNulo() {
        // Act
        Double resultado = Calculadora.dividir(10, 2);

        // Assert
        assertNotNull("O resultado não deve ser null", resultado);
    }

    /**
     * @Test(expected = MinhaException.class)
     * Verifica se o método lança a exceção esperada.
     * O teste PASSA se a exceção for lançada; FALHA se não for.
     */
    @Test(expected = IllegalArgumentException.class)
    public void dividir_deveLancarExcecaoQuandoDivisorForZero() {
        // Act — deve lançar IllegalArgumentException
        Calculadora.dividir(10, 0);
    }
}
