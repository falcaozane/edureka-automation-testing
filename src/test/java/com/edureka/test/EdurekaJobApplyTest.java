package com.edureka.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EdurekaJobApplyTest {
    public static void main(String[] args) throws InterruptedException {
        // ====== CONFIGURATION ======
        String url = "https://www.edureka.co/openpositions/2/48";
        String name = "Abc";
        String email = "abc@gmail.com";
        String mobile = "9999999999";
        String filePath = "C:\\Users\\falca\\git\\edureka-automation-testing\\src\\test\\resources\\PropertyData\\ZaneResume.pdf"; // change if needed

        // ====== SETUP DRIVER ======
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        Thread.sleep(8000);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // ====== OPEN PAGE ======
            driver.get(url);

            // ====== FILL FORM FIELDS ======
            driver.findElement(By.id("jobapplicantname")).sendKeys(name);
            driver.findElement(By.id("jobapplicantemail")).sendKeys(email);
            driver.findElement(By.id("jobapplicantmob")).sendKeys(mobile);
            
            driver.findElement(By.className("fileupload-new")).click();

            // ====== UPLOAD RESUME ======
            WebElement uploadInput = driver.findElement(By.id("jobapplicantresume"));
            uploadInput.sendKeys(filePath);
            System.out.println("✅ Resume uploaded successfully.");

            // ====== WAIT FOR PREVIEW TO APPEAR ======
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".fileupload-preview")));
            System.out.println("✅ File preview visible.");

            // ====== OPTIONAL: SUBMIT FORM ======
            // WebElement submitBtn = driver.findElement(By.xpath("//button[contains(text(), 'Submit')]"));
            // submitBtn.click();
            // System.out.println("✅ Form submitted successfully.");

            Thread.sleep(3000); // just for demo to see the upload preview

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit();
            System.out.println("🔚 Test completed. Browser closed.");
        }
    }
}
