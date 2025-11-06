package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.edureka.pages.*;
import com.edureka.setup.BaseSteps;
import com.edureka.utils.ReportManager;
import com.edureka.dataprovider.TestDataProvider;

public class DummyTest2 extends ReportManager {

    WebDriver driver;
    BasePage basePage;
    HomePage hp;
    LoginPage lp;
    CareerPage cp;

    @BeforeClass
    public void setupTest() {
        driver = BaseSteps.initializeDriver();
        basePage = new BasePage(driver);
		basePage.waitForPageLoadComplete();
    }
    
    @BeforeMethod
    public void initializing() {
        hp = new HomePage(driver);
        lp = new LoginPage(driver);
        cp = new CareerPage(driver);
    }

    @Test(dataProvider = "ApplyForJob", dataProviderClass = TestDataProvider.class)
    public void careerJobApplicationTest(String applicantName, String applicantEmail, String applicantPhone) throws Throwable {

        
        Thread.sleep(3000);

        hp.clicklogIn();
        
        Thread.sleep(3000);

        lp.logIn(); // still using data.properties for login

        
        System.out.println("Page title is: " + driver.getTitle());

        // Scroll until Careers link is visible
        int attempts = 0;
        while (attempts++ <10 && !hp.isCareerDisplayed()) {
            basePage.scrollDownByPixels(800);
        }

        hp.clickCareers();
        hp.getPageTitle();

        // Navigate to job openings section
        cp.navigateToJobOpenings();
        cp.openJobListing();
        cp.getCurrentUrl();

        // Switch to job application window
        cp.handleMultipleWindows(driver);

        // Fill job application form using DataProvider values
        cp.applyForJob(applicantName, applicantEmail, applicantPhone);

        Thread.sleep(2000);

        // Upload resume and submit (using Robot class)
        cp.uploadResumeAndSubmit();
        
        Thread.sleep(2000);

        System.out.println("Test completed successfully!");
    }
    
    @AfterClass
	public void shutdown() {
		BaseSteps.tearDown();
	}
}
