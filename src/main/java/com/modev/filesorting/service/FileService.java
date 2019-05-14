package com.modev.filesorting.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Service which does the main logic
 *
 * @author Moritz Vogt (moritz.vogt@vogges.de)
 */
@Service
public class FileService {

    private final static String[] allowedMimeTypes = {"image", "audio", "video"};

    public void processDaily(Map<String, String> arguments, String directoryInterval) throws IOException {
        Path path = Paths.get(arguments.get("inputPath"));
        ArrayList<String> mediaFiles = new ArrayList<>();

        Files.walk(path).filter(Files::isRegularFile).forEach(pathSingleFile -> {
            try {
                if (Files.probeContentType(pathSingleFile)  != null) {
                    String mimeType = Files.probeContentType(pathSingleFile).substring(0, Files.probeContentType(pathSingleFile).indexOf("/"));
                    if (Arrays.asList(allowedMimeTypes).contains(mimeType)) {
                        mediaFiles.add(Files.probeContentType(pathSingleFile));
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void processMontly(Map<String, String> arguments, String montly) {

    }

    public void processYearly(Map<String, String> arguments, String yearly) {

    }
}

