package com.qa.automation.pages;

import com.qa.automation.utils.ElementUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage class represents the login page of the application
 * Uses Fluent pattern - methods return page objects for method chaining
 */
public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Page Elements using @FindBy annotations
    @FindBy(xpath = "//input[contains(@name,'email') or contains(@id,'email') or contains(@type,'email') or contains(@placeholder,'Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//input[contains(@name,'password') or contains(@id,'password') or contains(@type,'password') and contains(@placeholder,'Password')]")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Login') or contains(text(),'Sign In')] | //input[@type='submit' and contains(@value,'Login')]")
    private WebElement loginButton;

    /**
     * Enters email address
     * @param email Email address to enter
     * @return LoginPage instance for Fluent pattern
     */
    public LoginPage enterEmail(String email) {
        try {
            logger.info("Entering email: {}", email);
            elementUtil.doSendKeys(emailField, email);
            logger.info("Successfully entered email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to enter email: {}. Error: {}", email, e.getMessage());
            throw new RuntimeException("Failed to enter email: " + email, e);
        }
        return this;
    }

    /**
     * Enters password
     * @param password Password to enter
     * @return LoginPage instance for Fluent pattern
     */
    public LoginPage enterPassword(String password) {
        try {
            logger.info("Entering password");
            elementUtil.doSendKeys(passwordField, password);
            logger.info("Successfully entered password");
        } catch (Exception e) {
            logger.error("Failed to enter password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter password", e);
        }
        return this;
    }

    /**
     * Clicks the Login button
     * @return LoginPage instance for Fluent pattern
     */
    public LoginPage clickLoginButton() {
        try {
            logger.info("Clicking Login button");
            elementUtil.doClick(loginButton);
            logger.info("Successfully clicked Login button");
            // Wait for page to load after login
            wait.until(webDriver -> {
                String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").toString();
                return state.equals("complete");
            });
        } catch (Exception e) {
            logger.error("Failed to click Login button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Login button", e);
        }
        return this;
    }

    /**
     * Performs login with email and password
     * @param email Email address
     * @param password Password
     * @return LoginPage instance for Fluent pattern
     */
    public LoginPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    /**
     * Verifies if user is redirected to Patient Dashboard
     * @return true if redirected to Patient Dashboard, false otherwise
     */
    public boolean isRedirectedToPatientDashboard() {
        try {
            Thread.sleep(2000); // Wait for redirection
            String currentUrl = getCurrentUrl().toLowerCase();
            String pageTitle = getPageTitle().toLowerCase();
            logger.info("Current URL: {}, Page Title: {}", currentUrl, pageTitle);
            
            boolean isPatientDashboard = currentUrl.contains("patient") && 
                                        (currentUrl.contains("dashboard") || pageTitle.contains("dashboard") || 
                                         pageTitle.contains("patient"));
            
            return isPatientDashboard;
        } catch (Exception e) {
            logger.error("Error checking Patient Dashboard redirection. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if user is redirected to Doctor Dashboard
     * @return true if redirected to Doctor Dashboard, false otherwise
     */
    public boolean isRedirectedToDoctorDashboard() {
        try {
            Thread.sleep(2000); // Wait for redirection
            String currentUrl = getCurrentUrl().toLowerCase();
            String pageTitle = getPageTitle().toLowerCase();
            logger.info("Current URL: {}, Page Title: {}", currentUrl, pageTitle);
            
            boolean isDoctorDashboard = currentUrl.contains("doctor") && 
                                       (currentUrl.contains("dashboard") || pageTitle.contains("dashboard") || 
                                        pageTitle.contains("doctor"));
            
            return isDoctorDashboard;
        } catch (Exception e) {
            logger.error("Error checking Doctor Dashboard redirection. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if "Welcome Back" message is displayed in the header
     * @return true if "Welcome Back" is found, false otherwise
     */
    public boolean isWelcomeBackMessageDisplayed() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            boolean hasWelcomeBack = pageSource.contains("welcome back") || 
                                    pageSource.contains("welcome") ||
                                    getPageTitle().toLowerCase().contains("welcome");
            logger.info("Welcome Back message displayed: {}", hasWelcomeBack);
            return hasWelcomeBack;
        } catch (Exception e) {
            logger.error("Error checking Welcome Back message. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if patient name is displayed in the header
     * @param firstName Expected first name
     * @param lastName Expected last name
     * @return true if name is found, false otherwise
     */
    public boolean isPatientNameDisplayed(String firstName, String lastName) {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            boolean hasFirstName = pageSource.contains(firstName.toLowerCase());
            boolean hasLastName = pageSource.contains(lastName.toLowerCase());
            logger.info("Patient name displayed - First Name: {}, Last Name: {}", hasFirstName, hasLastName);
            return hasFirstName && hasLastName;
        } catch (Exception e) {
            logger.error("Error checking patient name. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if doctor name is displayed in the header
     * @param firstName Expected first name
     * @param lastName Expected last name
     * @return true if name is found, false otherwise
     */
    public boolean isDoctorNameDisplayed(String firstName, String lastName) {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            boolean hasFirstName = pageSource.contains(firstName.toLowerCase());
            boolean hasLastName = pageSource.contains(lastName.toLowerCase());
            logger.info("Doctor name displayed - First Name: {}, Last Name: {}", hasFirstName, hasLastName);
            return hasFirstName && hasLastName;
        } catch (Exception e) {
            logger.error("Error checking doctor name. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if dashboard cards are visible (for Doctor Dashboard)
     * @return true if dashboard cards are found, false otherwise
     */
    public boolean areDashboardCardsVisible() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            boolean hasTotalPatients = pageSource.contains("total patients") || 
                                     pageSource.contains("patients");
            boolean hasTotalAppointments = pageSource.contains("total appointments") || 
                                         pageSource.contains("appointments");
            logger.info("Dashboard cards visible - Total Patients: {}, Total Appointments: {}", 
                       hasTotalPatients, hasTotalAppointments);
            return hasTotalPatients || hasTotalAppointments;
        } catch (Exception e) {
            logger.error("Error checking dashboard cards. Error: {}", e.getMessage());
            return false;
        }
    }
}

