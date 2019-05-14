package com.modev.filesorting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

import static com.modev.helper.ArgumentHelper.processArguments;
import static java.lang.System.exit;

@SpringBootApplication
public class FilesortingApplication implements CommandLineRunner {

	@Autowired
	private com.modev.facade.InputFacade inputFacade;

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(FilesortingApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String, String> mapArguments = processArguments(args);


		// Iterate through given Directory and search for files
		inputFacade.processInputDirectory(mapArguments);
		// Write files in three ways

		exit(0);
	}
}
