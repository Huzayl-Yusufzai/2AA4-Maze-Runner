package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RightHandTest {

    private boolean[][] maze;

    @BeforeEach
    public void setUp() {
        maze = new boolean[][] {
                { true, true, true },
                { false, false, true }
        };
    }

    @Test
    public void testRightHandSolvesSimpleMaze() {
        RightHand alg = new RightHand(0, 0, maze, 0, 2);
        String canonical = alg.returnCanonicalPath();

        Marker checker = new Marker(0, 0, maze, 0, 2);
        assertEquals("valid", checker.checkPath(canonical));
    }
}
