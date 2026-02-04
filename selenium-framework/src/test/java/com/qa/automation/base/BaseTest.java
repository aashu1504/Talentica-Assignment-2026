package com.qa.automation.base;

import com.qa.automation.utils.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest class that handles driver initialization and teardown
 * All test classes should extend this class
 */
public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static final int BROWSER_KEEP_OPEN_DELAY = 5000; // 5 seconds to see the page

    /**
     * Initializes ExtentReports before the test suite starts
     */
    @BeforeSuite
    public void setUpSuite() {
        ExtentReportManager.getExtentReports();
    }

    /**
     * Initializes the WebDriver before each test method
     * Also ensures ExtentTest is available for the test method
     */
    @BeforeMethod
    public void setUp(org.testng.ITestResult result) {
        DriverFactory.getDriver();
        logger.info("WebDriver initialized and ready for test execution");
        
        // Ensure ExtentTest is available - create if not exists
        // This is a fallback in case listener didn't create it
        if (ExtentReportManager.getTest() == null && result != null) {
            String testName = result.getMethod().getMethodName();
            String testDescription = result.getMethod().getDescription();
            logger.info("ExtentTest not found, creating in @BeforeMethod: {}", testName);
            if (testDescription != null && !testDescription.isEmpty()) {
                ExtentReportManager.createTest(testName, testDescription);
            } else {
                ExtentReportManager.createTest(testName);
            }
        }
    }

    /**
     * Closes the WebDriver after each test method
     * Adds a delay to allow visual verification of the page before closing
     */
    @AfterMethod
    public void tearDown() {
        logger.info("Test completed. Keeping browser open for {} seconds for visual verification...", 
                BROWSER_KEEP_OPEN_DELAY / 1000);
        
        // Add a delay to allow visual verification of the page before closing
        try {
            Thread.sleep(BROWSER_KEEP_OPEN_DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Delay interrupted");
        }
        
        logger.info("Closing browser...");
        DriverFactory.closeDriver();
        logger.info("Browser closed successfully");
        
        // Remove test from ThreadLocal after test method completes
        // This ensures clean state for next test
        com.qa.automation.utils.ExtentReportManager.removeTest();
    }

    /**
     * Flushes ExtentReports after all tests complete
     */
    @AfterSuite
    public void tearDownSuite() {
        logger.info("Test suite completed. Flushing ExtentReports...");
        ExtentReportManager.flushReport();
        logger.info("ExtentReports flushed successfully");
    }
}

