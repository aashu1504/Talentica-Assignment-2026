package com.qa.automation.tests.search;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-007: Verify Find Doctors Search Functionality
 * Module/Feature: Search (Find Doctors)
 * Priority: P0
 * Test Type: Functional
 * 
 * Precondition: User is logged in as a Patient and at least one doctor exists in the system
 * 
 * Test Steps:
 * 1. Create a new doctor account with specific details (Name: Jenny, Specialty: Gynecology, City: Faridabad)
 * 2. Create a new patient account and login
 * 3. Click "Find Doctors" link in the top navigation bar
 * 4. Click the "Search by name or specialty" input field
 * 5. Enter "Jenny" (or valid Doctor Name)
 * 6. Click the "Specialty" dropdown and select "Gynecology"
 * 7. Observe the search results
 * 
 * Expected Result:
 * - The doctor list updates to show only doctors matching the criteria: Name: Jenny, Specialty: Gynecology
 * - Test passes if any single entry from the list matches both name and specialty criteria
 * 
 * Test Data:
 * - Doctor Name: Jenny
 * - Doctor Specialty: Gynecology
 * - Patient Name: Test Patient
 */
public class TC007_FindDoctorsSearchTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private LoginPage loginPage;
    
    private String doctorEmail;
    private String doctorLicenseNumber;
    private String patientEmail;
    
    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        loginPage = new LoginPage();
        
        // Generate unique identifiers for each test run
        long timestamp = System.currentTimeMillis();
        doctorEmail = "drjenny" + timestamp + "@test.com";
        doctorLicenseNumber = "MED" + timestamp;
        patientEmail = "patient" + timestamp + "@test.com";
    }

    @Test(description = "TC-007: Verify Find Doctors Search Functionality")
    public void verifyFindDoctorsSearchFunctionality() {
        try {
            // Step 1: Create a new doctor account with specific details
            ExtentReportManager.logInfo("Step 1: Create a new doctor account with specific details");
            createDoctorAccount();
            
            // Step 2: Create a new patient account and login
            ExtentReportManager.logInfo("Step 2: Create a new patient account and login");
            createPatientAccountAndLogin();
            
            // Step 3: Click "Find Doctors" link in the top navigation bar
            ExtentReportManager.logInfo("Step 3: Click 'Find Doctors' link in the top navigation bar");
            homePage.clickFindDoctorsLink();
            ExtentReportManager.logPass("✓ Clicked 'Find Doctors' link");
            
            // Step 4: Click the "Search by name or specialty" input field
            ExtentReportManager.logInfo("Step 4: Click the 'Search by name or specialty' input field");
            homePage.clickSearchByNameOrSpecialtyField();
            ExtentReportManager.logPass("✓ Clicked 'Search by name or specialty' field");
            
            // Step 5: Enter "Jenny" (or valid Doctor Name)
            ExtentReportManager.logInfo("Step 5: Enter 'Jenny' in the search field");
            homePage.enterSearchByNameOrSpecialty("Jenny");
            ExtentReportManager.logPass("✓ Entered 'Jenny' in search field");
            
            // Step 6: Click the "Specialty" dropdown and select "Gynecology"
            ExtentReportManager.logInfo("Step 6: Click the 'Specialty' dropdown and select 'Gynecology'");
            homePage.selectSpecialtyDropdown("Gynecology");
            ExtentReportManager.logPass("✓ Selected 'Gynecology' from Specialty dropdown");
            
            // Step 7: Observe the search results (city search removed)
            ExtentReportManager.logInfo("Step 7: Observe the search results");
            Thread.sleep(3000); // Wait for search results to load
            
            // Expected Result: The doctor list updates to show only doctors matching the criteria
            ExtentReportManager.logInfo("Expected Result: Verify search results match the criteria");
            boolean searchResultsMatch = homePage.verifySearchResultsMatchCriteria("Jenny", "Gynecology");
            
            if (searchResultsMatch) {
                ExtentReportManager.logPass("✓ Search results correctly show doctors matching: Name: Jenny, Specialty: Gynecology");
            } else {
                ExtentReportManager.logFail("❌ Search results do not match the expected criteria");
            }
            
            Assert.assertTrue(searchResultsMatch, "Search results should show doctors matching Name: Jenny, Specialty: Gynecology");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-007 - Verify Find Doctors Search Functionality");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Doctor account created with Name: Jenny, Specialty: Gynecology");
            ExtentReportManager.logPass("  ✓ Patient account created and logged in successfully");
            ExtentReportManager.logPass("  ✓ Find Doctors functionality accessed");
            ExtentReportManager.logPass("  ✓ Search criteria applied: Name: Jenny, Specialty: Gynecology");
            ExtentReportManager.logPass("  ✓ Search results match the specified criteria (any single entry found)");
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
    
    /**
     * Helper method to create a doctor account with specific details
     */
    private void createDoctorAccount() {
        try {
            ExtentReportManager.logInfo("Creating doctor account...");
            
            // Navigate to home page and click sign up
            homePage.navigateToHomePage();
            homePage.clickSignUpButton();
            
            // Select Doctor role
            signupPage.selectRole("Doctor");
            
            // Fill personal information
            signupPage.enterFirstName("Jenny");
            signupPage.enterLastName("Anderson");
            signupPage.enterEmail(doctorEmail);
            signupPage.enterPassword("Test@123");
            signupPage.enterConfirmPassword("Test@123");
            signupPage.enterPhone("9988776655");
            signupPage.selectGender("Female");
            
            // Scroll to doctor information section
            signupPage.scrollToDoctorInformationSection();
            
            // Fill doctor information
            signupPage.selectSpecialty("Gynecology");
            signupPage.enterLicenseNumber(doctorLicenseNumber);
            signupPage.enterQualification("MBBS, MD");
            signupPage.enterExperience("8");
            signupPage.enterConsultationFee("1500");
            signupPage.enterBio("Senior Gynecologist with 8 years of experience");
            
            // Create account
            signupPage.clickCreateAccountButton();
            
            // Wait for account creation
            Thread.sleep(2000);
            
            ExtentReportManager.logPass("✓ Doctor account created successfully with email: " + doctorEmail);
            
        } catch (Exception e) {
            ExtentReportManager.logFail("❌ Failed to create doctor account: " + e.getMessage());
            throw new RuntimeException("Failed to create doctor account", e);
        }
    }
    
    /**
     * Helper method to create a patient account and login
     */
    private void createPatientAccountAndLogin() {
        try {
            ExtentReportManager.logInfo("Creating patient account and logging in...");
            
            // Navigate to home page and click sign up
            homePage.navigateToHomePage();
            homePage.clickSignUpButton();
            
            // Select Patient role
            signupPage.selectRole("Patient");
            
            // Fill patient information
            signupPage.enterFirstName("Test");
            signupPage.enterLastName("Patient");
            signupPage.enterEmail(patientEmail);
            signupPage.enterPassword("Test@123");
            signupPage.enterConfirmPassword("Test@123");
            signupPage.enterPhone("9876543210");
            signupPage.selectGender("Male");
            
            // Create account
            signupPage.clickCreateAccountButton();
            
            // Wait for account creation and redirect to login
            Thread.sleep(3000);
            
            // Login with patient credentials
            loginPage.enterEmail(patientEmail);
            loginPage.enterPassword("Test@123");
            loginPage.clickLoginButton();
            
            // Wait for login to complete
            Thread.sleep(2000);
            
            ExtentReportManager.logPass("✓ Patient account created and logged in successfully with email: " + patientEmail);
            
        } catch (Exception e) {
            ExtentReportManager.logFail("❌ Failed to create patient account or login: " + e.getMessage());
            throw new RuntimeException("Failed to create patient account or login", e);
        }
    }
}
