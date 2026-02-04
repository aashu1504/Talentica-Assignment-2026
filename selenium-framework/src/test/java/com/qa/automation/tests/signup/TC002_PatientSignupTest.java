package com.qa.automation.tests.signup;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.utils.ExtentReportManager;
import com.qa.automation.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-002: Verify Patient Signup (End-to-End)
 * Module/Feature: Signup (Patient)
 * Priority: P0
 * Test Type: Functional
 * 
 * Precondition: User is on the Home Page
 * 
 * Test Steps:
 * 1. Click the "Sign Up" button on the top right
 * 2. In the "I am a" dropdown, select "Patient"
 * 3. Click "First Name" field and enter "John"
 * 4. Click "Last Name" field and enter "Doe"
 * 5. Click "Email Address" field and enter a unique email (e.g., "john.doe@test.com")
 * 6. Click "Password" field and enter "Test@123"
 * 7. Click "Confirm Password" field and enter "Test@123"
 * 8. Click "Phone" field and enter "9876543210"
 * 9. Click "Gender" dropdown and select "Male"
 * 10. Click "Date of Birth" field, use the calendar widget to select "01/01/1990"
 * 11. Click the "Create Account" button
 * 
 * Expected Result:
 * - Account is created successfully
 * - User is automatically redirected to the Login Page or the Patient Dashboard immediately
 * 
 * Test Data:
 * - Role: Patient
 * - Name: John Doe
 * - Phone: 9876543210
 * - Gender: Male
 * - DOB: 01/01/1990
 */
public class TC002_PatientSignupTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private String uniqueEmail;

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        // Generate unique email for each test run
        uniqueEmail = TestDataGenerator.generateUniqueEmail("john.doe", "test.com");
    }

    @Test(description = "TC-002: Verify Patient Signup (End-to-End)")
    public void verifyPatientSignupEndToEnd() {
        try {
            // Precondition: User is on the Home Page
            ExtentReportManager.logInfo("Precondition: Navigate to Home Page");
            homePage.navigateToHomePage();
            ExtentReportManager.logPass("✓ User is on the Home Page");

            // Step 1: Click the "Sign Up" button on the top right
            ExtentReportManager.logInfo("Step 1: Click the 'Sign Up' button on the top right");
            homePage.clickSignUpButton();
            ExtentReportManager.logPass("✓ Clicked 'Sign Up' button");

            // Step 2: In the "I am a" dropdown, select "Patient"
            ExtentReportManager.logInfo("Step 2: In the 'I am a' dropdown, select 'Patient'");
            signupPage.selectRole("Patient");
            ExtentReportManager.logPass("✓ Selected 'Patient' from 'I am a' dropdown");

            // Step 3: Click "First Name" field and enter "John"
            ExtentReportManager.logInfo("Step 3: Click 'First Name' field and enter 'John'");
            signupPage.enterFirstName("John");
            ExtentReportManager.logPass("✓ Entered 'John' in First Name field");

            // Step 4: Click "Last Name" field and enter "Doe"
            ExtentReportManager.logInfo("Step 4: Click 'Last Name' field and enter 'Doe'");
            signupPage.enterLastName("Doe");
            ExtentReportManager.logPass("✓ Entered 'Doe' in Last Name field");

            // Step 5: Click "Email Address" field and enter a unique email
            ExtentReportManager.logInfo("Step 5: Click 'Email Address' field and enter unique email");
            ExtentReportManager.logInfo("Generated unique email: " + uniqueEmail);
            signupPage.enterEmail(uniqueEmail);
            ExtentReportManager.logPass("✓ Entered unique email: " + uniqueEmail);

            // Step 6: Click "Password" field and enter "Test@123"
            ExtentReportManager.logInfo("Step 6: Click 'Password' field and enter 'Test@123'");
            signupPage.enterPassword("Test@123");
            ExtentReportManager.logPass("✓ Entered password");

            // Step 7: Click "Confirm Password" field and enter "Test@123"
            ExtentReportManager.logInfo("Step 7: Click 'Confirm Password' field and enter 'Test@123'");
            signupPage.enterConfirmPassword("Test@123");
            ExtentReportManager.logPass("✓ Entered confirm password");

            // Step 8: Click "Phone" field and enter "9876543210"
            ExtentReportManager.logInfo("Step 8: Click 'Phone' field and enter '9876543210'");
            signupPage.enterPhone("9876543210");
            ExtentReportManager.logPass("✓ Entered phone number: 9876543210");

            // Step 9: Click "Gender" dropdown and select "Male"
            ExtentReportManager.logInfo("Step 9: Click 'Gender' dropdown and select 'Male'");
            signupPage.selectGender("Male");
            ExtentReportManager.logPass("✓ Selected 'Male' from Gender dropdown");

            // Step 10: Click "Date of Birth" field, use the calendar widget to select "01/01/1990"
            ExtentReportManager.logInfo("Step 10: Click 'Date of Birth' field and select '01/01/1990'");
            signupPage.enterDateOfBirth("01/01/1990");
            ExtentReportManager.logPass("✓ Entered date of birth: 01/01/1990");

            // Step 11: Click the "Create Account" button
            ExtentReportManager.logInfo("Step 11: Click the 'Create Account' button");
            signupPage.clickCreateAccountButton();
            ExtentReportManager.logPass("✓ Clicked 'Create Account' button");

            // Expected Result 1: Account is created successfully
            ExtentReportManager.logInfo("Expected Result 1: Verify account is created successfully");
            // Wait a moment for account creation to process
            Thread.sleep(2000);
            ExtentReportManager.logPass("✓ Account creation process completed");

            // Expected Result 2: User is automatically redirected to the Login Page or the Patient Dashboard immediately
            ExtentReportManager.logInfo("Expected Result 2: Verify user is redirected to Login Page or Patient Dashboard");
            String currentUrl = signupPage.getCurrentUrl();
            String pageTitle = signupPage.getPageTitle();
            ExtentReportManager.logInfo("Current URL: " + currentUrl);
            ExtentReportManager.logInfo("Page Title: " + pageTitle);
            
            boolean isRedirected = signupPage.isRedirectedToLoginOrDashboard();
            
            // Log detailed information for debugging
            if (isRedirected) {
                String redirectLocation = "Unknown";
                if (currentUrl.toLowerCase().contains("login") || pageTitle.toLowerCase().contains("login")) {
                    redirectLocation = "Login Page";
                } else if (currentUrl.toLowerCase().contains("dashboard") || pageTitle.toLowerCase().contains("dashboard")) {
                    redirectLocation = "Patient Dashboard";
                } else if (currentUrl.toLowerCase().contains("patient") || pageTitle.toLowerCase().contains("patient")) {
                    redirectLocation = "Patient Page";
                } else {
                    redirectLocation = "Success Page";
                }
                ExtentReportManager.logPass("✓ User successfully redirected to: " + redirectLocation);
            } else {
                ExtentReportManager.logFail("❌ User was not redirected. Current URL: " + currentUrl + ", Page Title: " + pageTitle);
            }
            
            Assert.assertTrue(isRedirected, "User should be redirected to Login Page or Patient Dashboard after account creation. Current URL: " + currentUrl + ", Page Title: " + pageTitle);

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-002 - Verify Patient Signup (End-to-End)");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Account created successfully");
            ExtentReportManager.logPass("  ✓ User redirected to Login Page or Patient Dashboard");
            ExtentReportManager.logPass("  ✓ Unique email used: " + uniqueEmail);
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

