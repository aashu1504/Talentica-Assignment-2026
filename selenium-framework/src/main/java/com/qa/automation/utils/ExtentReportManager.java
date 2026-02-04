package com.qa.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.automation.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager class manages ExtentReports instance and test reporting
 */
public class ExtentReportManager {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Initializes ExtentReports with HTML reporter
     * @return ExtentReports instance
     */
    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            try {
                String reportPath = DriverFactory.getReportPath();
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String reportFileName = "ExtentReport_" + timestamp + ".html";
                
                // Convert to absolute path
                java.io.File reportPathFile = new java.io.File(reportPath);
                if (!reportPathFile.isAbsolute()) {
                    String userDir = System.getProperty("user.dir");
                    reportPath = userDir + "/" + reportPath;
                }
                
                String reportFilePath = reportPath + "/" + reportFileName;

                // Create report directory if it doesn't exist
                java.nio.file.Path reportDir = java.nio.file.Paths.get(reportPath);
                if (!java.nio.file.Files.exists(reportDir)) {
                    java.nio.file.Files.createDirectories(reportDir);
                    logger.info("Created report directory: {}", reportPath);
                }

                extentReports = new ExtentReports();
                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
                
                // Configure report appearance
                sparkReporter.config().setTheme(Theme.DARK);
                sparkReporter.config().setReportName("Selenium Automation Test Report");
                sparkReporter.config().setDocumentTitle("Test Execution Report");
                sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
                
                // Set default view to dashboard
                try {
                    java.lang.reflect.Method setViewOrderingMethod = sparkReporter.config().getClass()
                            .getMethod("setViewOrdering", 
                                Class.forName("com.aventstack.extentreports.reporter.configuration.ViewOrdering"));
                    if (setViewOrderingMethod != null) {
                        Object viewOrdering = java.lang.Enum.valueOf(
                                (Class<? extends java.lang.Enum>) Class.forName("com.aventstack.extentreports.reporter.configuration.ViewOrdering"),
                                "OLDEST_FIRST");
                        setViewOrderingMethod.invoke(sparkReporter.config(), viewOrdering);
                    }
                } catch (Exception e) {
                    logger.debug("View ordering configuration not available: {}", e.getMessage());
                }
                
                extentReports.attachReporter(sparkReporter);
                
                // Set system information
                extentReports.setSystemInfo("Browser", DriverFactory.getBrowser());
                extentReports.setSystemInfo("Headless Mode", String.valueOf(DriverFactory.isHeadless()));
                extentReports.setSystemInfo("Timeout", String.valueOf(DriverFactory.getTimeout()) + " seconds");
                extentReports.setSystemInfo("OS", System.getProperty("os.name"));
                extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
                
                logger.info("ExtentReports initialized successfully. Report path: {}", reportFilePath);
            } catch (Exception e) {
                logger.error("Failed to initialize ExtentReports. Error: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        return extentReports;
    }

    /**
     * Creates a new test in the report
     * @param testName Name of the test
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName) {
        try {
            ExtentTest test = getExtentReports().createTest(testName);
            extentTest.set(test);
            
            // Add test category for better organization
            test.assignCategory("Sanity Tests");
            
            logger.info("Created test in ExtentReport: {}", testName);
            return test;
        } catch (Exception e) {
            logger.error("Failed to create test in ExtentReport: {}. Error: {}", testName, e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new test in the report with description
     * @param testName Name of the test
     * @param description Description of the test
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        try {
            ExtentTest test = getExtentReports().createTest(testName, description);
            extentTest.set(test);
            
            // Add test category for better organization
            test.assignCategory("Sanity Tests");
            
            logger.info("Created test in ExtentReport: {} with description: {}", testName, description);
            return test;
        } catch (Exception e) {
            logger.error("Failed to create test in ExtentReport: {}. Error: {}", testName, e.getMessage());
            return null;
        }
    }

    /**
     * Gets the current test instance
     * @return ExtentTest instance
     */
    public static ExtentTest getTest() {
        ExtentTest test = extentTest.get();
        if (test == null) {
            logger.warn("No test found in ThreadLocal. Thread: {}", Thread.currentThread().getName());
        }
        return test;
    }

    /**
     * Gets or creates a test instance
     * If test doesn't exist, creates a new one with the given name
     * @param testName Name of the test
     * @return ExtentTest instance
     */
    public static ExtentTest getOrCreateTest(String testName) {
        ExtentTest test = extentTest.get();
        if (test == null) {
            logger.warn("Test not found in ThreadLocal, creating new test: {}", testName);
            test = createTest(testName);
        }
        return test;
    }

    /**
     * Logs an info message to the current test
     * @param message Message to log
     */
    public static void logInfo(String message) {
        try {
            ExtentTest test = getTest();
            if (test == null) {
                // Try to get test from current thread name or create a fallback
                String threadName = Thread.currentThread().getName();
                logger.warn("No active test found. Thread: {}. Message: {}", threadName, message);
                // Don't create test here as it should be created in onTestStart
                return;
            }
            test.info(message);
            logger.debug("ExtentReport Info logged: {}", message);
        } catch (Exception e) {
            logger.error("Failed to log info to ExtentReport. Error: {}", e.getMessage());
        }
    }

    /**
     * Logs a pass message to the current test
     * @param message Message to log
     */
    public static void logPass(String message) {
        try {
            ExtentTest test = getTest();
            if (test != null) {
                test.pass(message);
                logger.info("ExtentReport Pass: {}", message);
            } else {
                logger.warn("No active test found to log pass: {}", message);
            }
        } catch (Exception e) {
            logger.error("Failed to log pass to ExtentReport. Error: {}", e.getMessage());
        }
    }

    /**
     * Logs a fail message to the current test
     * @param message Message to log
     */
    public static void logFail(String message) {
        try {
            if (getTest() != null) {
                getTest().fail(message);
                logger.error("ExtentReport Fail: {}", message);
            }
        } catch (Exception e) {
            logger.error("Failed to log fail to ExtentReport. Error: {}", e.getMessage());
        }
    }

    /**
     * Logs a skip message to the current test
     * @param message Message to log
     */
    public static void logSkip(String message) {
        try {
            if (getTest() != null) {
                getTest().skip(message);
                logger.warn("ExtentReport Skip: {}", message);
            }
        } catch (Exception e) {
            logger.error("Failed to log skip to ExtentReport. Error: {}", e.getMessage());
        }
    }

    /**
     * Attaches a screenshot to the current test
     * @param screenshotPath Path to the screenshot file (relative or absolute)
     */
    public static void attachScreenshot(String screenshotPath) {
        try {
            ExtentTest test = getTest();
            if (test != null && screenshotPath != null) {
                // Convert to absolute path if relative
                java.io.File screenshotFile = new java.io.File(screenshotPath);
                String absolutePath;
                if (screenshotFile.isAbsolute()) {
                    absolutePath = screenshotPath;
                } else {
                    String userDir = System.getProperty("user.dir");
                    absolutePath = userDir + "/" + screenshotPath;
                }
                
                // Verify file exists
                if (new java.io.File(absolutePath).exists()) {
                    test.addScreenCaptureFromPath(absolutePath);
                    logger.info("Screenshot attached to ExtentReport: {}", absolutePath);
                } else {
                    logger.warn("Screenshot file not found: {}. Trying relative path.", absolutePath);
                    // Try relative path as fallback
                    test.addScreenCaptureFromPath(screenshotPath);
                }
            } else {
                logger.warn("Cannot attach screenshot - Test: {}, Path: {}", test != null ? "EXISTS" : "NULL", screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to ExtentReport: {}. Error: {}", screenshotPath, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Attaches a base64 screenshot to the current test
     * @param base64Screenshot Base64 encoded screenshot string
     */
    public static void attachScreenshotBase64(String base64Screenshot) {
        try {
            if (getTest() != null && base64Screenshot != null) {
                getTest().addScreenCaptureFromBase64String(base64Screenshot);
                logger.info("Base64 screenshot attached to ExtentReport");
            }
        } catch (Exception e) {
            logger.error("Failed to attach base64 screenshot to ExtentReport. Error: {}", e.getMessage());
        }
    }

    /**
     * Flushes the ExtentReports instance
     */
    public static void flushReport() {
        try {
            if (extentReports != null) {
                extentReports.flush();
                logger.info("ExtentReports flushed successfully");
                
                // Add script to show dashboard by default - after ExtentReports scripts load
                setDashboardAsDefault();
            }
        } catch (Exception e) {
            logger.error("Failed to flush ExtentReports. Error: {}", e.getMessage());
        }
    }

    /**
     * Sets dashboard as default view by adding a script after ExtentReports scripts
     */
    private static void setDashboardAsDefault() {
        try {
            String reportPath = DriverFactory.getReportPath();
            java.io.File reportPathFile = new java.io.File(reportPath);
            if (!reportPathFile.isAbsolute()) {
                String userDir = System.getProperty("user.dir");
                reportPath = userDir + "/" + reportPath;
            }
            
            java.io.File reportDir = new java.io.File(reportPath);
            if (reportDir.exists() && reportDir.isDirectory()) {
                java.io.File[] reportFiles = reportDir.listFiles((dir, name) -> 
                    name.startsWith("ExtentReport_") && name.endsWith(".html"));
                
                if (reportFiles != null && reportFiles.length > 0) {
                    java.util.Arrays.sort(reportFiles, (f1, f2) -> 
                        Long.compare(f2.lastModified(), f1.lastModified()));
                    java.io.File latestReport = reportFiles[0];
                    
                    String content = new String(java.nio.file.Files.readAllBytes(latestReport.toPath()), 
                            java.nio.charset.StandardCharsets.UTF_8);
                    
                    // Check if our dashboard script already exists (look for the specific script pattern)
                    // Don't match onclick attributes, only actual script tags
                    boolean hasDashboardScript = (content.contains("window.addEventListener('load'") && 
                                                  content.contains("toggleView('dashboard-view')") &&
                                                  content.contains("initDashboard")) ||
                                                 (content.contains("window.addEventListener(\"load\"") && 
                                                  content.contains("toggleView(\"dashboard-view\")") &&
                                                  content.contains("initDashboard"));
                    
                    // Only add if not already present and report has body tag and spark-script
                    if (!hasDashboardScript && content.contains("</body>") && content.contains("spark-script.js")) {
                        // Add script to show dashboard by default - must run after ExtentReports scripts load
                        String scriptToAdd = "<script type=\"text/javascript\">" +
                                "window.addEventListener('load', function() {" +
                                "  setTimeout(function() {" +
                                "    try {" +
                                "      if (typeof toggleView === 'function') {" +
                                "        toggleView('dashboard-view');" +
                                "        var navDashboard = document.getElementById('nav-dashboard');" +
                                "        var navTest = document.getElementById('nav-test');" +
                                "        if (navDashboard) navDashboard.classList.add('active');" +
                                "        if (navTest) navTest.classList.remove('active');" +
                                "      }" +
                                "    } catch(e) { console.error('Error setting dashboard view:', e); }" +
                                "  }, 300);" +
                                "});" +
                                "</script>";
                        
                        // Insert before closing body tag
                        content = content.replace("</body>", scriptToAdd + "</body>");
                        java.nio.file.Files.write(latestReport.toPath(), content.getBytes(
                                java.nio.charset.StandardCharsets.UTF_8));
                        logger.info("✓ Added dashboard default script to: {}", latestReport.getName());
                    } else if (hasDashboardScript) {
                        logger.debug("Dashboard script already exists in report");
                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Could not set dashboard as default: {}", e.getMessage());
        }
    }

    /**
     * Removes the current test from ThreadLocal
     */
    public static void removeTest() {
        extentTest.remove();
    }
}

