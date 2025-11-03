package com.edureka.pages;


import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
	WebDriver driver;
	private final Actions actions;

	// WebElements
	@FindBy(xpath = "//button[contains(text(), 'Log in')]")
	WebElement login;
	@FindBy(xpath = "//ul[@class='courselist_list__LeKn6']/li/a[contains(text(), 'Webinars')]")
	WebElement webinarsLink;
	@FindBy(xpath = "//ul[@class='courselist_list__LeKn6']/li/a[contains(text(), 'Careers')]")
	WebElement careersLink;

	// Constructor
	public HomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);

	}

	// methods
	public void clicklogIn() {

		clickElement(login);
	}

	private void dismissOverlayIfAny() {
	    try {
	        // Attempt to click somewhere neutral on the page to dismiss overlays
	        actions.moveByOffset(5, 5).click().perform();
	        
	        // Optionally, wait briefly for overlay to disappear
	        Thread.sleep(500); // Replace with explicit wait if you have a known overlay locator
	    } catch (MoveTargetOutOfBoundsException ignored) {
	        // Cursor offset was invalid, ignore
	    } catch (Exception e) {
	        // Log for debugging if needed
	        System.out.println("Overlay dismissal attempt failed: " + e.getMessage());
	    }
	}

	// click webinars with robust handling
	public void clickWebinars() {
		try {
			scrollIntoView(webinarsLink);

			// If overlays/popups exist, click body to dismiss
			dismissOverlayIfAny();

			clickElement(webinarsLink); // internally should do waitClickable(...) if possible
		} catch (StaleElementReferenceException e) {
			// recover: re-scroll and re-try
			scrollIntoView(webinarsLink);
			clickElement(webinarsLink);
		} catch (ElementClickInterceptedException e) {
			js.executeScript("arguments[0].click();", webinarsLink);
		} catch (JavascriptException e) {
			// optional: log and bubble up or try a final reattempt
			System.out.println("JavascriptException encountered while clicking webinars link: " + e.getMessage());
		}
	}

	public boolean isWebinarDisplayed() {
		return webinarsLink.isDisplayed();
	}
	
	public void clickCareers() {
		try {
			scrollIntoView(careersLink);

			// If overlays/popups exist, click body to dismiss
			dismissOverlayIfAny();

			clickElement(careersLink); // internally should do waitClickable(...) if possible
		} catch (StaleElementReferenceException e) {
			// recover: re-scroll and re-try
			scrollIntoView(careersLink);
			clickElement(careersLink);
		} catch (ElementClickInterceptedException e) {
			js.executeScript("arguments[0].click();", careersLink);
		} catch (JavascriptException e) {
			// optional: log and bubble up or try a final reattempt
			System.out.println("JavascriptException encountered while clicking careers link: " + e.getMessage());
		}
	}
	
	public boolean isCareerDisplayed() {
		return careersLink.isDisplayed();
	}

}
