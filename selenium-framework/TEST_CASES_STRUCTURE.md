# Test Cases Organization Structure

This document outlines the folder structure for organizing automated test cases in the Selenium Framework.

## Folder Structure

```
src/test/java/com/qa/automation/tests/
├── homepage/                    # Home Page related test cases
│   └── TC001_HomePageTest.java
├── signup/                      # Signup related test cases
│   ├── TC002_PatientSignupTest.java
│   └── TC003_DoctorSignupTest.java
├── login/                       # Login related test cases
│   ├── TC004_PatientLoginTest.java
│   └── TC005_DoctorLoginTest.java
├── appointment/                 # Appointment related test cases
│   └── TC006_BookAppointmentTest.java (future)
├── dashboard/                   # Dashboard related test cases
│   └── TC007_PatientDashboardTest.java (future)
│   └── TC008_DoctorDashboardTest.java (future)
└── ...                          # Other modules as needed
```

## Naming Convention

- **Test Class Names**: `TC###_DescriptiveNameTest.java`
  - Example: `TC001_HomePageTest.java`, `TC002_PatientSignupTest.java`
  
- **Test Method Names**: Descriptive method names matching test case steps
  - Example: `verifyPortalHomePageRedirectionAndLoad()`, `verifyPatientSignupEndToEnd()`

## Test Case Categories

Tests are automatically categorized in ExtentReports based on their package:
- `homepage.*` → "Home Page Tests"
- `signup.*` → "Signup Tests"
- `login.*` → "Login Tests"
- etc.

## TestNG Configuration

The `testng.xml` file organizes tests by module:

```xml
<test name="Home Page Tests">
    <classes>
        <class name="com.qa.automation.tests.homepage.TC001_HomePageTest"/>
    </classes>
</test>

<test name="Signup Tests">
    <classes>
        <class name="com.qa.automation.tests.signup.TC002_PatientSignupTest"/>
    </classes>
</test>
```

## Running Tests

### Run all tests:
```bash
mvn clean test
```

### Run specific test class:
```bash
mvn clean test -Dtest=TC002_PatientSignupTest
```

### Run tests by module:
```bash
mvn clean test -Dtest=com.qa.automation.tests.signup.*
```

## Test Data Management

- **Unique Email Generation**: `TestDataGenerator.generateUniqueEmail()` ensures unique emails for each test run
- **Test Data Files**: Store in `src/test/resources/testdata/`
  - JSON files: `patient_signup_data.json`
  - Excel files: `test_data.xlsx`

## Best Practices

1. **One test case per test method** - Each test method should correspond to one test case
2. **Descriptive test descriptions** - Use `@Test(description = "TC-XXX: Test Case Name")`
3. **Page Object Model** - All page interactions through Page Objects
4. **Fluent Pattern** - Page methods return page objects for method chaining
5. **ExtentReport Logging** - Log all test steps with `ExtentReportManager.logInfo()`, `logPass()`, `logFail()`
6. **Unique Test Data** - Use `TestDataGenerator` for generating unique test data
7. **Proper Assertions** - Use TestNG assertions with descriptive messages

