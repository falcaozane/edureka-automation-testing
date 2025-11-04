package com.edureka.pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edureka.setup.BaseSteps;
import com.edureka.utils.ScreenShotTest;


public class BasePage extends BaseSteps {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void loadURL(String url) {
        driver.get(url);
    }

    public void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public void clickElement(WebElement element) {
        try {
			waitUntilClickable(element).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", element);
		}
    }
    
    public void implicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	}
    
    public void explicitWait(int seconds, WebElement element) {
    	WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
    	customWait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public void takeScreenshot(String screenshotName) {
        ScreenShotTest.capture(driver, screenshotName);
    }
    
    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}