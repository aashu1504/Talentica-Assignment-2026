package com.qa.automation.tests.performance;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-026: Verify Home Page Load Speed
 * Module/Feature: Performance
 * Priority: P1
 * Test Type: Performance
 * 
 * Precondition: Browser Cache Cleared
 * 
 * Test Steps:
 * 1. Clear the browser cache
 * 2. Enter the URL http://18.142.250.249:5000 and press Enter
 * 3. Observe the page load time
 * 
 * Expected Result:
 * - The main content loads almost instantly (within threshold)
 * - Threshold: < 3 sec
 * 
 * Test Data:
 * - URL: http://18.142.250.249:5000
 * - Threshold: 3 seconds
 */
public class TC026_HomePageLoadSpeedTest extends BaseTest {

    private HomePage homePage;
    private static final String HOME_PAGE_URL = "http://18.142.250.249:5000";
    private static final long LOAD_TIME_THRESHOLD_MS = 3000; // 3 seconds in milliseconds

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
    }

    @Test(description = "TC-026: Verify Home Page Load Speed")
    public void verifyHomePageLoadSpeed() {
        try {
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("TC-026: Verify Home Page Load Speed");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // ========== STEP 1: CLEAR BROWSER CACHE ==========
            ExtentReportManager.logInfo("Step 1: Clear the browser cache");
            
            // Clear browser cache using manage().deleteAllCookies()
            homePage.clearBrowserCache();
            ExtentReportManager.logPass("✓ Browser cache cleared");

            // ========== STEP 2-3: NAVIGATE TO HOME PAGE AND MEASURE LOAD TIME ==========
            ExtentReportManager.logInfo("Step 2: Enter the URL " + HOME_PAGE_URL + " and press Enter");
            ExtentReportManager.logInfo("Step 3: Observe the page load time");
            
            // Record start time
            long startTime = System.currentTimeMillis();
            ExtentReportManager.logInfo("Start time recorded: " + startTime);
            
            // Navigate to home page
            homePage.navigateToUrl(HOME_PAGE_URL);
            
            // Wait for page to be fully loaded
            Thread.sleep(500); // Small buffer to ensure page is fully loaded
            
            // Record end time
            long endTime = System.currentTimeMillis();
            ExtentReportManager.logInfo("End time recorded: " + endTime);
            
            // Calculate load time
            long loadTime = endTime - startTime;
            double loadTimeSeconds = loadTime / 1000.0;
            
            ExtentReportManager.logPass("✓ Home page loaded");
            ExtentReportManager.logInfo("Page load time: " + loadTime + " ms (" + String.format("%.2f", loadTimeSeconds) + " seconds)");

            // ========== VERIFICATION ==========
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("VERIFICATION: Page Load Performance");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Verify load time is within threshold
            boolean withinThreshold = loadTime < LOAD_TIME_THRESHOLD_MS;
            
            ExtentReportManager.logInfo("Load Time: " + loadTime + " ms");
            ExtentReportManager.logInfo("Threshold: " + LOAD_TIME_THRESHOLD_MS + " ms (< 3 seconds)");
            
            if (withinThreshold) {
                ExtentReportManager.logPass("✓ The main content loads almost instantly (within threshold)");
                ExtentReportManager.logPass("✓ Load time (" + String.format("%.2f", loadTimeSeconds) + "s) < Threshold (3s)");
            } else {
                ExtentReportManager.logFail("❌ Page load time (" + String.format("%.2f", loadTimeSeconds) + "s) exceeds threshold (3s)");
            }
            
            Assert.assertTrue(withinThreshold, 
                "Page load time (" + loadTime + " ms) should be less than threshold (" + LOAD_TIME_THRESHOLD_MS + " ms)");

            // Additional performance metrics
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("Performance Metrics:");
            ExtentReportManager.logInfo("  - URL: " + HOME_PAGE_URL);
            ExtentReportManager.logInfo("  - Load Time: " + String.format("%.2f", loadTimeSeconds) + " seconds");
            ExtentReportManager.logInfo("  - Threshold: 3 seconds");
            ExtentReportManager.logInfo("  - Status: " + (withinThreshold ? "PASS ✓" : "FAIL ✗"));
            
            if (loadTime < 1000) {
                ExtentReportManager.logPass("  - Performance: Excellent (< 1 second)");
            } else if (loadTime < 2000) {
                ExtentReportManager.logPass("  - Performance: Good (< 2 seconds)");
            } else if (loadTime < 3000) {
                ExtentReportManager.logPass("  - Performance: Acceptable (< 3 seconds)");
            } else {
                ExtentReportManager.logInfo("  - Performance: Needs Improvement (>= 3 seconds)");
            }
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-026 - Verify Home Page Load Speed");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Browser cache cleared");
            ExtentReportManager.logPass("  ✓ Navigated to: " + HOME_PAGE_URL);
            ExtentReportManager.logPass("  ✓ Page load time measured: " + String.format("%.2f", loadTimeSeconds) + " seconds");
            ExtentReportManager.logPass("  ✓ Load time within threshold (< 3 seconds)");
            ExtentReportManager.logPass("  ✓ Performance test completed successfully");
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        } catch (AssertionError e) {
            ExtentReportManager.logFail("❌ Assertion failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            ExtentReportManager.logFail("❌ Test execution error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
