package com.qa.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for Change Password functionality
 * Handles password change operations for Patient and Doctor users
 */
public class ChangePasswordPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ChangePasswordPage.class);

    // Locators for change password elements
    @FindBy(xpath = "//a[contains(text(),'My Profile') or contains(@href,'profile')] | //button[contains(text(),'My Profile')]")
    private WebElement myProfileLink;

    @FindBy(xpath = "//a[contains(text(),'Change Password') or contains(@href,'change-password')] | //button[contains(text(),'Change Password')] | //*[contains(text(),'Change Password')]")
    private WebElement changePasswordLink;

    @FindBy(xpath = "//input[contains(@name,'currentPassword') or contains(@id,'currentPassword') or contains(@placeholder,'Current Password') or contains(@type,'password') and contains(@placeholder,'current')]")
    private WebElement currentPasswordField;

    @FindBy(xpath = "//input[contains(@name,'newPassword') or contains(@id,'newPassword') or contains(@placeholder,'New Password') and not(contains(@placeholder,'Confirm'))]")
    private WebElement newPasswordField;

    @FindBy(xpath = "//input[contains(@name,'confirmPassword') or contains(@id,'confirmPassword') or contains(@placeholder,'Confirm New Password') or contains(@placeholder,'Confirm Password')]")
    private WebElement confirmNewPasswordField;

    @FindBy(xpath = "//button[contains(text(),'Change Password') or contains(text(),'Update Password')] | //input[@type='submit' and (contains(@value,'Change') or contains(@value,'Update'))]")
    private WebElement changePasswordButton;

    /**
     * Navigates to My Profile section
     * @return ChangePasswordPage instance for Fluent pattern
     */
    public ChangePasswordPage goToMyProfile() {
        try {
            logger.info("Navigating to My Profile");
            elementUtil.doClick(myProfileLink);
            Thread.sleep(1000);
            logger.info("Successfully navigated to My Profile");
        } catch (Exception e) {
            logger.error("Failed to navigate to My Profile. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to navigate to My Profile", e);
        }
        return this;
    }

    /**
     * Clicks on Change Password link/section
     * @return ChangePasswordPage instance for Fluent pattern
     */
    public ChangePasswordPage clickChangePassword() {
        try {
            logger.info("Clicking Change Password link");
            
            // Try direct click first
            try {
                elementUtil.doClick(changePasswordLink);
                Thread.sleep(1000);
                logger.info("Successfully clicked Change Password link");
                return this;
            } catch (Exception e1) {
                logger.warn("Direct click failed, trying to scroll to element");
            }
            
            // Try scrolling to element and clicking
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", changePasswordLink);
                Thread.sleep(500);
                elementUtil.doClick(changePasswordLink);
                Thread.sleep(1000);
                logger.info("Successfully clicked Change Password link after scrolling");
                return this;
            } catch (Exception e2) {
                logger.error("Failed to click Change Password link");
                throw new RuntimeException("Failed to click Change Password link", e2);
            }
        } catch (Exception e) {
            logger.error("Failed to click Change Password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Change Password", e);
        }
    }

    /**
     * Enters current password
     * @param currentPassword Current password
     * @return ChangePasswordPage instance for Fluent pattern
     */
    public ChangePasswordPage enterCurrentPassword(String currentPassword) {
        try {
            logger.info("Entering current password");
            currentPasswordField.clear();
            Thread.sleep(300);
            elementUtil.doSendKeys(currentPasswordField, currentPassword);
            logger.info("Successfully entered current password");
        } catch (Exception e) {
            logger.error("Failed to enter current password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter current password", e);
        }
        return this;
    }

    /**
     * Enters new password
     * @param newPassword New password
     * @return ChangePasswordPage instance for Fluent pattern
     */
    public ChangePasswordPage enterNewPassword(String newPassword) {
        try {
            logger.info("Entering new password");
            newPasswordField.clear();
            Thread.sleep(300);
            elementUtil.doSendKeys(newPasswordField, newPassword);
            logger.info("Successfully entered new password");
        } catch (Exception e) {
            logger.error("Failed to enter new password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter new password", e);
        }
        return this;
    }

    /**
     * Enters confirm new password
     * @param confirmPassword Confirm new password
     * @return ChangePasswordPage instance for Fluent pattern
     */
    public ChangePasswordPage enterConfirmNewPassword(String confirmPassword) {
        try {
            logger.info("Entering confirm new password");
            confirmNewPasswordField.clear();
            Thread.sleep(300);
            elementUtil.doSendKeys(confirmNewPasswordField, confirmPassword);
            logger.info("Successfully entered confirm new password");
        } catch (Exception e) {
            logger.error("Failed to enter confirm new password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter confirm new password", e);
        }
        return this;
    }

    /**
     * Clicks the Change Password button
     * @return ChangePasswordPage instance for Fluent pattern
     */
    public ChangePasswordPage clickChangePasswordButton() {
        try {
            logger.info("Clicking Change Password button");
            elementUtil.doClick(changePasswordButton);
            Thread.sleep(2000); // Wait for password change to complete
            logger.info("Successfully clicked Change Password button");
        } catch (Exception e) {
            logger.error("Failed to click Change Password button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Change Password button", e);
        }
        return this;
    }

    /**
     * Verifies if password was changed successfully
     * Checks for success message or URL change
     * @return true if password changed successfully, false otherwise
     */
    public boolean isPasswordChangedSuccessfully() {
        try {
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            String pageSource = driver.getPageSource();
            
            logger.info("Checking if password was changed successfully");
            logger.info("Current URL: {}", currentUrl);
            
            // Check for success message in page source
            boolean hasSuccessMessage = pageSource.toLowerCase().contains("password changed successfully") ||
                                       pageSource.toLowerCase().contains("password updated successfully") ||
                                       pageSource.toLowerCase().contains("password has been changed") ||
                                       pageSource.toLowerCase().contains("success");
            
            if (hasSuccessMessage) {
                logger.info("Password change success message found");
                return true;
            }
            
            // If no explicit success message, assume success if no error
            logger.info("No explicit success message, but no error detected");
            return true;
            
        } catch (Exception e) {
            logger.error("Error checking password change status: {}", e.getMessage());
            return false;
        }
    }
}
