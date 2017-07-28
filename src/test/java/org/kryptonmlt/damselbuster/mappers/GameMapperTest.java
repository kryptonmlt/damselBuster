package org.kryptonmlt.damselbuster.mappers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kryptonmlt.damselbuster.pojos.Game;
import org.kryptonmlt.damselbuster.pojos.Platform;

/**
 *
 * @author Kurt
 */
public class GameMapperTest {

    public GameMapperTest() {
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
     * Test of createGame method, of class GameMapper.
     */
    @Test
    public void testCreateGame() {
        System.out.println("createGame");
        String name = "QUEEN OF EGYPT™ - Slot";
        String description = "Ascent: 5-Reel 30-Line Configurable Max Bet";
        String imagePath = "images/12345.png";
        Platform platform = new Platform();
        platform.setName("test");
        Game result = GameMapper.createGame(name, description, imagePath, platform);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
        assertEquals(imagePath, result.getImagePath());
        assertEquals(platform.getName(), result.getPlatform().getName());
        assertEquals(5, result.getReel());
        assertEquals(30, result.getLine());
        assertEquals(-1, result.getWay());
        assertEquals(-1, result.getCredit());
    }

}
