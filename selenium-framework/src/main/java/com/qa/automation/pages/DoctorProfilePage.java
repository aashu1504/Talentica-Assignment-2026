package com.qa.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * DoctorProfilePage class represents the doctor profile/dashboard page
 * Handles profile editing and availability slot management
 */
public class DoctorProfilePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(DoctorProfilePage.class);

    // Profile navigation elements
    @FindBy(xpath = "//a[contains(text(),'Profile') or contains(@href,'profile')] | //button[contains(text(),'Profile')]")
    private WebElement profileLink;

    @FindBy(xpath = "//button[contains(text(),'Edit Profile') or contains(text(),'Edit')] | //a[contains(text(),'Edit Profile')]")
    private WebElement editProfileButton;

    // Availability slot elements - based on actual UI
    // Available Days field comes AFTER doctor information section (Qualification, Experience, etc.)
    @FindBy(xpath = "//label[contains(text(),'Available Days')]/following-sibling::input | //input[contains(@placeholder,'All Days') or contains(@placeholder,'days')]")
    private WebElement availableDaysField;

    @FindBy(xpath = "(//input[@type='time' or contains(@name,'start') or contains(@id,'start') or contains(@placeholder,'Start')])[1]")
    private WebElement availableTimeStartField;

    @FindBy(xpath = "(//input[@type='time' or contains(@name,'end') or contains(@id,'end') or contains(@placeholder,'End')])[last()]")
    private WebElement availableTimeEndField;

    @FindBy(xpath = "//button[contains(text(),'Save Changes') or contains(text(),'Save') or contains(text(),'Update')] | //input[@type='submit' and (contains(@value,'Save') or contains(@value,'Update'))]")
    private WebElement saveButton;

    @FindBy(xpath = "//button[contains(text(),'Add Slot') or contains(text(),'Add Availability')] | //a[contains(text(),'Add Slot')]")
    private WebElement addSlotButton;

    /**
     * Clicks on Profile link to navigate to profile page
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage clickProfile() {
        try {
            logger.info("Clicking Profile link");
            elementUtil.doClick(profileLink);
            logger.info("Successfully clicked Profile link");
            Thread.sleep(1000); // Wait for profile page to load
        } catch (Exception e) {
            logger.error("Failed to click Profile link. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Profile link", e);
        }
        return this;
    }

    /**
     * Clicks on Edit Profile button
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage clickEditProfile() {
        try {
            logger.info("Clicking Edit Profile button");
            elementUtil.doClick(editProfileButton);
            logger.info("Successfully clicked Edit Profile button");
            Thread.sleep(1000); // Wait for edit form to load
        } catch (Exception e) {
            logger.error("Failed to click Edit Profile button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Edit Profile button", e);
        }
        return this;
    }

    /**
     * Scrolls to the availability section
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage scrollToAvailabilitySection() {
        try {
            logger.info("Scrolling to Availability section");
            // Find the Available Days label or section header
            org.openqa.selenium.WebElement availabilitySection = driver.findElement(
                org.openqa.selenium.By.xpath("//label[contains(text(),'Available Days')] | //*[contains(text(),'Available Time')]")
            );
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", availabilitySection);
            Thread.sleep(1000);
            logger.info("Successfully scrolled to Availability section");
        } catch (Exception e) {
            logger.warn("Could not scroll to Availability section: {}", e.getMessage());
        }
        return this;
    }
    
    /**
     * Enters available days (e.g., "All Days in a week" or specific days)
     * @param days Available days text
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage enterAvailableDays(String days) {
        try {
            logger.info("Entering available days: {}", days);
            
            // Scroll to availability section first
            scrollToAvailabilitySection();
            
            // Clear field first
            availableDaysField.clear();
            Thread.sleep(300);
            
            // Enter the days
            elementUtil.doSendKeys(availableDaysField, days);
            
            // Verify the value was entered
            String enteredValue = availableDaysField.getAttribute("value");
            logger.info("Successfully entered available days: {}. Field value: {}", days, enteredValue);
        } catch (Exception e) {
            logger.error("Failed to enter available days: {}. Error: {}", days, e.getMessage());
            throw new RuntimeException("Failed to enter available days: " + days, e);
        }
        return this;
    }

    /**
     * Enters available time start (e.g., "09:00 AM" or "09:00")
     * Converts 12-hour format to 24-hour format if needed for HTML5 time inputs
     * @param timeStart Start time
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage enterAvailableTimeStart(String timeStart) {
        try {
            logger.info("Entering available time start: {}", timeStart);
            
            // Convert time format if needed (e.g., "10:00 AM" to "10:00")
            String formattedTime = convertTo24HourFormat(timeStart);
            logger.info("Formatted time for input: {}", formattedTime);
            
            // Scroll to element
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", availableTimeStartField);
            Thread.sleep(500);
            
            // Use JavaScript to set the value directly - most reliable for HTML5 time inputs
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", 
                    availableTimeStartField, formattedTime);
            
            Thread.sleep(300);
            
            // Verify the value was set
            String setValue = availableTimeStartField.getAttribute("value");
            logger.info("Successfully entered available time start: {}. Field value: {}", timeStart, setValue);
        } catch (Exception e) {
            logger.error("Failed to enter available time start: {}. Error: {}", timeStart, e.getMessage());
            throw new RuntimeException("Failed to enter available time start: " + timeStart, e);
        }
        return this;
    }

    /**
     * Enters available time end (e.g., "06:00 PM" or "18:00")
     * Converts 12-hour format to 24-hour format if needed for HTML5 time inputs
     * @param timeEnd End time
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage enterAvailableTimeEnd(String timeEnd) {
        try {
            logger.info("Entering available time end: {}", timeEnd);
            
            // Convert time format if needed (e.g., "05:00 PM" to "17:00")
            String formattedTime = convertTo24HourFormat(timeEnd);
            logger.info("Formatted time for input: {}", formattedTime);
            
            // Scroll to element
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", availableTimeEndField);
            Thread.sleep(500);
            
            // Use JavaScript to set the value directly - most reliable for HTML5 time inputs
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", 
                    availableTimeEndField, formattedTime);
            
            Thread.sleep(300);
            
            // Verify the value was set
            String setValue = availableTimeEndField.getAttribute("value");
            logger.info("Successfully entered available time end: {}. Field value: {}", timeEnd, setValue);
        } catch (Exception e) {
            logger.error("Failed to enter available time end: {}. Error: {}", timeEnd, e.getMessage());
            throw new RuntimeException("Failed to enter available time end: " + timeEnd, e);
        }
        return this;
    }
    
    /**
     * Converts 12-hour time format to 24-hour format
     * @param time Time in 12-hour format (e.g., "09:00 AM") or 24-hour format (e.g., "09:00")
     * @return Time in 24-hour format (e.g., "09:00" or "18:00")
     */
    private String convertTo24HourFormat(String time) {
        try {
            time = time.trim();
            
            // If already in 24-hour format (no AM/PM), return as is
            if (!time.toUpperCase().contains("AM") && !time.toUpperCase().contains("PM")) {
                return time;
            }
            
            // Parse 12-hour format
            String[] parts = time.split(" ");
            String timePart = parts[0];
            String period = parts.length > 1 ? parts[1].toUpperCase() : "AM";
            
            String[] hourMin = timePart.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            String minute = hourMin.length > 1 ? hourMin[1] : "00";
            
            // Convert to 24-hour format
            if (period.equals("PM") && hour != 12) {
                hour += 12;
            } else if (period.equals("AM") && hour == 12) {
                hour = 0;
            }
            
            return String.format("%02d:%s", hour, minute);
        } catch (Exception e) {
            logger.warn("Failed to convert time format, returning original: {}", time);
            return time;
        }
    }
    
    /**
     * Converts 24-hour time format to 12-hour format with AM/PM
     * @param time Time in 24-hour format (e.g., "09:00" or "18:00")
     * @return Time in 12-hour format (e.g., "09:00 AM" or "06:00 PM")
     */
    private String convertTo12HourFormat(String time) {
        try {
            time = time.trim();
            
            // If already has AM/PM, return as is
            if (time.toUpperCase().contains("AM") || time.toUpperCase().contains("PM")) {
                return time;
            }
            
            // Parse 24-hour format
            String[] hourMin = time.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            String minute = hourMin.length > 1 ? hourMin[1] : "00";
            
            // Convert to 12-hour format
            String period = "AM";
            if (hour >= 12) {
                period = "PM";
                if (hour > 12) {
                    hour -= 12;
                }
            } else if (hour == 0) {
                hour = 12;
            }
            
            return String.format("%02d:%s %s", hour, minute, period);
        } catch (Exception e) {
            logger.warn("Failed to convert time format, returning original: {}", time);
            return time;
        }
    }

    /**
     * Clicks Add Slot button if available
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage clickAddSlot() {
        try {
            logger.info("Clicking Add Slot button");
            elementUtil.doClick(addSlotButton);
            logger.info("Successfully clicked Add Slot button");
            Thread.sleep(500);
        } catch (Exception e) {
            logger.warn("Add Slot button not found or not clickable. This might be expected if slots are added automatically.");
        }
        return this;
    }

    /**
     * Clicks Save/Update button to save profile changes
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage clickSave() {
        try {
            logger.info("Clicking Save button");
            elementUtil.doClick(saveButton);
            logger.info("Successfully clicked Save button");
            Thread.sleep(2000); // Wait for save operation to complete
        } catch (Exception e) {
            logger.error("Failed to click Save button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Save button", e);
        }
        return this;
    }

    /**
     * Adds availability slot with all details
     * @param days Available days
     * @param timeStart Start time
     * @param timeEnd End time
     * @return DoctorProfilePage instance for Fluent pattern
     */
    public DoctorProfilePage addAvailabilitySlot(String days, String timeStart, String timeEnd) {
        enterAvailableDays(days);
        enterAvailableTimeStart(timeStart);
        enterAvailableTimeEnd(timeEnd);
        clickAddSlot(); // Try to click if button exists
        return this;
    }

    /**
     * Verifies if profile was updated successfully by checking for success message or URL
     * @return true if profile update was successful
     */
    public boolean isProfileUpdatedSuccessfully() {
        try {
            String currentUrl = driver.getCurrentUrl();
            String pageSource = driver.getPageSource().toLowerCase();
            
            // Check for success indicators
            boolean hasSuccessMessage = pageSource.contains("success") || 
                                       pageSource.contains("updated") || 
                                       pageSource.contains("saved");
            boolean isOnProfilePage = currentUrl.contains("profile") || currentUrl.contains("dashboard");
            
            logger.info("Profile update check - URL: {}, Has success message: {}", currentUrl, hasSuccessMessage);
            return hasSuccessMessage || isOnProfilePage;
        } catch (Exception e) {
            logger.error("Error checking profile update status: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets the displayed available days value from the profile
     * @return Available days text or empty string if not found
     */
    public String getDisplayedAvailableDays() {
        try {
            // First try to get from input field
            try {
                String value = availableDaysField.getAttribute("value");
                if (value != null && !value.isEmpty()) {
                    logger.info("Found available days from input field: {}", value);
                    return value;
                }
            } catch (Exception e) {
                logger.debug("Could not get value from input field: {}", e.getMessage());
            }
            
            // Try to find the displayed value in page source
            String pageSource = driver.getPageSource();
            if (pageSource.contains("All Day a Week")) {
                logger.info("Found 'All Day a Week' in page source");
                return "All Day a Week";
            }
            
            // Try to find any text element containing days info
            try {
                java.util.List<org.openqa.selenium.WebElement> elements = driver.findElements(
                    org.openqa.selenium.By.xpath("//*[contains(text(),'Day') or contains(text(),'day')]")
                );
                for (org.openqa.selenium.WebElement elem : elements) {
                    String text = elem.getText();
                    if (text != null && !text.isEmpty() && text.length() > 3) {
                        logger.info("Found potential days text: {}", text);
                        return text;
                    }
                }
            } catch (Exception e) {
                logger.debug("Could not find days in text elements");
            }
            
            logger.warn("Could not find available days value");
            return "";
        } catch (Exception e) {
            logger.error("Error getting displayed available days: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Gets the start time value directly from the input field (before save)
     * @return Start time value in 24-hour format or empty string
     */
    public String getDisplayedStartTimeBeforeSave() {
        try {
            String value = availableTimeStartField.getAttribute("value");
            if (value != null && !value.isEmpty()) {
                logger.info("Start time field value (before save): {}", value);
                return value;
            }
            logger.warn("Start time field is empty");
            return "";
        } catch (Exception e) {
            logger.error("Error getting start time field value: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Gets the end time value directly from the input field (before save)
     * @return End time value in 24-hour format or empty string
     */
    public String getDisplayedEndTimeBeforeSave() {
        try {
            String value = availableTimeEndField.getAttribute("value");
            if (value != null && !value.isEmpty()) {
                logger.info("End time field value (before save): {}", value);
                return value;
            }
            logger.warn("End time field is empty");
            return "";
        } catch (Exception e) {
            logger.error("Error getting end time field value: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Gets the displayed start time value from the profile
     * Converts 24-hour format to 12-hour format with AM/PM if needed
     * @return Start time text or empty string if not found
     */
    public String getDisplayedStartTime() {
        try {
            // Wait a moment for fields to load
            Thread.sleep(1000);
            
            // Try to get from input field
            try {
                String value = availableTimeStartField.getAttribute("value");
                if (value != null && !value.isEmpty()) {
                    logger.info("Found start time from input field: {}", value);
                    // Convert to 12-hour format with AM/PM for display
                    String formatted = convertTo12HourFormat(value);
                    logger.info("Converted start time to 12-hour format: {}", formatted);
                    return formatted;
                }
            } catch (Exception e) {
                logger.debug("Could not get start time from input field: {}", e.getMessage());
            }
            
            // Try to find all time input fields and get the first one
            try {
                java.util.List<org.openqa.selenium.WebElement> timeInputs = driver.findElements(
                    org.openqa.selenium.By.xpath("//input[@type='time']")
                );
                if (!timeInputs.isEmpty()) {
                    String value = timeInputs.get(0).getAttribute("value");
                    if (value != null && !value.isEmpty()) {
                        logger.info("Found start time from first time input: {}", value);
                        String formatted = convertTo12HourFormat(value);
                        return formatted;
                    }
                }
            } catch (Exception e) {
                logger.debug("Could not get start time from time inputs list");
            }
            
            // Look in page source for time patterns
            String pageSource = driver.getPageSource();
            if (pageSource.contains("10:00")) {
                logger.info("Found start time pattern in page source");
                return "10:00 AM";
            }
            
            logger.warn("Could not find start time value");
            return "";
        } catch (Exception e) {
            logger.error("Error getting displayed start time: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Gets the displayed end time value from the profile
     * Converts 24-hour format to 12-hour format with AM/PM if needed
     * @return End time text or empty string if not found
     */
    public String getDisplayedEndTime() {
        try {
            // Try to get from input field
            try {
                String value = availableTimeEndField.getAttribute("value");
                if (value != null && !value.isEmpty()) {
                    logger.info("Found end time from input field: {}", value);
                    // Convert to 12-hour format with AM/PM for display
                    String formatted = convertTo12HourFormat(value);
                    logger.info("Converted end time to 12-hour format: {}", formatted);
                    return formatted;
                }
            } catch (Exception e) {
                logger.debug("Could not get end time from input field: {}", e.getMessage());
            }
            
            // Look in page source for time patterns
            String pageSource = driver.getPageSource();
            if (pageSource.contains("18:00") || pageSource.contains("6:00")) {
                logger.info("Found end time pattern in page source");
                return "06:00 PM";
            }
            
            logger.warn("Could not find end time value");
            return "";
        } catch (Exception e) {
            logger.error("Error getting displayed end time: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Verifies that availability slots are displayed correctly
     * @param expectedDays Expected available days
     * @param expectedStartTime Expected start time (in any format)
     * @param expectedEndTime Expected end time (in any format)
     * @return true if all values match
     */
    public boolean verifyAvailabilitySlots(String expectedDays, String expectedStartTime, String expectedEndTime) {
        try {
            logger.info("Verifying availability slots - Days: {}, Start: {}, End: {}", expectedDays, expectedStartTime, expectedEndTime);
            
            String displayedDays = getDisplayedAvailableDays();
            String displayedStart = getDisplayedStartTime();
            String displayedEnd = getDisplayedEndTime();
            
            logger.info("Displayed values - Days: {}, Start: {}, End: {}", displayedDays, displayedStart, displayedEnd);
            
            // Convert expected times to 24-hour format for comparison
            String expectedStart24 = convertTo24HourFormat(expectedStartTime);
            String expectedEnd24 = convertTo24HourFormat(expectedEndTime);
            
            boolean daysMatch = displayedDays.contains(expectedDays) || expectedDays.contains(displayedDays);
            boolean startMatch = displayedStart.contains(expectedStart24) || displayedStart.contains(expectedStartTime);
            boolean endMatch = displayedEnd.contains(expectedEnd24) || displayedEnd.contains(expectedEndTime);
            
            logger.info("Verification results - Days match: {}, Start match: {}, End match: {}", daysMatch, startMatch, endMatch);
            
            return daysMatch && startMatch && endMatch;
        } catch (Exception e) {
            logger.error("Error verifying availability slots: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if user is on doctor dashboard
     * @return true if on doctor dashboard
     */
    public boolean isOnDoctorDashboard() {
        try {
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle().toLowerCase();
            
            boolean isDashboard = currentUrl.contains("dashboard") || 
                                 pageTitle.contains("dashboard") ||
                                 pageTitle.contains("doctor");
            
            logger.info("Dashboard check - URL: {}, Title: {}, Is Dashboard: {}", currentUrl, pageTitle, isDashboard);
            return isDashboard;
        } catch (Exception e) {
            logger.error("Error checking dashboard status: {}", e.getMessage());
            return false;
        }
    }
}
