package com.edureka.test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EdurekaJobApplyTest {

    // ========== CONFIGURATION ==========
    static String url = "https://www.edureka.co/openpositions/2/48";
    static String name = "Abc";
    static String email = "abc@gmail.com";
    static String mobile = "9999999999";
    static String filePath = "C:\\Users\\zvijayfa\\eclipse-workspace\\edureka-automation-testing\\src\\test\\resources\\PropertyData\\ZaneResume.pdf";

    // ========== PAGE OBJECT MODEL (POM) ==========
    public static class JobApplyPage {
        WebDriver driver;
        WebDriverWait wait;

        // ==== FORM FIELDS ====
        @FindBy(id = "jobapplicantname")
        WebElement nameField;

        @FindBy(id = "jobapplicantemail")
        WebElement emailField;

        @FindBy(id = "jobapplicantmob")
        WebElement mobileField;

        @FindBy(id = "jobapplicantresume")
        WebElement resumeUpload;

        // ==== MULTIPLE ELEMENT EXAMPLES ====
        @FindBys({
            @FindBy(className = "fileupload"),
            @FindBy(tagName = "span")
        })
        List<WebElement> fileUploadButtons;

        @FindAll({
            @FindBy(className = "fileupload-new"),
            @FindBy(css = ".btn.btn-file")
        })
        List<WebElement> uploadOptions;

        @FindBy(css = ".fileupload-preview")
        WebElement filePreview;

        @FindBy(xpath = "//button[contains(text(),'Submit')]")
        WebElement submitButton;
        

        // ==== CONSTRUCTOR ====
        public JobApplyPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            PageFactory.initElements(driver, this);
        }

        // ==== METHODS ====
        public void fillForm(String name, String email, String mobile) {
            nameField.sendKeys(name);
            emailField.sendKeys(email);
            mobileField.sendKeys(mobile);
        }

        public void uploadResume(String path) {
            // click using FindAll/FindBys if present
            if (!uploadOptions.isEmpty()) {
                uploadOptions.get(0).click();
            }
            resumeUpload.sendKeys(path);
        }

        public void waitForPreview() {
            wait.until(ExpectedConditions.visibilityOf(filePreview));
            System.out.println("✅ File preview is visible.");
        }

        public void submitForm() {
            if (submitButton.isDisplayed()) {
                submitButton.click();
                System.out.println("✅ Form submitted successfully.");
            }
        }
    }

    // ========== MAIN TEST ==========
    public static void main(String[] args) throws InterruptedException {
        System.out.println("🚀 Starting Edureka Job Apply Test...");

        // Setup WebDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // Load the page
            driver.get(url);
            Thread.sleep(4000); // allow dynamic elements to load

            // Initialize page
            JobApplyPage page = new JobApplyPage(driver);

            // Fill form details
            page.fillForm(name, email, mobile);
            System.out.println("Form fields filled successfully.");

            // Upload resume
            page.uploadResume(filePath);
            System.out.println("Resume uploaded successfully.");

            // Wait for preview
            page.waitForPreview();

            // Optionally submit
            // page.submitForm();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Test completed. Closing browser...");
            //driver.quit();
        }
    }
}
