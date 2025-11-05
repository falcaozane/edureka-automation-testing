package com.edureka.test;

import org.openqa.selenium.WebDriver;
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
    }

    @Test
    public void loginAndNavigateToWebinars() throws Throwable {
        basePage = new BasePage(driver);
        hp = new HomePage(driver);
        Thread.sleep(3000);
        hp.clicklogIn();

        lp = new LoginPage(driver);
        Thread.sleep(3000);
        lp.logIn(); // still using data.properties

        wp = new WebinarPage(driver);
        System.out.println("Page title is: " + driver.getTitle());

        while (true) {
            BaseSteps.scrollDownByPixels();
            Thread.sleep(2000);
            if (hp.isWebinarDisplayed()) break;
        }

        hp.clickWebinars();
        System.out.println("Page title is: " + driver.getTitle());
    }

    @Test(dataProvider = "webinarSearchTerms", dataProviderClass = TestDataProvider.class, dependsOnMethods = "loginAndNavigateToWebinars")
    public void searchWebinarTest(String searchTerm) throws InterruptedException {
        wp.searchWebinar(searchTerm);
        Thread.sleep(3000);
        wp.clearWebinarSearch();
        Thread.sleep(1000);
    }
}