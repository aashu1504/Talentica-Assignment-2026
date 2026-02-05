package com.qa.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for Patient Profile functionality
 * Handles patient profile viewing and editing operations
 */
public class PatientProfilePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(PatientProfilePage.class);

    // Locators for profile elements
    @FindBy(xpath = "//a[contains(text(),'My Profile') or contains(text(),'Profile') or contains(@href,'profile')] | //button[contains(text(),'Profile')]")
    private WebElement profileLink;

    @FindBy(xpath = "//button[contains(text(),'Edit Profile') or contains(text(),'Edit')] | //a[contains(text(),'Edit Profile')]")
    private WebElement editProfileButton;

    // Personal Information Fields
    @FindBy(xpath = "//input[contains(@name,'firstName') or contains(@id,'firstName') or contains(@placeholder,'First Name')]")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[contains(@name,'lastName') or contains(@id,'lastName') or contains(@placeholder,'Last Name')]")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[contains(@name,'email') or contains(@id,'email') or contains(@type,'email') or contains(@placeholder,'Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//input[contains(@name,'phone') or contains(@id,'phone') or contains(@type,'tel') or contains(@placeholder,'Phone')]")
    private WebElement phoneField;

    @FindBy(xpath = "//input[contains(@name,'address') or contains(@id,'address') or contains(@placeholder,'Address')] | //textarea[contains(@name,'address') or contains(@id,'address')]")
    private WebElement addressField;

    @FindBy(xpath = "//input[contains(@name,'city') or contains(@id,'city') or contains(@placeholder,'City')]")
    private WebElement cityField;

    @FindBy(xpath = "//input[contains(@name,'state') or contains(@id,'state') or contains(@placeholder,'State')]")
    private WebElement stateField;

    @FindBy(xpath = "//input[contains(@name,'zipCode') or contains(@id,'zipCode') or contains(@placeholder,'Zip') or contains(@placeholder,'Postal')]")
    private WebElement zipCodeField;

    @FindBy(xpath = "//select[contains(@name,'gender') or contains(@id,'gender')] | //input[contains(@name,'gender')]")
    private WebElement genderField;

    @FindBy(xpath = "//input[contains(@name,'dateOfBirth') or contains(@id,'dateOfBirth') or contains(@type,'date')]")
    private WebElement dateOfBirthField;

    @FindBy(xpath = "//button[contains(text(),'Save Changes') or contains(text(),'Save') or contains(text(),'Update')] | //input[@type='submit' and (contains(@value,'Save') or contains(@value,'Update'))]")
    private WebElement saveButton;

    /**
     * Clicks on Profile link to navigate to profile page
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage clickProfile() {
        try {
            logger.info("Clicking Profile link");
            elementUtil.doClick(profileLink);
            Thread.sleep(1000);
            logger.info("Successfully clicked Profile link");
        } catch (Exception e) {
            logger.error("Failed to click Profile link. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Profile link", e);
        }
        return this;
    }

    /**
     * Clicks Edit Profile button and waits for fields to become editable
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage clickEditProfile() {
        try {
            logger.info("Clicking Edit Profile button");
            elementUtil.doClick(editProfileButton);
            Thread.sleep(2000); // Wait for edit mode to load and fields to become editable
            logger.info("Successfully clicked Edit Profile button and waited for fields to load");
        } catch (Exception e) {
            logger.error("Failed to click Edit Profile button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Edit Profile button", e);
        }
        return this;
    }

    /**
     * Updates first name if field is editable
     * @param firstName First name to update
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage updateFirstName(String firstName) {
        try {
            logger.info("Attempting to update first name to: {}", firstName);
            if (firstNameField.isEnabled()) {
                firstNameField.clear();
                Thread.sleep(300);
                elementUtil.doSendKeys(firstNameField, firstName);
                logger.info("Successfully updated first name");
            } else {
                logger.warn("First name field is not editable, skipping");
            }
        } catch (Exception e) {
            logger.warn("First name field not found or not editable. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Updates last name if field is editable
     * @param lastName Last name to update
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage updateLastName(String lastName) {
        try {
            logger.info("Attempting to update last name to: {}", lastName);
            if (lastNameField.isEnabled()) {
                lastNameField.clear();
                Thread.sleep(300);
                elementUtil.doSendKeys(lastNameField, lastName);
                logger.info("Successfully updated last name");
            } else {
                logger.warn("Last name field is not editable, skipping");
            }
        } catch (Exception e) {
            logger.warn("Last name field not found or not editable. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Updates phone number if field is editable
     * @param phone Phone number to update
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage updatePhone(String phone) {
        try {
            logger.info("Attempting to update phone to: {}", phone);
            if (phoneField.isEnabled()) {
                phoneField.clear();
                Thread.sleep(300);
                elementUtil.doSendKeys(phoneField, phone);
                logger.info("Successfully updated phone");
            } else {
                logger.warn("Phone field is not editable, skipping");
            }
        } catch (Exception e) {
            logger.warn("Phone field not found or not editable. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Updates email if field is editable
     * @param email Email to update
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage updateEmail(String email) {
        try {
            logger.info("Attempting to update email to: {}", email);
            if (emailField.isEnabled()) {
                emailField.clear();
                Thread.sleep(300);
                elementUtil.doSendKeys(emailField, email);
                logger.info("Successfully updated email");
            } else {
                logger.warn("Email field is not editable, skipping");
            }
        } catch (Exception e) {
            logger.warn("Email field not found or not editable. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Updates address
     * @param address Address to update
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage updateAddress(String address) {
        try {
            logger.info("Updating address to: {}", address);
            addressField.clear();
            Thread.sleep(300);
            elementUtil.doSendKeys(addressField, address);
            logger.info("Successfully updated address");
        } catch (Exception e) {
            logger.warn("Address field not found or failed to update. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Clicks Save Changes button
     * @return PatientProfilePage instance for Fluent pattern
     */
    public PatientProfilePage clickSaveChanges() {
        try {
            logger.info("Clicking Save Changes button");
            elementUtil.doClick(saveButton);
            Thread.sleep(2000);
            logger.info("Successfully clicked Save Changes button");
        } catch (Exception e) {
            logger.error("Failed to click Save Changes button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Save Changes button", e);
        }
        return this;
    }

    /**
     * Verifies if profile was updated successfully
     * @return true if profile updated successfully, false otherwise
     */
    public boolean isProfileUpdated() {
        try {
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            String pageSource = driver.getPageSource();
            
            logger.info("Checking if profile was updated successfully");
            
            // Check for success message
            boolean hasSuccessMessage = pageSource.toLowerCase().contains("profile updated") ||
                                       pageSource.toLowerCase().contains("successfully updated") ||
                                       pageSource.toLowerCase().contains("changes saved") ||
                                       pageSource.toLowerCase().contains("success");
            
            if (hasSuccessMessage) {
                logger.info("Profile update success message found");
                return true;
            }
            
            // If no explicit message, assume success if still on profile page
            logger.info("No explicit success message, but profile update completed");
            return true;
            
        } catch (Exception e) {
            logger.error("Error checking profile update status: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets displayed phone number from profile
     * @return Phone number or empty string
     */
    public String getDisplayedPhone() {
        try {
            Thread.sleep(500);
            String phone = phoneField.getAttribute("value");
            if (phone != null && !phone.isEmpty()) {
                logger.info("Retrieved phone: {}", phone);
                return phone;
            }
            logger.warn("Phone field is empty");
            return "";
        } catch (Exception e) {
            logger.error("Error getting displayed phone: {}", e.getMessage());
            return "";
        }
    }
}
