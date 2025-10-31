package com.edureka.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	
	//WebElements
	@FindBy(xpath = "//button[contains(text(), 'Log in')]") WebElement login;
	@FindBy(xpath = "//ul[@class='courselist_list__LeKn6']/li/a[contains(text(), 'Webinars')]")
	WebElement webinarsLink;
	
	//Constructor
	public HomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this); 
		
	}
	
	
	//methods
	public void clicklogIn() {

		login.click();
	}
	
	public void clickWebinar() {
	    try {
	        // Scroll into view
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webinarsLink);
	        Thread.sleep(1000);

	        // Dismiss pop-up by clicking outside
	        new Actions(driver).moveByOffset(10, 10).click().perform();
	        Thread.sleep(500);

	        // Click using JavaScript
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webinarsLink);
	    } catch (Exception e) {
	        System.out.println("Error clicking Webinars link: " + e.getMessage());
	    }
	}
	
	public boolean isWebinarDisplayed() {
	    return webinarsLink.isDisplayed();
	}
	

}
