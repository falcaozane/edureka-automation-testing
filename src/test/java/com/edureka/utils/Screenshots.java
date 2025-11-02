package com.edureka.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshots Utility Class
 * Handles screenshot capture functionality
 */
public class Screenshots {
    
    private static final String SCREENSHOT_DIR = "Target/Screenshots/";
    
    /**
     * Capture screenshot with timestamp
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Create directory if not exists
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Generate timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            
            // Create screenshot file name
            String fileName = screenshotName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;
            
            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            
            // Copy file to destination
            FileUtils.copyFile(srcFile, destFile);
            
            System.out.println("📸 Screenshot saved: " + fileName);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("✗ Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Capture screenshot without timestamp
     */
    public static String captureScreenshotSimple(WebDriver driver, String screenshotName) {
        try {
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String filePath = SCREENSHOT_DIR + screenshotName + ".png";
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            
            return filePath;
        } catch (IOException e) {
            System.err.println("✗ Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}