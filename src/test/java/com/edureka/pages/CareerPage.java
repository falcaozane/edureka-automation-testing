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

	// PRIMARY UPLOAD INPUT - This is the one that works!
	@FindBy(id = "jobapplicantresume")
	WebElement resumeUploadInput;

	// Alternative locators using @FindBys and @FindAll
	@FindBys({ @FindBy(id = "jobapplicantresume"), @FindBy(xpath = "//input[@type='file']") })
	WebElement uploadInputFindBys;

	@FindAll({ @FindBy(id = "jobapplicantresume"), @FindBy(css = "input#jobapplicantresume"),
			@FindBy(xpath = "//input[@type='file' and @id='jobapplicantresume']"),
			@FindBy(className = "fileupload-new") })
	List<WebElement> uploadInputOptions;

	@FindAll({ @FindBy(id = "applicsubjob"), @FindBy(css = "button.btn-block.appsub"),
			@FindBy(xpath = "//button[contains(text(),'Submit')]") })
	List<WebElement> submitButtons;

	// ========== METHODS ==========

	/** Scroll and wait for job openings section */
	public void navigateToJobOpenings() {
		scrollToElement(jobOpenings);
		waitUntilVisible(jobOpenings);
		System.out.println("✓ Navigated to Job Openings section");
	}

	/** Click on the desired job listing */
	public void openJobListing() {
		for (WebElement jobLink : jobLinks) {
			try {
				if (jobLink.getText().equalsIgnoreCase("Associate Research Analyst")) {
					clickElement(jobLink);
					System.out.println("✓ Clicked: Associate Research Analyst");
					break;
				}
			} catch (Exception e) {
				// Continue to next element if this one fails
				continue;
			}
		}
	}

	/** Switch to child window (job application popup) */
	public void handleMultipleWindows(WebDriver driver) {
		String parent = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();

		System.out.println("Total windows open: " + handles.size());

		for (String handle : handles) {
			if (!handle.equals(parent)) {
				driver.switchTo().window(handle);
				System.out.println("✓ Switched to: " + driver.getTitle());

				// Wait for page to fully load after switching
				waitForPageLoadComplete();
				implicitWait(3);
				break;
			}
		}
	}

	/** Fill job application form */
	public void applyForJob(String name, String email, String phone) {
		System.out.println("\n=== Filling Job Application Form ===");

		explicitWait(10, nameInput);
		nameInput.sendKeys(name);
		System.out.println("✓ Name entered: " + name);

		emailInput.sendKeys(email);
		System.out.println("✓ Email entered: " + email);

		phoneInput.sendKeys(phone);
		System.out.println("✓ Phone entered: " + phone);
		
		removeFocusFromPhoneField();
	}

	/**
	 * MAIN UPLOAD METHOD - Uses direct sendKeys
	 */
	/**
	 * MAIN UPLOAD METHOD - Enhanced with JavaScript approach
	 * 
	 * @throws AWTException
	 */
	public void uploadResume() throws AWTException {
		System.out.println("\n=== Starting Resume Upload ===");
		System.out.println("File path: " + filePath);
		System.out.println("File exists: " + file.exists());

		try {
			
			removeFocusFromPhoneField();
			
			// Wait for the upload input
			explicitWait(10, resumeUploadInput);

			// Scroll to element
			scrollIntoView(resumeUploadInput);
			makeUploadInputVisible();
			Thread.sleep(1000);

			// METHOD 1: Try direct sendKeys first
			System.out.println("Attempting Method 1: Direct sendKeys...");
			resumeUploadInput.sendKeys(filePath);
			Thread.sleep(2000);

			// Verify upload by checking the value attribute
			String uploadedValue = resumeUploadInput.getAttribute("value");
			System.out.println("Uploaded value: " + uploadedValue);

			if (uploadedValue != null && !uploadedValue.isEmpty()) {
				System.out.println("✓ Resume uploaded successfully using sendKeys");

				// Take screenshot to verify upload
				takeScreenshot("FileUploadSuccess");
			} else {
				throw new Exception("sendKeys did not set the file path");
			}

		} catch (Exception e) {
			System.err.println("✗ sendKeys upload failed: " + e.getMessage());
			System.out.println("Attempting Method 2: JavaScript injection...");

			try {
				// METHOD 2: JavaScript approach
				js.executeScript("arguments[0].style.display = 'block';", resumeUploadInput);
				js.executeScript("arguments[0].style.visibility = 'visible';", resumeUploadInput);
				Thread.sleep(500);

				resumeUploadInput.sendKeys(filePath);
				Thread.sleep(2000);

				String jsValue = (String) js.executeScript("return arguments[0].value;", resumeUploadInput);
				System.out.println("JS Value: " + jsValue);

				if (jsValue != null && !jsValue.isEmpty()) {
					System.out.println("✓ Resume uploaded using JavaScript method");
					takeScreenshot("FileUploadSuccessJS");
				} else {
					throw new Exception("JavaScript method also failed");
				}

			} catch (Exception jsException) {
				System.err.println("✗ JavaScript method failed: " + jsException.getMessage());
				System.out.println("Attempting Method 3: Robot class...");

				try {
					uploadFileUsingRobot(filePath);
				} catch (AWTException robotException) {
					System.err.println("✗ All upload methods failed!");
					robotException.printStackTrace();
					throw robotException;
				}
			}
		}
	}

	/**
	 * Alternative upload using @FindAll (demonstrates multiple locator strategies)
	 */
	public void uploadResumeUsingFindAll() {
		System.out.println("\n=== Attempting Upload with FindAll Locators ===");

		try {
			// Try each locator from @FindAll until one works
			for (int i = 0; i < uploadInputOptions.size(); i++) {
				try {
					WebElement uploadOption = uploadInputOptions.get(i);
					if (uploadOption.isDisplayed() || uploadOption.isEnabled()) {
						uploadOption.sendKeys(filePath);
						System.out.println("✓ Upload successful using FindAll option #" + i);
						return;
					}
				} catch (Exception e) {
					// Try next option
					continue;
				}
			}

			// If all options failed
			System.out.println("All FindAll options failed, using Robot class...");
			uploadFileUsingRobot(filePath);

		} catch (Exception e) {
			System.err.println("✗ Upload failed: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Remove focus from phone field to prevent file path being entered there
	 */
	private void removeFocusFromPhoneField() {
		try {
			// Method 1: Click on a neutral element (like the form itself or page body)
			js.executeScript("document.body.click();");
			System.out.println("✓ Focus removed from phone field (body click)");
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("Could not remove focus using body click: " + e.getMessage());
			
			// Method 2: Use blur() JavaScript
			try {
				js.executeScript("arguments[0].blur();", phoneInput);
				System.out.println("✓ Focus removed using blur()");
				Thread.sleep(500);
			} catch (Exception blurException) {
				System.out.println("Could not blur phone field: " + blurException.getMessage());
			}
		}
	}

	/**
	 * /** Robot class with improved timing and error handling
	 */
	public void uploadFileUsingRobot(String path) throws AWTException {
		System.out.println("=== Using Robot Class for Upload ===");

		try {
			// Click the browse button or upload input to open dialog
			scrollIntoView(resumeUploadInput);
			Thread.sleep(500);

			// Try clicking with JavaScript (more reliable)
			js.executeScript("arguments[0].click();", resumeUploadInput);
			System.out.println("✓ Upload dialog triggered");

			// CRITICAL: Wait longer for dialog to open
			Thread.sleep(3000);

			Robot robot = new Robot();

			// Copy file path to clipboard
			StringSelection selection = new StringSelection(path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
			System.out.println("✓ File path copied to clipboard: " + path);

			robot.delay(1000);

			// Select all in file name field (clear any existing text)
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(500);

			// Paste file path
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("✓ File path pasted");

			robot.delay(1500);

			// Press ENTER to confirm
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("✓ Upload confirmed");

			robot.delay(2000);

			// Verify upload
			Thread.sleep(1000);
			String uploadedValue = resumeUploadInput.getAttribute("value");
			System.out.println("Uploaded file name: " + uploadedValue);

		} catch (Exception e) {
			System.err.println("✗ Robot class upload failed: " + e.getMessage());
			throw new AWTException("Robot upload failed: " + e.getMessage());
		}
	}

	/**
	 * Complete upload and submit flow
	 */
	public void uploadResumeAndSubmit() throws AWTException {
		System.out.println("\n========== RESUME UPLOAD & SUBMIT ==========");

		// Upload the resume (using the working sendKeys approach)
		uploadResume();

		// Wait for upload to complete
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Take screenshot before submission
		takeScreenshot("BeforeJobSubmission");

		// Find and click submit button
		System.out.println("\nLooking for Submit button...");
		boolean submitted = false;

		for (WebElement submitButton : submitButtons) {
			try {
				if (submitButton.isDisplayed() && submitButton.isEnabled()) {
					System.out.println("✓ Submit button found!");

					explicitWait(5, submitButton);
					scrollIntoView(submitButton);
					clickElement(submitButton);

					System.out.println("✓ Application submitted successfully!");
					submitted = true;

					// Take screenshot after submission
					Thread.sleep(2000);
					takeScreenshot("JobApplicationSubmitted");
					break;
				}
			} catch (Exception e) {
				// Try next submit button
				continue;
			}
		}

		if (!submitted) {
			System.err.println("✗ Could not find enabled submit button!");
		}

		System.out.println("==========================================\n");
	}

	/**
	 * Make upload input visible if it's hidden
	 */
	public void makeUploadInputVisible() {
		try {
			// Remove any hiding CSS
			js.executeScript("arguments[0].style.display = 'block';" + "arguments[0].style.visibility = 'visible';"
					+ "arguments[0].style.opacity = '1';" + "arguments[0].style.height = 'auto';"
					+ "arguments[0].style.width = 'auto';", resumeUploadInput);
			System.out.println("✓ Upload input made visible");
		} catch (Exception e) {
			System.out.println("Could not modify input visibility: " + e.getMessage());
		}
	}

	/** Print URL and Page Title for debugging */
	public void printPageDetails() {
		System.out.println("Current URL: " + getCurrentUrl());
		System.out.println("Current Title: " + getPageTitle());
	}
}