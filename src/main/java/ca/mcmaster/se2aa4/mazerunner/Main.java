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
        // Add flag options to input
        Options options = new Options();
        options.addOption("i", "input", true, "Maze input path");
        options.addOption("p", "input", true, "Proposed Maze Solution");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("i") && cmd.hasOption("p")) { // If user path needs to be checked

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

            } else { // If path needs to be generated
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
                    map.runAlg();
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