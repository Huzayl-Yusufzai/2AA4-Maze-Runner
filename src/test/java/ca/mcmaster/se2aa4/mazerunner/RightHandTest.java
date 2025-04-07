package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RightHandTest {

    @Test
    public void testRightHandSolverStraightLine() {

        boolean[][] maze = {
                { true, true, true }
        };

        Marker marker = new Marker(0, 0, maze, 0, 2);
        MazeAlg solver = new RightHand(marker);
        solver.solve();

        assertEquals(0, marker.getCurrentRow());
        assertEquals(2, marker.getCurrentCol());
    }
}
