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
    	driver = BaseSteps.initializeDriver(); // Reads browser from properties
    }
 
    @Test
    public void sampleTest() throws Throwable {
        
        basePage = new BasePage(driver);
        hp = new HomePage(driver);
        Thread.sleep(3000);
        hp.clicklogIn();
        lp = new LoginPage(driver);
        Thread.sleep(3000);
        lp.logIn();
        cp = new CareerPage(driver);
        
 
        System.out.println("Page title is: " + driver.getTitle());
        
        while (true) {
			BaseSteps.scrollDownByPixels();
			Thread.sleep(2000);
			if (hp.isCareerDisplayed()) {
				break;
			}
		}
        
        hp.clickCareers();
        
        hp.getPageTitle();
        
        cp.navigateToJobOpenings();
        
        cp.openJobListing();
        
        cp.getCurrentUrl();
        
        cp.handleMultipleWindows(driver);
        
        cp.applyForJob("Zane Falcao", "falcaozane@gmail.com", "9028921961");
        
        cp.uploadResumeAndSubmit();
 
//        BaseSteps.tearDown();
    }
}