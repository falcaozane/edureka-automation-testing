package com.edureka.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseSteps {
	public static WebDriver driver;
	public static ChromeOptions coptions;
	public static FirefoxOptions foptions;
	public static EdgeOptions eoptions;
	
	public static WebDriver chromeDriver() {
		coptions = new ChromeOptions();
		coptions.addArguments("--start-maximized");
		coptions.addArguments("--incognito");
		coptions.addArguments("--disable-popup-blocking");
		coptions.addArguments("--disable-notifications");
		
		driver = new ChromeDriver(coptions);
		driver.get("https://www.edureka.co");;
		
		return driver;
	}
	
	public static WebDriver firefoxDriver() {
		foptions = new FirefoxOptions();
		foptions.addArguments("--start-maximized");
		foptions.addArguments("--private");
		foptions.addArguments("--disable-popup-blocking");
		foptions.addArguments("--disable-notifications");
		
		driver = new FirefoxDriver(foptions);
		driver.get("https://www.edureka.co");
		
		return driver;
	}
	
	public static WebDriver edgeDriver() {
		eoptions = new EdgeOptions();
		eoptions.addArguments("--start-maximized");
		eoptions.addArguments("--inprivate");
		eoptions.addArguments("--disable-popup-blocking");
		eoptions.addArguments("--disable-notifications");
		
		driver = new EdgeDriver(eoptions);
		driver.get("https://www.edureka.co");
		
		return driver;
	}
	
	public static void tearDown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
}
