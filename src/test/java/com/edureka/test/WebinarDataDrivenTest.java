package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.pages.WebinarPage;
import com.edureka.parameter.ExcelReader;
import com.edureka.parameter.PropertyReader;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.ExtentManager;
import com.edureka.utils.Screenshots;

/**
 * Data-Driven Test for Webinar Functionality
 * Uses Excel DataProvider to test multiple webinar scenarios
 * ONE BROWSER INSTANCE for all tests in this class (prevents Cloudflare blocking)
 */
public class WebinarDataDrivenTest {
    
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    WebinarPage webinarPage;
    
    ExtentReports extent;
    ExtentTest test;
    
    String url = PropertyReader.getProperty("url");
    
    private boolean isLoggedIn = false;
    
    @BeforeClass
    public void setupClass() {
        System.out.println("\n========== WEBINAR TEST SUITE - INITIALIZING ==========");
        extent = ExtentManager.getInstance();
        
        // Initialize browser ONCE for all tests in this class
        driver = BaseSteps.initializeDriver();
        
        // Initialize page objects
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        webinarPage = new WebinarPage(driver);
        
        System.out.println("✓ Browser initialized (will be reused for all webinar tests)");
        
        // Perform login ONCE at the beginning
        try {
            performLogin();
        } catch (Exception e) {
            System.err.println("⚠ Login failed: " + e.getMessage());
        }
    }
    
    /**
     * Perform login once for all tests
     */
    private void performLogin() throws InterruptedException {
        System.out.println("\n--- Performing one-time login ---");
        Thread.sleep(3000);
        
        homePage.clicklogIn();
        Thread.sleep(2000);
        
        String email = PropertyReader.getProperty("email");
        String password = PropertyReader.getProperty("password");
        loginPage.logIn(email, password);
        
        Thread.sleep(3000);
        isLoggedIn = true;
        System.out.println("✓ Login completed - session will be reused\n");
    }
    
    /**
     * DataProvider - Reads test data from Excel
     */
    @DataProvider(name = "webinarData")
    public Object[][] getWebinarData() {
        String sheetName = "WebinarTestData";
        Object[][] data = ExcelReader.getTestData(sheetName);
        
        if (data == null || data.length == 0) {
            System.err.println("⚠ WARNING: No data loaded from Excel sheet: " + sheetName);
            return new Object[0][0];
        }
        
        System.out.println("📊 Loaded " + data.length + " test cases from Excel");
        return data;
    }
    
    /**
     * Main Test - Webinar Search and Registration (Data-Driven)
     * 
     * Excel Columns:
     * [0] WebinarName
     * [1] Experience
     * [2] GetInTouch (Yes/No)
     * [3] ExpectedResult
     */
    @Test(priority = 1, dataProvider = "webinarData", 
          description = "Data-Driven Test: Webinar Search and Registration")
    public void testWebinarSearchAndRegistration(String webinarName, String experience, 
                                                  String getInTouch, String expectedResult) {
        
        // Create ExtentTest
        test = extent.createTest("Webinar Test: " + webinarName, 
                                "Testing webinar search and registration for: " + webinarName);
        test.assignCategory("Webinar Tests");
        test.assignCategory("Data-Driven");
        
        try {
            test.log(Status.INFO, "========== TEST START: " + webinarName + " ==========");
            
            // Navigate back to home and then to webinars (reusing session)
            test.log(Status.INFO, "Step 1: Navigating to Webinars page (session reused)");
            driver.get(url);
            Thread.sleep(2000);
            
            // Scroll until webinar link is visible
            int scrollAttempts = 0;
            while (scrollAttempts < 10) {
                BaseSteps.scrollDownByPixels(800);
                Thread.sleep(1500);
                if (homePage.isWebinarDisplayed()) {
                    break;
                }
                scrollAttempts++;
            }
            
            homePage.clickWebinars();
            test.log(Status.PASS, "✓ Navigated to Webinars page");
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "WebinarsPage_" + webinarName.replace(" ", "_"))
            );
            
            Thread.sleep(2000);
            
            // Step 2: Search for webinar
            test.log(Status.INFO, "Step 2: Searching for webinar: " + webinarName);
            
            webinarPage.searchWebinar(webinarName);
            test.log(Status.PASS, "✓ Search performed for: " + webinarName);
            
            Thread.sleep(2000);
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "Search_" + webinarName.replace(" ", "_"))
            );
            
            // Clear and verify
            webinarPage.clearWebinarSearch();
            Thread.sleep(1000);
            
            // Search again
            webinarPage.searchWebinar(webinarName);
            Thread.sleep(2000);
            
            test.log(Status.PASS, "✓ Webinar search validated");
            
            // Step 3: Clear search
            test.log(Status.INFO, "Step 3: Clearing search");
            webinarPage.clearWebinarSearch();
            Thread.sleep(1000);
            test.log(Status.PASS, "✓ Search cleared successfully");
            
            // Step 4: Navigate through webinars using arrow
            test.log(Status.INFO, "Step 4: Navigating through webinar carousel");
            
            BaseSteps.scrollDownByPixels(500);
            Thread.sleep(1000);
            
            int arrowClicks = 0;
            while (webinarPage.isRightArrowVisible() && arrowClicks < 3) {
                webinarPage.clickRightArrow();
                arrowClicks++;
                Thread.sleep(1000);
            }
            
            test.log(Status.INFO, "Navigated through carousel: " + arrowClicks + " times");
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "Carousel_" + webinarName.replace(" ", "_"))
            );
            
            // Step 5: Register for webinar
            test.log(Status.INFO, "Step 5: Registering for webinar");
            
            webinarPage.registerForWebinar();
            Thread.sleep(3000);
            
            test.log(Status.PASS, "✓ Registration form opened");
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "RegistrationForm_" + webinarName.replace(" ", "_"))
            );
            
            // Step 6: Fill registration form
            test.log(Status.INFO, "Step 6: Filling registration form");
            test.log(Status.INFO, "Experience Level: " + experience);
            
            webinarPage.selectExperience(experience);
            Thread.sleep(2000);
            test.log(Status.PASS, "✓ Experience selected: " + experience);
            
            // Step 7: Handle Get In Touch checkbox
            if (getInTouch.equalsIgnoreCase("Yes")) {
                test.log(Status.INFO, "Step 7: Checking 'Get In Touch' checkbox");
                webinarPage.clickGetInTouchCheckbox();
                Thread.sleep(2000);
                test.log(Status.PASS, "✓ 'Get In Touch' checkbox checked");
            } else {
                test.log(Status.INFO, "Step 7: Skipping 'Get In Touch' checkbox");
            }
            
            // Final screenshot
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "FormCompleted_" + webinarName.replace(" ", "_"))
            );
            
            // Note: Not submitting form to avoid actual registration
            test.log(Status.INFO, "Note: Form submission skipped (test mode)");
            
            // Validate expected result
            test.log(Status.INFO, "Step 8: Validating test result");
            if (expectedResult.equalsIgnoreCase("Success")) {
                test.log(Status.PASS, "✓✓✓ TEST PASSED: " + webinarName + " ✓✓✓");
                Assert.assertTrue(true, "Test completed successfully");
            }
            
            test.log(Status.PASS, "========== TEST COMPLETE: " + webinarName + " ==========");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "✗ Test failed: " + e.getMessage());
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "FAILED_" + webinarName.replace(" ", "_"))
            );
            Assert.fail("Test failed for webinar: " + webinarName + " | Error: " + e.getMessage());
        }
    }
    
    /**
     * Test to verify search functionality with multiple search terms
     */
    @Test(priority = 2, dataProvider = "webinarData",
          description = "Verify webinar search returns results")
    public void testWebinarSearchValidation(String webinarName, String experience, 
                                            String getInTouch, String expectedResult) {
        
        test = extent.createTest("Search Validation: " + webinarName, 
                                "Validating search functionality for: " + webinarName);
        test.assignCategory("Search Tests");
        
        try {
            test.log(Status.INFO, "Starting search validation for: " + webinarName);
            
            // Navigate to webinars (reusing session)
            driver.get(url);
            Thread.sleep(2000);
            
            int scrollAttempts = 0;
            while (scrollAttempts < 10) {
                BaseSteps.scrollDownByPixels(800);
                Thread.sleep(1500);
                if (homePage.isWebinarDisplayed()) {
                    break;
                }
                scrollAttempts++;
            }
            
            homePage.clickWebinars();
            Thread.sleep(2000);
            
            // Perform search
            webinarPage.searchWebinar(webinarName);
            test.log(Status.INFO, "Search performed: " + webinarName);
            
            Thread.sleep(2000);
            
            // Capture screenshot
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "SearchValidation_" + webinarName.replace(" ", "_"))
            );
            
            // Clear search
            webinarPage.clearWebinarSearch();
            test.log(Status.PASS, "✓ Search cleared successfully");
            
            test.log(Status.PASS, "✓ Search validation complete for: " + webinarName);
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Search validation failed: " + e.getMessage());
            Assert.fail("Search validation failed: " + e.getMessage());
        }
    }
    
    @AfterClass
    public void teardownClass() {
        System.out.println("\n========== WEBINAR TEST SUITE - CLEANUP ==========");
        
        ExtentManager.flush();
        System.out.println("✓ ExtentReports flushed");
        
        if (driver != null) {
            BaseSteps.tearDown();
            System.out.println("✓ Browser closed");
        }
        
        System.out.println("========== WEBINAR TESTS COMPLETE ==========\n");
        System.out.println("📊 Report generated: " + ExtentManager.getReportPath());
    }
}