package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.parameter.ObjectReader;
import com.edureka.setup.BaseSteps;

/**
 * Test demonstrating Object.properties usage
 * Shows centralized locator repository in action
 */
public class TestObjectProperties {
    
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    
    @BeforeTest
    public void setup() {
        System.out.println("\n========== TEST SETUP ==========");
        
        // Initialize driver
        driver = BaseSteps.initializeDriver();
        System.out.println("✓ Driver initialized");
        
        // Initialize page objects
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        System.out.println("✓ Page objects initialized");
        
        // Optional: Print all locators for debugging
        // ObjectReader.printAllLocators();
        
        System.out.println("========== SETUP COMPLETE ==========\n");
    }
    
    
    @Test(priority = 1, description = "Test login using standard @FindBy locators")
    public void testLoginWithPageFactory() throws InterruptedException {
        System.out.println("\n========== TEST 1: Login with Page Factory ==========");
        
        Thread.sleep(3000);
        
        // Click login button
        homePage.clicklogIn();
        System.out.println("✓ Clicked login button using Page Factory");
        
        Thread.sleep(2000);
        
        // Perform login
        loginPage.logIn();
        System.out.println("✓ Login completed using Page Factory");
        
        Thread.sleep(3000);
        
        System.out.println("========== TEST 1 COMPLETE ==========\n");
    }
    
    
    @Test(priority = 2, description = "Test login using Object.properties with ObjectReader")
    public void testLoginWithObjectReader() throws InterruptedException {
        System.out.println("\n========== TEST 2: Login with ObjectReader ==========");
        
        // Navigate back to home
        driver.get("https://edureka.co/");
        Thread.sleep(3000);
        
        // Click login using ObjectReader
        homePage.clickLoginUsingObjectReader();
        System.out.println("✓ Clicked login button using ObjectReader (xpath)");
        
        Thread.sleep(2000);
        
        // Perform login using ObjectReader
        loginPage.logInUsingObjectReader();
        System.out.println("✓ Login completed using ObjectReader");
        System.out.println("  → Email field located using: id");
        System.out.println("  → Password field located using: name");
        System.out.println("  → Login button located using: xpath");
        
        Thread.sleep(3000);
        
        System.out.println("========== TEST 2 COMPLETE ==========\n");
    }
    
    
    @Test(priority = 3, description = "Verify locators exist in Object.properties")
    public void testVerifyLocatorsExist() {
        System.out.println("\n========== TEST 3: Verify Locators ==========");
        
        // Check if critical locators exist
        Assert.assertTrue(ObjectReader.hasLocator("homePage.loginButton.xpath"), 
            "Login button xpath locator should exist");
        System.out.println("✓ homePage.loginButton.xpath exists");
        
        Assert.assertTrue(ObjectReader.hasLocator("loginPage.emailField.id"), 
            "Email field id locator should exist");
        System.out.println("✓ loginPage.emailField.id exists");
        
        Assert.assertTrue(ObjectReader.hasLocator("loginPage.passwordField.name"), 
            "Password field name locator should exist");
        System.out.println("✓ loginPage.passwordField.name exists");
        
        Assert.assertTrue(ObjectReader.hasLocator("homePage.webinarsLink.linkText"), 
            "Webinars link text locator should exist");
        System.out.println("✓ homePage.webinarsLink.linkText exists");
        
        System.out.println("========== TEST 3 COMPLETE ==========\n");
    }
    
    
    @Test(priority = 4, description = "Test different locator types for same element")
    public void testMultipleLocatorTypes() {
        System.out.println("\n========== TEST 4: Multiple Locator Types ==========");
        
        // Get different locator types for email field
        String idLocator = ObjectReader.getLocator("loginPage.emailField.id");
        String nameLocator = ObjectReader.getLocator("loginPage.emailField.name");
        String cssLocator = ObjectReader.getLocator("loginPage.emailField.css");
        String xpathLocator = ObjectReader.getLocator("loginPage.emailField.xpath");
        
        System.out.println("Email Field Locators:");
        System.out.println("  → ID: " + idLocator);
        System.out.println("  → NAME: " + nameLocator);
        System.out.println("  → CSS: " + cssLocator);
        System.out.println("  → XPATH: " + xpathLocator);
        
        // Verify all are non-null
        Assert.assertNotNull(idLocator, "ID locator should not be null");
        Assert.assertNotNull(nameLocator, "NAME locator should not be null");
        Assert.assertNotNull(cssLocator, "CSS locator should not be null");
        Assert.assertNotNull(xpathLocator, "XPATH locator should not be null");
        
        System.out.println("✓ All locator types available for email field");
        
        System.out.println("========== TEST 4 COMPLETE ==========\n");
    }
    
    
    @Test(priority = 5, description = "Demonstrate all 7 locator types")
    public void testAllLocatorTypes() {
        System.out.println("\n========== TEST 5: All 7 Locator Types ==========");
        
        System.out.println("Demonstrating all locator types from Object.properties:");
        
        // 1. ID
        String idExample = ObjectReader.getLocator("loginPage.emailField.id");
        System.out.println("1. ID: " + idExample);
        
        // 2. NAME
        String nameExample = ObjectReader.getLocator("loginPage.passwordField.name");
        System.out.println("2. NAME: " + nameExample);
        
        // 3. CLASS NAME
        String classExample = ObjectReader.getLocator("careerPage.jobOpenings.className");
        System.out.println("3. CLASS NAME: " + classExample);
        
        // 4. CSS
        String cssExample = ObjectReader.getLocator("loginPage.emailField.css");
        System.out.println("4. CSS: " + cssExample);
        
        // 5. XPATH
        String xpathExample = ObjectReader.getLocator("loginPage.loginButton.xpath");
        System.out.println("5. XPATH: " + xpathExample);
        
        // 6. LINK TEXT
        String linkTextExample = ObjectReader.getLocator("homePage.webinarsLink.linkText");
        System.out.println("6. LINK TEXT: " + linkTextExample);
        
        // 7. PARTIAL LINK TEXT
        String partialLinkExample = ObjectReader.getLocator("homePage.careersLink.partialLinkText");
        System.out.println("7. PARTIAL LINK TEXT: " + partialLinkExample);
        
        System.out.println("\n✓ All 7 locator types demonstrated!");
        System.out.println("========== TEST 5 COMPLETE ==========\n");
    }
    
    
    @AfterTest
    public void teardown() {
        System.out.println("\n========== TEST TEARDOWN ==========");
        
        if (driver != null) {
            // Uncomment to close browser
            // BaseSteps.tearDown();
            System.out.println("✓ Browser session maintained for review");
        }
        
        System.out.println("========== TEARDOWN COMPLETE ==========\n");
    }
}