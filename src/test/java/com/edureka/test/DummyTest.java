package com.edureka.test;
 
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.edureka.pages.BasePage;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.pages.WebinarPage;
import com.edureka.setup.BaseSteps;
 
public class DummyTest {
 
    WebDriver driver;
    BasePage basePage;
    HomePage hp;
    LoginPage lp;
    WebinarPage wp;
 
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
        wp = new WebinarPage(driver);
        
 
        System.out.println("Page title is: " + driver.getTitle());
        
        while (true) {
			BaseSteps.scrollDownByPixels();
			Thread.sleep(2000);
			if (hp.isWebinarDisplayed()) {
				break;
			}
		}
        
        hp.clickWebinars();
        
        System.out.println("Page title is: " + driver.getTitle());
        
        wp.searchWebinar("agentic ai");
        Thread.sleep(2000);
        wp.clearWebinarSearch();
        Thread.sleep(2000);
        wp.searchWebinar("cyber security");
        Thread.sleep(2000);
        wp.clearWebinarSearch();
        Thread.sleep(2000);
        wp.searchWebinar("hwchwvhenov");
		Thread.sleep(2000);
		wp.clearWebinarSearch();
        Thread.sleep(2000);
		
		BaseSteps.scrollDownByPixels(500);
		
		while (true) {
			wp.clickRightArrow();
			Thread.sleep(1000);
			if (wp.isRightArrowVisible() == false) {
				break;
			}
		}
		
		Thread.sleep(2000);
		
		wp.registerForWebinar();
		
		Thread.sleep(3000);
		
		wp.selectExperience("Student");
		Thread.sleep(2000);
		wp.clickGetInTouchCheckbox();
		Thread.sleep(2000);
		
//		wp.submitWebinarRegistration();
 
//        BaseSteps.tearDown();
    }
}