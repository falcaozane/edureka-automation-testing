package com.edureka.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Utility class for capturing screenshots during test execution
 * Supports full page screenshots and element-specific screenshots
 */
public class ScreenShotTest {

    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "/target/Reports/screenshots/";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    /**
     * Captures a full page screenshot
     *
     * @param driver WebDriver instance
     * @param testName Name of the test case
     * @return String path of the captured screenshot, or null if failed
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            if (driver == null) {
                System.err.println("Driver is null, cannot capture screenshot");
                return null;
            }

            // Create screenshot directory if it doesn't exist
            createScreenshotDirectory();

            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String screenshotPath = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

            // Capture screenshot
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath);

            FileUtils.copyFile(sourceFile, destinationFile);

            System.out.println("Screenshot captured: " + screenshotPath);
            return screenshotPath;

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while capturing screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Captures a screenshot with a custom name
     *
     * @param driver WebDriver instance
     * @param fileName Custom filename (without extension)
     * @return String path of the captured screenshot, or null if failed
     */
    public static String captureScreenshotWithCustomName(WebDriver driver, String fileName) {
        try {
            if (driver == null) {
                System.err.println("Driver is null, cannot capture screenshot");
                return null;
            }

            createScreenshotDirectory();

            String screenshotPath = SCREENSHOT_DIR + fileName + ".png";

            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath);

            FileUtils.copyFile(sourceFile, destinationFile);

            System.out.println("Screenshot captured: " + screenshotPath);
            return screenshotPath;

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Captures a screenshot of a specific WebElement
     *
     * @param element WebElement to capture
     * @param elementName Name to identify the element
     * @return String path of the captured screenshot, or null if failed
     */
    public static String captureElementScreenshot(WebElement element, String elementName) {
        try {
            if (element == null) {
                System.err.println("Element is null, cannot capture screenshot");
                return null;
            }

            createScreenshotDirectory();

            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String screenshotPath = SCREENSHOT_DIR + elementName + "_element_" + timestamp + ".png";

            File sourceFile = element.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath);

            FileUtils.copyFile(sourceFile, destinationFile);

            System.out.println("Element screenshot captured: " + screenshotPath);
            return screenshotPath;

        } catch (Exception e) {
            System.err.println("Failed to capture element screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Captures screenshot as Base64 string (useful for embedding in reports)
     *
     * @param driver WebDriver instance
     * @return String Base64 encoded screenshot, or null if failed
     */
    public static String captureScreenshotAsBase64(WebDriver driver) {
        try {
            if (driver == null) {
                System.err.println("Driver is null, cannot capture screenshot");
                return null;
            }

            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot as Base64: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Captures screenshot and returns as byte array
     *
     * @param driver WebDriver instance
     * @return byte[] screenshot data, or null if failed
     */
    public static byte[] captureScreenshotAsBytes(WebDriver driver) {
        try {
            if (driver == null) {
                System.err.println("Driver is null, cannot capture screenshot");
                return null;
            }

            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot as bytes: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates the screenshot directory if it doesn't exist
     */
    private static void createScreenshotDirectory() {
        File directory = new File(SCREENSHOT_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Screenshot directory created: " + SCREENSHOT_DIR);
            }
        }
    }

    

    /**
     * Gets the screenshot directory path
     *
     * @return String screenshot directory path
     */
    public static String getScreenshotDirectory() {
        return SCREENSHOT_DIR;
    }
}