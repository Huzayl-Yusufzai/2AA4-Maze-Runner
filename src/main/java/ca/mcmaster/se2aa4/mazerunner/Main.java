package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.commons.cli.*;
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

            try {

                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line= "";
                Map map = new Map(reader, line, filePath);
                logger.info("**** Computing path");
                map.initiateCheck();
                
            } catch(Exception e) {
                System.err.println("/!\\ An error has occured /!\\");
            }
        }  catch (ParseException e) {
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

    public Map(BufferedReader reader, String line, String filePath) {
        try {
            int row = 0;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        mapValues[idx][row]= false;
                    } else if (line.charAt(idx) == ' ') {
                        mapValues[idx][row]= true;
                    }
                }
                System.out.print(System.lineSeparator());
                row++;
            }
        } catch (Exception e){
            System.err.println("/!\\ An error has occured /!\\");
        }
    }

    public void catchStart(){
        for (int i = 0; i < mapValues.length; i++) {
            if(mapValues[i][0] == true){
                currentRow = i;
                break;
            }
        }
        currentCol = 0;
    }

    public void initiateCheck(){
        Marker marker = new Marker(currentRow, currentCol, mapValues);
        marker.rightHandRule();
        System.out.println(marker.returnPath());
    }
    
}
class Marker {
    private String path = "";
    private boolean[][] mapValues;
    private int currentRow;
    private int currentCol;
    private String direction = "W";

    public Marker(int row, int col, boolean[][] mapValues) {
        currentRow = row;
        currentCol = col;
        this.mapValues = mapValues;
    }

    public void rightHandRule(){
       while (mapValues[currentRow][currentCol]!= null){
            if (direction.equals("E")){
                if (mapValues[currentRow-1][currentCol] == false){
                    if (mapValues[currentRow][currentCol+1] == false){
                        if (mapValues[currentRow+1][currentCol] == false){
                            direction = "W";
                            addToPath("LLLF");
                            currentCol++;   
                        } else {
                            direction = "N";
                            addToPath("LF");
                            currentCol++;     
                        }
                    } else {
                        direction = "E";
                        addToPath("F");
                        currentCol++;    
                    }
                } else {
                    direction = "S";
                    addToPath("RF");
                    currentRow--;
                }
            }
            if (direction.equals("N")){
                if (mapValues[currentRow][currentCol+1] == false){
                    if (mapValues[currentRow+1][currentCol] == false){
                        if (mapValues[currentRow][currentCol-1] == false){
                            direction = "S";
                            addToPath("LLLF");
                            currentRow--;   
                        } else {
                            direction = "W";
                            addToPath("LF");
                            currentCol--;     
                        }
                    } else {
                        direction = "N";
                        addToPath("F");
                        currentRow++;    
                    }
                } else {
                    direction = "E";
                    addToPath("RF");
                    currentCol++;
                }
            }
            if (direction.equals("W")){
                if (mapValues[currentRow+1][currentCol] == false){
                    if (mapValues[currentRow][currentCol-1] == false){
                        if (mapValues[currentRow-1][currentCol] == false){
                            direction = "E";
                            addToPath("LLLF");
                            currentCol--;   
                        } else {
                            direction = "S";
                            addToPath("LF");
                            currentCol--;     
                        }
                    } else {
                        direction = "W";
                        addToPath("F");
                        currentCol--;    
                    }
                } else {
                    direction = "N";
                    addToPath("RF");
                    currentRow++;
                }
            if (direction.equals("S")){
                if (mapValues[currentRow][currentCol-1] == false){
                    if (mapValues[currentRow-1][currentCol] == false){
                        if (mapValues[currentRow][currentCol+1] == false){
                            direction = "N";
                            addToPath("LLLF");
                            currentRow++;   
                        } else {
                            direction = "W";
                            addToPath("LF");
                            currentCol++;     
                        }
                    } else {
                        direction = "S";
                        addToPath("F");
                        currentRow--;    
                    }
                } else {
                    direction = "W";
                    addToPath("RF");
                    currentCol--;
                }
            }
       }
    }

    public void addToPath(String direction){
        path = path.concat(direction);
    }

    public String returnPath(){
        return path;
    }
}
