package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options options = new Options();
        options.addOption("i", "input", true, "Maze input path");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            if (!cmd.hasOption("i")) {
                logger.error("-i flag required for input path");
                System.out.println("Usage: <File call> -i <path_to_maze_file>");
                return;
            }

            String filePath = cmd.getOptionValue("i");
            logger.info("**** Reading the maze from file {}", filePath);

            try { //ERROR here

                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line = "";
                Map map = new Map(reader, line, filePath);
                logger.info("**** Computing path");
                map.catchStartnEnd();
                System.out.println(map.initiateCheck());

            } catch (Exception e) {
                logger.error("/!\\ An error has occurred: {}", e.getMessage(), e);
            }
        } catch (ParseException e) {
            logger.error("Failed to parse command-line arguments: {}", e.getMessage());
            System.out.println("Usage: <File call> -i <path_to_maze_file>");
            return;
        }
        logger.info("** End of Maze Runner");
    }
}

class Map {

    private boolean[][] mapValues;
    private int currentRow;
    private int currentCol;

    private int endRow;
    private int endCol;

    public Map(BufferedReader reader, String line, String filePath) {
        try {
            int row = 0;
            int cols = 0;

            reader.mark(10000);

            while ((line = reader.readLine()) != null) { //Allocating space for array
                cols = Math.max(cols, line.length());
                row++;
            }

            mapValues = new boolean[cols][row];

            reader.reset();
            row = 0;

            while ((line = reader.readLine()) != null) { //Populating with Boolean values according to text file
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        mapValues[row][idx] = false; //Wall
                    } else if (line.charAt(idx) == ' ') {
                        mapValues[row][idx] = true; //Path
                    }
                }
                row++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void catchStartnEnd() {
        for (int i = 0; i < mapValues.length; i++) {
            if (mapValues[i][0] == true) {
                currentRow = i;
                currentCol = 0;
                break;
            }
        }

        for (int i = 0; i < mapValues.length; i++) {
            if (mapValues[i][mapValues.length-1] == true) {
                endRow = i;
                endCol = mapValues.length-1;
                return;
            }
        }
    }

    public String initiateCheck() {
        Marker marker = new Marker(currentRow, currentCol, mapValues, endRow, endCol);
        marker.rightHandRule();
        return marker.returnPath();
    }

}

class Marker {

    private String path = "";
    private boolean[][] mapValues;
    private int currentRow;
    private int currentCol;

    private int endRow;
    private int endCol;
    private String direction = "E";

    public Marker(int row, int col, boolean[][] mapValues, int endRow, int endCol) {
        currentRow = row;
        currentCol = col;
        this.endRow = endRow;
        this.endCol = endCol;
        this.mapValues = mapValues;
    }

    public void rightHandRule() {
        while (true) {
            System.out.println("Current Position: (" + currentRow + ", " + currentCol + ") | Direction: " + direction);
            switch (direction) {
                case "E": //When Facing East
                    if (isValid(currentRow + 1, currentCol)) { //Checking SOUTH
                        direction = "S";
                        addToPath("RF");
                        currentRow++;
                    } else if (isValid(currentRow, currentCol + 1)) {//Checking EAST
                        direction = "E";
                        addToPath("F");
                        currentCol++;
                    } else if (isValid(currentRow - 1, currentCol)) {//Checking NORTH
                        direction = "N";
                        addToPath("LF");
                        currentRow--;
                    } else { //GO WEST
                        direction = "W";
                        addToPath("RRF");
                        currentCol--;
                    }
                    break;

                case "N": //When Facing NORTH
                    if (isValid(currentRow, currentCol + 1)) { //Checking EAST
                        direction = "E";
                        addToPath("RF");
                        currentCol++;
                    } else if (isValid(currentRow - 1, currentCol)) {//Checking NORTH
                        direction = "N";
                        addToPath("F");
                        currentRow--;
                    } else if (isValid(currentRow, currentCol - 1)) {//Checking WEST
                        direction = "W";
                        addToPath("LF");
                        currentCol--;
                    } else { //GO SOUTH
                        direction = "S";
                        addToPath("RRF");
                        currentRow++;
                    }
                    break;

                case "S": //When Facing SOUTH
                    if (isValid(currentRow, currentCol - 1)) {//Checking WEST
                        direction = "W";
                        addToPath("RF");
                        currentCol--;
                    } else if (isValid(currentRow + 1, currentCol)) {//Checing SOUTH
                        direction = "S";
                        addToPath("F");
                        currentRow++;
                    } else if (isValid(currentRow, currentCol + 1)) { //Checking EAST
                        direction = "E";
                        addToPath("LF");
                        currentCol++;
                    } else if (isValid(currentRow-1, currentCol)){ //GO NORTH
                        direction = "N";
                        addToPath("RRF");
                        currentRow--;
                    }
                    break;
                case "W": //When Facing WEST
                    if (isValid(currentRow - 1, currentCol)) {//Checking NORTH
                        direction = "N";
                        addToPath("RF");
                        currentRow--;
                    } else if (isValid(currentRow, currentCol - 1)) {//Checking WEST
                        direction = "W";
                        addToPath("F");
                        currentCol--;
                    } else if (isValid(currentRow + 1, currentCol)) { //Checking SOUTH
                        direction = "S";
                        addToPath("LF");
                        currentRow++;
                    } else { //GO WEST
                        direction = "E";
                        addToPath("RRF");
                        currentCol++;
                    }
                    break;

            
            }

            if (endCol == currentCol && endRow == currentRow){
                break;
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < mapValues.length
                && col >= 0 && col < mapValues[0].length
                && mapValues[row][col];
    }

    public void addToPath(String direction) {
        path = path.concat(direction);
    }

    public String returnPath() {
        return path;
    }
}
