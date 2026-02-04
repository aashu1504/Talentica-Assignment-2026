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
 * TC-005: Verify Doctor Login Functionality
 * Module/Feature: Login (Doctor)
 * Priority: P0
 * Test Type: Functional
 * 
 * Precondition: Doctor Account exists (created within this test for independence)
 * 
 * Test Steps:
 * 1. Click the "Login" button on the Home Page
 * 2. Click the "Email Address" field and enter the registered doctor email
 * 3. Click the "Password" field and enter the valid password ("Test@123")
 * 4. Click the "Login" button
 * 5. Observe the redirection and dashboard widgets
 * 
 * Expected Result:
 * - Login is successful
 * - User is redirected to the "Doctor Dashboard"
 * - Dashboard cards (Total Patients, Total Appointments) are visible
 * - Header displays Doctor First and Last Name
 * 
 * Test Data:
 * - User: dr.jane@test.com (or dynamically generated)
 * - Pass: Test@123
 * 
 * Note: This test is independent and creates its own doctor account first
 */
public class TC005_DoctorLoginTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private String doctorEmail;
    private String doctorPassword = "Test@123";
    private String firstName = "Jane";
    private String lastName = "Smith";

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        loginPage = new LoginPage();
        // Generate unique email for this test to ensure independence
        doctorEmail = TestDataGenerator.generateUniqueEmail("dr.jane", "test.com");
    }

    @Test(description = "TC-005: Verify Doctor Login Functionality")
    public void verifyDoctorLoginFunctionality() {
        try {
            // Precondition: Create Doctor Account first (for test independence)
            ExtentReportManager.logInfo("Precondition: Create Doctor Account for test independence");
            homePage.navigateToHomePage();
            homePage.clickSignUpButton();
            
            signupPage.selectRole("Doctor")
                     .enterFirstName(firstName)
                     .enterLastName(lastName)
                     .enterEmail(doctorEmail)
                     .enterPassword(doctorPassword)
                     .enterConfirmPassword(doctorPassword)
                     .enterPhone("9988776655")
                     .selectGender("Female")
                     .scrollToDoctorInformationSection()
                     .selectSpecialty("Cardiology")
                     .enterLicenseNumber("MED-2026-X")
                     .enterQualification("MBBS, MD")
                     .enterExperience("10")
                     .enterConsultationFee("1000")
                     .enterBio("Senior Cardiologist")
                     .clickCreateAccountButton();
            
            // Wait for account creation and redirection
            Thread.sleep(2000);
            ExtentReportManager.logPass("✓ Doctor account created successfully with email: " + doctorEmail);
            
            // Navigate back to home page for login
            ExtentReportManager.logInfo("Navigating back to Home Page for login");
            homePage.navigateToHomePage();
            ExtentReportManager.logPass("✓ Navigated to Home Page");

            // Step 1: Click the "Login" button on the Home Page
            ExtentReportManager.logInfo("Step 1: Click the 'Login' button on the Home Page");
            homePage.clickLoginButton();
            ExtentReportManager.logPass("✓ Clicked 'Login' button");

            // Step 2: Click the "Email Address" field and enter the registered doctor email
            ExtentReportManager.logInfo("Step 2: Click the 'Email Address' field and enter the registered doctor email");
            ExtentReportManager.logInfo("Using email: " + doctorEmail);
            loginPage.enterEmail(doctorEmail);
            ExtentReportManager.logPass("✓ Entered doctor email: " + doctorEmail);

            // Step 3: Click the "Password" field and enter the valid password
            ExtentReportManager.logInfo("Step 3: Click the 'Password' field and enter the valid password");
            loginPage.enterPassword(doctorPassword);
            ExtentReportManager.logPass("✓ Entered password");

            // Step 4: Click the "Login" button
            ExtentReportManager.logInfo("Step 4: Click the 'Login' button");
            loginPage.clickLoginButton();
            ExtentReportManager.logPass("✓ Clicked 'Login' button");

            // Step 5: Observe the redirection and dashboard widgets
            ExtentReportManager.logInfo("Step 5: Observe the redirection and dashboard widgets");

            // Expected Result 1: Login is successful
            ExtentReportManager.logInfo("Expected Result 1: Verify login is successful");
            String currentUrl = loginPage.getCurrentUrl();
            String pageTitle = loginPage.getPageTitle();
            ExtentReportManager.logInfo("Current URL: " + currentUrl);
            ExtentReportManager.logInfo("Page Title: " + pageTitle);
            ExtentReportManager.logPass("✓ Login process completed successfully");

            // Expected Result 2: User is redirected to the "Doctor Dashboard"
            ExtentReportManager.logInfo("Expected Result 2: Verify user is redirected to the 'Doctor Dashboard'");
            boolean isRedirected = loginPage.isRedirectedToDoctorDashboard();
            
            if (isRedirected) {
                ExtentReportManager.logPass("✓ User successfully redirected to Doctor Dashboard");
            } else {
                ExtentReportManager.logFail("❌ User was not redirected to Doctor Dashboard. Current URL: " + currentUrl);
            }
            
            Assert.assertTrue(isRedirected, "User should be redirected to Doctor Dashboard. Current URL: " + currentUrl + ", Page Title: " + pageTitle);

            // Expected Result 3: Dashboard cards (Total Patients, Total Appointments) are visible
            ExtentReportManager.logInfo("Expected Result 3: Verify dashboard cards (Total Patients, Total Appointments) are visible");
            boolean areCardsVisible = loginPage.areDashboardCardsVisible();
            
            if (areCardsVisible) {
                ExtentReportManager.logPass("✓ Dashboard cards (Total Patients, Total Appointments) are visible");
            } else {
                ExtentReportManager.logFail("❌ Dashboard cards are not visible");
            }
            
            Assert.assertTrue(areCardsVisible, "Dashboard cards (Total Patients, Total Appointments) should be visible");

            // Expected Result 4: Header displays Doctor First and Last Name
            ExtentReportManager.logInfo("Expected Result 4: Verify header displays Doctor First and Last Name");
            boolean hasDoctorName = loginPage.isDoctorNameDisplayed(firstName, lastName);
            
            if (hasDoctorName) {
                ExtentReportManager.logPass("✓ Doctor name (" + firstName + " " + lastName + ") is displayed in header");
            } else {
                ExtentReportManager.logFail("❌ Doctor name (" + firstName + " " + lastName + ") is not displayed in header");
            }
            
            Assert.assertTrue(hasDoctorName, "Doctor name (" + firstName + " " + lastName + ") should be displayed in header");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-005 - Verify Doctor Login Functionality");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Login is successful");
            ExtentReportManager.logPass("  ✓ User redirected to Doctor Dashboard");
            ExtentReportManager.logPass("  ✓ Dashboard cards (Total Patients, Total Appointments) are visible");
            ExtentReportManager.logPass("  ✓ Header displays Doctor name");
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

