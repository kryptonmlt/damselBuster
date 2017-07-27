/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kryptonmlt.damselbuster.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kryptonmlt.damselbuster.enums.InfoTypes;

/**
 *
 * @author Kurt
 */
public class ExtractorUtilsTest {

    public ExtractorUtilsTest() {
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
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Reel() {
        System.out.println("testGetValue_Line");
        String text = "Ascent MLD: 20-Reel 40-Line 1024-Way 200-Credit ";
        int expResult = 20;
        int result = ExtractorUtils.getValue(InfoTypes.REEL.getName(), text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Line() {
        System.out.println("testGetValue_Line");
        String text = "Ascent MLD: 20-Reel 40-Line 1024-Way 200-Credit ";
        int expResult = 40;
        int result = ExtractorUtils.getValue(InfoTypes.LINE.getName(), text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Credit() {
        System.out.println("testGetValue_Credit");
        String text = "Ascent MLD: 20-Reel 40-Line 1024-Way 200-Credit ";
        int expResult = 200;
        int result = ExtractorUtils.getValue(InfoTypes.CREDIT.getName(), text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Way() {
        System.out.println("testGetValue_Way");
        String text = "Ascent MLD: 20-Reel 40-Line 1024-Way 200-Credit ";
        int expResult = 1024;
        int result = ExtractorUtils.getValue(InfoTypes.WAY.getName(), text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Negative() {
        System.out.println("testGetValue_Negative");
        String text = "Ascent MLD: 20-Reel 40-Line 1024-Way 200-Credit ";
        int expResult = -1;
        int result = ExtractorUtils.getValue("Cre", text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Text() {
        System.out.println("testGetValue_Text");
        String text = "Ascent MLD: 20-Reel 40-Line AY1024X-Way 200-Credit ";
        int expResult = 1024;
        int result = ExtractorUtils.getValue(InfoTypes.WAY.getName(), text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class ExtractorUtils.
     */
    @Test
    public void testGetValue_Text_Spaces() {
        System.out.println("testGetValue_Text");
        String text = "AVP - Bingo: 5-Reel 100-Line Configurable Max Bet ";
        int expResult = 100;
        int result = ExtractorUtils.getValue(InfoTypes.LINE.getName(), text);
        assertEquals(expResult, result);
    }

    /**
     * Test of stripWord method, of class ExtractorUtils.
     */
    @Test
    public void testStripWord_Space() {
        System.out.println("testStripWord");
        String expResult = "Advantage";
        String result = ExtractorUtils.stripWord("Advantage (100)", "(");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of stripWord method, of class ExtractorUtils.
     */
    @Test
    public void testStripWord_Tab() {
        System.out.println("testStripWord");
        String expResult = "Advantage";
        String result = ExtractorUtils.stripWord("Advantage     (100)", "(");
        assertEquals(expResult, result);
    }

}
