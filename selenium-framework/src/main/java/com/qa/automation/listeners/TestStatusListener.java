package com.qa.automation.listeners;

import com.qa.automation.utils.ExtentReportManager;
import com.qa.automation.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestStatusListener implements ITestListener to handle test execution events
 * Automatically captures screenshots on test failure and attaches to ExtentReport
 */
public class TestStatusListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestStatusListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            String testDescription = result.getMethod().getDescription();
            
            // Create test in ExtentReport
            com.aventstack.extentreports.ExtentTest test;
            if (testDescription != null && !testDescription.isEmpty()) {
                test = ExtentReportManager.createTest(testName, testDescription);
            } else {
                test = ExtentReportManager.createTest(testName);
            }
            
            // Verify test was created and set in ThreadLocal
            com.aventstack.extentreports.ExtentTest verifyTest = ExtentReportManager.getTest();
            if (test != null && verifyTest != null) {
                // Add test start time for timeline
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime = sdf.format(new java.util.Date());
                
                logger.info("Test started: {} - {} | ThreadLocal test: {}", testName, 
                        testDescription != null ? testDescription : "", verifyTest != null ? "SET" : "NULL");
                
                ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                ExtentReportManager.logInfo("Test: " + testName);
                if (testDescription != null && !testDescription.isEmpty()) {
                    ExtentReportManager.logInfo("Description: " + testDescription);
                }
                ExtentReportManager.logInfo("Start Time: " + startTime);
                ExtentReportManager.logInfo("Status: STARTED");
                ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            } else {
                logger.error("Failed to create test in ExtentReport: {} | Test: {} | ThreadLocal: {}", 
                        testName, test != null ? "CREATED" : "NULL", verifyTest != null ? "SET" : "NULL");
            }
        } catch (Exception e) {
            logger.error("Error in onTestStart for test: {}. Error: {}", 
                    result.getMethod().getMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            logger.info("Test passed: {}", testName);
            
            // Get the test instance and mark it as passed
            com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getTest();
            if (test != null) {
                // Add test end time for timeline
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String endTime = sdf.format(new java.util.Date());
                long duration = result.getEndMillis() - result.getStartMillis();
                
                ExtentReportManager.logInfo("End Time: " + endTime);
                ExtentReportManager.logInfo("Duration: " + (duration / 1000.0) + " seconds");
                ExtentReportManager.logInfo("✓ Test execution completed successfully");
                
                // Capture screenshot on success for visual verification
                String base64Screenshot = ScreenshotUtil.captureScreenshotAsBase64();
                if (base64Screenshot != null && !base64Screenshot.isEmpty()) {
                    ExtentReportManager.attachScreenshotBase64(base64Screenshot);
                    logger.info("Success screenshot captured and attached as base64");
                }
                
                // Mark test as passed - this is important for timeline and charts
                test.pass("✅ TEST PASSED: " + testName);
                logger.info("Test marked as PASSED in ExtentReport: {}", testName);
            } else {
                logger.warn("No ExtentTest instance found for test: {} - creating new test", testName);
                // Try to create test if it doesn't exist
                test = ExtentReportManager.createTest(testName, "Test passed");
                if (test != null) {
                    test.pass("✅ TEST PASSED: " + testName);
                }
            }
        } catch (Exception e) {
            logger.error("Error in onTestSuccess for test: {}. Error: {}", 
                    result.getMethod().getMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            Throwable throwable = result.getThrowable();
            String errorMessage = throwable != null ? throwable.getMessage() : "Unknown error";
            
            logger.error("Test failed: {}. Error: {}", testName, errorMessage);
            
            // Get the test instance and mark it as failed
            com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getTest();
            if (test != null) {
                // Log failure details to ExtentReport
                ExtentReportManager.logFail("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                ExtentReportManager.logFail("❌ TEST FAILED: " + testName);
                ExtentReportManager.logFail("Error Message: " + errorMessage);
                
                // Capture screenshot on failure
                String screenshotPath = ScreenshotUtil.captureScreenshot(testName + "_failure");
                if (screenshotPath != null) {
                    ExtentReportManager.attachScreenshot(screenshotPath);
                    logger.info("Screenshot captured and attached to ExtentReport: {}", screenshotPath);
                } else {
                    // Try base64 screenshot as fallback
                    String base64Screenshot = ScreenshotUtil.captureScreenshotAsBase64();
                    if (base64Screenshot != null) {
                        ExtentReportManager.attachScreenshotBase64(base64Screenshot);
                        logger.info("Base64 screenshot captured and attached to ExtentReport");
                    } else {
                        logger.warn("Failed to capture screenshot for failed test: {}", testName);
                    }
                }
                
                // Log stack trace if available
                if (throwable != null) {
                    String stackTrace = getStackTrace(throwable);
                    ExtentReportManager.logFail("<details><summary>Stack Trace</summary><pre>" + stackTrace + "</pre></details>");
                }
                
                // Mark test as failed
                test.fail("Test failed: " + errorMessage);
                ExtentReportManager.logFail("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            } else {
                logger.warn("No ExtentTest instance found for failed test: {}", testName);
            }
        } catch (Exception e) {
            logger.error("Error in onTestFailure for test: {}. Error: {}", 
                    result.getMethod().getMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            String skipReason = result.getThrowable() != null ? 
                    result.getThrowable().getMessage() : "Test was skipped";
            
            logger.warn("Test skipped: {}. Reason: {}", testName, skipReason);
            ExtentReportManager.logSkip("Test skipped: " + testName);
            ExtentReportManager.logSkip("Reason: " + skipReason);
        } catch (Exception e) {
            logger.error("Error in onTestSkipped for test: {}. Error: {}", 
                    result.getMethod().getMethodName(), e.getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            logger.warn("Test failed but within success percentage: {}", testName);
            ExtentReportManager.logFail("Test failed but within success percentage: " + testName);
        } catch (Exception e) {
            logger.error("Error in onTestFailedButWithinSuccessPercentage for test: {}. Error: {}", 
                    result.getMethod().getMethodName(), e.getMessage());
        }
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        try {
            logger.info("Test suite execution finished");
            int totalTests = context.getPassedTests().size() + context.getFailedTests().size() + context.getSkippedTests().size();
            int passed = context.getPassedTests().size();
            int failed = context.getFailedTests().size();
            int skipped = context.getSkippedTests().size();
            
            logger.info("Total tests run: {}, Passed: {}, Failed: {}, Skipped: {}", 
                    totalTests, passed, failed, skipped);
            
            // Don't remove test here - let it be removed after each test method
            // Just flush the report
            ExtentReportManager.flushReport();
            logger.info("ExtentReport has been generated and flushed successfully");
        } catch (Exception e) {
            logger.error("Error in onFinish. Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets formatted stack trace from throwable
     * @param throwable Throwable object
     * @return Formatted stack trace string
     */
    private String getStackTrace(Throwable throwable) {
        try {
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            throwable.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            logger.error("Error getting stack trace. Error: {}", e.getMessage());
            return throwable.toString();
        }
    }
}

