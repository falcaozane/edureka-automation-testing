package com.edureka.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import com.edureka.parameter.ObjectReader;
import com.edureka.parameter.PropertyReader;

/**
 * LoginPage - Enhanced with multiple locator strategies
 * Demonstrates: @FindBy, @FindAll, @FindBys and all 7 locator types
 */
public class LoginPage extends BasePage {

    
    // ==========================================
    // EMAIL/USERNAME FIELD - Multiple Locators
    // ==========================================
    @FindAll({
        @FindBy(id = "loginFormEmail"),
        @FindBy(name = "email"),
        @FindBy(className = "form-control"),
        @FindBy(css = "input#loginFormEmail"),
        @FindBy(xpath = "//input[@id='loginFormEmail']")
    })
    WebElement emailField;
    
    // ==========================================
    // PASSWORD FIELD - Multiple Locators
    // ==========================================
    @FindAll({
        @FindBy(id = "loginFormPassword"),
        @FindBy(name = "password"),
        @FindBy(className = "form-control"),
        @FindBy(css = "input[name='password']"),
        @FindBy(xpath = "//input[@name='password']")
    })
    WebElement passwordField;
    
    // ==========================================
    // LOGIN BUTTON - Multiple Locators
    // ==========================================
    @FindAll({
        @FindBy(xpath = "//button[text()='LOG IN']"),
        @FindBy(css = "button[type='submit']"),
        @FindBy(className = "login-btn"),
        @FindBy(id = "loginBtn")
    })
    WebElement loginButton;
    
    // ==========================================
    // SIGN UP LINK - Using @FindBys (AND condition)
    // Demonstrates: Element must match ALL conditions
    // ==========================================
    @FindBys({
        @FindBy(tagName = "a"),
        @FindBy(partialLinkText = "Sign")
    })
    WebElement signUpLink;
    
    // ==========================================
    // FORGOT PASSWORD LINK - Multiple Locators
    // ==========================================
    @FindAll({
        @FindBy(linkText = "Forgot Password?"),
        @FindBy(partialLinkText = "Forgot"),
        @FindBy(css = "a[href*='forgot']"),
        @FindBy(xpath = "//a[contains(text(),'Forgot')]")
    })
    WebElement forgotPasswordLink;
    
    // ==========================================
    // REMEMBER ME CHECKBOX - Multiple Locators
    // ==========================================
    @FindAll({
        @FindBy(id = "rememberMe"),
        @FindBy(name = "remember"),
        @FindBy(css = "input[type='checkbox']"),
        @FindBy(xpath = "//input[@type='checkbox']")
    })
    WebElement rememberMeCheckbox;
    
    // ==========================================
    // LOGIN FORM - Using @FindBys
    // ==========================================
    @FindBys({
        @FindBy(tagName = "form"),
        @FindBy(id = "loginForm")
    })
    WebElement loginForm;
    
    // ==========================================
    // ERROR MESSAGE - Multiple Locators
    // ==========================================
    @FindAll({
        @FindBy(className = "error-message"),
        @FindBy(css = "div.error-message"),
        @FindBy(xpath = "//div[contains(@class,'error-message')]")
    })
    WebElement errorMessage;
    
    
    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;

    }
    
    
    // ==========================================
    // METHODS
    // ==========================================
    
    /**
     * Enter email in email field
     */
    public void enterEmail(String email) {
        try {
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("✓ Email entered: " + email);
        } catch (Exception e) {
            System.err.println("✗ Failed to enter email: " + e.getMessage());
        }
    }
    
    /**
     * Enter password in password field
     */
    public void enterPassword(String password) {
        try {
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("✓ Password entered");
        } catch (Exception e) {
            System.err.println("✗ Failed to enter password: " + e.getMessage());
        }
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        try {
            clickElement(loginButton);
            System.out.println("✓ Login button clicked");
        } catch (Exception e) {
            System.err.println("✗ Failed to click login button: " + e.getMessage());
        }
    }
    
    /**
     * Complete login with credentials from properties file
     */
    public void logIn() {
        String email = PropertyReader.getProperty("email");
        String password = PropertyReader.getProperty("password");
        
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
    
 
    
    /**
     * Click Sign Up link
     */
    public void clickSignUp() {
        try {
            waitUntilClickable(signUpLink);
            clickElement(signUpLink);
            System.out.println("✓ Sign Up link clicked");
        } catch (Exception e) {
            System.err.println("✗ Failed to click Sign Up: " + e.getMessage());
        }
    }
    

    
    
    
    /**
     * Check if login form is displayed
     */
    public boolean isLoginFormDisplayed() {
        try {
            return loginForm.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get error message text
     */
    public String getErrorMessage() {
        try {
            waitUntilVisible(errorMessage);
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Check if email field is displayed
     */
    public boolean isEmailFieldDisplayed() {
        try {
            return emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            return passwordField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        try {
            return loginButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    
    // ==========================================
    // METHODS USING ObjectReader (Centralized Locators)
    // ==========================================
    
    /**
     * Login using ObjectReader for locators
     * Demonstrates centralized locator management
     */
    public void logInUsingObjectReader() {
        try {
            // Get email from properties
            String email = PropertyReader.getProperty("email");
            String password = PropertyReader.getProperty("password");
            
            // Find elements using ObjectReader
            WebElement emailInput = driver.findElement(
                ObjectReader.getByLocator("loginPage.emailField.id")
            );
            WebElement passwordInput = driver.findElement(
                ObjectReader.getByLocator("loginPage.passwordField.name")
            );
            WebElement loginBtn = driver.findElement(
                ObjectReader.getByLocator("loginPage.loginButton.xpath")
            );
            
            // Perform login
            emailInput.clear();
            emailInput.sendKeys(email);
            
            passwordInput.clear();
            passwordInput.sendKeys(password);
            
            loginBtn.click();
            
            System.out.println("✓ Login completed using ObjectReader");
            
        } catch (Exception e) {
            System.err.println("✗ Failed to login using ObjectReader: " + e.getMessage());
        }
    }
    
    
}