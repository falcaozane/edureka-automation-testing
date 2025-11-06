package com.edureka.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.edureka.parameter.PropertyReader;
import com.edureka.setup.BaseSteps;

public class ReportManager extends BaseSteps {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private ExtentSparkReporter sparkReport;
    private static final String REPORT_DIR = System.getProperty("user.dir") + "/target/Reports/";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    String browser = PropertyReader.getProperty("browser").toLowerCase();

    @BeforeSuite
    public void reportGeneration() {
        try {
            // Create reports directory if not exists
            File reportDirectory = new File(REPORT_DIR);
            if (!reportDirectory.exists()) {
                reportDirectory.mkdirs();
            }

            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String reportPath = REPORT_DIR + "EdurekaReport_" + timestamp + ".html";
            sparkReport = new ExtentSparkReporter(reportPath);

            // Configure Extent Reports
            sparkReport.config().setTheme(Theme.DARK);
            sparkReport.config().setDocumentTitle("Edureka Automation Report");
            sparkReport.config().setReportName("Functional Test Report");
            sparkReport.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
            sparkReport.config().setEncoding("UTF-8");

            // Enable offline mode for better performance
            sparkReport.config().setOfflineMode(true);

            extent = new ExtentReports();
            extent.attachReporter(sparkReport);

            // System Information
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("OS Version", System.getProperty("os.version"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", browser);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester Name", "Zane Falcao");
            extent.setSystemInfo("User", System.getProperty("user.name"));

            System.out.println("Extent Report initialized at: " + reportPath);
        } catch (Exception e) {
            System.err.println("Error initializing Extent Reports: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void reportTestCreation(Method method, ITestContext context) {
        try {
            ExtentTest extentTest = extent.createTest(method.getName());

            // Add test metadata
            extentTest.assignCategory(context.getCurrentXmlTest().getName());
            extentTest.assignAuthor("Zane Falcao");

            // Add description from method annotations if available
            if (method.isAnnotationPresent(org.testng.annotations.Test.class)) {
                org.testng.annotations.Test testAnnotation = method.getAnnotation(org.testng.annotations.Test.class);
                if (testAnnotation.description() != null && !testAnnotation.description().isEmpty()) {
                    extentTest.info(testAnnotation.description());
                }
            }

            test.set(extentTest);
            System.out.println("Started Test: " + method.getName());
        } catch (Exception e) {
            System.err.println("Error creating test in report: " + e.getMessage());
        }
    }

    @AfterMethod
    public void reportTestCompletion(ITestResult result) {
        try {
            ExtentTest extentTest = test.get();
            String testName = result.getMethod().getMethodName();

            // Add test execution time
            long executionTime = result.getEndMillis() - result.getStartMillis();
            extentTest.info("Execution Time: " + executionTime + " ms");

            if (result.getStatus() == ITestResult.SUCCESS) {
                extentTest.log(Status.PASS,
                        MarkupHelper.createLabel("Test PASSED: " + testName, ExtentColor.GREEN));

                // Capture screenshot for passed tests (optional)
                String screenShotPath = ScreenShotTest.captureScreenshot(driver, testName);
                if (screenShotPath != null) {
                    extentTest.addScreenCaptureFromPath(screenShotPath, "Test Passed Screenshot");
                }

            } else if (result.getStatus() == ITestResult.FAILURE) {
                extentTest.log(Status.FAIL,
                        MarkupHelper.createLabel("Test FAILED: " + testName, ExtentColor.RED));

                // Log the exception details
                Throwable throwable = result.getThrowable();
                if (throwable != null) {
                    extentTest.fail("Error Message: " + throwable.getMessage());
                    extentTest.fail("Stack Trace: <br>" + getStackTrace(throwable));
                }

                // Capture screenshot for failed tests
                String screenShotPath = ScreenShotTest.captureScreenshot(driver, testName);
                if (screenShotPath != null) {
                    extentTest.addScreenCaptureFromPath(screenShotPath, "Failure Screenshot");
                }

            } else if (result.getStatus() == ITestResult.SKIP) {
                extentTest.log(Status.SKIP,
                        MarkupHelper.createLabel("Test SKIPPED: " + testName, ExtentColor.YELLOW));

                // Log skip reason if available
                if (result.getThrowable() != null) {
                    extentTest.skip("Skip Reason: " + result.getThrowable().getMessage());
                }
            }

            System.out.println("Completed Test: " + testName + " - Status: " + getStatusString(result.getStatus()));

        } catch (Exception e) {
            System.err.println("Error updating test result in report: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.remove(); // Clean up ThreadLocal
        }
    }

    @AfterSuite
    public void reportCompletion() {
        try {
            if (extent != null) {
                extent.flush();
                System.out.println("Extent Report generated successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error flushing Extent Reports: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to format stack trace
    private String getStackTrace(Throwable throwable) {
        return Arrays.toString(throwable.getStackTrace())
                .replaceAll(",", "<br>")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
    }

    // Helper method to get status string
    private String getStatusString(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return "PASSED";
            case ITestResult.FAILURE:
                return "FAILED";
            case ITestResult.SKIP:
                return "SKIPPED";
            default:
                return "UNKNOWN";
        }
    }

    // Public method to log info during test execution
    public static void logInfo(String message) {
        if (test.get() != null) {
            test.get().info(message);
        }
    }

    // Public method to log pass steps
    public static void logPass(String message) {
        if (test.get() != null) {
            test.get().pass(message);
        }
    }

    // Public method to log fail steps
    public static void logFail(String message) {
        if (test.get() != null) {
            test.get().fail(message);
        }
    }

    // Public method to log warning
    public static void logWarning(String message) {
        if (test.get() != null) {
            test.get().warning(message);
        }
    }
}