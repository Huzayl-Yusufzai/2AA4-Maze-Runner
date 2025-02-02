package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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
        //Add flag options to input
        Options options = new Options();
        options.addOption("i", "input", true, "Maze input path");
        options.addOption("p", "input", true, "Proposed Maze Solution");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("i") && cmd.hasOption("p")){ //If user path needs to be checked

                String filePath = cmd.getOptionValue("i");
                String userPath = cmd.getOptionValue("p");

                try {

                    BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    String line = "";
                    Map map = new Map(reader, line, filePath);
                    map.catchStartnEnd();
                    map.createMarker();
                    System.out.println("Input path is " + map.initiateCheck(userPath));

                } catch (Exception e) {
                    logger.error("/!\\ An error has occurred: {}", e.getMessage(), e);
                }

            } else { //If path needs to be generated
                if (!cmd.hasOption("i")) {
                    logger.error("-i flag required for input path");
                    System.out.println("Usage: <File call> -i <path_to_maze_file>");
                    return;
                }

                String filePath = cmd.getOptionValue("i");
                try {

                    BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    String line = "";
                    Map map = new Map(reader, line, filePath);
                    map.catchStartnEnd();
                    map.createMarker();
                    System.out.println("West entrance: ");
                    map.initiateSearch();

                } catch (Exception e) {
                    logger.error("/!\\ An error has occurred: {}", e.getMessage(), e);
                }
            }
        } catch (ParseException e) {
            logger.error("Failed to parse command-line arguments: {}", e.getMessage());
            System.out.println("Usage: <File call> -i <path_to_maze_file>");
            return;
        }
    }
}

class Map { //Class to deal woth map data

    private boolean[][] mapValues;
    private int currentRow;
    private int currentCol;
    private Marker marker;
    private int endRow;
    private int endCol;

    public Map(BufferedReader reader, String line, String filePath) {
        try { //Text file to boolean array
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

    public void catchStartnEnd() { //Finds exit and enterences on each side of the map
        for (int i = 0; i < mapValues.length; i++) {
            if (mapValues[i][0] == true) {
                currentRow = i;
                currentCol = 0;
                break;
            }
        }

        for (int i = 0; i < mapValues.length; i++) { 
            if (mapValues[i][mapValues[i].length-1] == true) {
                endRow = i;
                endCol = mapValues[i].length-1;
                return;
            }
        }
    }

    public void createMarker(){ //Creates a marker for the map
        marker = new Marker(currentRow, currentCol, mapValues, endRow, endCol);
    }


    public void initiateSearch() { //States the path if it needs to be determined
        marker.rightHandRule();
        System.out.println("    Canonical: " + marker.returnCanonicalPath());
        System.out.println("    Factorized: " + marker.returnFactorizedPath());
    }

    public String initiateCheck(String userPath){ //Starts the verification if path needs to be checked
        return marker.checkPath(marker.fac2Canonical(userPath));
    }

}

class Marker { //Marker class which walks through the maze

    private String path = "";
    private boolean[][] mapValues;
    private int currentRow;
    private int currentCol;
    private int eastRow;
    private int eastCol;
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
                    } else { //GO EAST
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

    private boolean isValid(int row, int col) { //Checks if potion is valid
        return row >= 0 && row < mapValues.length
                && col >= 0 && col < mapValues[0].length
                && mapValues[row][col];
    }

    public void addToPath(String direction) { //Adds steps to path
        path = path.concat(direction);
    }

    public String returnCanonicalPath() { //Returns canonized path
        return path;
    }

    public String returnFactorizedPath() { //Returns factorized path
        path = factorize();
        return path;
    }

    public String factorize(){ //Factorizes the path
        char[] splitPath = path.toCharArray();
        char previous = splitPath[0];
        String newFactorized="";
        int counter = 0;
        int index = 0;

        for (char chars: splitPath){
            if (previous !=chars){
                newFactorized = (counter == 1 ) ? newFactorized.concat(String.valueOf(previous + " ")): newFactorized.concat(String.valueOf(counter)).concat(String.valueOf(previous + " "));
                previous = chars;
                counter=1;
            } else {
                counter++;
            }
            index++;
        }
        newFactorized = (counter == 1 ) ? newFactorized.concat(String.valueOf(previous + " ")): newFactorized.concat(String.valueOf(counter)).concat(String.valueOf(previous + " "));
        return newFactorized;
    }

    public String fac2Canonical(String userInput){ //Turns factorized user input to canonical form for searching purposes
        String[] token = userInput.split("");
        String currentNum = "";
        StringBuilder canonicalInput = new StringBuilder();
        for (String tokens: token){
            if (Character.isDigit(tokens.charAt(0))){
                currentNum += tokens;
            } else {
                if (currentNum.isEmpty()){
                    canonicalInput.append(tokens);
                } else {
                    canonicalInput.append(tokens.repeat(Integer.parseInt(currentNum)));
                    currentNum = "";
                }
            }
        }
        return canonicalInput.toString();
    }

    public String checkPath(String userInput){ //Manager method for west to east and east to west checks
        eastCol = currentCol;
        eastRow = currentRow;

        //Start from east entrance
        if (west2east(userInput)){
            return "valid";
        } else if (east2west(userInput)){
            return "valid";
        } else {
            return "not valid";
        }
    }

    public boolean west2east(String userInput){ //West to east check
        char[] userArrInput = userInput.toCharArray();
        direction = "E";
        for (char chars: userArrInput){
            switch (direction) {
                case "E": //When Facing East
                    if (String.valueOf(chars).equals("F")){
                        currentCol++;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "S";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "N";
                    }
                    break;

                case "N": //When Facing NORTH
                    if (String.valueOf(chars).equals("F")){
                        currentRow--;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "E";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "W";
                    }
                    break;

                case "S": //When Facing SOUTH
                    if (String.valueOf(chars).equals("F")){
                        currentRow++;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "W";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "E";
                    }
                    break;

                case "W": //When Facing WEST
                    if (String.valueOf(chars).equals("F")){
                        currentCol--;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "N";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "S";
                    }
                    break;
            }
        }
        if (currentCol == endCol && currentRow == endRow){
            return true;
        } else {
            return false;
        }
    }

    public boolean east2west(String userInput){ // East to west check
        currentCol = endCol;
        currentRow = endRow;

        char[] userArrInput = userInput.toCharArray();
        direction = "W";
        for (char chars: userArrInput){
            switch (direction) {
                case "E": //When Facing East
                    if (String.valueOf(chars).equals("F")){
                        currentCol++;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "S";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "N";
                    }
                    break;

                case "N": //When Facing NORTH
                    if (String.valueOf(chars).equals("F")){
                        currentRow--;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "E";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "W";
                    }
                    break;

                case "S": //When Facing SOUTH
                    if (String.valueOf(chars).equals("F")){
                        currentRow++;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "W";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "E";
                    }
                    break;

                case "W": //When Facing WEST
                    if (String.valueOf(chars).equals("F")){
                        currentCol--;
                        if (isValid(currentRow, currentCol)){
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R"))  {
                        direction = "N";
                    } else if (String.valueOf(chars).equals("L")){
                        direction = "S";
                    }
                    break;
            }
        }
        if (currentCol == eastCol && currentRow == eastRow){
            return true;
        } else {
            return false;
        }
    }
}
