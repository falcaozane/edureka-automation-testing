package com.edureka.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.edureka.setup.BaseSteps;

/**
 * TestLogger - Utility class for easy ExtentReports logging
 * Simplifies logging in test classes
 */
public class TestLogger {
    
    /**
     * Log a step with bold formatting
     */
    public static void logStep(String message) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, "<b>📌 " + message + "</b>");
        }
        System.out.println("\n" + message);
    }
    
    /**
     * Log info message
     */
    public static void logInfo(String message) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, message);
        }
        System.out.println("  ℹ " + message);
    }
    
    /**
     * Log pass message with green color
     */
    public static void logPass(String message) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(Status.PASS, "✓ " + message);
        }
        System.out.println("  ✓ " + message);
    }
    
    /**
     * Log fail message with red color
     */
    public static void logFail(String message) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(Status.FAIL, "✗ " + message);
        }
        System.err.println("  ✗ " + message);
    }
    
    /**
     * Log warning message
     */
    public static void logWarning(String message) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(Status.WARNING, "⚠ " + message);
        }
        System.out.println("  ⚠ " + message);
    }
    
    /**
     * Log with custom status
     */
    public static void log(Status status, String message) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(status, message);
        }
        System.out.println("  " + message);
    }
    
    /**
     * Log test data in a table format
     */
    public static void logTestData(String title, String... data) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            StringBuilder table = new StringBuilder();
            table.append("<details><summary><b>").append(title).append("</b></summary>");
            table.append("<table style='width:100%; border: 1px solid #ddd;'>");
            
            for (int i = 0; i < data.length; i += 2) {
                if (i + 1 < data.length) {
                    table.append("<tr>");
                    table.append("<td style='padding: 8px;'><b>").append(data[i]).append(":</b></td>");
                    table.append("<td style='padding: 8px;'>").append(data[i + 1]).append("</td>");
                    table.append("</tr>");
                }
            }
            
            table.append("</table></details><br>");
            test.info(table.toString());
        }
    }
    
    /**
     * Capture and attach screenshot
     */
    public static void captureScreenshot(String screenshotName) {
        if (BaseSteps.driver != null) {
            ExtentTest test = ExtentTestNGListener.getTest();
            
            String path = Screenshots.captureScreenshot(BaseSteps.driver, screenshotName);
            
            if (path != null && !path.isEmpty() && test != null) {
                try {
                    test.addScreenCaptureFromPath(path, screenshotName);
                    System.out.println("  📸 Screenshot: " + screenshotName);
                } catch (Exception e) {
                    System.err.println("  ⚠ Failed to attach screenshot: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Log with colored label
     */
    public static void logLabel(String message, ExtentColor color) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, MarkupHelper.createLabel(message, color));
        }
        System.out.println("  " + message);
    }
    
    /**
     * Log success with colored label
     */
    public static void logSuccess(String message) {
        logLabel("✓✓✓ " + message + " ✓✓✓", ExtentColor.GREEN);
    }
    
    /**
     * Log error with colored label
     */
    public static void logError(String message) {
        logLabel("✗✗✗ " + message + " ✗✗✗", ExtentColor.RED);
    }
    
    /**
     * Log code block
     */
    public static void logCode(String code) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.info("<pre>" + code + "</pre>");
        }
    }
    
    /**
     * Log separator
     */
    public static void logSeparator() {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            test.info("<hr>");
        }
        System.out.println("  ----------------------------------------");
    }
}