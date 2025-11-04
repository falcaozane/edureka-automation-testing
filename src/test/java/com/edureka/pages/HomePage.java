package com.edureka.pages;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.edureka.parameter.ObjectReader;

/**
 * HomePage - Enhanced with multiple locator strategies
 * Demonstrates: @FindBy, @FindAll, and all 7 locator types
 */
public class HomePage extends BasePage {
    
    WebDriver driver;
    private final Actions actions;
    
    // ==========================================
    // LOGIN BUTTON - Multiple Locator Strategies
    // ==========================================
    @FindAll({
        @FindBy(xpath = "//button[contains(text(), 'Log in')]"),
        @FindBy(css = "button[class*='login']"),
        @FindBy(linkText = "Log in"),
        @FindBy(partialLinkText = "Log"),
        @FindBy(className = "login-btn"),
        @FindBy(id = "loginBtn")
    })
    WebElement loginButton;
    
    // ==========================================
    // WEBINARS LINK - Multiple Locator Strategies
    // ==========================================
    @FindAll({
        @FindBy(xpath = "//ul[@class='courselist_list__LeKn6']/li/a[contains(text(), 'Webinars')]"),
        @FindBy(linkText = "Webinars"),
        @FindBy(partialLinkText = "Webinar"),
        @FindBy(css = "ul.courselist_list__LeKn6 li a[href*='webinar']")
    })
    WebElement webinarsLink;
    
    // ==========================================
    // CAREERS LINK - Multiple Locator Strategies
    // ==========================================
    @FindAll({
    	@FindBy(xpath = "//a[@href='/careers/']"),
    	@FindBy(xpath = "//a[text()='Careers']"),
        @FindBy(xpath = "//ul[@class='courselist_list__LeKn6']/li/a[contains(text(), 'Careers')]"),
        @FindBy(linkText = "Careers"),
        @FindBy(partialLinkText = "Careers"),
        @FindBy(css = "ul.courselist_list__LeKn6 li a[href*='career']"),
        
    })
    WebElement careersLink;
    
    // ==========================================
    // LOGO - Using different locator types
    // ==========================================
    @FindAll({
        @FindBy(className = "edureka-logo"),
        @FindBy(css = "img.edureka-logo"),
        @FindBy(xpath = "//img[contains(@class,'edureka-logo')]")
    })
    WebElement logo;
    
    // ==========================================
    // SEARCH BOX - Demonstrating all locator types
    // ==========================================
    @FindAll({
        @FindBy(id = "search-inp"),
        @FindBy(name = "search"),
        @FindBy(className = "search-input"),
        @FindBy(css = "input#search-inp"),
        @FindBy(xpath = "//input[@id='search-inp']")
    })
    WebElement searchBox;
    
    // ==========================================
    // NAVIGATION MENU
    // ==========================================
    @FindAll({
        @FindBy(className = "courselist_list__LeKn6"),
        @FindBy(css = "ul.courselist_list__LeKn6"),
        @FindBy(xpath = "//ul[@class='courselist_list__LeKn6']")
    })
    WebElement navMenu;
    
    
    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
    
    
    // ==========================================
    // METHODS
    // ==========================================
    
    /**
     * Click Login button
     */
    public void clicklogIn() {
        clickElement(loginButton);
    }
    
    /**
     * Dismiss overlay/popup if present
     */
    private void dismissOverlayIfAny() {
        try {
            // Attempt to click somewhere neutral on the page to dismiss overlays
            actions.moveByOffset(5, 5).click().perform();
            
            // Optionally, wait briefly for overlay to disappear
            Thread.sleep(500);
        } catch (MoveTargetOutOfBoundsException ignored) {
            // Cursor offset was invalid, ignore
        } catch (Exception e) {
            // Log for debugging if needed
            System.out.println("Overlay dismissal attempt failed: " + e.getMessage());
        }
    }
    
    /**
     * Click Webinars link with robust handling
     */
    public void clickWebinars() {
        try {
            scrollIntoView(webinarsLink);
            dismissOverlayIfAny();
            clickElement(webinarsLink);
        } catch (StaleElementReferenceException e) {
            // Recover: re-scroll and re-try
            scrollIntoView(webinarsLink);
            clickElement(webinarsLink);
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", webinarsLink);
        } catch (JavascriptException e) {
            System.out.println("JavascriptException encountered while clicking webinars link: " + e.getMessage());
        }
    }
    
    /**
     * Check if Webinar link is displayed
     */
    public boolean isWebinarDisplayed() {
        try {
            return webinarsLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click Careers link with robust handling
     */
    public void clickCareers() {
        try {
            scrollIntoView(careersLink);
            dismissOverlayIfAny();
            clickElement(careersLink);
        } catch (StaleElementReferenceException e) {
            // Recover: re-scroll and re-try
            scrollIntoView(careersLink);
            clickElement(careersLink);
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", careersLink);
        } catch (JavascriptException e) {
            System.out.println("JavascriptException encountered while clicking careers link: " + e.getMessage());
        }
    }
    
    /**
     * Check if Career link is displayed
     */
    public boolean isCareerDisplayed() {
        try {
            return careersLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if logo is displayed (for page load validation)
     */
    public boolean isLogoDisplayed() {
        try {
            return logo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Search for a course/topic
     */
    public void searchCourse(String courseName) {
        try {
            searchBox.clear();
            searchBox.sendKeys(courseName);
        } catch (Exception e) {
            System.out.println("Search box interaction failed: " + e.getMessage());
        }
    }
    
    /**
     * Check if navigation menu is displayed
     */
    public boolean isNavMenuDisplayed() {
        try {
            return navMenu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    
    // ==========================================
    // METHODS USING ObjectReader (Centralized Locators)
    // ==========================================
    
    /**
     * Click Login using ObjectReader
     * Demonstrates reading locator from Object.properties
     */
    public void clickLoginUsingObjectReader() {
        try {
            WebElement loginBtn = driver.findElement(
                ObjectReader.getByLocator("homePage.loginButton.xpath")
            );
            clickElement(loginBtn);
        } catch (Exception e) {
            System.out.println("Failed to click login using ObjectReader: " + e.getMessage());
        }
    }
    
    /**
     * Click Webinars using ObjectReader
     */
    public void clickWebinarsUsingObjectReader() {
        try {
            WebElement webinars = driver.findElement(
                ObjectReader.getByLocator("homePage.webinarsLink.linkText")
            );
            clickElement(webinars);
        } catch (Exception e) {
            System.out.println("Failed to click webinars using ObjectReader: " + e.getMessage());
        }
    }
    
    /**
     * Click Careers using ObjectReader
     */
    public void clickCareersUsingObjectReader() {
        try {
            WebElement careers = driver.findElement(
                ObjectReader.getByLocator("homePage.careersLink.css")
            );
            clickElement(careers);
        } catch (Exception e) {
            System.out.println("Failed to click careers using ObjectReader: " + e.getMessage());
        }
    }
}