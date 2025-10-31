package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.edureka.pages.LoginPage;



public class Test {
	WebDriver driver;
	LoginPage loginPage;
	
	@org.testng.annotations.Test
	public void test01() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.edureka.co");
		
		loginPage = new LoginPage(driver);
		loginPage.logIn();
	}
}
