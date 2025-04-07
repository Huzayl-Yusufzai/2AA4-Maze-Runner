package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

public class MapTest {

    private Map map;

    @BeforeEach
    public void setUp() throws Exception {
        String filePath = "examples/tiny.maz.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        map = new Map(reader, "", filePath);
        map.catchStartnEnd();
    }

    @Test
    public void testLoadTinyMazeFile() {
        map.createMarker();

        boolean[][] mazeValues = map.getMapValues();
        assertNotNull(mazeValues, "The maze should be loaded");
        assertTrue(mazeValues.length > 0, "Maze should have at least one row");
        assertTrue(mazeValues[0].length > 0, "Maze should have at least one column");

        int endRow = map.getEndRow();
        int endCol = map.getEndCol();
        assertTrue(endRow >= 0 && endRow < mazeValues.length, "End row is within bounds");
        assertTrue(endCol >= 0 && endCol < mazeValues[0].length, "End col is within bounds");
    }

    @Test
    public void testRunAlgTinyMazeFile() {
        String mazeString = "XXX\n" +
                "XXX\n" +
                "XXX";
        BufferedReader reader = new BufferedReader(new StringReader(mazeString));
        Map testMap = new Map(reader, mazeString, "dummyPath");
        testMap.catchStartnEnd();

        testMap.runAlg();
        testMap.initiateSearch();

        boolean[][] mazeValues = testMap.getMapValues();
        assertNotNull(mazeValues, "Maze values should be initialized");
        assertEquals(3, mazeValues.length, "Maze should have 3 rows");
        assertEquals(3, mazeValues[0].length, "Maze should have 3 columns");
    }
}
