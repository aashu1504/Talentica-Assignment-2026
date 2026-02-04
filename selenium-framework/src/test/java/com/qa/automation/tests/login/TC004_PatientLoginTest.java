package com.qa.automation.tests.login;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.utils.ExtentReportManager;
import com.qa.automation.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-004: Verify Patient Login Functionality
 * Module/Feature: Login (Patient)
 * Priority: P0
 * Test Type: Functional
 * 
 * Precondition: Patient Account exists (created within this test for independence)
 * 
 * Test Steps:
 * 1. Click the "Login" button on the Home Page
 * 2. Click the "Email Address" field and enter the registered patient email
 * 3. Click the "Password" field and enter the valid password ("Test@123")
 * 4. Click the "Login" button
 * 5. Observe the redirection and header
 * 
 * Expected Result:
 * - Login is successful
 * - User is redirected to the "Patient Dashboard"
 * - Header displays "Welcome Back" and Patient First and Last Name
 * 
 * Test Data:
 * - User: john.doe@test.com (or dynamically generated)
 * - Pass: Test@123
 * 
 * Note: This test is independent and creates its own patient account first
 */
public class TC004_PatientLoginTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private String patientEmail;
    private String patientPassword = "Test@123";
    private String firstName = "John";
    private String lastName = "Doe";

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        loginPage = new LoginPage();
        // Generate unique email for this test to ensure independence
        patientEmail = TestDataGenerator.generateUniqueEmail("john.doe", "test.com");
    }

    @Test(description = "TC-004: Verify Patient Login Functionality")
    public void verifyPatientLoginFunctionality() {
        try {
            // Precondition: Create Patient Account first (for test independence)
            ExtentReportManager.logInfo("Precondition: Create Patient Account for test independence");
            homePage.navigateToHomePage();
            homePage.clickSignUpButton();
            
            signupPage.selectRole("Patient")
                     .enterFirstName(firstName)
                     .enterLastName(lastName)
                     .enterEmail(patientEmail)
                     .enterPassword(patientPassword)
                     .enterConfirmPassword(patientPassword)
                     .enterPhone("9876543210")
                     .selectGender("Male")
                     .enterDateOfBirth("01/01/1990")
                     .clickCreateAccountButton();
            
            // Wait for account creation and redirection
            Thread.sleep(2000);
            ExtentReportManager.logPass("✓ Patient account created successfully with email: " + patientEmail);
            
            // Navigate back to home page for login
            ExtentReportManager.logInfo("Navigating back to Home Page for login");
            homePage.navigateToHomePage();
            ExtentReportManager.logPass("✓ Navigated to Home Page");

            // Step 1: Click the "Login" button on the Home Page
            ExtentReportManager.logInfo("Step 1: Click the 'Login' button on the Home Page");
            homePage.clickLoginButton();
            ExtentReportManager.logPass("✓ Clicked 'Login' button");

            // Step 2: Click the "Email Address" field and enter the registered patient email
            ExtentReportManager.logInfo("Step 2: Click the 'Email Address' field and enter the registered patient email");
            ExtentReportManager.logInfo("Using email: " + patientEmail);
            loginPage.enterEmail(patientEmail);
            ExtentReportManager.logPass("✓ Entered patient email: " + patientEmail);

            // Step 3: Click the "Password" field and enter the valid password
            ExtentReportManager.logInfo("Step 3: Click the 'Password' field and enter the valid password");
            loginPage.enterPassword(patientPassword);
            ExtentReportManager.logPass("✓ Entered password");

            // Step 4: Click the "Login" button
            ExtentReportManager.logInfo("Step 4: Click the 'Login' button");
            loginPage.clickLoginButton();
            ExtentReportManager.logPass("✓ Clicked 'Login' button");

            // Step 5: Observe the redirection and header
            ExtentReportManager.logInfo("Step 5: Observe the redirection and header");

            // Expected Result 1: Login is successful
            ExtentReportManager.logInfo("Expected Result 1: Verify login is successful");
            String currentUrl = loginPage.getCurrentUrl();
            String pageTitle = loginPage.getPageTitle();
            ExtentReportManager.logInfo("Current URL: " + currentUrl);
            ExtentReportManager.logInfo("Page Title: " + pageTitle);
            ExtentReportManager.logPass("✓ Login process completed successfully");

            // Expected Result 2: User is redirected to the "Patient Dashboard"
            ExtentReportManager.logInfo("Expected Result 2: Verify user is redirected to the 'Patient Dashboard'");
            boolean isRedirected = loginPage.isRedirectedToPatientDashboard();
            
            if (isRedirected) {
                ExtentReportManager.logPass("✓ User successfully redirected to Patient Dashboard");
            } else {
                ExtentReportManager.logFail("❌ User was not redirected to Patient Dashboard. Current URL: " + currentUrl);
            }
            
            Assert.assertTrue(isRedirected, "User should be redirected to Patient Dashboard. Current URL: " + currentUrl + ", Page Title: " + pageTitle);

            // Expected Result 3: Header displays "Welcome Back" and Patient First and Last Name
            ExtentReportManager.logInfo("Expected Result 3: Verify header displays 'Welcome Back' and Patient First and Last Name");
            
            boolean hasWelcomeBack = loginPage.isWelcomeBackMessageDisplayed();
            if (hasWelcomeBack) {
                ExtentReportManager.logPass("✓ 'Welcome Back' message is displayed in header");
            } else {
                ExtentReportManager.logFail("❌ 'Welcome Back' message is not displayed in header");
            }
            
            boolean hasPatientName = loginPage.isPatientNameDisplayed(firstName, lastName);
            if (hasPatientName) {
                ExtentReportManager.logPass("✓ Patient name (" + firstName + " " + lastName + ") is displayed in header");
            } else {
                ExtentReportManager.logFail("❌ Patient name (" + firstName + " " + lastName + ") is not displayed in header");
            }
            
            Assert.assertTrue(hasWelcomeBack, "'Welcome Back' message should be displayed in header");
            Assert.assertTrue(hasPatientName, "Patient name (" + firstName + " " + lastName + ") should be displayed in header");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-004 - Verify Patient Login Functionality");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Login is successful");
            ExtentReportManager.logPass("  ✓ User redirected to Patient Dashboard");
            ExtentReportManager.logPass("  ✓ Header displays 'Welcome Back' and Patient name");
            ExtentReportManager.logPass("  ✓ Test is independent (account created within test)");
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

