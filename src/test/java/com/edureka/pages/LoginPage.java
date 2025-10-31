package com.edureka.pages;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
import com.edureka.parameter.PropertyReader;
import com.edureka.setup.BaseSteps;
 
public class LoginPage {
	
	WebDriver driver;
	HomePage homePage;
	BasePage basePage;
	BaseSteps baseSteps;
	PropertyReader propReader;
	
	//WebElements
	@FindBy(id="loginFormEmail") WebElement userName;
	@FindBy(name = "password") WebElement passWord;
	@FindBy(xpath = "//button[text()='LOG IN']") WebElement logInBtn;
	
	 public LoginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void logIn() {
		
		userName.sendKeys(PropertyReader.getProperty("email"));
		passWord.sendKeys(PropertyReader.getProperty("password"));
		logInBtn.click();
	}
 
}