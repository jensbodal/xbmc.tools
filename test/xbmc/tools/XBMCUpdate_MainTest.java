/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xbmc.tools;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jensb
 */
public class XBMCUpdate_MainTest {
    
    public XBMCUpdate_MainTest() {
    }
    
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
    }

    /**
     * Test of main method, of class XBMCUpdate_Main.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testMain0() {
        System.out.println("main");
        String[] args = {"-utor"};
        XBMCUpdate_Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void testMainUtor() {
        System.out.println("Testing uTorrent implementation");
        String[] args = {"-utor", "logfile", "label", "filename"};
        XBMCUpdate_Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
