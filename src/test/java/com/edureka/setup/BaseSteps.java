package com.edureka.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.edureka.parameter.PropertyReader;

public class BaseSteps {
    public static WebDriver driver;

    public static WebDriver initializeDriver() {
        String browser = PropertyReader.getProperty("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                ChromeOptions coptions = new ChromeOptions();
                coptions.addArguments("--start-maximized", "--incognito", "--disable-popup-blocking", "--disable-notifications");
                driver = new ChromeDriver(coptions);
                break;

            case "firefox":
                FirefoxOptions foptions = new FirefoxOptions();
                foptions.addArguments("--start-maximized", "--private", "--disable-popup-blocking", "--disable-notifications");
                driver = new FirefoxDriver(foptions);
                break;

            case "edge":
                EdgeOptions eoptions = new EdgeOptions();
                eoptions.addArguments("--start-maximized", "--inprivate", "--disable-popup-blocking", "--disable-notifications");
                driver = new EdgeDriver(eoptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser specified in properties file: " + browser);
        }

        try {
            driver.get(PropertyReader.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Unable to navigate to the URL: " + e.getMessage());
        }

        return driver;
    }
    

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}