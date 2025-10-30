package com.edureka.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	HomePage homePage;
	
	//WebElements
	
	 public LoginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this); 
	}
	 
	public void logIn() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.edureka.co");
		
		homePage = new HomePage(driver);
		homePage.logIn();
	}

}
