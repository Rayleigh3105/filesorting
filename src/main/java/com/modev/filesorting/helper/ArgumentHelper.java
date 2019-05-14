package com.modev.filesorting.helper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.modev.utils.DirectoryInterval;

import static java.lang.System.exit;
import static java.nio.file.Files.exists;

/**
 * Helper for handling Arguments of the CommanLine Application
 *
 * @author Moritz Vogt (moritz.vogt@vogges.de)
 */
public class ArgumentHelper {

    private static final String[] orderInterval = {"d","m","y"};

    /**
     * Validates the Arguments and cast it to a {@link Map<String, String>}.
     *
     * @param args current Arguments
     * @return {@link Map<String, String>} a Map holding the information about the three arguments
     */
    public static Map<String, String> processArguments(String... args) {
        Map<String, String> arguments = new HashMap<>();
        if (args.length != 3) {
            System.out.println("ERROR Please use following arguments <input Path> <output Path> <directory order (d, m, y)>");
            exit(1);
        }

        // INPUT Path Argument
        String inputPath = args[0];
        checkIfPathExists(inputPath);
        arguments.put("inputPath", inputPath);
        System.out.println("Getting files from path: " + arguments.get("inputPath"));

        // OUTPUT Path Argument
        String outputPath = args[1];
        checkIfPathExists(outputPath);
        arguments.put("outputPath", outputPath);
        System.out.println("Writing files to: " + arguments.get("outputPath"));

        // Order Argument
        String orderInterval = "";
        if(!args[2].isEmpty()) {
            checkIfOrderArgumentIsValid(args[2]);
            orderInterval = args[2];
        }
        arguments.put("orderInterval", orderInterval);
        System.out.println("Directory Intervall: " + orderInterval + " is used.");

        return arguments;
    }

    /**
     * Checks if given Path is valid and exists
     * @param pathToFiles {@link String} which holds the informaton about the path of the files
     */
    private static void checkIfPathExists(String pathToFiles) {
        Path path = Paths.get(pathToFiles);
        if (!exists(path)) {
            System.out.println("ERROR Path: " + pathToFiles + " does not exist. First Argument should be the Path to the files.");
            exit(1);
        }
    }

    /**
     * Checks if given String is part of a array
     *
     * @param orderArgument {@link String} to check against the allowed array
     */
    private static void checkIfOrderArgumentIsValid(String orderArgument) {
        Objects.requireNonNull(orderArgument);
        ArrayList<String> allowedDirectoryIntervals = new ArrayList<>();
        allowedDirectoryIntervals.add(DirectoryInterval.daily);
        allowedDirectoryIntervals.add(DirectoryInterval.montly);
        allowedDirectoryIntervals.add(DirectoryInterval.yearly);

        if (!allowedDirectoryIntervals.contains(orderArgument)) {
            System.out.println(
                    "ERROR Following sorting intervalls are allowed (d = Create Folder for each day, m = Create Folder for each Month, y = create Folder for each Year).");
            exit(1);
        }
    }
}
