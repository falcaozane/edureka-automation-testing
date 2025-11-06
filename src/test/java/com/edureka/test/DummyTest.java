package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.edureka.pages.*;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.ReportManager;
import com.edureka.dataprovider.TestDataProvider;

public class DummyTest extends ReportManager {

	WebDriver driver;
	BasePage basePage;
	HomePage hp;
	LoginPage lp;
	WebinarPage wp;
	
	

	@BeforeClass
	public void setupTest() {
		driver = BaseSteps.initializeDriver();
		basePage = new BasePage(driver);
		basePage.waitForPageLoadComplete();
	}
	
	

	@Test(priority = 1)
	public void loginAndNavigateToWebinars() throws Throwable {

		hp = new HomePage(driver);
		basePage.implicitWait(5);
		hp.clicklogIn();

		lp = new LoginPage(driver);
		basePage.implicitWait(5);
		lp.logIn(); // still using data.properties

		wp = new WebinarPage(driver);
		System.out.println("Page title is: " + driver.getTitle());

		// Scroll until webinar section/card is visible (bounded)
		int attempts = 0;
		while (attempts++ < 10 && !hp.isWebinarDisplayed()) {
			basePage.scrollDownByPixels(800);
		}

		hp.clickWebinars();
		System.out.println("Page title is: " + driver.getTitle());
	}

	@Test(priority = 2, dataProvider = "webinarSearchTerms", dataProviderClass = TestDataProvider.class, dependsOnMethods = "loginAndNavigateToWebinars")
	public void searchWebinarTest(String searchTerm) throws InterruptedException {
		wp = new WebinarPage(driver);
		wp.searchWebinar(searchTerm);
		Thread.sleep(3000);
		wp.clearWebinarSearch();	
	}
	
	@Test(priority = 3, dependsOnMethods = "loginAndNavigateToWebinars")
	public void registerForWebinar() throws InterruptedException{
		
		basePage.scrollDownByPixels(500);

		while (wp.isRightArrowVisible()) {
		    wp.clickRightArrow();
		    basePage.implicitWait(5);
		}

		basePage.implicitWait(3);
		wp.searchWebinarCarousel();
		Thread.sleep(3000);
		wp.selectExperience("Student");
		Thread.sleep(3000);
		wp.clickGetInTouchCheckbox();
		basePage.implicitWait(5);
		wp.submitWebinarRegistration();
	}


	@AfterClass
	public void shutdown() {
		BaseSteps.tearDown();
	}
}