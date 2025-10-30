package com.edureka.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	
	//WebElements
	@FindBy(xpath = "//button[contains(text(), 'Log in')]") WebElement login;
	
	//Constructor
	public HomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this); 
		
	}
	
	//methods
	public void logIn() {
		login.click();
	}
	

}
