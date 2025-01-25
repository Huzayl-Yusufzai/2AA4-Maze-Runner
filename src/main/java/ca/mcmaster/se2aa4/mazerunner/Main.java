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
                logger.info("**** Reading the maze from file {}", filePath);
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            System.out.print("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            System.out.print("PASS ");
                        }
                    }
                    System.out.print(System.lineSeparator());
                }
            } catch(Exception e) {
                System.err.println("/!\\ An error has occured /!\\");
            }
        }  catch (ParseException e) {
            logger.error("Failed to parse command-line arguments: {}", e.getMessage());
            System.out.println("Usage: <File call> -i <path_to_maze_file>");
            return;
        }
        logger.info("**** Computing path");
        logger.warn("Pathfinding logic not implemented yet.");
        logger.info("** End of Maze Runner");
    }
}
