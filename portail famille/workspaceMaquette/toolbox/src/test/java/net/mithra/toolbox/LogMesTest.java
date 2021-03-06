/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox;

import org.apache.log4j.PropertyConfigurator;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author frebeche
 */

public class LogMesTest {
    
    public LogMesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        PropertyConfigurator.configure("src/test/resources/log4j.xml");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of log method, of class LogMes.
     */
    @Test
    @Ignore
    public void testLog() {
        System.out.println("log");
        Class sClass = null;
        int type = 0;
        Object message = null;
        LogMes.log(sClass, type, message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDebug method, of class LogMes.
     */
    @Test
    public void testIsDebug() {
        System.out.println("isDebug");
        Class sClass = LogMesTest.class;
        boolean expResult = false;
        boolean result = LogMes.isDebug(sClass);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInfo method, of class LogMes.
     */
    @Test
    public void testIsInfo() {
        System.out.println("isInfo");
        Class sClass = LogMesTest.class;
        boolean expResult = true;
        boolean result = LogMes.isInfo(sClass);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFolderName method, of class LogMes.
     */
    @Test
    public void testGetFolderName() {
        System.out.println("getFolderName");
        Class sClass = LogMesTest.class;
        String expResult = System.getProperty("user.dir")+"/";
        String result = LogMes.getFolderName(sClass);
        assertEquals(expResult, result);
    }
}
