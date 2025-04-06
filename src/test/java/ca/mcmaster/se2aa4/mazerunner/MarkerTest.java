package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkerTest {

    private Marker marker;

    @BeforeEach
    public void setUp() {
        marker = new Marker(0, 0, new boolean[1][1], 0, 0);
    }

    @Test
    public void testFactorizeSimple() {
        marker.path = "FFFRRL";
        String result = marker.returnFactorizedPath();
        assertEquals("3F 2R L ", result);
    }

    @Test
    public void testFactorizeConplex() {
        marker.path = "FFFFFFFFFFFFFFRRRLLRFRL";
        String result = marker.returnFactorizedPath();
        assertEquals("14F 3R 2L R F R L ", result);
    }

    @Test
    public void testFac2CanonicalSingleDigits() {
        String result = marker.fac2Canonical("2F3R");
        assertEquals("FFRRR", result);
    }

    @Test
    public void testFac2CanonicalMixed() {
        String result = marker.fac2Canonical("F2RL2F");
        assertEquals("FRRLFF", result);
    }

    @Test
    public void testCheckPathValidSimple() {
        boolean[][] maze = {
                { true, true, true },
                { false, false, true }
        };
        marker = new Marker(0, 0, maze, 0, 2);
        String canonical = "FF";
        String result = marker.checkPath(canonical);
        assertEquals("valid", result);
    }

    @Test
    public void testCheckPathValidComplex() {
        boolean[][] maze = {
                { false, false, false, false, false },
                { false, true, false, true, false },
                { false, true, true, true, false },
                { false, true, false, true, false },
                { false, true, false, true, true },
                { true, true, false, false, false },
                { false, false, false, false, false }
        };
        marker = new Marker(5, 0, maze, 4, 4);
        String canonical = "FLFFFRFFRFFLF";
        String result = marker.checkPath(canonical);
        assertEquals("valid", result);
    }

    @Test
    public void testCheckPathInvalid() {
        boolean[][] maze = {
                { true, false, true },
                { false, false, true }
        };
        marker = new Marker(0, 0, maze, 0, 2);
        String canonical = "FF";
        String result = marker.checkPath(canonical);
        assertEquals("not valid", result);
    }
}
