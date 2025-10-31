//package com.edureka.pages;
//
//import java.awt.Robot;
//import java.awt.Toolkit;
//import java.awt.datatransfer.StringSelection;
//import java.awt.event.KeyEvent;
//
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class ProfilePage extends BasePage {
//
//    // --- Locators ---
//
//    // Link to the Profile section/page
//    @FindBy(xpath = "//a[contains(text(),'My Profile')]")
//    private WebElement myProfileLink;
//
//    // The 'Change Photo' button/link
//    @FindBy(xpath = "//button[text()='Change Photo']")
//    private WebElement changePhotoButton;
//
//    // The 'Upload' button within the photo change modal
//    @FindBy(xpath = "//button[contains(text(),'Upload')]")
//    private WebElement uploadButton;
//
//    // Success message after a profile update
//    @FindBy(css = ".toast-success")
//    private WebElement successToast;
//
//    // --- Constructor ---
//    public ProfilePage(WebDriver driver) {
//        super(driver);
//    }
//
//    // --- Actions ---
//
//    /**
//     * Navigates to the user's profile page.
//     */
//    public void goToMyProfile() {
//        // Assuming the profile link is available via the dropdown after login
//        // First click the general profile icon (from HomePage) and then the 'My Profile' link
//        // (You might need an extra method to click the main profile icon)
//        waitUntillWebElementIsClickable(myProfileLink);
//        myProfileLink.click();
//    }
//
//    /**
//     * Handles file upload using the **Robot** class (for TC_003).
//     * NOTE: Requires the input field to trigger the OS dialog.
//     * @param absoluteFilePath The path to the dummy image.
//     */
//    public void uploadProfilePictureWithRobot(String absoluteFilePath) throws Exception {
//        waitUntillWebElementIsClickable(changePhotoButton);
//        changePhotoButton.click();
//
//        // The input file element (often hidden, so we click the *button* which opens the dialog)
//        // You might need to make sure the click above actually opens the OS file dialog.
//
//        // 1. Copy the path to the system clipboard
//        StringSelection stringSelection = new StringSelection(absoluteFilePath);
//        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
//
//        // 2. Use Robot to paste the path and press Enter
//        Robot robot = new Robot();
//        robot.delay(2000); // Give the dialog time to open (a slight pause never hurts)
//
//        // Press Ctrl+V (Paste)
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//
//        robot.delay(1000);
//
//        // Press Enter
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);
//
//        // Now, click the internal upload button (if applicable)
//        waitUntillWebElementIsClickable(uploadButton);
//        uploadButton.click();
//    }
//
//    /**
//     * Scrolls the window using **JavaScript Executor** (for TC_005/Footer links).
//     * @param pixelCount The number of pixels to scroll down (e.g., 5000)
//     */
//    public void scrollPageDown(int pixelCount) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0, " + pixelCount + ")");
//    }
//
//    /**
//     * Gets the profile update success message.
//     * @return Success message text.
//     */
//    public String getSuccessMessage() {
//        waitUntillWebElementIsVisible(successToast);
//        return successToast.getText();
//    }
//}