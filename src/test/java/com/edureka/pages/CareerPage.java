package com.edureka.pages;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CareerPage extends BasePage {
	
	WebDriver driver;
	
	public CareerPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(className="openpostitle") WebElement jobOpenings;
	
	@FindAll({
		@FindBy(xpath = "//ul[@class='openposslist']//a[text()='Associate Research Analyst']"),
		@FindBy(linkText = "Associate Research Analyst"),
		@FindBy(xpath = "//p[contains(text(),'Associate Research Analyst')]"),
		@FindBy(css = "ul.openposslist li p a[href='/openpositions/2/48']"), // Full selector for the list
	    @FindBy(xpath = "//ul[@class='openposslist']/li") // All <li> items inside the list
	})
	List<WebElement> jobLinks;
	
	@FindAll({
		@FindBy(id="jobapplicantname"),
		@FindBy(className="inputapply"),
		@FindBy(name="appname"),
	}) WebElement nameInput;
	
	@FindAll({
		@FindBy(id="jobapplicantemail"),
		@FindBy(className="inputapply"),
		@FindBy(name="appemail"),
	}) WebElement emailInput;
	
	@FindAll({
		@FindBy(id="jobapplicantmob"),
		@FindBy(className="inputapply"),
		@FindBy(name="appmobile"),
	}) WebElement phoneInput;
	
	
	public void navigateToJobOpenings() {
		scrollIntoView(jobOpenings);
	}
	
	// navigate to specific job and click
	public void openJobListing() {
		for (WebElement jobLink : jobLinks) {
			if (jobLink.getText().equalsIgnoreCase("Associate Research Analyst")) {
				clickElement(jobLink);
				break;
			}
		}
	}
	
	
	// apply for job
	public void applyForJob(String name, String email, String phone) {
		nameInput.sendKeys(name);
		emailInput.sendKeys(email);
		phoneInput.sendKeys(phone);
	}
	
	public void handleMultipleWindows(WebDriver driver) {
		this.driver = driver;
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();
		String parentWindow = iterator.next();
		String childWindow = iterator.next();
		driver.switchTo().window(childWindow);
		
	}
	
	
}
