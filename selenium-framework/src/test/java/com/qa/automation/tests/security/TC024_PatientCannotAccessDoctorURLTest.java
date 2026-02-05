package com.qa.automation.tests.security;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-024: Verify RBAC: Patient Cannot Access Doctor URL
 * Module/Feature: Security
 * Priority: P0
 * Test Type: Security
 * 
 * Precondition: Patient is logged in
 * 
 * Test Steps:
 * 1. Login as a Patient
 * 2. Click the browser address bar
 * 3. Enter the doctor dashboard URL: http://18.142.250.249:5000/doctor/dashboard
 * 4. Press 'Enter'
 * 
 * Expected Result:
 * - Access is denied
 * - The user is redirected back to patient dashboard or login page
 * - URL: /doctor/dashboard is not accessible
 * 
 * Test Data:
 * - Role: Patient
 * - Email: Unique (timestamp-based)
 * - Password: Test@123
 * - Doctor URL: http://18.142.250.249:5000/doctor/dashboard
 */
public class TC024_PatientCannotAccessDoctorURLTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private String uniqueEmail;
    private String password = "Test@123";
    private String doctorDashboardUrl = "http://18.142.250.249:5000/doctor/dashboard";

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        loginPage = new LoginPage();
        
        // Generate unique email for each test run
        long timestamp = System.currentTimeMillis();
        uniqueEmail = "patient" + timestamp + "@test.com";
    }

    @Test(description = "TC-024: Verify RBAC: Patient Cannot Access Doctor URL")
    public void verifyPatientCannotAccessDoctorURL() {
        try {
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("TC-024: Verify RBAC: Patient Cannot Access Doctor URL");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // ========== PRECONDITION: CREATE AND LOGIN AS PATIENT ==========
            ExtentReportManager.logInfo("Precondition: Create patient account and login");
            
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

            // ========== STEP 1: LOGIN AS PATIENT ==========
            ExtentReportManager.logInfo("Step 1: Login as a Patient");
            
            homePage.navigateToHomePage();
            homePage.clickLoginButton();
            ExtentReportManager.logPass("✓ Clicked 'Login' button");
            
            loginPage.enterEmail(uniqueEmail)
                    .enterPassword(password)
                    .clickLoginButton();
            ExtentReportManager.logPass("✓ Entered credentials and clicked 'Login'");
            
            Thread.sleep(3000);
            
            String patientUrl = loginPage.getCurrentUrl();
            Assert.assertTrue(patientUrl.contains("/patient/"), 
                "Patient should be logged in to patient dashboard");
            ExtentReportManager.logPass("✓ Patient logged in successfully");
            ExtentReportManager.logInfo("  - Current URL: " + patientUrl);

            // ========== STEP 2-4: TRY TO ACCESS DOCTOR DASHBOARD URL ==========
            ExtentReportManager.logInfo("Step 2-4: Click browser address bar, enter doctor URL, and press Enter");
            ExtentReportManager.logInfo("Attempting to access: " + doctorDashboardUrl);
            
            // Navigate to doctor dashboard URL using page object
            homePage.navigateToUrl(doctorDashboardUrl);
            Thread.sleep(3000); // Wait for redirect or access denial
            
            String currentUrl = loginPage.getCurrentUrl();
            ExtentReportManager.logInfo("Current URL after attempting access: " + currentUrl);

            // ========== VERIFICATION ==========
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("VERIFICATION: Access Control Status");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Verify access is denied - user should NOT be on doctor dashboard
            boolean accessDenied = !currentUrl.contains("/doctor/dashboard") || 
                                  currentUrl.contains("/patient/") ||
                                  currentUrl.contains("/login") ||
                                  currentUrl.equals("http://18.142.250.249:5000/");
            
            Assert.assertTrue(accessDenied, 
                "Patient should NOT be able to access doctor dashboard. Current URL: " + currentUrl);
            ExtentReportManager.logPass("✓ Access is denied");
            
            if (currentUrl.contains("/patient/")) {
                ExtentReportManager.logPass("✓ User is redirected back to patient dashboard");
            } else if (currentUrl.contains("/login")) {
                ExtentReportManager.logPass("✓ User is redirected to login page");
            } else {
                ExtentReportManager.logPass("✓ User is redirected to home page");
            }
            
            ExtentReportManager.logPass("✓ URL /doctor/dashboard is not accessible to patient");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-024 - Verify RBAC: Patient Cannot Access Doctor URL");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Patient account created: " + uniqueEmail);
            ExtentReportManager.logPass("  ✓ Patient logged in successfully");
            ExtentReportManager.logPass("  ✓ Attempted to access: " + doctorDashboardUrl);
            ExtentReportManager.logPass("  ✓ Access denied (RBAC working correctly)");
            ExtentReportManager.logPass("  ✓ User redirected to: " + currentUrl);
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
