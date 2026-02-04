package com.qa.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * DriverFactory class to manage WebDriver instances using ThreadLocal
 * Supports Chrome, Firefox, and Edge browsers with Headless mode toggle
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static Properties config;

    /**
     * Loads configuration from config.properties file
     */
    static {
        try {
            config = new Properties();
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            config.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initializes and returns WebDriver instance based on browser configuration
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        try {
            if (driver.get() == null) {
                String browser = getBrowser();
                boolean headless = isHeadless();
                
                switch (browser.toLowerCase()) {
                    case "chrome":
                        driver.set(initChromeDriver(headless));
                        break;
                    case "firefox":
                        driver.set(initFirefoxDriver(headless));
                        break;
                    case "edge":
                        driver.set(initEdgeDriver(headless));
                        break;
                    default:
                        System.err.println("Unsupported browser: " + browser + ". Defaulting to Chrome.");
                        driver.set(initChromeDriver(headless));
                        break;
                }
                
                driver.get().manage().window().maximize();
                driver.get().manage().timeouts().implicitlyWait(getTimeout(), TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            System.err.println("Error initializing WebDriver: " + e.getMessage());
            e.printStackTrace();
        }
        return driver.get();
    }

    /**
     * Initializes ChromeDriver with optional headless mode
     * @param headless true to enable headless mode, false otherwise
     * @return ChromeDriver instance
     */
    private static WebDriver initChromeDriver(boolean headless) {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            
            return new ChromeDriver(options);
        } catch (Exception e) {
            System.err.println("Error initializing ChromeDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize ChromeDriver", e);
        }
    }

    /**
     * Initializes FirefoxDriver with optional headless mode
     * @param headless true to enable headless mode, false otherwise
     * @return FirefoxDriver instance
     */
    private static WebDriver initFirefoxDriver(boolean headless) {
        try {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            
            if (headless) {
                options.addArguments("--headless");
            }
            
            return new FirefoxDriver(options);
        } catch (Exception e) {
            System.err.println("Error initializing FirefoxDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize FirefoxDriver", e);
        }
    }

    /**
     * Initializes EdgeDriver with optional headless mode
     * @param headless true to enable headless mode, false otherwise
     * @return EdgeDriver instance
     */
    private static WebDriver initEdgeDriver(boolean headless) {
        try {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            
            return new EdgeDriver(options);
        } catch (Exception e) {
            System.err.println("Error initializing EdgeDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize EdgeDriver", e);
        }
    }

    /**
     * Closes the current WebDriver instance and removes it from ThreadLocal
     */
    public static void closeDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
            }
        } catch (Exception e) {
            System.err.println("Error closing WebDriver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the browser name from configuration
     * @return browser name as String
     */
    public static String getBrowser() {
        try {
            return config.getProperty("browser", "chrome");
        } catch (Exception e) {
            System.err.println("Error reading browser configuration: " + e.getMessage());
            e.printStackTrace();
            return "chrome";
        }
    }

    /**
     * Checks if headless mode is enabled from configuration
     * @return true if headless mode is enabled, false otherwise
     */
    public static boolean isHeadless() {
        try {
            return Boolean.parseBoolean(config.getProperty("headless", "false"));
        } catch (Exception e) {
            System.err.println("Error reading headless configuration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the timeout value from configuration
     * @return timeout in seconds
     */
    public static int getTimeout() {
        try {
            return Integer.parseInt(config.getProperty("timeout", "30"));
        } catch (Exception e) {
            System.err.println("Error reading timeout configuration: " + e.getMessage());
            e.printStackTrace();
            return 30;
        }
    }

    /**
     * Gets the application URL from configuration
     * @return URL as String
     */
    public static String getUrl() {
        try {
            return config.getProperty("url", "https://www.google.com");
        } catch (Exception e) {
            System.err.println("Error reading URL configuration: " + e.getMessage());
            e.printStackTrace();
            return "https://www.google.com";
        }
    }

    /**
     * Gets the report path from configuration
     * @return report path as String
     */
    public static String getReportPath() {
        try {
            return config.getProperty("reportPath", "test-output/ExtentReports");
        } catch (Exception e) {
            System.err.println("Error reading reportPath configuration: " + e.getMessage());
            e.printStackTrace();
            return "test-output/ExtentReports";
        }
    }
}

