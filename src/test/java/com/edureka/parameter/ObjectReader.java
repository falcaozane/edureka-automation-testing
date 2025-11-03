package com.edureka.parameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

/**
 * ObjectReader - Reads locators from Object.properties file
 * Centralized locator repository management
 */
public class ObjectReader {
    
    private static Properties objectProperties;
    
    static {
        try {
            objectProperties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/PropertyData/Object.properties");
            objectProperties.load(fis);
            System.out.println("✓ Object.properties loaded successfully");
        } catch (IOException e) {
            System.err.println("✗ Failed to load Object.properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get locator value from Object.properties
     * @param key - locator key (e.g., "loginPage.emailField.id")
     * @return locator value
     */
    public static String getLocator(String key) {
        String value = objectProperties.getProperty(key);
        if (value == null) {
            System.err.println("✗ Locator not found for key: " + key);
        }
        return value;
    }
    
    /**
     * Get By locator based on key
     * Automatically determines locator type from key
     * 
     * @param key - Full locator key (e.g., "loginPage.emailField.id")
     * @return By object
     */
    public static By getByLocator(String key) {
        String locatorValue = getLocator(key);
        
        if (locatorValue == null) {
            throw new RuntimeException("Locator not found for key: " + key);
        }
        
        // Extract locator type from key (last part after final dot)
        String locatorType = key.substring(key.lastIndexOf('.') + 1).toLowerCase();
        
        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
                
            case "name":
                return By.name(locatorValue);
                
            case "classname":
            case "class":
                return By.className(locatorValue);
                
            case "css":
                return By.cssSelector(locatorValue);
                
            case "xpath":
                return By.xpath(locatorValue);
                
            case "linktext":
                return By.linkText(locatorValue);
                
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
                
            case "tagname":
            case "tag":
                return By.tagName(locatorValue);
                
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
    }
    
    /**
     * Get By locator with explicit locator type
     * 
     * @param key - Locator key
     * @param locatorType - Type of locator (id, name, css, xpath, etc.)
     * @return By object
     */
    public static By getByLocator(String key, String locatorType) {
        String locatorValue = getLocator(key);
        
        if (locatorValue == null) {
            throw new RuntimeException("Locator not found for key: " + key);
        }
        
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
                
            case "name":
                return By.name(locatorValue);
                
            case "classname":
            case "class":
                return By.className(locatorValue);
                
            case "css":
                return By.cssSelector(locatorValue);
                
            case "xpath":
                return By.xpath(locatorValue);
                
            case "linktext":
                return By.linkText(locatorValue);
                
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
                
            case "tagname":
            case "tag":
                return By.tagName(locatorValue);
                
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
    }
    
    /**
     * Get all properties
     * @return Properties object
     */
    public static Properties getAllProperties() {
        return objectProperties;
    }
    
    /**
     * Check if key exists
     * @param key - locator key
     * @return true if exists
     */
    public static boolean hasLocator(String key) {
        return objectProperties.containsKey(key);
    }
    
    /**
     * Print all loaded locators (for debugging)
     */
    public static void printAllLocators() {
        System.out.println("\n========== ALL LOCATORS FROM Object.properties ==========");
        objectProperties.forEach((key, value) -> 
            System.out.println(key + " = " + value)
        );
        System.out.println("=========================================================\n");
    }
}