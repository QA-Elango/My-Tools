package com.rapl.common;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Helper {
	private static final Logger LOGGER = LogManager.getLogger(Helper.class);
	private static final String USER_DIR = "user.dir";
	private static String os = System.getProperty("os.name").toLowerCase();

	public static void uploadFiles(String fileText, boolean isFirstTime) throws AWTException {
		try {
			// Creating a Robot class instance to simulate keyborad and mouse actions.
			Robot robot = new Robot();

			// Reading OS name using get property method
			String os = System.getProperty("os.name").toLowerCase();

			String filepath;

			if (os.contains("win")) {
				filepath = System.getProperty("user.dir") + ("\\src\\test\\resources\\file" + fileText);
			} else {
				filepath = System.getProperty("user.dir") + ("//src//test//resources//file//" + fileText);
			}

			// copying file path into string and storing it in the selection.
			StringSelection selection = new StringSelection(filepath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

			// If the platform is Windows this if will perform CTRL+V
			if (os.contains("win")) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(500);

				// Press Enter key to close the Goto window and Upload window
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(5000);

			} else if (os.contains("linux")) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_L);
				robot.keyRelease(KeyEvent.VK_L);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(5000);

				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.delay(5000);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				robot.delay(5000);
				// Press Enter key to close the Goto window and Upload window
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(5000);
			} else if (os.contains("mac")) {
				// Cmd + Tab is needed since it launches a Java app and the browser looses focus
				if (isFirstTime) {

					robot.keyPress(KeyEvent.VK_META);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_META);
					robot.delay(3000);

					isFirstTime = false;

				}
				// Open Goto window

				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_G);

				robot.keyRelease(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_META);

				robot.delay(3000);

				// Paste the clipboard value

				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_META);

				robot.delay(3000);

				// Press Enter key to close the Goto window and Upload window
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(5000);

				// Press Enter key to close the Goto window and Upload window
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(5000);
			} else {
				// Handle unsupported platform
				LOGGER.info("Unsupported operating system: " + os);
				// Optionally, throw an exception or log a message to indicate unsupported
				// platform
			}
		} catch (AWTException e) {
			// TODO: Log the exception
			throw new RuntimeException("File upload failed due to AWTException", e);
		}
	}

	public static String getOS() {
		return os;
	}

	public enum OS {
		WINDOWS("win", System.getProperty(USER_DIR) + "\\AutoIT\\AutoITScripts\\"),
		LINUX("linux", System.getProperty(USER_DIR) + "/file/"), MAC("mac", System.getProperty(USER_DIR) + "/file/");

		private String osName;
		private String resourcePath;

		OS(String os, String resPath) {
			this.osName = os;
			this.resourcePath = resPath;
		}

		public String getOsName() {
			return osName;
		}

		public String getResourcePath() {
			return resourcePath;
		}
	}
}