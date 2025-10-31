package com.edureka.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edureka.setup.BaseSteps;




public class BasePage extends BaseSteps{
	WebDriver driver;
	WebDriverWait wait;
	
	
	public BasePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loadURL(String url) {
		driver.get(url);
	}
	public void waitUntillWebElementIsVisible(WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions. visibilityOf(element));
	}
	public void waitUntillWebElementIsClickable(WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
}

