package com.qa.automation.utils;

import com.qa.automation.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * ElementUtil class provides wrapper methods for common Selenium actions
 * Uses WebDriverWait for all actions (no hard-coded sleeps)
 * All methods are wrapped in Try-Catch blocks with Log4j logging
 */
public class ElementUtil {

    private static final Logger logger = LogManager.getLogger(ElementUtil.class);
    private WebDriver driver;
    private WebDriverWait wait;

    public ElementUtil() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DriverFactory.getTimeout()));
    }

    /**
     * Clicks on an element after waiting for it to be clickable
     * @param locator By locator of the element
     * @return true if click successful, false otherwise
     */
    public boolean doClick(By locator) {
        try {
            logger.info("Attempting to click element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Successfully clicked element: {}", locator);
            return true;
        } catch (Exception e) {
            logger.error("Failed to click element: {}. Error: {}", locator, e.getMessage());
            return false;
        }
    }

    /**
     * Clicks on a WebElement after waiting for it to be clickable
     * @param element WebElement to click
     * @return true if click successful, false otherwise
     */
    public boolean doClick(WebElement element) {
        try {
            logger.info("Attempting to click element: {}", element);
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            clickableElement.click();
            logger.info("Successfully clicked element");
            return true;
        } catch (Exception e) {
            logger.error("Failed to click element. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Sends keys to an element after waiting for it to be visible
     * @param locator By locator of the element
     * @param text Text to send
     * @return true if sendKeys successful, false otherwise
     */
    public boolean doSendKeys(By locator, String text) {
        try {
            logger.info("Attempting to send keys to element: {} with text: {}", locator, text);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            logger.info("Successfully sent keys to element: {}", locator);
            return true;
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {}. Error: {}", locator, e.getMessage());
            return false;
        }
    }

    /**
     * Sends keys to a WebElement after waiting for it to be visible
     * @param element WebElement to send keys to
     * @param text Text to send
     * @return true if sendKeys successful, false otherwise
     */
    public boolean doSendKeys(WebElement element, String text) {
        try {
            logger.info("Attempting to send keys to element with text: {}", text);
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            visibleElement.clear();
            visibleElement.sendKeys(text);
            logger.info("Successfully sent keys to element");
            return true;
        } catch (Exception e) {
            logger.error("Failed to send keys to element. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets text from an element after waiting for it to be visible
     * @param locator By locator of the element
     * @return Text content of the element, or null if failed
     */
    public String doGetText(By locator) {
        try {
            logger.info("Attempting to get text from element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            logger.info("Successfully retrieved text from element: {}. Text: {}", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}. Error: {}", locator, e.getMessage());
            return null;
        }
    }

    /**
     * Gets text from a WebElement after waiting for it to be visible
     * @param element WebElement to get text from
     * @return Text content of the element, or null if failed
     */
    public String doGetText(WebElement element) {
        try {
            logger.info("Attempting to get text from element");
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            String text = visibleElement.getText();
            logger.info("Successfully retrieved text from element. Text: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element. Error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Waits for an element to be visible
     * @param locator By locator of the element
     * @return WebElement if found, null otherwise
     */
    public WebElement waitForElementVisible(By locator) {
        try {
            logger.info("Waiting for element to be visible: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element is now visible: {}", locator);
            return element;
        } catch (Exception e) {
            logger.error("Element not visible within timeout: {}. Error: {}", locator, e.getMessage());
            return null;
        }
    }

    /**
     * Waits for an element to be present in DOM
     * @param locator By locator of the element
     * @return WebElement if found, null otherwise
     */
    public WebElement waitForElementPresent(By locator) {
        try {
            logger.info("Waiting for element to be present: {}", locator);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("Element is now present: {}", locator);
            return element;
        } catch (Exception e) {
            logger.error("Element not present within timeout: {}. Error: {}", locator, e.getMessage());
            return null;
        }
    }

    /**
     * Checks if an element is displayed
     * @param locator By locator of the element
     * @return true if element is displayed, false otherwise
     */
    public boolean isElementDisplayed(By locator) {
        try {
            logger.info("Checking if element is displayed: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            boolean isDisplayed = element.isDisplayed();
            logger.info("Element display status: {} for locator: {}", isDisplayed, locator);
            return isDisplayed;
        } catch (Exception e) {
            logger.warn("Element not displayed: {}. Error: {}", locator, e.getMessage());
            return false;
        }
    }

    /**
     * Selects an option from a dropdown by visible text
     * @param element WebElement dropdown element
     * @param visibleText Visible text to select
     * @return true if selection successful, false otherwise
     */
    public boolean doSelectByVisibleText(WebElement element, String visibleText) {
        try {
            logger.info("Attempting to select dropdown option by visible text: {}", visibleText);
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            Select select = new Select(visibleElement);
            select.selectByVisibleText(visibleText);
            logger.info("Successfully selected dropdown option: {}", visibleText);
            return true;
        } catch (Exception e) {
            logger.error("Failed to select dropdown option: {}. Error: {}", visibleText, e.getMessage());
            return false;
        }
    }
}

