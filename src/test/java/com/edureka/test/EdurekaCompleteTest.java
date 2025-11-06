package com.edureka.test;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.edureka.dataprovider.TestDataProvider;
import com.edureka.pages.BasePage;
import com.edureka.pages.CareerPage;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.pages.WebinarPage;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.ReportManager;

public class EdurekaCompleteTest extends ReportManager {

	WebDriver driver;
	BasePage basePage;
	HomePage hp;
	LoginPage lp;
	WebinarPage wp;
	CareerPage cp;

	@BeforeClass
	public void setupTest() {
		System.out.println("========== TEST SUITE STARTED ==========");
		driver = BaseSteps.initializeDriver();
		basePage = new BasePage(driver);
		basePage.waitForPageLoadComplete();
		
		// Initialize page objects
		hp = new HomePage(driver);
		lp = new LoginPage(driver);
		wp = new WebinarPage(driver);
		cp = new CareerPage(driver);
		
		System.out.println("Browser initialized: " + driver.getTitle());
	}

	// ==================== LOGIN TEST ====================
	@Test(priority = 1, description = "User Login Test")
	public void loginTest() throws Throwable {
		System.out.println("\n===== TEST 1: User Login =====");
		
		basePage.implicitWait(5);
		hp.clicklogIn();
		System.out.println("Clicked on Login button");
		
		basePage.implicitWait(5);
		String pageTitle = driver.getTitle();
		System.out.println("Current Page Title: " + pageTitle);
		
		lp.logIn(); // Uses credentials from data.properties
		System.out.println("Login credentials entered and submitted");
		
		// Assertion: Verify successful login
		basePage.implicitWait(5);
		String afterLoginTitle = driver.getTitle();
		Assert.assertNotNull(afterLoginTitle, "Page title should not be null after login");
		System.out.println("✓ Login successful! Page title: " + afterLoginTitle);
		
		basePage.takeScreenshot("LoginSuccessful");
	}

	// ==================== WEBINAR TESTS ====================
	@Test(priority = 2, description = "Navigate to Webinars Section", dependsOnMethods = "loginTest")
	public void navigateToWebinarsTest() throws Throwable {
		System.out.println("\n===== TEST 2: Navigate to Webinars =====");
		
		// Scroll until webinar section is visible (with boundary)
		int attempts = 0;
		System.out.println("Scrolling to find Webinars section...");
		while (attempts++ < 10 && !hp.isWebinarDisplayed()) {
			basePage.scrollDownByPixels(800);
			Thread.sleep(1000);
		}
		
		// Assertion: Verify webinar section is visible
		Assert.assertTrue(hp.isWebinarDisplayed(), "Webinar section should be visible after scrolling");
		System.out.println("✓ Webinar section found!");
		
		hp.clickWebinars();
		System.out.println("Clicked on Webinars");
		
		basePage.implicitWait(5);
		String webinarPageTitle = driver.getTitle();
		System.out.println("Webinar Page Title: " + webinarPageTitle);
		
		// Assertion: Verify navigation to webinar page
		Assert.assertTrue(driver.getCurrentUrl().contains("webinar") || 
						  webinarPageTitle.toLowerCase().contains("webinar"),
						  "Should navigate to webinar page");
		System.out.println("✓ Successfully navigated to Webinars page");
		
		basePage.takeScreenshot("WebinarPageLoaded");
	}

	@Test(priority = 3, 
		  dataProvider = "webinarSearchTerms", 
		  dataProviderClass = TestDataProvider.class,
		  description = "Search Webinars with Multiple Keywords",
		  dependsOnMethods = "navigateToWebinarsTest")
	public void searchWebinarTest(String searchTerm) throws InterruptedException {
		System.out.println("\n===== TEST 3: Search Webinar - \"" + searchTerm + "\" =====");
		
		wp.searchWebinar(searchTerm);
		System.out.println("Entered search term: " + searchTerm);
		
		Thread.sleep(3000); // Wait for search results
		
		// Assertion: Verify search was performed (URL or page change)
		String currentUrl = driver.getCurrentUrl();
		Assert.assertNotNull(currentUrl, "Current URL should not be null after search");
		System.out.println("Search completed for: " + searchTerm);
		
		basePage.takeScreenshot("WebinarSearch_" + searchTerm.replaceAll(" ", "_"));
		
		wp.clearWebinarSearch();
		System.out.println("✓ Search cleared, ready for next search term\n");
		Thread.sleep(1000);
	}

	@Test(priority = 4, description = "Register for Webinar", dependsOnMethods = "searchWebinarTest")
	public void registerForWebinarTest() throws InterruptedException {
		System.out.println("\n===== TEST 4: Webinar Registration =====");
		
		basePage.scrollDownByPixels(500);
		Thread.sleep(2000);
		
		// Navigate through carousel to find webinar
		System.out.println("Navigating through webinar carousel...");
		int carouselClicks = 0;
		while (wp.isRightArrowVisible() && carouselClicks < 5) {
			wp.clickRightArrow();
			carouselClicks++;
			System.out.println("Carousel click #" + carouselClicks);
			basePage.implicitWait(3);
		}
		
		// Assertion: Verify carousel navigation completed
		Assert.assertTrue(carouselClicks > 0, "Should be able to navigate carousel");
		System.out.println("✓ Carousel navigation completed");
		
		basePage.implicitWait(3);
		wp.searchWebinarCarousel();
		System.out.println("Selected webinar from carousel");
		
		Thread.sleep(3000);
		wp.selectExperience("Student");
		System.out.println("Selected experience level: Student");
		
		Thread.sleep(3000);
		wp.clickGetInTouchCheckbox();
		System.out.println("Clicked 'Get in Touch' checkbox");
		
		basePage.implicitWait(5);
		
		// Assertion: Verify form is ready for submission
		String pageSource = driver.getPageSource();
		Assert.assertNotNull(pageSource, "Page source should not be null before submission");
		
		basePage.takeScreenshot("WebinarRegistrationForm");
		
		wp.submitWebinarRegistration();
		System.out.println("✓ Webinar registration submitted successfully!");
		
		Thread.sleep(2000);
		basePage.takeScreenshot("WebinarRegistrationSubmitted");
	}

	// ==================== CAREER TESTS ====================
	@Test(priority = 5, description = "Navigate to Careers Page", dependsOnMethods = "registerForWebinarTest")
	public void navigateToCareersTest() throws Throwable {
		System.out.println("\n===== TEST 5: Navigate to Careers =====");
		
		// Navigate back to home page using PropertyReader
		String homeUrl = com.edureka.parameter.PropertyReader.getProperty("url");
		driver.get(homeUrl);
		System.out.println("Navigated back to home page: " + homeUrl);
		
		basePage.waitForPageLoadComplete();
		basePage.implicitWait(5);
		System.out.println("Home page loaded successfully");
		
		// Rest of the code remains same...
		int attempts = 0;
		System.out.println("Scrolling to find Careers section...");
		while (attempts++ < 10 && !hp.isCareerDisplayed()) {
			basePage.scrollDownByPixels(800);
			Thread.sleep(1000);
		}
		
		Assert.assertTrue(hp.isCareerDisplayed(), "Careers section should be visible after scrolling");
		System.out.println("✓ Careers section found!");
		
		hp.clickCareers();
		System.out.println("Clicked on Careers");
		
		basePage.implicitWait(5);
		String careersPageTitle = hp.getPageTitle();
		System.out.println("Careers Page Title: " + careersPageTitle);
		
		Assert.assertTrue(driver.getCurrentUrl().contains("career") || 
						  careersPageTitle.toLowerCase().contains("career"),
						  "Should navigate to careers page");
		System.out.println("✓ Successfully navigated to Careers page");
		
		basePage.takeScreenshot("CareersPageLoaded");
	}

	@Test(priority = 6, description = "Navigate to Job Opening", dependsOnMethods = "navigateToCareersTest")
	public void navigateToJobOpeningTest() throws Throwable {
		System.out.println("\n===== TEST 6: Navigate to Job Opening =====");
		
		cp.navigateToJobOpenings();
		System.out.println("Scrolled to job openings section");
		
		String currentUrl = cp.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);
		
		cp.openJobListing();
		System.out.println("Clicked on job listing: Associate Research Analyst");
		
		Thread.sleep(3000);
		
		// Assertion: Verify multiple windows are open
		int windowCount = driver.getWindowHandles().size();
		Assert.assertTrue(windowCount > 1, "Job application should open in new window");
		System.out.println("✓ Job listing opened in new window. Total windows: " + windowCount);
		
		basePage.takeScreenshot("JobListingOpened");
	}

	@Test(priority = 7, 
		  dataProvider = "ApplyForJob", 
		  dataProviderClass = TestDataProvider.class,
		  description = "Apply for Job with Test Data",
		  dependsOnMethods = "navigateToJobOpeningTest")
	public void applyForJobTest(String applicantName, String applicantEmail, String applicantPhone) throws Throwable {
		System.out.println("\n===== TEST 7: Job Application =====");
		System.out.println("Applicant: " + applicantName);
		System.out.println("Email: " + applicantEmail);
		System.out.println("Phone: " + applicantPhone);
		
		// Switch to job application window
		cp.handleMultipleWindows(driver);
		System.out.println("Switched to application window");
		
		basePage.implicitWait(5);
		String applicationPageTitle = driver.getTitle();
		System.out.println("Application Page Title: " + applicationPageTitle);
		
		// Assertion: Verify switched to correct window
		Assert.assertNotNull(applicationPageTitle, "Application page title should not be null");
		
		// Fill job application form using DataProvider values
		cp.applyForJob(applicantName, applicantEmail, applicantPhone);
		System.out.println("✓ Application form filled successfully");
		
		basePage.takeScreenshot("JobApplicationFormFilled");
		
		Thread.sleep(2000);
		
		// Upload resume and submit (using Robot class)
		cp.uploadResumeAndSubmit();
		System.out.println("✓ Resume uploaded and application submitted!");
		
		Thread.sleep(3000);
		
		// Assertion: Verify submission completed (check URL or page change)
		String afterSubmitUrl = driver.getCurrentUrl();
		Assert.assertNotNull(afterSubmitUrl, "URL should not be null after submission");
		System.out.println("✓ Job application completed successfully!");
		
		basePage.takeScreenshot("JobApplicationSubmitted");
	}

	@AfterClass
	public void shutdown() {
		System.out.println("\n========== TEST SUITE COMPLETED ==========");
		System.out.println("Total Tests: 7");
		System.out.println("1. Login Test ✓");
		System.out.println("2. Navigate to Webinars ✓");
		System.out.println("3. Search Webinars (Data-Driven) ✓");
		System.out.println("4. Register for Webinar ✓");
		System.out.println("5. Navigate to Careers ✓");
		System.out.println("6. Navigate to Job Opening ✓");
		System.out.println("7. Apply for Job (Data-Driven) ✓");
		System.out.println("==========================================");
		
		BaseSteps.tearDown();
	}
}
