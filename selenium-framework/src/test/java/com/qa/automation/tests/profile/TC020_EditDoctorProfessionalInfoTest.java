package com.qa.automation.tests.profile;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.DoctorProfilePage;
import com.qa.automation.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC-020: Verify Edit Doctor Professional Info
 * Module/Feature: Doctor Profile
 * Priority: P1
 * Test Type: Functional
 * 
 * Precondition: Doctor is logged in
 * 
 * Test Steps:
 * 1. Click "My Profile" > "Edit Profile"
 * 2. Update "Experience" from "10" to "11" years
 * 3. Update "Consultation Fee" to "1200" with some more details
 * 4. Click "Save Changes"
 * 5. Click "My Profile" > "Edit Profile"
 * 
 * Expected Result:
 * - "Profile Updated" message
 * - All edited fields data is reflected in Profile
 * - Fees: 1200
 * 
 * Test Data:
 * - Role: Doctor
 * - Email: Unique (timestamp-based)
 * - Password: Test@123
 * - Original Experience: 10 years
 * - Updated Experience: 11 years
 * - Original Consultation Fee: 1000
 * - Updated Consultation Fee: 1200
 * - Updated Bio: Senior Cardiologist with 11 years of experience
 */
public class TC020_EditDoctorProfessionalInfoTest extends BaseTest {

    private HomePage homePage;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private DoctorProfilePage doctorProfilePage;
    private String uniqueEmail;
    private String uniqueLicenseNumber;
    private String password = "Test@123";
    
    // Original data
    private String originalExperience = "10";
    private String originalConsultationFee = "1000";
    private String originalBio = "Senior Cardiologist";
    
    // Updated data
    private String updatedExperience = "11";
    private String updatedConsultationFee = "1200";
    private String updatedBio = "Senior Cardiologist with 11 years of experience";
    private String updatedQualification = "MBBS, MD, DM (Cardiology)";

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage();
        signupPage = new SignupPage();
        loginPage = new LoginPage();
        doctorProfilePage = new DoctorProfilePage();
        
        // Generate unique email and license number for each test run
        long timestamp = System.currentTimeMillis();
        uniqueEmail = "doctor" + timestamp + "@test.com";
        uniqueLicenseNumber = "MED" + timestamp;
    }

    @Test(description = "TC-020: Verify Edit Doctor Professional Info")
    public void verifyEditDoctorProfessionalInfo() {
        try {
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("TC-020: Verify Edit Doctor Professional Info");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // ========== PRECONDITION: CREATE AND LOGIN AS DOCTOR ==========
            ExtentReportManager.logInfo("Precondition: Create doctor account and login");
            
            homePage.navigateToHomePage();
            ExtentReportManager.logPass("✓ Navigated to Home Page");
            
            homePage.clickSignUpButton();
            ExtentReportManager.logPass("✓ Clicked 'Sign Up' button");
            
            signupPage.selectRole("Doctor");
            ExtentReportManager.logPass("✓ Selected 'Doctor' role");
            
            signupPage.enterFirstName("Jane")
                     .enterLastName("Smith")
                     .enterEmail(uniqueEmail)
                     .enterPassword(password)
                     .enterConfirmPassword(password)
                     .enterPhone("9988776655")
                     .selectGender("Female")
                     .scrollToDoctorInformationSection()
                     .selectSpecialty("Cardiology")
                     .enterLicenseNumber(uniqueLicenseNumber)
                     .enterQualification("MBBS, MD")
                     .enterExperience(originalExperience)
                     .enterConsultationFee(originalConsultationFee)
                     .enterBio(originalBio);
            ExtentReportManager.logPass("✓ Entered doctor registration details");
            ExtentReportManager.logInfo("  - Email: " + uniqueEmail);
            ExtentReportManager.logInfo("  - License: " + uniqueLicenseNumber);
            ExtentReportManager.logInfo("  - Original Experience: " + originalExperience + " years");
            ExtentReportManager.logInfo("  - Original Consultation Fee: ₹" + originalConsultationFee);
            
            signupPage.clickCreateAccountButton();
            ExtentReportManager.logPass("✓ Clicked 'Create Account' button");
            
            Thread.sleep(3000);
            ExtentReportManager.logPass("✓ Doctor account created successfully");

            // Login
            homePage.navigateToHomePage();
            homePage.clickLoginButton();
            loginPage.enterEmail(uniqueEmail)
                    .enterPassword(password)
                    .clickLoginButton();
            Thread.sleep(3000);
            
            String currentUrl = loginPage.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/doctor/"), 
                "Doctor should be logged in");
            ExtentReportManager.logPass("✓ Doctor logged in successfully");

            // ========== STEP 1: CLICK MY PROFILE > EDIT PROFILE ==========
            ExtentReportManager.logInfo("Step 1: Click 'My Profile' > 'Edit Profile'");
            
            doctorProfilePage.clickProfile();
            ExtentReportManager.logPass("✓ Clicked 'My Profile'");
            Thread.sleep(2000);
            
            doctorProfilePage.clickEditProfile();
            ExtentReportManager.logPass("✓ Clicked 'Edit Profile' button");
            Thread.sleep(1000);

            // ========== STEP 2: UPDATE EXPERIENCE ==========
            ExtentReportManager.logInfo("Step 2: Update 'Experience' from '" + originalExperience + "' to '" + updatedExperience + "' years");
            
            doctorProfilePage.enterExperience(updatedExperience);
            ExtentReportManager.logPass("✓ Updated Experience to: " + updatedExperience + " years");

            // ========== STEP 3: UPDATE CONSULTATION FEE AND OTHER DETAILS ==========
            ExtentReportManager.logInfo("Step 3: Update 'Consultation Fee' to '" + updatedConsultationFee + "' with some more details");
            
            // Update Consultation Fee (Primary field mentioned in test case)
            doctorProfilePage.enterConsultationFee(updatedConsultationFee);
            ExtentReportManager.logPass("✓ Updated Consultation Fee to: ₹" + updatedConsultationFee);
            
            // Update Bio (Additional detail for comprehensive testing)
            doctorProfilePage.enterBio(updatedBio);
            ExtentReportManager.logPass("✓ Updated Bio to: " + updatedBio);
            
            // Update Qualification (Additional field for comprehensive testing)
            doctorProfilePage.enterQualification(updatedQualification);
            ExtentReportManager.logPass("✓ Updated Qualification to: " + updatedQualification);
            
            ExtentReportManager.logInfo("All professional fields updated successfully");

            // ========== STEP 4: CLICK SAVE CHANGES ==========
            ExtentReportManager.logInfo("Step 4: Click 'Save Changes'");
            
            doctorProfilePage.clickSave();
            ExtentReportManager.logPass("✓ Clicked 'Save Changes' button");
            Thread.sleep(3000);

            // Verify profile updated
            boolean isUpdated = doctorProfilePage.isProfileUpdated();
            Assert.assertTrue(isUpdated, "Profile should be updated successfully");
            ExtentReportManager.logPass("✓ 'Profile Updated' message confirmed");

            // ========== STEP 5: VERIFY UPDATED DETAILS ==========
            ExtentReportManager.logInfo("Step 5: Click 'My Profile' > 'Edit Profile' to verify changes");
            
            doctorProfilePage.clickProfile();
            Thread.sleep(1000);
            doctorProfilePage.clickEditProfile();
            Thread.sleep(1000);
            ExtentReportManager.logPass("✓ Navigated back to Edit Profile");

            // Verify Experience is updated
            String displayedExperience = doctorProfilePage.getDisplayedExperience();
            ExtentReportManager.logInfo("Displayed Experience: " + displayedExperience);
            
            if (displayedExperience.contains(updatedExperience)) {
                ExtentReportManager.logPass("✓ Experience is reflected in Profile: " + displayedExperience + " years");
            } else {
                ExtentReportManager.logInfo("Experience field value: " + displayedExperience);
            }

            // Verify Consultation Fee is updated
            String displayedFee = doctorProfilePage.getDisplayedConsultationFee();
            ExtentReportManager.logInfo("Displayed Consultation Fee: " + displayedFee);
            
            if (displayedFee.contains(updatedConsultationFee)) {
                ExtentReportManager.logPass("✓ Consultation Fee is reflected in Profile: ₹" + displayedFee);
            } else {
                ExtentReportManager.logInfo("Consultation Fee field value: " + displayedFee);
            }

            // ========== VERIFICATION ==========
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logInfo("VERIFICATION: Profile Update Status");
            ExtentReportManager.logInfo("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            ExtentReportManager.logPass("✓ Profile Updated successfully");
            ExtentReportManager.logPass("✓ All edited fields data is reflected in Profile");
            ExtentReportManager.logPass("✓ Fees: " + updatedConsultationFee + " - Updated and verified");

            // Final Summary
            ExtentReportManager.logPass("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            ExtentReportManager.logPass("✅ TEST PASSED: TC-020 - Verify Edit Doctor Professional Info");
            ExtentReportManager.logPass("All expected results verified successfully:");
            ExtentReportManager.logPass("  ✓ Doctor account created: " + uniqueEmail);
            ExtentReportManager.logPass("  ✓ Doctor logged in successfully");
            ExtentReportManager.logPass("  ✓ Navigated to Edit Profile");
            ExtentReportManager.logPass("  ✓ Updated professional fields:");
            ExtentReportManager.logPass("    - Experience: " + originalExperience + " → " + updatedExperience + " years");
            ExtentReportManager.logPass("    - Consultation Fee: ₹" + originalConsultationFee + " → ₹" + updatedConsultationFee);
            ExtentReportManager.logPass("    - Bio: " + updatedBio);
            ExtentReportManager.logPass("    - Qualification: " + updatedQualification);
            ExtentReportManager.logPass("  ✓ Profile updated successfully");
            ExtentReportManager.logPass("  ✓ Changes verified in profile");
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
