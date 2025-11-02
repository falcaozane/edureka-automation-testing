package com.edureka.test;
 
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.edureka.pages.BasePage;
import com.edureka.pages.CareerPage;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.setup.BaseSteps;
 
public class DummyTest2 {
 
    WebDriver driver;
    BasePage basePage;
    HomePage hp;
    LoginPage lp;
    CareerPage cp;
 
    @Test
    public void sampleTest() throws Throwable {
        driver = BaseSteps.initializeDriver(); // Reads browser from properties
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
			if (hp.isWebinarDisplayed()) {
				break;
			}
		}
        
        hp.clickWebinar();
        
        System.out.println("Page title is: " + driver.getTitle());

 
//        BaseSteps.tearDown();
    }
}