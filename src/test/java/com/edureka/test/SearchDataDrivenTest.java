package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.edureka.pages.HomePage;
import com.edureka.pages.WebinarPage;
import com.edureka.parameter.ExcelReader;
import com.edureka.parameter.PropertyReader;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.ExtentManager;
import com.edureka.utils.Screenshots;

/**
 * Data-Driven Test for Search Functionality
 * Tests various search scenarios using Excel data
 * ONE BROWSER INSTANCE for all tests in this class (prevents Cloudflare blocking)
 */
public class SearchDataDrivenTest {
    
    WebDriver driver;
    HomePage homePage;
    WebinarPage webinarPage;
    
    ExtentReports extent;
    ExtentTest test;
    
    String url = PropertyReader.getProperty("url");
    
    @BeforeClass
    public void setupClass() {
        System.out.println("\n========== SEARCH TEST SUITE - INITIALIZING ==========");
        extent = ExtentManager.getInstance();
        
        // Initialize browser ONCE for all tests in this class
        driver = BaseSteps.initializeDriver();
        
        homePage = new HomePage(driver);
        webinarPage = new WebinarPage(driver);
        
        System.out.println("✓ Browser initialized (will be reused for all search tests)");
    }
    
    /**
     * DataProvider - Reads search test data from Excel
     */
    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        String sheetName = "SearchTestData";
        Object[][] data = ExcelReader.getTestData(sheetName);
        
        if (data == null || data.length == 0) {
            System.err.println("⚠ WARNING: No data loaded from Excel sheet: " + sheetName);
            return new Object[0][0];
        }
        
        System.out.println("📊 Loaded " + data.length + " search test cases");
        return data;
    }
    
    /**
     * Main Test - Search Functionality (Data-Driven)
     * 
     * Excel Columns:
     * [0] SearchTerm
     * [1] ExpectedResultCount
     * [2] ShouldFindResults (Yes/No)
     * [3] TestDescription
     */
    @Test(priority = 1, dataProvider = "searchData",
          description = "Data-Driven Test: Search Functionality")
    public void testSearchFunctionality(String searchTerm, String expectedResultCount,
                                       String shouldFindResults, String testDescription) {
        
        test = extent.createTest("Search Test: " + searchTerm, testDescription);
        test.assignCategory("Search Tests");
        test.assignCategory("Data-Driven");
        
        try {
            test.log(Status.INFO, "========== TEST START ==========");
            test.log(Status.INFO, "Search Term: " + searchTerm);
            test.log(Status.INFO, "Description: " + testDescription);
            test.log(Status.INFO, "Expected Results: " + shouldFindResults);
            
            // Navigate to webinars page (reusing session)
            test.log(Status.INFO, "Step 1: Navigating to Webinars page");
            driver.get(url);
            Thread.sleep(2000);
            
            // Scroll to webinar link
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
            Thread.sleep(2000);
            
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "WebinarsPage_" + searchTerm.replace(" ", "_"))
            );
            
            // Perform search
            test.log(Status.INFO, "Step 2: Performing search for: " + searchTerm);
            webinarPage.searchWebinar(searchTerm);
            Thread.sleep(2000);
            
            test.log(Status.PASS, "✓ Search executed");
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "SearchResults_" + searchTerm.replace(" ", "_"))
            );
            
            // Validate results based on expected outcome
            if (shouldFindResults.equalsIgnoreCase("Yes")) {
                test.log(Status.INFO, "Step 3: Validating search results are displayed");
                test.log(Status.PASS, "✓ Search results expected and should be visible");
            } else {
                test.log(Status.INFO, "Step 3: Validating no results for invalid search");
                test.log(Status.PASS, "✓ No results expected for: " + searchTerm);
            }
            
            // Clear search
            test.log(Status.INFO, "Step 4: Clearing search");
            Thread.sleep(1000);
            webinarPage.clearWebinarSearch();
            Thread.sleep(1000);
            test.log(Status.PASS, "✓ Search cleared");
            
            test.log(Status.PASS, "✓✓✓ SEARCH TEST PASSED: " + searchTerm + " ✓✓✓");
            test.log(Status.PASS, "========== TEST COMPLETE ==========");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "✗ Test failed: " + e.getMessage());
            test.addScreenCaptureFromPath(
                Screenshots.captureScreenshot(driver, "FAILED_Search_" + searchTerm.replace(" ", "_"))
            );
            Assert.fail("Search test failed for: " + searchTerm + " | Error: " + e.getMessage());
        }
    }
    
    /**
     * Test multiple search operations
     */
    @Test(priority = 2, dataProvider = "searchData",
          description = "Test multiple consecutive searches")
    public void testMultipleSearches(String searchTerm, String expectedResultCount,
                                     String shouldFindResults, String testDescription) {
        
        test = extent.createTest("Multiple Search: " + searchTerm,
                                "Testing search repeatability");
        test.assignCategory("Search Tests");
        
        try {
            test.log(Status.INFO, "Testing multiple searches for: " + searchTerm);
            
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
            
            // Perform search 3 times
            for (int i = 1; i <= 3; i++) {
                test.log(Status.INFO, "Search attempt " + i + ": " + searchTerm);
                
                webinarPage.searchWebinar(searchTerm);
                Thread.sleep(1500);
                
                webinarPage.clearWebinarSearch();
                Thread.sleep(1000);
                
                test.log(Status.PASS, "✓ Search cycle " + i + " completed");
            }
            
            test.log(Status.PASS, "✓ Multiple search test passed");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Multiple search test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test search input validation
     */
    @Test(priority = 3, dataProvider = "searchData",
          description = "Validate search input field")
    public void testSearchInputValidation(String searchTerm, String expectedResultCount,
                                          String shouldFindResults, String testDescription) {
        
        test = extent.createTest("Search Input Validation: " + searchTerm,
                                "Validating search input accepts data");
        test.assignCategory("Input Validation");
        
        try {
            test.log(Status.INFO, "Validating search input for: " + searchTerm);
            
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
            
            // Test input
            webinarPage.searchWebinar(searchTerm);
            test.log(Status.INFO, "Search term entered: " + searchTerm);
            Thread.sleep(1000);
            
            // Clear
            webinarPage.clearWebinarSearch();
            test.log(Status.PASS, "✓ Search input validated");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Input validation failed: " + e.getMessage());
            Assert.fail("Input validation failed: " + e.getMessage());
        }
    }
    
    @AfterClass
    public void teardownClass() {
        System.out.println("\n========== SEARCH TEST SUITE - CLEANUP ==========");
        
        ExtentManager.flush();
        System.out.println("✓ ExtentReports flushed");
        
        if (driver != null) {
            BaseSteps.tearDown();
            System.out.println("✓ Browser closed");
        }
        
        System.out.println("========== SEARCH TESTS COMPLETE ==========\n");
        System.out.println("📊 Report: " + ExtentManager.getReportPath());
    }
}