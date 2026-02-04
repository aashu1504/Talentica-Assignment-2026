package com.qa.automation.pages;

import com.qa.automation.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * HomePage class represents the home page of the application
 * Uses Fluent pattern - methods return page objects for method chaining
 */
public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);
    private static final String HOME_PAGE_URL = DriverFactory.getUrl();

    // Page Elements using @FindBy annotations
    @FindBy(xpath = "//img[contains(@class,'logo') or contains(@alt,'logo') or contains(@src,'logo')] | //div[contains(@class,'logo')] | //a[contains(@class,'logo')]")
    private WebElement logo;

    @FindBy(xpath = "//a[contains(text(),'Login') or contains(text(),'login')] | //button[contains(text(),'Login') or contains(text(),'login')] | //*[@id='login' or @class='login']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[contains(text(),'Sign Up') or contains(text(),'Signup') or contains(text(),'sign up')] | //button[contains(text(),'Sign Up') or contains(text(),'Signup')] | //*[@id='signup' or @class='signup']")
    private WebElement signUpButton;

    @FindBy(xpath = "//a[contains(text(),'Book Appointment') or contains(text(),'book appointment')] | //button[contains(text(),'Book Appointment') or contains(text(),'book appointment')]")
    private WebElement bookAppointmentButton;

    @FindBy(xpath = "//a[contains(text(),'Join as Doctor') or contains(text(),'join as doctor')] | //button[contains(text(),'Join as Doctor') or contains(text(),'join as doctor')]")
    private WebElement joinAsDoctorButton;

    /**
     * Navigates to the home page
     * @return HomePage instance for Fluent pattern
     */
    public HomePage navigateToHomePage() {
        try {
            logger.info("Navigating to home page: {}", HOME_PAGE_URL);
            navigateTo(HOME_PAGE_URL);
            logger.info("Successfully navigated to home page");
        } catch (Exception e) {
            logger.error("Failed to navigate to home page. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Verifies if the logo is displayed
     * @return HomePage instance for Fluent pattern
     */
    public HomePage verifyLogoDisplayed() {
        try {
            logger.info("Verifying logo visibility");
            if (logo != null && logo.isDisplayed()) {
                logger.info("Logo is displayed");
            } else {
                logger.warn("Logo is not displayed");
            }
        } catch (Exception e) {
            logger.error("Error verifying logo visibility. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Checks if logo is displayed
     * @return true if logo is displayed, false otherwise
     */
    public boolean isLogoDisplayed() {
        try {
            if (logo != null) {
                return logo.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking logo visibility. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clicks on the Login button
     * @return HomePage instance for Fluent pattern
     */
    public HomePage clickLoginButton() {
        try {
            logger.info("Clicking Login button");
            if (elementUtil.doClick(loginButton)) {
                logger.info("Successfully clicked Login button");
            } else {
                logger.error("Failed to click Login button");
            }
        } catch (Exception e) {
            logger.error("Error clicking Login button. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Verifies if Login button is present and displayed
     * @return HomePage instance for Fluent pattern
     */
    public HomePage verifyLoginButtonPresent() {
        try {
            logger.info("Verifying Login button presence");
            if (loginButton != null && loginButton.isDisplayed()) {
                logger.info("Login button is present and displayed");
            } else {
                logger.warn("Login button is not present or not displayed");
            }
        } catch (Exception e) {
            logger.error("Error verifying Login button presence. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Checks if Login button is displayed
     * @return true if Login button is displayed, false otherwise
     */
    public boolean isLoginButtonDisplayed() {
        try {
            if (loginButton != null) {
                return loginButton.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking Login button visibility. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clicks on the Sign Up button
     * @return HomePage instance for Fluent pattern
     */
    public HomePage clickSignUpButton() {
        try {
            logger.info("Clicking Sign Up button");
            if (elementUtil.doClick(signUpButton)) {
                logger.info("Successfully clicked Sign Up button");
            } else {
                logger.error("Failed to click Sign Up button");
            }
        } catch (Exception e) {
            logger.error("Error clicking Sign Up button. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Verifies if Sign Up button is present and displayed
     * @return HomePage instance for Fluent pattern
     */
    public HomePage verifySignUpButtonPresent() {
        try {
            logger.info("Verifying Sign Up button presence");
            if (signUpButton != null && signUpButton.isDisplayed()) {
                logger.info("Sign Up button is present and displayed");
            } else {
                logger.warn("Sign Up button is not present or not displayed");
            }
        } catch (Exception e) {
            logger.error("Error verifying Sign Up button presence. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Checks if Sign Up button is displayed
     * @return true if Sign Up button is displayed, false otherwise
     */
    public boolean isSignUpButtonDisplayed() {
        try {
            if (signUpButton != null) {
                return signUpButton.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking Sign Up button visibility. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets the page title
     * @return Page title as String
     */
    public String getPageTitle() {
        String title = super.getPageTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    /**
     * Verifies the page title
     * @param expectedTitle Expected page title
     * @return HomePage instance for Fluent pattern
     */
    public HomePage verifyPageTitle(String expectedTitle) {
        try {
            String actualTitle = getPageTitle();
            logger.info("Verifying page title. Expected: {}, Actual: {}", expectedTitle, actualTitle);
            if (actualTitle.equals(expectedTitle)) {
                logger.info("Page title matches expected value");
            } else {
                logger.warn("Page title does not match. Expected: {}, Actual: {}", expectedTitle, actualTitle);
            }
        } catch (Exception e) {
            logger.error("Error verifying page title. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Checks if Book Appointment button is displayed
     * @return true if Book Appointment button is displayed, false otherwise
     */
    public boolean isBookAppointmentButtonDisplayed() {
        try {
            if (bookAppointmentButton != null) {
                return bookAppointmentButton.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking Book Appointment button visibility. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if Join as Doctor button is displayed
     * @return true if Join as Doctor button is displayed, false otherwise
     */
    public boolean isJoinAsDoctorButtonDisplayed() {
        try {
            if (joinAsDoctorButton != null) {
                return joinAsDoctorButton.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking Join as Doctor button visibility. Error: {}", e.getMessage());
            return false;
        }
    }
}

