package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

class Map { // Class to deal woth map data

    private boolean[][] mapValues;
    private int currentRow;
    private int currentCol;
    private int endRow;
    private int endCol;
    private RightHand alg;
    private Marker marker;

    public Map(BufferedReader reader, String line, String filePath) {
        try { // Text file to boolean array
            List<String> lines = new ArrayList<>();
            int cols = 0;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
                cols = Math.max(cols, line.length());
            }

            int rows = lines.size();
            mapValues = new boolean[rows][cols];

            for (int row = 0; row < rows; row++) {
                line = lines.get(row);
                for (int col = 0; col < line.length(); col++) {
                    if (line.charAt(col) == '#') {
                        mapValues[row][col] = false; // WALL
                    } else {
                        mapValues[row][col] = true; // PATH
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void catchStartnEnd() { // Finds exit and enterences on each side of the map
        for (int i = 0; i < mapValues.length; i++) {
            if (mapValues[i][0] == true) {
                currentRow = i;
                currentCol = 0;
                break;
            }
        }

        for (int i = 0; i < mapValues.length; i++) {
            if (mapValues[i][mapValues[i].length - 1] == true) {
                endRow = i;
                endCol = mapValues[i].length - 1;
                return;
            }
        }
    }

    public void createMarker() { // Creates a marker for the map
        marker = new Marker(currentRow, currentCol, mapValues, endRow, endCol);
    }

    public void runAlg() { // Creates a marke and runs right hand rule for the map
        alg = new RightHand(currentRow, currentCol, mapValues, endRow, endCol);
    }

    public void initiateSearch() { // States the path if it needs to be determined
        System.out.println("    Canonical: " + alg.returnCanonicalPath());
        System.out.println("    Factorized: " + alg.returnFactorizedPath());
    }

    public String initiateCheck(String userPath) { // Starts the verification if path needs to be checked
        return marker.checkPath(marker.fac2Canonical(userPath));
    }

    public boolean[][] getMapValues() {
        return mapValues;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

}