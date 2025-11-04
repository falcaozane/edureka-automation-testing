package com.edureka.pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebinarPage {
	WebDriver driver;
	
	//WebElements
	@FindAll({
	    @FindBy(id = "webinar_search_input"),
	    @FindBy(className = "wl_search_inp"),
	    @FindBy(css = "input#webinar_search_input"),
	    @FindBy(css = "input.wl_search_inp"),
	    @FindBy(css = "input[type='search'][placeholder*='Search webinars']"),
	    @FindBy(tagName = "input") // Very generic, use only with filtering logic
	})
	List<WebElement> webinarSearchInputs;
	
	@FindAll({
	    @FindBy(className = "arrowright"), // First class
	    @FindBy(className = "slick-arrow"), // Second class
	    @FindBy(css = "span.arrowright.slick-arrow"), // Both classes
	    @FindBy(css = "span[style='display: block;']"), // Style attribute
	    @FindBy(css = "span[aria-disabled='false']"), // aria-disabled attribute
	    @FindBy(tagName = "span") // Generic, use with filtering logic
	})
	List<WebElement> arrowRightElements;
	
	@FindAll({
	    @FindBy(className = "register_webinar_upcoming"), // First class
	    @FindBy(className = "register_webinar_btn"),       // Second class
	    @FindBy(className = "registerbtn"),                // Third class
	    @FindBy(className = "register-for-webinar_5_7459"),// Fourth class
	    @FindBy(xpath = "//button[contains(@class, 'register-for-webinar_5_7459') and text()='Register Now']"),
	    @FindBy(css = "button.register_webinar_upcoming.register_webinar_btn.registerbtn.register-for-webinar_5_7457"),
	    @FindBy(css = "button[data-webinar-id='7459']"),
	    @FindBy(css = "button[data-category-name='Machine Learning']"),
	    @FindBy(css = "button[tabindex='0']"),
	    @FindBy(tagName = "button") // Generic, use with filtering logic
	})
	List<WebElement> registerButtons;
	
	@FindAll({
	    @FindBy(name = "Experience"),
	    @FindBy(className = "experience"),
	    @FindBy(css = "select[name='Experience']"),
	    @FindBy(css = "select.experience"),
	    @FindBy(tagName = "select")
	})
	List<WebElement> experienceDropdowns;
	
	@FindAll({
	    @FindBy(id = "getintouch_5_7459"),
	    @FindBy(className = "getintouch_form"),
	    @FindBy(css = "input#getintouch_5_7459"),
	    @FindBy(css = "input.getintouch_form"),
	    @FindBy(css = "input[type='checkbox'][value='getintouch']"),
	    @FindBy(css = "input[data-gi-label='default']"),
	    @FindBy(tagName = "input"), // Generic, use with filtering logic
	    @FindBy(xpath = "//input[@type='checkbox' and @id='getintouch_5_7459']")
	})
	List<WebElement> getInTouchCheckboxes;
	
	@FindAll({
	    @FindBy(xpath = "//button[contains(text(), 'Submit')]"),
	    @FindBy(className = "submitbtn"),
	    @FindBy(css = "button.submitbtn"),
	    @FindBy(css = "button[type='submit']"),
	    @FindBy(tagName = "button") // Generic, use with filtering logic
	}) List<WebElement> submitButtons;
	
	//Constructor
	public WebinarPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//methods
	
	// enter webinar name in search input
	public void searchWebinar(String webinarName) {
	    for (Object element : webinarSearchInputs) {
	        WebElement input = (WebElement) element;
	        String placeholder = input.getAttribute("placeholder");
	        if (placeholder != null && placeholder.contains("Search webinars")) {
	            input.sendKeys(webinarName);
	            break;
	        }
	    }
	}
	
	// clear webinar search input
	public void clearWebinarSearch() {
	    for (Object element : webinarSearchInputs) {
	        WebElement input = (WebElement) element;
	        String placeholder = input.getAttribute("placeholder");
	        if (placeholder != null && placeholder.contains("Search webinars")) {
	            input.clear();
	            break;
	        }
	    }
	}
	
	
	// click right arrow to navigate webinars
	public void clickRightArrow() {
	    for (Object element : arrowRightElements) {
	        WebElement arrow = (WebElement) element;
	        String classAttr = arrow.getAttribute("class");
	        if (classAttr != null && classAttr.contains("arrowright") && classAttr.contains("slick-arrow")) {
	            arrow.click();
	            break;
	        }
	    }
	}
	
	// is right arrow visible
	public boolean isRightArrowVisible() {
	    for (Object element : arrowRightElements) {
	        WebElement arrow = (WebElement) element;
	        String classAttr = arrow.getAttribute("class");
	        if (classAttr != null && classAttr.contains("arrowright") && classAttr.contains("slick-arrow")) {
	            return arrow.isDisplayed();
	        }
	    }
	    return false;
	}
	
	// register for webinar
	public void registerForWebinar() {
	    for (Object element : registerButtons) {
	        WebElement button = (WebElement) element;
	        String classAttr = button.getAttribute("class");
	        if (classAttr != null && classAttr.contains("register_webinar_upcoming") &&
	            classAttr.contains("register_webinar_btn") &&
	            classAttr.contains("registerbtn") &&
	            classAttr.contains("register-for-webinar_5_7459")) {
	            button.click();
	            break;
	        }
	    }
	}
	
	// select experience from dropdown
	public void selectExperience(String experienceLevel) {
	    for (Object element : experienceDropdowns) {
	        WebElement dropdown = (WebElement) element;
	        String nameAttr = dropdown.getAttribute("name");
	        if (nameAttr != null && nameAttr.equals("Experience")) {
	            dropdown.sendKeys(experienceLevel);
	            break;
	        }
	    }
	}
	
	// click get in touch checkbox
	public void clickGetInTouchCheckbox() {
	    for (WebElement checkbox : getInTouchCheckboxes) {
	        String idAttr = checkbox.getAttribute("id");
	        if (idAttr != null && idAttr.equals("getintouch_5_7459")) {
	            if (!checkbox.isSelected()) {
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
	            }
	            break;
	        }
	    }
	}
	
	// register for webinar
	public void submitWebinarRegistration() {
		for (Object element : submitButtons) {
	        WebElement button = (WebElement) element;
	        String classAttr = button.getAttribute("class");
	        String text = button.getText();
	        if (classAttr != null && classAttr.contains("submitbtn") && text.equals("Submit")) {
	            button.click();
	            break;
	        }
	    }
	}
	
	
	
}
