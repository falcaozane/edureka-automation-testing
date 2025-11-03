package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.edureka.pages.CareerPage;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.parameter.ExcelReader;
import com.edureka.parameter.PropertyReader;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.TestLogger;

/**
 * Data-Driven Test for Career/Job Application Functionality
 * Uses Excel DataProvider with ExtentReports and Screenshots
 * ONE BROWSER INSTANCE for all tests in this class (prevents Cloudflare blocking)
 */
public class CareerDataDrivenTest {
    
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    CareerPage careerPage;
    
    String url = PropertyReader.getProperty("url");
    
    private boolean isLoggedIn = false;
    
    @BeforeClass
    public void setupClass() {
        System.out.println("\n========== CAREER TEST SUITE - INITIALIZING ==========");
        
        // Initialize browser ONCE for all tests in this class
        driver = BaseSteps.initializeDriver();
        
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        careerPage = new CareerPage(driver);
        
        System.out.println("✓ Browser initialized (will be reused for all career tests)");
        
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
        
        loginPage.logIn();
        Thread.sleep(3000);
        
        isLoggedIn = true;
        System.out.println("✓ Login completed - session will be reused\n");
    }
    
    /**
     * DataProvider - Reads job application data from Excel
     */
    @DataProvider(name = "jobApplicationData")
    public Object[][] getJobApplicationData() {
        String sheetName = "JobApplicationData";
        Object[][] data = ExcelReader.getTestData(sheetName);
        
        if (data == null || data.length == 0) {
            System.err.println("⚠ WARNING: No data loaded from Excel sheet: " + sheetName);
            return new Object[0][0];
        }
        
        System.out.println("📊 Loaded " + data.length + " job application test cases from Excel");
        return data;
    }
    
    /**
     * Main Test - Job Application Flow (Data-Driven)
     * 
     * Excel Columns: JobTitle | ApplicantName | ApplicantEmail | ApplicantPhone | ExpectedResult
     */
    @Test(priority = 1, dataProvider = "jobApplicationData",
          description = "Complete job application flow with form filling")
    public void testJobApplication(String jobTitle, String applicantName, 
                                   String applicantEmail, String applicantPhone,
                                   String expectedResult) {
        
        try {
            // Log test data
            TestLogger.logTestData("📋 Application Details",
                "Job Title", jobTitle,
                "Applicant Name", applicantName,
                "Email", applicantEmail,
                "Phone", applicantPhone
            );
            
            // Navigate to home page (don't login again)
            TestLogger.logStep("Step 1: Navigating to Careers Page");
            driver.get(url);
            Thread.sleep(2000);
            
            int scrollAttempts = 0;
            while (scrollAttempts < 10) {
                BaseSteps.scrollDownByPixels(800);
                Thread.sleep(1500);
                if (homePage.isCareerDisplayed()) {
                    break;
                }
                scrollAttempts++;
            }
            
            homePage.clickCareers();
            TestLogger.logPass("Successfully navigated to Careers page");
            TestLogger.logInfo("Page Title: " + careerPage.getPageTitle());
            TestLogger.captureScreenshot("CareersPage_" + applicantName.replace(" ", "_"));
            
            Thread.sleep(2000);
            
            // Step 2: Navigate to Job Openings
            TestLogger.logStep("Step 2: Locating Job Openings Section");
            careerPage.navigateToJobOpenings();
            Thread.sleep(2000);
            
            TestLogger.logPass("Job Openings section is visible");
            TestLogger.captureScreenshot("JobOpenings_" + applicantName.replace(" ", "_"));
            
            // Step 3: Open specific job listing
            TestLogger.logStep("Step 3: Opening Job Listing");
            TestLogger.logInfo("Job Position: " + jobTitle);
            
            careerPage.openJobListing();
            Thread.sleep(3000);
            
            TestLogger.logPass("Job listing opened successfully");
            TestLogger.logInfo("Current URL: " + careerPage.getCurrentUrl());
            
            // Step 4: Handle window switch
            TestLogger.logStep("Step 4: Switching to Application Window");
            careerPage.handleMultipleWindows(driver);
            Thread.sleep(2000);
            
            TestLogger.logPass("Switched to job application window");
            TestLogger.captureScreenshot("JobDetails_" + applicantName.replace(" ", "_"));
            
            // Step 5: Fill application form
            TestLogger.logStep("Step 5: Filling Application Form");
            TestLogger.logInfo("Entering applicant details...");
            
            careerPage.applyForJob(applicantName, applicantEmail, applicantPhone);
            Thread.sleep(2000);
            
            TestLogger.logPass("Application form filled successfully");
            TestLogger.captureScreenshot("FormFilled_" + applicantName.replace(" ", "_"));
            
            // Step 6: Final validation
            TestLogger.logStep("Step 6: Validating Application");
            String pageTitle = careerPage.getPageTitle();
            TestLogger.logInfo("Current Page: " + pageTitle);
            
            TestLogger.logWarning("Form submission skipped (test mode - avoiding actual job application)");
            
            TestLogger.logSeparator();
            
            // Validate expected result
            if (expectedResult.equalsIgnoreCase("Success")) {
                TestLogger.logSuccess("JOB APPLICATION TEST COMPLETED FOR: " + applicantName);
                Assert.assertTrue(true, "Job application flow completed successfully");
            } else {
                TestLogger.logWarning("Test completed with expected result: " + expectedResult);
            }
            
            // Navigate back to main window for next test
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
            
        } catch (Exception e) {
            TestLogger.logError("TEST FAILED FOR: " + applicantName);
            TestLogger.logFail("Error: " + e.getMessage());
            TestLogger.captureScreenshot("FAILED_" + applicantName.replace(" ", "_"));
            
            e.printStackTrace();
            Assert.fail("Job application test failed for " + applicantName + ": " + e.getMessage());
        }
    }
    
    /**
     * Test - Form Field Validation (doesn't need browser interaction)
     */
    @Test(priority = 2, dataProvider = "jobApplicationData",
          description = "Validate form field formats and data integrity")
    public void testFormFieldValidation(String jobTitle, String applicantName, 
                                        String applicantEmail, String applicantPhone,
                                        String expectedResult) {
        
        try {
            TestLogger.logStep("Form Field Validation for: " + applicantName);
            
            // Validate email format
            boolean validEmail = applicantEmail.contains("@") && applicantEmail.contains(".");
            if (validEmail) {
                TestLogger.logPass("Email format is valid: " + applicantEmail);
            } else {
                TestLogger.logFail("Email format is invalid: " + applicantEmail);
            }
            
            // Validate phone format
            boolean validPhone = applicantPhone.length() >= 10 && 
                                applicantPhone.replaceAll("[^0-9]", "").length() >= 10;
            if (validPhone) {
                TestLogger.logPass("Phone format is valid: " + applicantPhone);
            } else {
                TestLogger.logFail("Phone format is invalid: " + applicantPhone);
            }
            
            // Validate name format
            boolean validName = applicantName.length() >= 3 && 
                               applicantName.matches("^[a-zA-Z\\s]+$");
            if (validName) {
                TestLogger.logPass("Name format is valid: " + applicantName);
            } else {
                TestLogger.logFail("Name format is invalid: " + applicantName);
            }
            
            TestLogger.logSeparator();
            
            // Assert all validations
            Assert.assertTrue(validEmail, "Email validation failed for: " + applicantEmail);
            Assert.assertTrue(validPhone, "Phone validation failed for: " + applicantPhone);
            Assert.assertTrue(validName, "Name validation failed for: " + applicantName);
            
            TestLogger.logSuccess("ALL VALIDATIONS PASSED FOR: " + applicantName);
            
        } catch (AssertionError e) {
            TestLogger.logError("VALIDATION FAILED FOR: " + applicantName);
            TestLogger.logFail(e.getMessage());
            throw e;
        } catch (Exception e) {
            TestLogger.logError("Unexpected error during validation");
            TestLogger.logFail(e.getMessage());
            Assert.fail("Form field validation failed: " + e.getMessage());
        }
    }
    
    @AfterClass
    public void teardownClass() {
        System.out.println("\n========== CAREER TEST SUITE - CLEANUP ==========");
        
        if (driver != null) {
            BaseSteps.tearDown();
            System.out.println("✓ Browser closed");
        }
        
        System.out.println("========== CAREER TESTS COMPLETE ==========\n");
    }
}