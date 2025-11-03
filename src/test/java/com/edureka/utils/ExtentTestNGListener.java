package com.edureka.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.edureka.setup.BaseSteps;

/**
 * TestNG Listener for ExtentReports
 * Works with existing ExtentManager
 * Automatically handles test reporting and screenshots
 */
public class ExtentTestNGListener implements ITestListener {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    
    @Override
    public void onStart(ITestContext context) {
        // Initialize ExtentReports using existing ExtentManager
        extent = ExtentManager.getInstance();
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  TEST SUITE STARTED: " + context.getName());
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        
        // Create test using ExtentManager
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        
        // Assign category based on class name
        String className = result.getTestClass().getName();
        if (className.contains("Webinar")) {
            test.assignCategory("Webinar Tests");
        } else if (className.contains("Career")) {
            test.assignCategory("Career Tests");
        } else if (className.contains("Search")) {
            test.assignCategory("Search Tests");
        } else {
            test.assignCategory("Functional Tests");
        }
        
        // Log data provider parameters if any
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            StringBuilder params = new StringBuilder();
            params.append("<details><summary><b>📊 Test Data (Click to expand)</b></summary>");
            params.append("<table style='width:100%; border: 1px solid #ddd;'>");
            
            for (int i = 0; i < parameters.length && i < 10; i++) {
                params.append("<tr><td><b>Parameter ").append(i + 1).append(":</b></td>");
                params.append("<td>").append(parameters[i]).append("</td></tr>");
            }
            
            params.append("</table></details><br>");
            test.info(params.toString());
        }
        
        test.info("<b>🚀 Test Execution Started</b>");
        System.out.println("\n▶ Starting: " + testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        String testName = result.getMethod().getMethodName();
        
        test.log(Status.PASS, 
            MarkupHelper.createLabel("✓✓✓ TEST PASSED ✓✓✓", ExtentColor.GREEN));
        
        // Capture screenshot on success
        captureAndAttachScreenshot(result, "PASS");
        
        // Log execution time
        long duration = result.getEndMillis() - result.getStartMillis();
        test.info("<b>⏱ Execution Time:</b> " + duration + " ms");
        
        System.out.println("✓ PASSED: " + testName + " (" + duration + "ms)");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        String testName = result.getMethod().getMethodName();
        
        test.log(Status.FAIL, 
            MarkupHelper.createLabel("✗✗✗ TEST FAILED ✗✗✗", ExtentColor.RED));
        
        // Log failure details
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.fail("<details><summary><b>❌ Failure Reason (Click to expand)</b></summary>" +
                     "<pre>" + throwable.getMessage() + "</pre></details>");
            
            // Log stack trace in collapsible section
            test.fail("<details><summary><b>📋 Stack Trace (Click to expand)</b></summary>" +
                     "<pre>" + getFormattedStackTrace(throwable) + "</pre></details>");
        }
        
        // Capture screenshot on failure
        captureAndAttachScreenshot(result, "FAIL");
        
        System.err.println("✗ FAILED: " + testName);
        if (throwable != null) {
            System.err.println("  Reason: " + throwable.getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        String testName = result.getMethod().getMethodName();
        
        test.log(Status.SKIP, 
            MarkupHelper.createLabel("⊘ TEST SKIPPED", ExtentColor.YELLOW));
        
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.skip("<b>Skip Reason:</b> " + throwable.getMessage());
        }
        
        System.out.println("⊘ SKIPPED: " + testName);
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  TEST SUITE FINISHED: " + context.getName());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  Total Tests: " + context.getAllTestMethods().length);
        System.out.println("║  ✓ Passed: " + context.getPassedTests().size());
        System.out.println("║  ✗ Failed: " + context.getFailedTests().size());
        System.out.println("║  ⊘ Skipped: " + context.getSkippedTests().size());
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // Flush reports using ExtentManager
        ExtentManager.flush();
        
        System.out.println("📊 ExtentReport Location: " + ExtentManager.getReportPath());
        System.out.println("📂 Open report in browser to view results\n");
    }
    
    /**
     * Get current ExtentTest instance (Thread-safe)
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }
    
    /**
     * Capture and attach screenshot
     */
    private void captureAndAttachScreenshot(ITestResult result, String status) {
        if (BaseSteps.driver != null) {
            try {
                String testName = result.getMethod().getMethodName();
                String screenshotName = status + "_" + testName;
                
                // Add parameter info to screenshot name if available
                Object[] params = result.getParameters();
                if (params != null && params.length > 0) {
                    String paramStr = String.valueOf(params[0]).replaceAll("[^a-zA-Z0-9]", "_");
                    screenshotName += "_" + paramStr;
                }
                
                String screenshotPath = Screenshots.captureScreenshot(
                    BaseSteps.driver, 
                    screenshotName
                );
                
                if (screenshotPath != null && !screenshotPath.isEmpty()) {
                    ExtentTest test = extentTest.get();
                    test.addScreenCaptureFromPath(screenshotPath, 
                        status.equals("PASS") ? "✓ Success Screenshot" : "✗ Failure Screenshot");
                }
            } catch (Exception e) {
                System.err.println("⚠ Warning: Could not capture screenshot - " + e.getMessage());
            }
        }
    }
    
    /**
     * Format stack trace for better readability
     */
    private String getFormattedStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        
        StackTraceElement[] elements = throwable.getStackTrace();
        int count = 0;
        
        for (StackTraceElement element : elements) {
            // Only show relevant stack trace lines (from test package)
            String elementStr = element.toString();
            if (elementStr.contains("com.edureka") || count < 5) {
                sb.append("    at ").append(elementStr).append("\n");
                count++;
            }
            
            if (count >= 15) {
                sb.append("    ... (").append(elements.length - count)
                  .append(" more lines)\n");
                break;
            }
        }
        
        return sb.toString();
    }
}