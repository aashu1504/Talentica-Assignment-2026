package com.qa.automation.tests.homepage;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-001: Verify Portal Home Page Redirection & Load
 * Module/Feature: Home Page
 * Priority: P0
 * Test Type: Functional
 * 
 * Test Steps:
 * 1. Open the browser (Chrome/Safari)
 * 2. Enter the URL http://18.142.250.249:5000/ in the address bar
 * 3. Press 'Enter'
 * 4. Wait for the page to load completely
 * 5. Observe the top navigation bar and Hero section
 * 
 * Expected Result:
 * - The Home page loads successfully
 * - "HealthCare Connect" logo is visible
 * - "Login" and "Sign Up" buttons are visible in the top right
 * - "Book Appointment" and "Join as Doctor" buttons are visible in the center
 */
public class TC001_HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
    }

    @Test(description = "TC-001: Verify Portal Home Page Redirection & Load")
    public void verifyPortalHomePageRedirectionAndLoad() {
        try {
            // Step 1: Open the browser (Chrome/Safari) - Browser is opened by BaseTest @BeforeMethod
            ExtentReportManager.logInfo("Step 1: Browser is opened (Chrome/Safari)");
            ExtentReportManager.logPass("✓ Browser opened successfully");

            // Step 2: Enter the URL http://18.142.250.249:5000/ in the address bar
            ExtentReportManager.logInfo("Step 2: Enter the URL http://18.142.250.249:5000/ in the address bar");
            homePage.navigateToHomePage();
            ExtentReportManager.logPass("✓ URL entered and navigation initiated");

            // Step 3: Press 'Enter' - Handled by navigateToHomePage()
            ExtentReportManager.logInfo("Step 3: Press 'Enter'");
            ExtentReportManager.logPass("✓ Enter key pressed (handled by navigation)");

            // Step 4: Wait for the page to load completely
            ExtentReportManager.logInfo("Step 4: Wait for the page to load completely");
            // Page load is handled by BasePage.navigateTo() which includes waitForPageLoad()
            String pageTitle = homePage.getPageTitle();
            ExtentReportManager.logInfo("Page Title: " + pageTitle);
            ExtentReportManager.logPass("✓ Page loaded completely");

            // Step 5: Observe the top navigation bar and Hero section
            ExtentReportManager.logInfo("Step 5: Observe the top navigation bar and Hero section");

            // Expected Result 1: The Home page loads successfully
            ExtentReportManager.logInfo("Expected Result 1: Verify the Home page loads successfully");
            Assert.assertNotNull(pageTitle, "Home page should load successfully");
            Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
            ExtentReportManager.logPass("✓ Home page loaded successfully");

            // Expected Result 2: "HealthCare Connect" logo is visible
            ExtentReportManager.logInfo("Expected Result 2: Verify 'HealthCare Connect' logo is visible");
            boolean isLogoVisible = homePage.isLogoDisplayed();
            Assert.assertTrue(isLogoVisible, "HealthCare Connect logo should be visible");
            ExtentReportManager.logPass("✓ 'HealthCare Connect' logo is visible");

            // Expected Result 3: "Login" and "Sign Up" buttons are visible in the top right
            ExtentReportManager.logInfo("Expected Result 3: Verify 'Login' and 'Sign Up' buttons are visible in the top right");
            boolean isLoginButtonVisible = homePage.isLoginButtonDisplayed();
            boolean isSignUpButtonVisible = homePage.isSignUpButtonDisplayed();
            Assert.assertTrue(isLoginButtonVisible, "Login button should be visible in the top right");
            Assert.assertTrue(isSignUpButtonVisible, "Sign Up button should be visible in the top right");
            ExtentReportManager.logPass("✓ 'Login' button is visible in the top right");
            ExtentReportManager.logPass("✓ 'Sign Up' button is visible in the top right");

            // Expected Result 4: "Book Appointment" and "Join as Doctor" buttons are visible in the center
            ExtentReportManager.logInfo("Expected Result 4: Verify 'Book Appointment' and 'Join as Doctor' buttons are visible in the center");
            boolean isBookAppointmentVisible = homePage.isBookAppointmentButtonDisplayed();
            boolean isJoinAsDoctorVisible = homePage.isJoinAsDoctorButtonDisplayed();
            Assert.assertTrue(isBookAppointmentVisible, "Book Appointment button should be visible in the center");
            Assert.assertTrue(isJoinAsDoctorVisible, "Join as Doctor button should be visible in the center");
            ExtentReportManager.logPass("✓ 'Book Appointment' button is visible in the center");
            ExtentReportManager.logPass("✓ 'Join as Doctor' button is visible in the center");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-001 - Verify Portal Home Page Redirection & Load");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Home page loaded successfully");
            ExtentReportManager.logPass("  ✓ HealthCare Connect logo is visible");
            ExtentReportManager.logPass("  ✓ Login and Sign Up buttons are visible in top right");
            ExtentReportManager.logPass("  ✓ Book Appointment and Join as Doctor buttons are visible in center");
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        } catch (AssertionError e) {
            ExtentReportManager.logFail("❌ Assertion failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            ExtentReportManager.logFail("❌ Test execution error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
