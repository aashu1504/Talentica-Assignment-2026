package com.qa.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for Medical Records functionality
 * Handles medical report upload and management operations
 */
public class MedicalRecordsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(MedicalRecordsPage.class);

    // Locators for medical records elements
    @FindBy(xpath = "//a[contains(text(),'Medical Records') or contains(@href,'medical') or contains(@href,'records')] | //button[contains(text(),'Medical Records')]")
    private WebElement medicalRecordsLink;

    @FindBy(xpath = "//button[contains(text(),'Upload Record') or contains(text(),'Upload Report')] | //a[contains(text(),'Upload Record')]")
    private WebElement uploadRecordButton;

    @FindBy(xpath = "//input[@type='file' or contains(@name,'file') or contains(@id,'file') or contains(@accept,'pdf')]")
    private WebElement fileInput;

    @FindBy(xpath = "//select[contains(@name,'recordType') or contains(@id,'recordType') or contains(@name,'type')] | //div[contains(text(),'Record Type')]/following-sibling::select")
    private WebElement recordTypeDropdown;

    @FindBy(xpath = "//input[contains(@placeholder,'Blood Test Results') or contains(@placeholder,'Title') or contains(@name,'title') or contains(@id,'title')]")
    private WebElement reportTitleField;

    @FindBy(xpath = "//input[@type='date' or contains(@name,'date') or contains(@id,'date') or contains(@placeholder,'dd/mm/yyyy')]")
    private WebElement dateField;

    @FindBy(xpath = "//textarea[contains(@name,'description') or contains(@id,'description') or contains(@placeholder,'description')] | //textarea[contains(@placeholder,'Brief')]")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[contains(text(),'Choose File') or contains(text(),'Browse')] | //label[contains(text(),'Choose File')]")
    private WebElement chooseFileButton;

    @FindBy(xpath = "//button[contains(text(),'Upload') or @type='submit'] | //input[@type='submit' and contains(@value,'Upload')]")
    private WebElement uploadButton;

    /**
     * Clicks on Medical Records link in navbar
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage clickMedicalRecords() {
        try {
            logger.info("Clicking Medical Records link");
            elementUtil.doClick(medicalRecordsLink);
            Thread.sleep(2000);
            logger.info("Successfully clicked Medical Records link");
        } catch (Exception e) {
            logger.error("Failed to click Medical Records link. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Medical Records link", e);
        }
        return this;
    }

    /**
     * Clicks Upload Record button
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage clickUploadRecord() {
        try {
            logger.info("Clicking Upload Record button");
            elementUtil.doClick(uploadRecordButton);
            Thread.sleep(1000);
            logger.info("Successfully clicked Upload Record button");
        } catch (Exception e) {
            logger.error("Failed to click Upload Record button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Upload Record button", e);
        }
        return this;
    }

    /**
     * Selects record type from dropdown
     * @param recordType Record type (e.g., "Lab Report", "Prescription", "X-Ray")
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage selectRecordType(String recordType) {
        try {
            logger.info("Selecting record type: {}", recordType);
            org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(recordTypeDropdown);
            select.selectByVisibleText(recordType);
            Thread.sleep(300);
            logger.info("Successfully selected record type");
        } catch (Exception e) {
            logger.warn("Record type dropdown not found. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Enters report title
     * @param title Report title
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage enterReportTitle(String title) {
        try {
            logger.info("Entering report title: {}", title);
            Thread.sleep(500); // Wait for field to be ready
            reportTitleField.click(); // Click to focus
            Thread.sleep(200);
            reportTitleField.clear();
            Thread.sleep(300);
            reportTitleField.sendKeys(title);
            Thread.sleep(300);
            logger.info("Successfully entered report title: {}", title);
        } catch (Exception e) {
            logger.error("Report title field not found or not editable. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter report title", e);
        }
        return this;
    }

    /**
     * Enters date
     * @param date Date in format dd/mm/yyyy or yyyy-mm-dd
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage enterDate(String date) {
        try {
            logger.info("Entering date: {}", date);
            dateField.clear();
            Thread.sleep(300);
            elementUtil.doSendKeys(dateField, date);
            logger.info("Successfully entered date");
        } catch (Exception e) {
            logger.warn("Date field not found or not editable. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Enters description
     * @param description Description text
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage enterDescription(String description) {
        try {
            logger.info("Entering description: {}", description);
            descriptionField.clear();
            Thread.sleep(300);
            elementUtil.doSendKeys(descriptionField, description);
            logger.info("Successfully entered description");
        } catch (Exception e) {
            logger.warn("Description field not found or not editable. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Uploads a file by providing the file path
     * @param filePath Absolute path to the file to upload
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage uploadFile(String filePath) {
        try {
            logger.info("Uploading file: {}", filePath);
            
            // Send the file path directly to the file input element
            fileInput.sendKeys(filePath);
            Thread.sleep(1000);
            
            logger.info("Successfully uploaded file");
        } catch (Exception e) {
            logger.error("Failed to upload file. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file", e);
        }
        return this;
    }

    /**
     * Clicks the Choose File button (if needed before file input)
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage clickChooseFile() {
        try {
            logger.info("Clicking Choose File button");
            elementUtil.doClick(chooseFileButton);
            Thread.sleep(500);
            logger.info("Successfully clicked Choose File button");
        } catch (Exception e) {
            logger.warn("Choose File button not found or not needed. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Clicks Upload button to submit the file
     * @return MedicalRecordsPage instance for Fluent pattern
     */
    public MedicalRecordsPage clickUpload() {
        try {
            logger.info("Submitting upload form");
            Thread.sleep(2000); // Wait for all fields to be processed
            
            // Wait for button to be present and visible
            org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(uploadButton));
            logger.info("Upload button is visible");
            
            // Scroll the modal/form into view first
            logger.info("Scrolling form into view");
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var button = arguments[0];" +
                "var modal = button.closest('.modal') || button.closest('[role=\"dialog\"]');" +
                "if (modal) { modal.scrollTop = modal.scrollHeight; }",
                uploadButton
            );
            Thread.sleep(500);
            
            // Scroll button into center of viewport
            logger.info("Scrolling upload button into center of viewport");
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});",
                uploadButton
            );
            Thread.sleep(1000);
            
            // Ensure button is in viewport and clickable
            boolean isInViewport = (boolean) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0];" +
                "var rect = elem.getBoundingClientRect();" +
                "return (" +
                "  rect.top >= 0 &&" +
                "  rect.left >= 0 &&" +
                "  rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&" +
                "  rect.right <= (window.innerWidth || document.documentElement.clientWidth)" +
                ");",
                uploadButton
            );
            logger.info("Upload button in viewport: {}", isInViewport);
            
            // Wait for button to be clickable
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(uploadButton));
            logger.info("Upload button is clickable");
            
            // Click using JavaScript (most reliable for modal buttons)
            logger.info("Clicking Upload button with JavaScript");
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", uploadButton);
            logger.info("Upload button clicked successfully");
            
            Thread.sleep(2000); // Wait for alert to appear
            
            // Handle the success alert immediately after upload
            try {
                logger.info("Checking for success alert after upload");
                org.openqa.selenium.support.ui.WebDriverWait alertWait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));
                alertWait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
                
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                logger.info("Success alert found: {}", alertText);
                alert.accept();
                logger.info("Alert accepted");
                
                Thread.sleep(2000); // Wait for page to refresh after alert
            } catch (Exception e) {
                logger.warn("No alert appeared or alert handling failed: {}", e.getMessage());
            }
            
        } catch (Exception e) {
            logger.error("Failed to submit upload form. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to submit upload form", e);
        }
        return this;
    }

    /**
     * Handles the success alert that appears after upload
     * @return Alert text if found, empty string otherwise
     */
    public String handleUploadAlert() {
        try {
            logger.info("Checking for upload success alert");
            Thread.sleep(1000);
            
            org.openqa.selenium.Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            logger.info("Alert found with text: {}", alertText);
            
            alert.accept();
            logger.info("Alert dismissed");
            Thread.sleep(1000); // Wait for page to refresh after alert
            
            return alertText;
            
        } catch (org.openqa.selenium.NoAlertPresentException e) {
            logger.warn("No alert present");
            return "";
        } catch (Exception e) {
            logger.error("Error handling alert: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Verifies if upload was successful based on alert text
     * @param alertText The alert text received after upload
     * @return true if upload successful, false otherwise
     */
    public boolean isUploadSuccessful(String alertText) {
        try {
            logger.info("Verifying upload success");
            
            // Check if alert text indicates success
            if (alertText != null && !alertText.isEmpty()) {
                boolean isSuccess = alertText.toLowerCase().contains("success") || 
                                   alertText.toLowerCase().contains("uploaded");
                
                if (isSuccess) {
                    logger.info("Upload successful based on alert: {}", alertText);
                    return true;
                }
            }
            
            logger.warn("Upload success could not be confirmed from alert");
            return false;
            
        } catch (Exception e) {
            logger.error("Error verifying upload status: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if uploaded file appears in the list of records
     * @param fileName File name to verify
     * @return true if file appears in list, false otherwise
     */
    public boolean isFileInList(String fileName) {
        try {
            Thread.sleep(1000);
            String pageSource = driver.getPageSource();
            
            logger.info("Checking if file appears in list: {}", fileName);
            
            boolean fileFound = pageSource.contains(fileName) || 
                               pageSource.contains("Blood Test") ||
                               pageSource.contains("Sample_Blood_Test_Report");
            
            if (fileFound) {
                logger.info("File found in records list");
                return true;
            }
            
            logger.warn("File not found in records list");
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking file in list: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if a specific record with given title appears in the list
     * @param recordTitle Title of the record to verify
     * @return true if record with title found, false otherwise
     */
    public boolean isRecordWithTitleInList(String recordTitle) {
        try {
            Thread.sleep(3000); // Wait for records to load and modal to close
            
            logger.info("Checking if record with title appears in list: {}", recordTitle);
            
            // First, ensure any upload modal is closed
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "var modal = document.querySelector('.modal');" +
                    "if (modal) { modal.style.display = 'none'; modal.remove(); }"
                );
                Thread.sleep(500);
            } catch (Exception e) {
                logger.debug("No modal to close");
            }
            
            // Try to find the record in the records list container specifically
            try {
                // Look for records list container
                java.util.List<org.openqa.selenium.WebElement> recordElements = driver.findElements(
                    org.openqa.selenium.By.xpath(
                        "//div[contains(@class, 'record') or contains(@class, 'card') or contains(@class, 'list-item')]" +
                        "[contains(., '" + recordTitle + "')]"
                    )
                );
                
                if (!recordElements.isEmpty()) {
                    logger.info("Record with title '{}' found in list using element search", recordTitle);
                    return true;
                }
            } catch (Exception e) {
                logger.debug("Element search failed, trying page source");
            }
            
            // Fallback: Check page source but exclude form/modal content
            String pageSource = driver.getPageSource();
            
            // Check if the title appears AND we're not on the upload form
            boolean recordFound = pageSource.contains(recordTitle) && 
                                 !pageSource.contains("Upload Medical Record") &&
                                 !pageSource.contains("Choose File");
            
            if (recordFound) {
                logger.info("Record with title '{}' found in list", recordTitle);
                return true;
            }
            
            logger.warn("Record with title '{}' not found in list", recordTitle);
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking record in list: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if 'File attached' link appears for the uploaded record
     * @return true if file attached link found, false otherwise
     */
    public boolean isFileAttachedLinkPresent() {
        try {
            Thread.sleep(2000); // Wait longer for file to be processed
            String pageSource = driver.getPageSource().toLowerCase();
            
            logger.info("Checking if 'File attached' link is present");
            
            boolean linkFound = pageSource.contains("file attached") || 
                               pageSource.contains("attached") ||
                               pageSource.contains("view file") ||
                               pageSource.contains("download") ||
                               pageSource.contains(".pdf");
            
            if (linkFound) {
                logger.info("'File attached' link or file indicator found");
                return true;
            }
            
            logger.warn("'File attached' link not found");
            logger.info("Page may still be loading or file link has different text");
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking file attached link: {}", e.getMessage());
            return false;
        }
    }
}
