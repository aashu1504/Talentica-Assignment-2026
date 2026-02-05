package com.qa.automation.tests.records;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.MedicalRecordsPage;
import org.testng.annotations.Test;

/**
 * Quick verification script to check if uploaded record exists
 */
public class VerifyUploadedRecord extends BaseTest {

    @Test
    public void verifyRecordExists() {
        try {
            HomePage homePage = new HomePage();
            LoginPage loginPage = new LoginPage();
            MedicalRecordsPage medicalRecordsPage = new MedicalRecordsPage();
            
            String email = "patient1770299553241@test.com";
            String password = "Test@123";
            
            System.out.println("========================================");
            System.out.println("Logging in with: " + email);
            System.out.println("========================================");
            
            homePage.navigateToHomePage();
            homePage.clickLoginButton();
            
            loginPage.enterEmail(email)
                    .enterPassword(password)
                    .clickLoginButton();
            
            Thread.sleep(3000);
            
            String currentUrl = loginPage.getCurrentUrl();
            System.out.println("Current URL after login: " + currentUrl);
            
            // Navigate to Medical Records
            medicalRecordsPage.clickMedicalRecords();
            Thread.sleep(3000);
            
            System.out.println("\n========================================");
            System.out.println("CHECKING FOR RECORDS:");
            System.out.println("========================================");
            
            // Check for "Blood Test" record
            boolean recordFound = medicalRecordsPage.isRecordWithTitleInList("Blood Test");
            if (recordFound) {
                System.out.println("✅ FOUND: 'Blood Test' record exists on the page");
            } else {
                System.out.println("❌ NOT FOUND: 'Blood Test' record NOT on the page");
            }
            
            // Check for file attached
            boolean fileAttached = medicalRecordsPage.isFileAttachedLinkPresent();
            if (fileAttached) {
                System.out.println("✅ FOUND: 'File attached' link exists");
            } else {
                System.out.println("❌ NOT FOUND: 'File attached' link NOT found");
            }
            
            // Check for file in list
            boolean fileInList = medicalRecordsPage.isFileInList("Sample_Blood_Test_Report.pdf");
            if (fileInList) {
                System.out.println("✅ FOUND: File appears in records list");
            } else {
                System.out.println("❌ NOT FOUND: File NOT in records list");
            }
            
            System.out.println("========================================");
            System.out.println("Page will stay open for 30 seconds for manual inspection");
            System.out.println("========================================");
            
            Thread.sleep(30000); // Keep browser open for 30 seconds
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
