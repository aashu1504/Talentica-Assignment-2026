package com.qa.automation.pages;

import com.qa.automation.base.DriverFactory;
import com.qa.automation.utils.ElementUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage class provides common functionality for all page objects
 * Initializes PageFactory for @FindBy annotations
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ElementUtil elementUtil;

    /**
     * Constructor initializes PageFactory and common utilities
     */
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DriverFactory.getTimeout()));
        this.elementUtil = new ElementUtil();
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigates to a specific URL and waits for page to load
     * @param url URL to navigate to
     * @return Current page instance for Fluent pattern
     */
    protected <T extends BasePage> T navigateTo(String url) {
        driver.get(url);
        // Wait for page to load completely
        wait.until(webDriver -> {
            String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").toString();
            return state.equals("complete");
        });
        return (T) this;
    }

    /**
     * Gets the current page title
     * @return Page title as String
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Gets the current page URL
     * @return Current URL as String
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}

