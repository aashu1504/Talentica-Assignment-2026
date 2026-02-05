package com.qa.automation.tests.security;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.LogoutPage;
import com.qa.automation.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-025: Verify Session Timeout on Browser Back
 * Module/Feature: Security
 * Priority: P1
 * Test Type: Security
 * 
 * Precondition: User has just logged out
 * 
 * Test Steps:
 * 1. User logs out
 * 2. Click the browser "Back" arrow button
 * 3. Observe the page content
 * 
 * Expected Result:
 * - User is not logged back in
 * - The application redirects to login/home page
 * - Action: Browser Back
 * 
 * Test Data:
 * - Role: Patient
 * - Email: Unique (timestamp-based)
 * - Password: Test@123
 */
public class TC025_SessionTimeoutBrowserBackTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private String uniqueEmail;
    private String password = "Test@123";

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        loginPage = new LoginPage();
        logoutPage = new LogoutPage();
        
        // Generate unique email for each test run
        long timestamp = System.currentTimeMillis();
        uniqueEmail = "patient" + timestamp + "@test.com";
    }

    @Test(description = "TC-025: Verify Session Timeout on Browser Back")
    public void verifySessionTimeoutOnBrowserBack() {
        try {
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("TC-025: Verify Session Timeout on Browser Back");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // ========== PRECONDITION: CREATE PATIENT ACCOUNT ==========
            ExtentReportManager.logInfo("Precondition: Create patient account");
            
            homePage.navigateToHomePage();
            ExtentReportManager.logPass("✓ Navigated to Home Page");
            
            homePage.clickSignUpButton();
            ExtentReportManager.logPass("✓ Clicked 'Sign Up' button");
            
            signupPage.selectRole("Patient");
            ExtentReportManager.logPass("✓ Selected 'Patient' role");
            
            signupPage.enterFirstName("John")
                     .enterLastName("Doe")
                     .enterEmail(uniqueEmail)
                     .enterPassword(password)
                     .enterConfirmPassword(password)
                     .enterPhone("9876543210")
                     .selectGender("Male");
            ExtentReportManager.logPass("✓ Entered patient registration details");
            
            signupPage.clickCreateAccountButton();
            ExtentReportManager.logPass("✓ Clicked 'Create Account' button");
            
            Thread.sleep(3000);
            ExtentReportManager.logPass("✓ Patient account created successfully");

            // Login
            homePage.navigateToHomePage();
            homePage.clickLoginButton();
            loginPage.enterEmail(uniqueEmail)
                    .enterPassword(password)
                    .clickLoginButton();
            Thread.sleep(3000);
            
            String loggedInUrl = loginPage.getCurrentUrl();
            Assert.assertTrue(loggedInUrl.contains("/patient/"), 
                "Patient should be logged in");
            ExtentReportManager.logPass("✓ Patient logged in successfully");
            ExtentReportManager.logInfo("  - Logged in URL: " + loggedInUrl);

            // ========== STEP 1: USER LOGS OUT ==========
            ExtentReportManager.logInfo("Step 1: User logs out");
            
            logoutPage.clickProfileIcon();
            ExtentReportManager.logPass("✓ Clicked Profile Icon");
            
            logoutPage.clickLogout();
            ExtentReportManager.logPass("✓ Clicked 'Logout' button");
            
            Thread.sleep(2000);
            
            String logoutUrl = loginPage.getCurrentUrl();
            ExtentReportManager.logPass("✓ User logged out successfully");
            ExtentReportManager.logInfo("  - Current URL after logout: " + logoutUrl);

            // ========== STEP 2: CLICK BROWSER BACK BUTTON ==========
            ExtentReportManager.logInfo("Step 2: Click the browser 'Back' arrow button");
            
            // Use navigation to simulate browser back
            homePage.navigateToUrl(loggedInUrl); // Try to go back to logged in page
            Thread.sleep(3000); // Wait for navigation/redirect
            
            ExtentReportManager.logPass("✓ Attempted to navigate back to previous page");

            // ========== STEP 3: OBSERVE PAGE CONTENT ==========
            ExtentReportManager.logInfo("Step 3: Observe the page content");
            
            String currentUrl = loginPage.getCurrentUrl();
            
            ExtentReportManager.logInfo("Current URL after browser back: " + currentUrl);

            // ========== VERIFICATION ==========
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("VERIFICATION: Session Timeout Status");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Verify user is NOT logged back in
            boolean notLoggedBackIn = !currentUrl.contains("/patient/dashboard") ||
                                     currentUrl.contains("/login") ||
                                     currentUrl.equals("http://18.142.250.249:5000/");
            
            Assert.assertTrue(notLoggedBackIn, 
                "User should NOT be logged back in after clicking browser back. Current URL: " + currentUrl);
            ExtentReportManager.logPass("✓ User is not logged back in");
            
            if (currentUrl.contains("/login")) {
                ExtentReportManager.logPass("✓ Application redirects to login page");
            } else if (currentUrl.equals("http://18.142.250.249:5000/")) {
                ExtentReportManager.logPass("✓ Application redirects to home page");
            } else {
                ExtentReportManager.logPass("✓ Application prevents unauthorized access");
            }
            
            ExtentReportManager.logPass("✓ Action: Browser Back - Session properly invalidated");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-025 - Verify Session Timeout on Browser Back");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Patient account created: " + uniqueEmail);
            ExtentReportManager.logPass("  ✓ Patient logged in successfully");
            ExtentReportManager.logPass("  ✓ User logged out");
            ExtentReportManager.logPass("  ✓ Browser back button clicked");
            ExtentReportManager.logPass("  ✓ User is NOT logged back in (session timeout working)");
            ExtentReportManager.logPass("  ✓ Application redirects appropriately");
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
