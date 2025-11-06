package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.edureka.pages.BasePage;
import com.edureka.pages.CareerPage;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.ReportManager;

public class DummyTest2 extends ReportManager {

	WebDriver driver;
	BasePage basePage;
	HomePage hp;
	LoginPage lp;
	CareerPage cp;

	@BeforeClass
	public void setupTest() {
		driver = BaseSteps.initializeDriver();
	}

	@Test
	public void careerJobApplicationTest() throws Throwable {

		basePage = new BasePage(driver);
		hp = new HomePage(driver);
		Thread.sleep(3000);
		
		hp.clicklogIn();
		lp = new LoginPage(driver);
		Thread.sleep(3000);
		
		lp.logIn();
		cp = new CareerPage(driver);

		System.out.println("Page title is: " + driver.getTitle());

		// Scroll until Careers link is visible
		while (true) {
			BaseSteps.scrollDownByPixels();
			Thread.sleep(2000);
			if (hp.isCareerDisplayed()) {
				break;
			}
		}

		hp.clickCareers();
		hp.getPageTitle();

		// Navigate to job openings section
		cp.navigateToJobOpenings();
		cp.openJobListing();
		cp.getCurrentUrl();

		// Switch to job application window
		cp.handleMultipleWindows(driver);

		// Fill job application form
		cp.applyForJob("Zane Falcao", "falcaozane@gmail.com", "9028921961");
		
		
		cp.clickBrowse();
		Thread.sleep(6000);
		// Upload resume and submit (using Robot class)
		cp.uploadResumeAndSubmit();

		System.out.println("Test completed successfully!");

		// BaseSteps.tearDown();
	}
}