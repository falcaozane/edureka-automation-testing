package com.edureka.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;


import com.edureka.pages.BasePage;
import com.edureka.pages.HomePage;
import com.edureka.pages.LoginPage;
import com.edureka.setup.BaseSteps;


public class Test {
	WebDriver driver;
	LoginPage loginPage;
	
	@org.testng.annotations.Test
	public void test01() {
		loginPage = new LoginPage(driver);
		loginPage.logIn();
	}
}
