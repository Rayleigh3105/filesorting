package com.modev.filesorting.facade;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import com.modev.filesorting.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.modev.utils.DirectoryInterval.daily;
import static com.modev.utils.DirectoryInterval.montly;
import static com.modev.utils.DirectoryInterval.yearly;

/**
 * Help for handling any kind of files operations
 *
 * @author Moritz Vogt (moritz.vogt@vogges.de)
 */
@Service
public class InputFacade {

     @Autowired
     private FileService fileService;

    /**
     * @param arguments not null - current Arguments
     */
    public void processInputDirectory(Map<String, String> arguments) throws IOException {
        Objects.requireNonNull(arguments);

        String directoryInterval = arguments.get("orderInterval");
        // Go to Facade
        switch (directoryInterval) {
            case daily:
                fileService.processDirectory(arguments, daily);
                break;
            case montly:
                fileService.processDirectory(arguments, montly);
                break;
            case yearly:
                fileService.processDirectory(arguments, yearly);
                break;
            default:
                fileService.processDirectory(arguments, yearly);
        }
    }
}
