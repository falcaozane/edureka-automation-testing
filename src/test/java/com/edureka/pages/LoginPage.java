package com.edureka.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.edureka.setup.BaseSteps;

public class LoginPage {
	
	WebDriver driver;
	HomePage homePage;
	BasePage basePage;
	BaseSteps baseSteps;
	
	//WebElements
	
	 public LoginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this); 
	}
	 
	public void logIn() {
		
		basePage = new BasePage(driver);
		basePage.loadURL("https://www.edureka.co");
		homePage = new HomePage(driver);
		homePage.clicklogIn();
	}

}
