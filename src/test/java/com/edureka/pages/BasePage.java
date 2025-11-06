package com.edureka.pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.edureka.utils.ScreenShotTest;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
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
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForTitleContains(String text) {
        wait.until(ExpectedConditions.titleContains(text));
    }

    public void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }

    public void waitForPageLoadComplete() {
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollDownByPixels() {
        js.executeScript("window.scrollBy(0,1000);");
    }

    public void scrollDownByPixels(int pixels) {
        js.executeScript("window.scrollBy(0," + pixels + ");");
    }

    public void scrollDownToFooter() {
        long height = (long) js.executeScript("return document.body.scrollHeight");
        js.executeScript("window.scrollTo(0, " + height + ");");
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // ==================== SCREENSHOT METHODS ====================

    
    public String takeScreenshot(String screenshotName) {
        return ScreenShotTest.captureScreenshot(driver, screenshotName);
    }



    /**
     * Captures a screenshot of a specific WebElement
     * @param element WebElement to capture
     * @param elementName Name to identify the element
     * @return String path of the captured screenshot
     */
    public String takeElementScreenshot(WebElement element, String elementName) {
        return ScreenShotTest.captureElementScreenshot(element, elementName);
    }

    
}