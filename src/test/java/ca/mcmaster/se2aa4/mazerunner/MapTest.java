package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;

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
        map.runAlg();
        map.initiateSearch();
        assertNotNull(map.getMapValues());
    }
}