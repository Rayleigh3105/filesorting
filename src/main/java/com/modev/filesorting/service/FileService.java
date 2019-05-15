package com.modev.filesorting.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormatSymbols;
import java.util.*;

import com.modev.filesorting.helper.SortingHelper;
import com.modev.utils.DirectoryInterval;
import org.springframework.stereotype.Service;

import static com.modev.filesorting.helper.SortingHelper.getPathForAllMediaFiles;
import static com.modev.utils.DirectoryInterval.daily;
import static com.modev.utils.DirectoryInterval.montly;
import static com.modev.utils.DirectoryInterval.yearly;

/**
 * Service which does the main logic
 *
 * @author Moritz Vogt (moritz.vogt@vogges.de)
 */
@Service
public class FileService {

    public void processDirectory(Map<String, String> arguments, String orderInterval) throws IOException {
        ArrayList<Path> mediaPath = getPathForAllMediaFiles(arguments);

        // Iterate through list
        mediaPath.forEach(path -> {
            File currentFile = new File(path.toString());
            String outputPath = arguments.get("outputPath");
            try {
                // Get Timestamp from file
                String localeString = SortingHelper.getCreationDateString(path);

                String directoryToCreate = null;
                // Build directory String
                switch (orderInterval) {
                    case daily:
                        directoryToCreate = outputPath + "/" + getDailyStructure(localeString);
                    case montly:
                        directoryToCreate = outputPath + "/" + getMontlytructure(path, false);
                    case yearly:
                        directoryToCreate = outputPath + "/" + getYearlyStructure(path);
                    default:
                        directoryToCreate = outputPath + "/" + getYearlyStructure(path);
                }

                // Create and move Directory and file
                createDirectorysAndMoveFile(directoryToCreate, path, currentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String getMontlytructure(Path path, boolean withoutYear) throws IOException {
        Date date = SortingHelper.getCreationDate(path);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String month = SortingHelper.monthName[calendar.get(Calendar.MONTH)];
        String montlyStructure;
        if (withoutYear) {
            montlyStructure = month;
        } else {
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            montlyStructure = month + " " + year;

        }

        return montlyStructure + "/" + getDailyStructure(SortingHelper.getCreationDateString(path));
    }

    private String getYearlyStructure(Path path) throws IOException {
        Date date = SortingHelper.getCreationDate(path);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String year = String.valueOf(calendar.get(Calendar.YEAR));

        return year + "/" + getMontlytructure(path, true);
    }

    private String getDailyStructure(String localeString) {
        return localeString.substring(0, localeString.indexOf(","));
    }

    private void createDirectorysAndMoveFile(String directoryToCreate, Path currentPath, File currentFile) throws IOException {
        File directory = new File(directoryToCreate);
        File fileToMove = new File(directoryToCreate + "/" + currentFile.getName());
        if (!directory.exists()) {
            // Create Directory
            directory.mkdirs();
            System.out.println("Directory " + directory.getAbsolutePath() + " created");
        }

        if (!fileToMove.exists()) {
            Files.move(currentPath, Paths.get(directory.getAbsolutePath() + "/" + currentFile.getName()));
            System.out.println("File has successfully been sorted and moved.");
        }
    }

}

