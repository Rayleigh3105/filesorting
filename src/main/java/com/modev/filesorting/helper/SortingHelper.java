package com.modev.filesorting.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Class for handling all kinds of operatoions with {@link java.nio.file.Files} or {@link java.nio.file.Path}
 *
 * @author Moritz Vogt (moritz.vogt@vogges.de)
 */
public class SortingHelper {

    private final static String[] allowedMimeTypes = {"image", "audio", "video"};
    public static String[] monthName = {"Januar", "Februar",
            "März", "April", "Mai", "Juni", "Juli",
            "August", "September", "Oktober", "November",
            "Dezember"};

    /**
     * Collects all Paths for Media files which are audio, video, image
     *
     * @param arguments not null - current arguments
     * @return a {@link ArrayList < Path >} with hold the information about the path of the mediafiles in the directory
     */
    public static ArrayList<Path> getPathForAllMediaFiles(Map<String, String> arguments) throws IOException {
        Objects.requireNonNull(arguments);

        Path path = Paths.get(arguments.get("inputPath"));
        ArrayList<Path> mediaFiles = new ArrayList<>();

        Files.walk(path).filter(Files::isRegularFile).forEach(pathSingleFile -> {
            try {
                if (Files.probeContentType(pathSingleFile) != null) {
                    String mimeType = Files.probeContentType(pathSingleFile).substring(0, Files.probeContentType(pathSingleFile).indexOf("/"));
                    if (Arrays.asList(allowedMimeTypes).contains(mimeType)) {
                        mediaFiles.add(pathSingleFile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return mediaFiles;
    }

    /**
     * Reads Creation Timestamp from file
     *
     * @param path - current path
     */
    public static String getCreationDateString(Path path) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        Date creationDate = new Date(attr.creationTime().toMillis());

        return creationDate.toLocaleString();
    }

    /**
     * Reads Creation Timestamo from file
     *
     * @param path - current path
     */
    public static Date getCreationDate(Path path) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return new Date(attr.creationTime().toMillis());
    }
}
