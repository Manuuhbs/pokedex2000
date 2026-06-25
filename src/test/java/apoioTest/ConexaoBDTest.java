/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apoioTest;

import apoio.ConexaoBD;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author belli
 */
public class ConexaoBDTest {
      @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        try {
            ConexaoBD instance = ConexaoBD.getInstance();
            if (instance.getConnection() != null) {
                instance.shutdown();
            }
        } catch (Exception e) {
        }
    }

    @Test
    public void testConexaoNaoNula() throws SQLException {
        Connection conn = ConexaoBD.getInstance().getConnection();
        assertNotNull("A conexão não deve ser nula", conn);
    }

    @Test
    public void testConexaoAberta() throws SQLException {
        Connection conn = ConexaoBD.getInstance().getConnection();
        assertFalse("A conexão não deve estar fechada", conn.isClosed());
        conn.close();
    }

}
