package com.qa.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for Logout functionality
 * Handles logout operations for both Patient and Doctor users
 */
public class LogoutPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LogoutPage.class);

    // Locators for logout elements
    @FindBy(xpath = "//a[contains(text(),'Profile') or contains(@href,'profile')] | //button[contains(text(),'Profile')] | //*[contains(@class,'profile') or contains(@class,'user-menu')]")
    private WebElement profileIcon;

    @FindBy(xpath = "//button[contains(text(),'Logout') or contains(text(),'Log out') or contains(text(),'Sign out')] | //a[contains(text(),'Logout') or contains(text(),'Log out') or contains(text(),'Sign out') or contains(@href,'logout')]")
    private WebElement logoutButton;

    /**
     * Clicks on the Profile Icon/Name in the top right
     * @return LogoutPage instance for Fluent pattern
     */
    public LogoutPage clickProfileIcon() {
        try {
            logger.info("Clicking Profile Icon/Name in the top right");
            elementUtil.doClick(profileIcon);
            Thread.sleep(1000); // Wait for dropdown/menu to appear
            logger.info("Successfully clicked Profile Icon");
        } catch (Exception e) {
            logger.error("Failed to click Profile Icon. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Profile Icon", e);
        }
        return this;
    }

    /**
     * Clicks on the Logout button
     * @return LogoutPage instance for Fluent pattern
     */
    public LogoutPage clickLogout() {
        try {
            logger.info("Clicking Logout button");
            
            // Try approach 1: Direct click using elementUtil
            try {
                elementUtil.doClick(logoutButton);
                Thread.sleep(2000);
                logger.info("Successfully clicked Logout button using direct click");
                return this;
            } catch (Exception e1) {
                logger.warn("Direct click failed, trying alternative approaches");
            }
            
            // Try approach 2: Find logout link by text
            try {
                java.util.List<org.openqa.selenium.WebElement> links = driver.findElements(
                    org.openqa.selenium.By.xpath("//*[contains(text(),'Logout') or contains(text(),'Log out') or contains(text(),'Sign out')]")
                );
                for (org.openqa.selenium.WebElement link : links) {
                    if (link.isDisplayed()) {
                        link.click();
                        Thread.sleep(2000);
                        logger.info("Successfully clicked Logout using text search");
                        return this;
                    }
                }
            } catch (Exception e2) {
                logger.warn("Text search approach failed");
            }
            
            // Try approach 3: JavaScript click
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", logoutButton);
                Thread.sleep(2000);
                logger.info("Successfully clicked Logout using JavaScript");
                return this;
            } catch (Exception e3) {
                logger.error("All logout click approaches failed");
                throw new RuntimeException("Failed to click Logout button after trying multiple approaches");
            }
            
        } catch (Exception e) {
            logger.error("Failed to click Logout button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Logout button", e);
        }
    }

    /**
     * Verifies if user is redirected to Home Page after logout
     * @return true if redirected to home page, false otherwise
     */
    public boolean isRedirectedToHomePage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            logger.info("Current URL after logout: {}", currentUrl);
            
            // Check if URL is the home page (base URL)
            boolean isHomePage = currentUrl.equals("http://18.142.250.249:5000/") || 
                                 currentUrl.equals("http://18.142.250.249:5000") ||
                                 currentUrl.endsWith("/home") ||
                                 !currentUrl.contains("/patient/") && !currentUrl.contains("/doctor/");
            
            if (isHomePage) {
                logger.info("User successfully redirected to Home Page");
            } else {
                logger.warn("User NOT redirected to Home Page. Current URL: {}", currentUrl);
            }
            
            return isHomePage;
        } catch (Exception e) {
            logger.error("Error checking redirect to Home Page: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if logout was successful by checking URL access
     * Tries to access a protected page and verifies user is logged out
     * @param protectedUrl URL that should be inaccessible after logout
     * @return true if user cannot access protected page, false otherwise
     */
    public boolean isLogoutSuccessful(String protectedUrl) {
        try {
            logger.info("Verifying logout by checking access to protected URL: {}", protectedUrl);
            
            // Try to navigate to protected page
            driver.get(protectedUrl);
            Thread.sleep(2000);
            
            String currentUrl = driver.getCurrentUrl();
            logger.info("After attempting to access protected page, current URL: {}", currentUrl);
            
            // If redirected away from protected page, logout was successful
            boolean isLoggedOut = !currentUrl.contains(protectedUrl) || 
                                  currentUrl.contains("/login") ||
                                  currentUrl.equals("http://18.142.250.249:5000/");
            
            if (isLoggedOut) {
                logger.info("Logout successful - cannot access protected page");
            } else {
                logger.warn("Logout may have failed - still can access protected page");
            }
            
            return isLoggedOut;
        } catch (Exception e) {
            logger.error("Error verifying logout: {}", e.getMessage());
            return false;
        }
    }
}
