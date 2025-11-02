package com.edureka.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.edureka.parameter.PropertyReader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentManager - Singleton class for ExtentReports management
 * Handles report initialization and configuration
 */
public class ExtentManager {
    
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static final String REPORT_DIR = "Target/ExtentReport/";
    private static final String REPORT_NAME = "Report.html";
    
    public static String Browser = PropertyReader.getProperty("browser");
    
    /**
     * Get ExtentReports instance (Singleton)
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }
    
    /**
     * Create ExtentReports instance with configuration
     */
    private static ExtentReports createInstance() {
        // Create report directory
        File directory = new File(REPORT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String reportPath = REPORT_DIR + REPORT_NAME;
        
        // Initialize SparkReporter
        sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Configure report
        sparkReporter.config().setDocumentTitle("Edureka Automation Test Report");
        sparkReporter.config().setReportName("Edureka Test Execution Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");
        sparkReporter.config().setEncoding("UTF-8");
        
        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // Set system information
        extent.setSystemInfo("Application", "Edureka");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", Browser);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Test Framework", "TestNG");
        
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        extent.setSystemInfo("Execution Time", timestamp);
        
        System.out.println("✓ ExtentReports initialized");
        System.out.println("→ Report location: " + reportPath);
        
        return extent;
    }
    
    /**
     * Create a test in ExtentReports
     */
    public static ExtentTest createTest(String testName, String description) {
        return extent.createTest(testName, description);
    }
    
    /**
     * Create a test in ExtentReports with category
     */
    public static ExtentTest createTest(String testName, String description, String category) {
        ExtentTest test = extent.createTest(testName, description);
        test.assignCategory(category);
        return test;
    }
    
    /**
     * Flush the reports
     */
    public static void flush() {
        if (extent != null) {
            extent.flush();
            System.out.println("✓ ExtentReports flushed successfully");
        }
    }
    
    /**
     * Get report path
     */
    public static String getReportPath() {
        return REPORT_DIR + REPORT_NAME;
    }
}