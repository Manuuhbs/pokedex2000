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
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println(">> @BeforeClass: roda uma vez antes de todos os testes");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println(">> @AfterClass: roda uma vez após todos os testes");
    }

    @Before
    public void setUp() {
        System.out.println("-- @Before: preparando para o próximo teste");
    }
    @After
    public void tearDown() {
        System.out.println("-- @After: limpando após o teste");
    }
    @Test
    public void somar_deveRetornarSomaDoisNumeros() {
        int a = 3;
        int b = 4;
        int resultado = Calculadora.somar(a, b);
        assertEquals(7, resultado);
    }

  
    @Test
    public void dividir_deveRetornarResultadoCorreto() {
        double a = 10.0;
        double b = 3.0;
        double resultado = Calculadora.dividir(a, b);
        assertEquals(3.333, resultado, 0.001);
    }

 
    @Test
    public void somar_deveRetornarValorPositivo() {
        int resultado = Calculadora.somar(5, 3);
        assertTrue("Soma de positivos deve ser positiva", resultado > 0);
    }

    @Test
    public void somar_naoDeveRetornarZeroComNumerosPositivos() {
        int resultado = Calculadora.somar(2, 3);
        assertFalse("Soma de 2 + 3 não deve ser zero", resultado == 0);
    }

    @Test
    public void dividir_deveRetornarObjetoNaoNulo() {
        Double resultado = Calculadora.dividir(10, 2);
        assertNotNull("O resultado não deve ser null", resultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dividir_deveLancarExcecaoQuandoDivisorForZero() {
        Calculadora.dividir(10, 0);
    }
}
