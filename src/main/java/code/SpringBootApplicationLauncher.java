package code;

// Copyright (c) 2024, NoCodeNoLife-cloud. All rights reserved.
// Author: NoCodeNoLife-cloud
// stay hungry, stay foolish
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShellAPI;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
// @EnableEurekaServer
// @EnableDiscoveryClient
// @EnableFeignClients
// @EnableDubbo
@SpringBootApplication
public class SpringBootApplicationLauncher {
	private static final String LOG_DIRECTORY_PATH = "log";
	private static final int MAXIMUM_SAVED_LOGS = 3;

	static {
		try {
			printBanner();
			retrieveLogs();
		} catch (IOException ioException) {
			log.error(ioException.getMessage());
		}
	}

	/**
	 * Entry point of the application.
	 *
	 * @param args The command line arguments.
	 */
	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplicationLauncher.class, args);
	}

	/**
	 * Prints the application banner.
	 */
	private static void printBanner() {
		// Print the banner
		log.info("""

				     _                _                                          _                 __            _ _     _    \s
				    | |              | |                                        | |               / _|          | (_)   | |   \s
				 ___| |_ __ _ _   _  | |__  _   _ _ __   __ _ _ __ _   _     ___| |_ __ _ _   _  | |_ ___   ___ | |_ ___| |__ \s
				/ __| __/ _` | | | | | '_ \\| | | | '_ \\ / _` | '__| | | |   / __| __/ _` | | | | |  _/ _ \\ / _ \\| | / __| '_ \\\s
				\\__ \\ || (_| | |_| | | | | | |_| | | | | (_| | |  | |_| |_  \\__ \\ || (_| | |_| | | || (_) | (_) | | \\__ \\ | | |
				|___/\\__\\__,_|\\__, | |_| |_|\\__,_|_| |_|\\__, |_|   \\__, ( ) |___/\\__\\__,_|\\__, | |_| \\___/ \\___/|_|_|___/_| |_|
				               __/ |                     __/ |      __/ |/                 __/ |                              \s
				              |___/                     |___/      |___/                  |___/                               \s""");
	}

	/**
	 * This method retrieves .txt files from a specified directory path and moves some of them to the trash.
	 */
	private static void retrieveLogs() throws IOException {
		// Creating a File object for the directory
		File dir = new File(LOG_DIRECTORY_PATH);

		if (!dir.exists()) {
			log.info("specified directory does not exist.");
			if (!dir.createNewFile()) {
				log.error("Failed to create log directory.");
			}
		}

		// Getting an array of .txt files from the directory
		File[] txtFiles = dir.listFiles((d, name) -> name.endsWith(".log"));

		// Checking if there are no .txt files in the directory
		if (txtFiles == null || txtFiles.length == 0) {
			log.info("specified directory has no .log files.");
			return;
		}

		// Sorting the .txt files based on last modified timestamp in descending order
		Arrays.sort(txtFiles, Comparator.comparingLong(File::lastModified).reversed());

		// Moving the files starting from the 4th file to the trash
		for (int i = MAXIMUM_SAVED_LOGS; i < txtFiles.length; i++) {
			moveToTrash(txtFiles[i]);
		}
	}

	/**
	 * Moves the specified file to the trash.
	 *
	 * @param file the file to be moved to trash
	 */
	private static void moveToTrash(File file) {
		// Check if the file exists
		if (!file.exists()) {
			return;
		}

		// Create a new SHFILEOPSTRUCT object
		ShellAPI.SHFILEOPSTRUCT fileOp = new ShellAPI.SHFILEOPSTRUCT();

		// Set the operation to delete the file
		fileOp.wFunc = ShellAPI.FO_DELETE;

		// Set the file path to be deleted
		fileOp.pFrom = file.getAbsolutePath() + "\0";

		// Set flags for the operation
		fileOp.fFlags = ShellAPI.FOF_ALLOWUNDO | ShellAPI.FOF_NOCONFIRMATION | ShellAPI.FOF_SILENT;

		// Perform the file operation
		int result = Shell32.INSTANCE.SHFileOperation(fileOp);

		// Check the result of the operation
		if (result != 0) {
			log.info("Failed to move to trash: {}", file.getAbsolutePath());
		} else {
			log.info("Moved to trash: {}", file.getAbsolutePath());
		}
	}
}