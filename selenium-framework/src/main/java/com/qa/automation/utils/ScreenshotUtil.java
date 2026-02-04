package com.qa.automation.utils;

import com.qa.automation.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil class provides methods to capture screenshots
 */
public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";

    /**
     * Captures a screenshot and saves it to the screenshots directory
     * @param screenshotName Name of the screenshot file (without extension)
     * @return Path to the saved screenshot file, or null if failed
     */
    public static String captureScreenshot(String screenshotName) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) {
                logger.error("WebDriver is null, cannot capture screenshot");
                return null;
            }

            // Create screenshots directory if it doesn't exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
                logger.info("Created screenshots directory: {}", SCREENSHOT_DIR);
            }

            // Generate timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            
            // Convert to absolute path
            String userDir = System.getProperty("user.dir");
            String absoluteScreenshotDir = userDir + "/" + SCREENSHOT_DIR;
            Path absoluteScreenshotPath = Paths.get(absoluteScreenshotDir);
            if (!Files.exists(absoluteScreenshotPath)) {
                Files.createDirectories(absoluteScreenshotPath);
            }
            
            String filePath = absoluteScreenshotDir + fileName;

            // Capture screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            
            // Copy file to destination
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("Screenshot captured successfully: {}", filePath);
            // Return relative path for ExtentReports (it will convert to absolute)
            return SCREENSHOT_DIR + fileName;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}. Error: {}", screenshotName, e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error while capturing screenshot: {}. Error: {}", screenshotName, e.getMessage());
            return null;
        }
    }

    /**
     * Captures a screenshot with default naming convention
     * @return Path to the saved screenshot file, or null if failed
     */
    public static String captureScreenshot() {
        return captureScreenshot("screenshot");
    }

    /**
     * Captures screenshot as base64 string for embedding in reports
     * @return Base64 encoded screenshot string, or null if failed
     */
    public static String captureScreenshotAsBase64() {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) {
                logger.error("WebDriver is null, cannot capture screenshot");
                return null;
            }

            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            String base64Screenshot = takesScreenshot.getScreenshotAs(OutputType.BASE64);
            logger.info("Screenshot captured as base64 successfully");
            return base64Screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as base64. Error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Gets the absolute path of a screenshot file
     * @param relativePath Relative path of the screenshot
     * @return Absolute path of the screenshot
     */
    public static String getAbsolutePath(String relativePath) {
        try {
            File file = new File(relativePath);
            return file.getAbsolutePath();
        } catch (Exception e) {
            logger.error("Failed to get absolute path for: {}. Error: {}", relativePath, e.getMessage());
            return relativePath;
        }
    }
}

