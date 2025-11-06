package com.edureka.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class CareerPage extends BasePage {

	// ========== CONFIGURATION ==========
	String filePath = "C:\\Users\\zvijayfa\\eclipse-workspace\\edureka-automation-testing\\src\\test\\resources\\PropertyData\\ZaneResume.pdf";
	File file = new File(filePath);

	// ========== CONSTRUCTOR ==========
	public CareerPage(WebDriver driver) {
		super(driver);
	}

	// ========== WEB ELEMENTS ==========

	@FindBy(className = "openpostitle")
	WebElement jobOpenings;

	@FindAll({ @FindBy(xpath = "//ul[@class='openposslist']//a[text()='Associate Research Analyst']"),
			@FindBy(linkText = "Associate Research Analyst"),
			@FindBy(xpath = "//p[contains(text(),'Associate Research Analyst')]"),
			@FindBy(css = "ul.openposslist li p a[href='/openpositions/2/48']"),
			@FindBy(xpath = "//ul[@class='openposslist']/li") })
	List<WebElement> jobLinks;

	@FindAll({ @FindBy(id = "jobapplicantname"), @FindBy(name = "appname"), @FindBy(className = "inputapply") })
	WebElement nameInput;

	@FindAll({ @FindBy(id = "jobapplicantemail"), @FindBy(name = "appemail"), @FindBy(className = "inputapply") })
	WebElement emailInput;

	@FindAll({ @FindBy(id = "jobapplicantmob"), @FindBy(name = "appmobile"), @FindBy(className = "inputapply") })
	WebElement phoneInput;

	@FindBys({ @FindBy(className = "fileupload"), @FindBy(xpath = "//input[@type='file']") })
	WebElement uploadInput;

	@FindAll({
			@FindBy(xpath="/html/body/section[2]/article/div[2]/div/div/div/div[2]/section[1]/form/div/div/span[2]/input"),
			@FindBy(className = "fileupload-new"), 
			@FindBy(css = "input#jobapplicantresume"),
			@FindBy(id = "jobapplicantresume")
	}) WebElement uploadButton;
	
	@FindBy(id = "jobapplicantresume") public WebElement resumeUploadInput;

	@FindAll({ @FindBy(id = "applicsubjob"), @FindBy(css = "button.btn-block.appsub"),
			@FindBy(xpath = "//button[contains(text(),'Submit')]") })
	List<WebElement> submitButtons;
	
	

	


	// ========== METHODS ==========

	/** Scroll and wait for job openings section */
	public void navigateToJobOpenings() {
		scrollToElement(jobOpenings);
		waitUntilVisible(jobOpenings);
		System.out.println("Navigated to Job Openings section");
	}

	/** Click on the desired job listing */
	public void openJobListing() {
		for (WebElement jobLink : jobLinks) {
			if (jobLink.getText().equalsIgnoreCase("Associate Research Analyst")) {
				clickElement(jobLink);
				System.out.println("Opened Associate Research Analyst job listing");
				break;
			}
		}
	}

	/** Switch to child window (job application popup) */
	public void handleMultipleWindows(WebDriver driver) {
		String parent = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(parent)) {
				driver.switchTo().window(handle);
				System.out.println("Switched to child window: " + driver.getTitle());
				break;
			}
		}
	}

	/** Fill job application form */
	public void applyForJob(String name, String email, String phone) {
		explicitWait(10, nameInput);
		nameInput.sendKeys(name);
		emailInput.sendKeys(email);
		phoneInput.sendKeys(phone);
		resumeUploadInput.sendKeys(filePath);
		uploadButton.sendKeys(filePath);
		System.out.println("Job application form filled successfully");
	}

	/** Try file upload using sendKeys; fallback to Robot if hidden */
	public void uploadFile() throws AWTException {
		System.out.println("=== Attempting Resume Upload ===");
		System.out.println("File exists: " + file.exists());

		try {
			waitUntilVisible(uploadButton);
			uploadButton.sendKeys(filePath);
			Thread.sleep(2000);

			// Try sendKeys first
			//uploadInput.sendKeys(filePath);
			js.executeScript("arguments[0].value='C:\\Users\\zvijayfa\\eclipse-workspace\\edureka-automation-testing\\src\\test\\resources\\PropertyData\\ZaneResume.pdf';", uploadInput);
			System.out.println("File uploaded successfully via sendKeys");
		} catch (Exception e) {
			System.out.println("sendKeys failed. Trying Robot class instead...");
			uploadFileUsingRobot(filePath);
		}
	}

	/** Robot fallback (for native OS dialogs) */
	public void uploadFileUsingRobot(String path) throws AWTException {
	    
	    Robot robot = new Robot();
	    robot.delay(1000);

	    StringSelection selection = new StringSelection(path);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.delay(500);

	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    System.out.println("File uploaded successfully using Robot class");
	}


	/** Upload resume and submit the job application */
	public void uploadResumeAndSubmit() throws AWTException {
		System.out.println("\n=== Starting Resume Upload & Submit Process ===");
		uploadFile();

		// Wait for upload
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Submit
		for (WebElement submitButton : submitButtons) {
			if (submitButton.isDisplayed() && submitButton.isEnabled()) {
				explicitWait(5, submitButton);
				clickElement(submitButton);
				System.out.println("Application submitted successfully!");
				break;
			}
		}
	}

	/** Print URL and Page Title for debug */
	public void printPageDetails() {
		System.out.println("URL: " + getCurrentUrl());
		System.out.println("Title: " + getPageTitle());
	}
	
}
