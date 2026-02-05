package com.qa.automation.tests.logout;

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
 * TC-022: Verify Patient Logout
 * Module/Feature: Patient Security
 * Priority: P2
 * Test Type: Security
 * 
 * Precondition: None
 * 
 * Test Steps:
 * 1. Register a new patient account
 * 2. Login with the registered patient credentials
 * 3. Click the Profile Icon/Name in the top right
 * 4. Click "Logout" from the dropdown
 * 
 * Expected Result:
 * - User is redirected to Home Page
 * - Direct URL access returns to login/home
 * - Action: Logout
 * 
 * Test Data:
 * - Role: Patient
 * - Email: Unique (timestamp-based)
 * - Password: Test@123
 */
public class TC022_PatientLogoutTest extends BaseTest {

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

    @Test(description = "TC-022: Verify Patient Logout")
    public void verifyPatientLogout() {
        try {
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("TC-022: Verify Patient Logout");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // ========== STEP 1: REGISTER PATIENT ==========
            ExtentReportManager.logInfo("Step 1: Register a new patient account");
            
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
            ExtentReportManager.logInfo("  - Email: " + uniqueEmail);
            
            signupPage.clickCreateAccountButton();
            ExtentReportManager.logPass("✓ Clicked 'Create Account' button");
            
            Thread.sleep(3000); // Wait for account creation
            ExtentReportManager.logPass("✓ Patient account created successfully");

            // ========== STEP 2: LOGIN WITH PATIENT CREDENTIALS ==========
            ExtentReportManager.logInfo("Step 2: Login with the registered patient credentials");
            
            homePage.navigateToHomePage();
            homePage.clickLoginButton();
            ExtentReportManager.logPass("✓ Clicked 'Login' button");
            
            loginPage.enterEmail(uniqueEmail)
                    .enterPassword(password)
                    .clickLoginButton();
            ExtentReportManager.logPass("✓ Entered credentials and clicked 'Login'");
            
            Thread.sleep(3000); // Wait for login to complete
            
            String currentUrl = loginPage.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/patient/"), 
                "Patient should be logged in and redirected to patient dashboard");
            ExtentReportManager.logPass("✓ Patient logged in successfully");
            ExtentReportManager.logInfo("  - Current URL: " + currentUrl);

            // ========== STEP 3: CLICK PROFILE ICON ==========
            ExtentReportManager.logInfo("Step 3: Click the Profile Icon/Name in the top right");
            
            logoutPage.clickProfileIcon();
            ExtentReportManager.logPass("✓ Clicked Profile Icon/Name");

            // ========== STEP 4: CLICK LOGOUT ==========
            ExtentReportManager.logInfo("Step 4: Click 'Logout' from the dropdown");
            
            logoutPage.clickLogout();
            ExtentReportManager.logPass("✓ Clicked 'Logout' button");

            // ========== VERIFICATION ==========
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("VERIFICATION: Logout Status");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Verify redirect to Home Page
            boolean isRedirectedToHome = logoutPage.isRedirectedToHomePage();
            Assert.assertTrue(isRedirectedToHome, "User should be redirected to Home Page after logout");
            ExtentReportManager.logPass("✓ User is redirected to Home Page");

            // Verify cannot access patient dashboard after logout
            String protectedUrl = "http://18.142.250.249:5000/patient/dashboard";
            boolean isLogoutSuccessful = logoutPage.isLogoutSuccessful(protectedUrl);
            Assert.assertTrue(isLogoutSuccessful, "User should not be able to access patient dashboard after logout");
            ExtentReportManager.logPass("✓ Direct URL access returns to login/home (logout successful)");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-022 - Verify Patient Logout");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Patient account registered: " + uniqueEmail);
            ExtentReportManager.logPass("  ✓ Patient logged in successfully");
            ExtentReportManager.logPass("  ✓ Logout button clicked");
            ExtentReportManager.logPass("  ✓ User redirected to Home Page");
            ExtentReportManager.logPass("  ✓ Cannot access protected pages after logout");
            ExtentReportManager.logPass("  ✓ Action: Logout - VERIFIED");
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
