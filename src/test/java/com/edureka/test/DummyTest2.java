package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
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
    }

    @Test(dataProvider = "ApplyForJob", dataProviderClass = TestDataProvider.class)
    public void careerJobApplicationTest(String applicantName, String applicantEmail, String applicantPhone) throws Throwable {

        basePage = new BasePage(driver);
        hp = new HomePage(driver);
        Thread.sleep(3000);

        hp.clicklogIn();
        lp = new LoginPage(driver);
        Thread.sleep(3000);

        lp.logIn(); // still using data.properties for login

        cp = new CareerPage(driver);
        System.out.println("Page title is: " + driver.getTitle());

        // Scroll until Careers link is visible
        while (true) {
            basePage.scrollDownByPixels();
            Thread.sleep(2000);
            if (hp.isCareerDisplayed()) break;
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
//        BaseSteps.tearDown();
    }
}
