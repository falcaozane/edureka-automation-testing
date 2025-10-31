package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.edureka.pages.BasePage;
import com.edureka.setup.BaseSteps;

public class DummyTest {

    WebDriver driver;
    BasePage basePage;

    @Test
    public void sampleTest() {
        driver = BaseSteps.initializeDriver(); // Reads browser from properties
        basePage = new BasePage(driver);

        System.out.println("Page title is: " + driver.getTitle());

//        BaseSteps.tearDown();
    }
}