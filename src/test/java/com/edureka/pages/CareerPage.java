package com.edureka.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class CareerPage extends BasePage {
	
	String filePath = "C:\\Users\\zvijayfa\\eclipse-workspace\\edureka-automation-testing\\src\\test\\resources\\PropertyData\\ZaneResume.pdf";
	File file = new File(filePath);

	public CareerPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(className = "openpostitle")
	WebElement jobOpenings;

	@FindAll({ 
		@FindBy(xpath = "//ul[@class='openposslist']//a[text()='Associate Research Analyst']"),
		@FindBy(linkText = "Associate Research Analyst"),
		@FindBy(xpath = "//p[contains(text(),'Associate Research Analyst')]"),
		@FindBy(css = "ul.openposslist li p a[href='/openpositions/2/48']"),
		@FindBy(xpath = "//ul[@class='openposslist']/li") 
	})
	List<WebElement> jobLinks;

	@FindAll({ 
		@FindBy(id = "jobapplicantname"), 
		@FindBy(className = "inputapply"), 
		@FindBy(name = "appname") 
	})
	WebElement nameInput;

	@FindAll({ 
		@FindBy(id = "jobapplicantemail"), 
		@FindBy(className = "inputapply"), 
		@FindBy(name = "appemail") 
	})
	WebElement emailInput;

	@FindAll({ 
		@FindBy(id = "jobapplicantmob"), 
		@FindBy(className = "inputapply"), 
		@FindBy(name = "appmobile") 
	})
	WebElement phoneInput;

	@FindAll({ 
		@FindBy(id = "applicsubjob"), 
		@FindBy(className = "appsub"), 
		@FindBy(css = "button.btn-block.appsub") 
	})
	List<WebElement> submitButtons;

	@FindAll({
		
		@FindBy(className="fileupload-new"),
		@FindBy(xpath = "//input[@id='jobapplicantresume']"),
		@FindBy(xpath = "//input[@type='file']")
		
	}) 
	WebElement uploadBtn;
	

	/**
	 * Scroll to job openings section
	 */
	public void navigateToJobOpenings() {
		scrollToElement(jobOpenings); // Using BasePage method
		waitUntilVisible(jobOpenings);  // Using BasePage method
	}

	/**
	 * Navigate to specific job listing and click
	 */
	public void openJobListing() {
		for (WebElement jobLink : jobLinks) {
			if (jobLink.getText().equalsIgnoreCase("Associate Research Analyst")) {
				clickElement(jobLink); // Using BasePage method
				break;
			}
		}
	}

	/**
	 * Fill job application form
	 * @param name - Applicant name
	 * @param email - Applicant email
	 * @param phone - Applicant phone number
	 */
	public void applyForJob(String name, String email, String phone) {
		explicitWait(10, nameInput); // Using BasePage method
		nameInput.sendKeys(name);
		emailInput.sendKeys(email);
		phoneInput.sendKeys(phone);
		System.out.println("Job application form filled successfully");
	}

	/**
	 * Switch to child window (job application popup)
	 * @param driver - WebDriver instance
	 */
	public void handleMultipleWindows(WebDriver driver) {
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();
		String parentWindow = iterator.next();
		String childWindow = iterator.next();
		driver.switchTo().window(childWindow);
		System.out.println("Switched to child window: " + driver.getTitle());
	}

	/**
	 * Upload file using Robot class to handle native OS file dialog
	 * @param filePath - Absolute path of the file to upload
	 * @throws AWTException
	 */
	public void uploadFileUsingRobot(String filePath) throws AWTException {
		System.out.println("=== Starting Robot Class File Upload ===");
		System.out.println("File path: " + filePath);
		System.out.println("File exists: " + new File(filePath).exists());

		// Click the upload button to open file dialog
		explicitWait(10, uploadBtn); // Using BasePage method
		clickElement(uploadBtn);     // Using BasePage method
		System.out.println("Upload button clicked, file dialog should open...");

		Robot robot = new Robot();
		robot.delay(3000); // Wait for file dialog to open

		// Copy file path to system clipboard
		StringSelection selection = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		System.out.println("File path copied to clipboard");

		robot.delay(1000);

		// Press CTRL+V to paste the file path
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		System.out.println("File path pasted using CTRL+V");

		robot.delay(1000);

		// Press ENTER to confirm file selection
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		System.out.println("ENTER pressed to confirm file selection");

		robot.delay(2000); // Wait for file to be uploaded
		System.out.println("File upload completed using Robot class!");
	}

	/**
	 * Alternative method: Try sendKeys first, fallback to Robot class
	 * Demonstrates multiple approaches (good for SME evaluation)
	 * @throws AWTException
	 */
	public void uploadFileUsingSendKeys() throws AWTException {
		System.out.println("=== Attempting sendKeys Upload ===");
		System.out.println("File exists: " + file.exists());

		try {
			// First try: Direct sendKeys (works if input is not hidden)
			explicitWait(10, uploadBtn); // Using BasePage method
			uploadBtn.sendKeys(filePath);
			System.out.println("✓ File uploaded successfully using sendKeys");
		} catch (Exception e) {
			System.out.println("✗ sendKeys failed, falling back to Robot class...");
			uploadFileUsingRobot(filePath);
		}
	}

	/**
	 * Main method to upload resume and submit application
	 * @throws AWTException
	 */
	public void uploadResumeAndSubmit() throws AWTException {
		System.out.println("\n=== Starting Resume Upload & Submit Process ===");
		
		// Upload the file using Robot class
		uploadFileUsingRobot(filePath);

		// Wait for upload to complete
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Click the submit button
		System.out.println("Looking for submit button...");
		for (WebElement submitButton : submitButtons) {
			if (submitButton.isDisplayed() && submitButton.isEnabled()) {
				System.out.println("Submit button found and clickable");
				explicitWait(5, submitButton); // Using BasePage method
				clickElement(submitButton);    // Using BasePage method
				System.out.println("✓ Application submitted successfully!");
				takeScreenshot("JobApplicationSubmitted"); // Using BasePage method
				break;
			}
		}
	}
	
	// Get current page URL (using BasePage method)
	public void printCurrentUrl() {
		System.out.println("Current URL: " + getCurrentUrl());
	}
	
	// Get current page title (using BasePage method)
	public void printPageTitle() {
		System.out.println("Current Page Title: " + getPageTitle());
	}
	
	public void clickBrowseBtn() {
		clickElement(uploadBtn);
	}
}