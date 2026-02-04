# Selenium Java Framework

A comprehensive Maven-based Selenium automation framework with TestNG, WebDriverManager, ExtentReports, Apache POI, and Log4j2.

## Project Structure

```
selenium-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/qa/automation/
│   │   │       ├── base/
│   │   │       │   └── DriverFactory.java
│   │   │       ├── pages/
│   │   │       ├── utils/
│   │   │       └── listeners/
│   │   └── resources/
│   │       ├── config.properties
│   │       └── log4j2.xml
│   └── test/
│       └── java/
│           └── com/qa/automation/
│               └── tests/
├── pom.xml
└── README.md
```

## Dependencies

- **Selenium 4.15.0** - Web automation framework
- **TestNG 7.8.0** - Testing framework
- **WebDriverManager 5.6.2** - Automatic driver management
- **ExtentReports 5.1.1** - HTML reporting
- **Apache POI 5.2.4** - Excel file handling
- **Log4j2 2.21.1** - Logging framework

## Configuration

Edit `src/main/resources/config.properties` to configure:

- `url` - Application URL
- `browser` - Browser type (chrome, firefox, edge)
- `headless` - Enable/disable headless mode (true/false)
- `timeout` - Implicit wait timeout in seconds
- `reportPath` - Path for ExtentReports output

## Usage

### Running Tests

```bash
mvn clean test
```

### DriverFactory Features

- ThreadLocal WebDriver management for parallel execution
- Support for Chrome, Firefox, and Edge browsers
- Headless mode toggle for all browsers
- Comprehensive error handling with Try-Catch blocks
- Configuration-driven browser and timeout settings

## Browser Support

The framework supports:
- Chrome (with headless mode)
- Firefox (with headless mode)
- Edge (with headless mode)

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

