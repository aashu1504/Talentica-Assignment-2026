package com.qa.automation.pages;

import com.qa.automation.utils.ElementUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * SignupPage class represents the signup page of the application
 * Uses Fluent pattern - methods return page objects for method chaining
 */
public class SignupPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(SignupPage.class);

    // Page Elements using @FindBy annotations
    @FindBy(xpath = "//select[contains(@name,'role') or contains(@id,'role') or contains(@class,'role')] | //select[contains(.,'I am a')]")
    private WebElement roleDropdown;

    @FindBy(xpath = "//input[contains(@name,'firstName') or contains(@id,'firstName') or contains(@placeholder,'First Name')] | //input[@type='text' and contains(@placeholder,'First')]")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[contains(@name,'lastName') or contains(@id,'lastName') or contains(@placeholder,'Last Name')] | //input[@type='text' and contains(@placeholder,'Last')]")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[contains(@name,'email') or contains(@id,'email') or contains(@type,'email') or contains(@placeholder,'Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//input[contains(@name,'password') or contains(@id,'password') or contains(@type,'password') and contains(@placeholder,'Password')]")
    private WebElement passwordField;

    @FindBy(xpath = "//input[contains(@name,'confirmPassword') or contains(@id,'confirmPassword') or contains(@placeholder,'Confirm Password')]")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//input[contains(@name,'phone') or contains(@id,'phone') or contains(@placeholder,'Phone')]")
    private WebElement phoneField;

    @FindBy(xpath = "//select[contains(@name,'gender') or contains(@id,'gender') or contains(@class,'gender')]")
    private WebElement genderDropdown;

    @FindBy(xpath = "//input[contains(@name,'dateOfBirth') or contains(@id,'dateOfBirth') or contains(@name,'dob') or contains(@id,'dob') or contains(@placeholder,'Date of Birth')]")
    private WebElement dateOfBirthField;

    @FindBy(xpath = "//button[contains(text(),'Create Account') or contains(text(),'Sign Up') or contains(text(),'Register')] | //input[@type='submit' and contains(@value,'Create Account')]")
    private WebElement createAccountButton;

    // Doctor-specific fields
    @FindBy(xpath = "//select[contains(@name,'specialty') or contains(@id,'specialty') or contains(@class,'specialty')] | //select[contains(.,'Specialty')]")
    private WebElement specialtyDropdown;

    @FindBy(xpath = "//input[contains(@name,'licenseNumber') or contains(@id,'licenseNumber') or contains(@name,'license') or contains(@id,'license') or contains(@placeholder,'License Number')]")
    private WebElement licenseNumberField;

    @FindBy(xpath = "//input[contains(@name,'qualification') or contains(@id,'qualification') or contains(@placeholder,'Qualification')]")
    private WebElement qualificationField;

    @FindBy(xpath = "//input[contains(@name,'experience') or contains(@id,'experience') or contains(@placeholder,'Experience')]")
    private WebElement experienceField;

    @FindBy(xpath = "//input[contains(@name,'consultationFee') or contains(@id,'consultationFee') or contains(@name,'fee') or contains(@id,'fee') or contains(@placeholder,'Consultation Fee')]")
    private WebElement consultationFeeField;

    @FindBy(xpath = "//textarea[contains(@name,'bio') or contains(@id,'bio') or contains(@placeholder,'Bio')]")
    private WebElement bioTextarea;

    /**
     * Selects role from "I am a" dropdown
     * @param role Role to select (e.g., "Patient", "Doctor")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage selectRole(String role) {
        try {
            logger.info("Selecting role: {}", role);
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(roleDropdown));
            Select select = new Select(roleDropdown);
            select.selectByVisibleText(role);
            logger.info("Successfully selected role: {}", role);
        } catch (Exception e) {
            logger.error("Failed to select role: {}. Error: {}", role, e.getMessage());
            throw new RuntimeException("Failed to select role: " + role, e);
        }
        return this;
    }

    /**
     * Enters first name
     * @param firstName First name to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterFirstName(String firstName) {
        try {
            logger.info("Entering first name: {}", firstName);
            elementUtil.doSendKeys(firstNameField, firstName);
            logger.info("Successfully entered first name: {}", firstName);
        } catch (Exception e) {
            logger.error("Failed to enter first name: {}. Error: {}", firstName, e.getMessage());
            throw new RuntimeException("Failed to enter first name: " + firstName, e);
        }
        return this;
    }

    /**
     * Enters last name
     * @param lastName Last name to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterLastName(String lastName) {
        try {
            logger.info("Entering last name: {}", lastName);
            elementUtil.doSendKeys(lastNameField, lastName);
            logger.info("Successfully entered last name: {}", lastName);
        } catch (Exception e) {
            logger.error("Failed to enter last name: {}. Error: {}", lastName, e.getMessage());
            throw new RuntimeException("Failed to enter last name: " + lastName, e);
        }
        return this;
    }

    /**
     * Enters email address
     * @param email Email address to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterEmail(String email) {
        try {
            logger.info("Entering email: {}", email);
            elementUtil.doSendKeys(emailField, email);
            logger.info("Successfully entered email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to enter email: {}. Error: {}", email, e.getMessage());
            throw new RuntimeException("Failed to enter email: " + email, e);
        }
        return this;
    }

    /**
     * Enters password
     * @param password Password to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterPassword(String password) {
        try {
            logger.info("Entering password");
            elementUtil.doSendKeys(passwordField, password);
            logger.info("Successfully entered password");
        } catch (Exception e) {
            logger.error("Failed to enter password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter password", e);
        }
        return this;
    }

    /**
     * Enters confirm password
     * @param confirmPassword Confirm password to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterConfirmPassword(String confirmPassword) {
        try {
            logger.info("Entering confirm password");
            elementUtil.doSendKeys(confirmPasswordField, confirmPassword);
            logger.info("Successfully entered confirm password");
        } catch (Exception e) {
            logger.error("Failed to enter confirm password. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to enter confirm password", e);
        }
        return this;
    }

    /**
     * Enters phone number
     * @param phone Phone number to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterPhone(String phone) {
        try {
            logger.info("Entering phone: {}", phone);
            elementUtil.doSendKeys(phoneField, phone);
            logger.info("Successfully entered phone: {}", phone);
        } catch (Exception e) {
            logger.error("Failed to enter phone: {}. Error: {}", phone, e.getMessage());
            throw new RuntimeException("Failed to enter phone: " + phone, e);
        }
        return this;
    }

    /**
     * Selects gender from dropdown
     * @param gender Gender to select (e.g., "Male", "Female")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage selectGender(String gender) {
        try {
            logger.info("Selecting gender: {}", gender);
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(genderDropdown));
            Select select = new Select(genderDropdown);
            select.selectByVisibleText(gender);
            logger.info("Successfully selected gender: {}", gender);
        } catch (Exception e) {
            logger.error("Failed to select gender: {}. Error: {}", gender, e.getMessage());
            throw new RuntimeException("Failed to select gender: " + gender, e);
        }
        return this;
    }

    /**
     * Enters date of birth using calendar widget
     * @param dateOfBirth Date of birth in format "MM/dd/yyyy" (e.g., "01/01/1990")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterDateOfBirth(String dateOfBirth) {
        try {
            logger.info("Entering date of birth: {}", dateOfBirth);
            // Click the field first to open calendar widget if needed
            elementUtil.doClick(dateOfBirthField);
            // Clear and enter the date
            dateOfBirthField.clear();
            elementUtil.doSendKeys(dateOfBirthField, dateOfBirth);
            logger.info("Successfully entered date of birth: {}", dateOfBirth);
        } catch (Exception e) {
            logger.error("Failed to enter date of birth: {}. Error: {}", dateOfBirth, e.getMessage());
            throw new RuntimeException("Failed to enter date of birth: " + dateOfBirth, e);
        }
        return this;
    }

    /**
     * Clicks the Create Account button
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage clickCreateAccountButton() {
        try {
            logger.info("Clicking Create Account button");
            elementUtil.doClick(createAccountButton);
            logger.info("Successfully clicked Create Account button");
            // Wait for page to load after account creation
            wait.until(webDriver -> {
                String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").toString();
                return state.equals("complete");
            });
        } catch (Exception e) {
            logger.error("Failed to click Create Account button. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to click Create Account button", e);
        }
        return this;
    }

    /**
     * Verifies if user is redirected to Login Page or Patient Dashboard
     * @return true if redirected successfully, false otherwise
     */
    public boolean isRedirectedToLoginOrDashboard() {
        try {
            // Wait a moment for redirection to complete
            Thread.sleep(2000);
            String currentUrl = getCurrentUrl().toLowerCase();
            String pageTitle = getPageTitle().toLowerCase();
            logger.info("Current URL: {}, Page Title: {}", currentUrl, pageTitle);
            
            // Check if redirected to login page or dashboard
            // Also check for success messages or account creation confirmation
            boolean isLoginPage = currentUrl.contains("login") || pageTitle.contains("login") || 
                                 currentUrl.contains("signin") || pageTitle.contains("sign in");
            boolean isDashboard = currentUrl.contains("dashboard") || pageTitle.contains("dashboard") ||
                                 currentUrl.contains("patient") || pageTitle.contains("patient");
            boolean hasSuccessMessage = pageTitle.contains("success") || currentUrl.contains("success") ||
                                       pageTitle.contains("welcome") || currentUrl.contains("welcome");
            
            // If still on signup page, check for success message or error
            boolean isStillOnSignup = currentUrl.contains("signup") || currentUrl.contains("register") ||
                                     pageTitle.contains("signup") || pageTitle.contains("register");
            
            // Account creation is successful if redirected OR if there's a success indicator
            boolean redirected = isLoginPage || isDashboard || hasSuccessMessage;
            
            if (!redirected && isStillOnSignup) {
                // Check for success message on the page
                try {
                    String pageSource = driver.getPageSource().toLowerCase();
                    if (pageSource.contains("account created") || pageSource.contains("successfully") ||
                        pageSource.contains("registered") || pageSource.contains("welcome")) {
                        redirected = true;
                    }
                } catch (Exception e) {
                    logger.debug("Could not check page source for success message");
                }
            }
            
            return redirected;
        } catch (Exception e) {
            logger.error("Error checking redirection. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets the current page URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Scrolls down to Doctor Information section
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage scrollToDoctorInformationSection() {
        try {
            logger.info("Scrolling down to Doctor Information section");
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            // Scroll to specialty dropdown which is typically in Doctor Information section
            if (specialtyDropdown != null) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", specialtyDropdown);
                Thread.sleep(500); // Small delay for scroll animation
                logger.info("Successfully scrolled to Doctor Information section");
            } else {
                // Fallback: scroll down by a fixed amount
                js.executeScript("window.scrollBy(0, 500);");
                Thread.sleep(500);
                logger.info("Scrolled down to locate Doctor Information section");
            }
        } catch (Exception e) {
            logger.error("Failed to scroll to Doctor Information section. Error: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Selects specialty from dropdown
     * @param specialty Specialty to select (e.g., "Cardiology")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage selectSpecialty(String specialty) {
        try {
            logger.info("Selecting specialty: {}", specialty);
            wait.until(ExpectedConditions.visibilityOf(specialtyDropdown));
            Select select = new Select(specialtyDropdown);
            select.selectByVisibleText(specialty);
            logger.info("Successfully selected specialty: {}", specialty);
        } catch (Exception e) {
            logger.error("Failed to select specialty: {}. Error: {}", specialty, e.getMessage());
            throw new RuntimeException("Failed to select specialty: " + specialty, e);
        }
        return this;
    }

    /**
     * Enters license number
     * @param licenseNumber License number to enter
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterLicenseNumber(String licenseNumber) {
        try {
            logger.info("Entering license number: {}", licenseNumber);
            elementUtil.doSendKeys(licenseNumberField, licenseNumber);
            logger.info("Successfully entered license number: {}", licenseNumber);
        } catch (Exception e) {
            logger.error("Failed to enter license number: {}. Error: {}", licenseNumber, e.getMessage());
            throw new RuntimeException("Failed to enter license number: " + licenseNumber, e);
        }
        return this;
    }

    /**
     * Enters qualification
     * @param qualification Qualification to enter (e.g., "MBBS, MD")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterQualification(String qualification) {
        try {
            logger.info("Entering qualification: {}", qualification);
            elementUtil.doSendKeys(qualificationField, qualification);
            logger.info("Successfully entered qualification: {}", qualification);
        } catch (Exception e) {
            logger.error("Failed to enter qualification: {}. Error: {}", qualification, e.getMessage());
            throw new RuntimeException("Failed to enter qualification: " + qualification, e);
        }
        return this;
    }

    /**
     * Enters experience in years
     * @param experience Experience in years (e.g., "10")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterExperience(String experience) {
        try {
            logger.info("Entering experience: {} years", experience);
            elementUtil.doSendKeys(experienceField, experience);
            logger.info("Successfully entered experience: {} years", experience);
        } catch (Exception e) {
            logger.error("Failed to enter experience: {}. Error: {}", experience, e.getMessage());
            throw new RuntimeException("Failed to enter experience: " + experience, e);
        }
        return this;
    }

    /**
     * Enters consultation fee
     * @param fee Consultation fee (e.g., "1000")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterConsultationFee(String fee) {
        try {
            logger.info("Entering consultation fee: ₹{}", fee);
            elementUtil.doSendKeys(consultationFeeField, fee);
            logger.info("Successfully entered consultation fee: ₹{}", fee);
        } catch (Exception e) {
            logger.error("Failed to enter consultation fee: {}. Error: {}", fee, e.getMessage());
            throw new RuntimeException("Failed to enter consultation fee: " + fee, e);
        }
        return this;
    }

    /**
     * Enters bio in textarea
     * @param bio Bio text to enter (e.g., "Senior Cardiologist")
     * @return SignupPage instance for Fluent pattern
     */
    public SignupPage enterBio(String bio) {
        try {
            logger.info("Entering bio: {}", bio);
            elementUtil.doSendKeys(bioTextarea, bio);
            logger.info("Successfully entered bio: {}", bio);
        } catch (Exception e) {
            logger.error("Failed to enter bio: {}. Error: {}", bio, e.getMessage());
            throw new RuntimeException("Failed to enter bio: " + bio, e);
        }
        return this;
    }
}

