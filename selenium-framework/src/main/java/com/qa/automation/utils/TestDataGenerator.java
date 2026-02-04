package com.qa.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * TestDataGenerator utility class for generating unique test data
 * Used for generating unique email addresses, phone numbers, etc.
 */
public class TestDataGenerator {

    private static final Random random = new Random();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * Generates a unique email address for test automation
     * Format: prefix_timestamp_random@domain.com
     * 
     * @param prefix Email prefix (e.g., "john.doe")
     * @param domain Email domain (e.g., "test.com")
     * @return Unique email address
     */
    public static String generateUniqueEmail(String prefix, String domain) {
        String timestamp = dateFormat.format(new Date());
        int randomNum = random.nextInt(10000);
        return prefix + "_" + timestamp + "_" + randomNum + "@" + domain;
    }

    /**
     * Generates a unique email address with default domain
     * @param prefix Email prefix
     * @return Unique email address
     */
    public static String generateUniqueEmail(String prefix) {
        return generateUniqueEmail(prefix, "test.com");
    }

    /**
     * Generates a unique email address with default prefix and domain
     * @return Unique email address
     */
    public static String generateUniqueEmail() {
        return generateUniqueEmail("testuser", "test.com");
    }

    /**
     * Generates a random phone number
     * @param length Length of phone number (default 10)
     * @return Random phone number as string
     */
    public static String generatePhoneNumber(int length) {
        StringBuilder phone = new StringBuilder();
        // First digit should not be 0
        phone.append(random.nextInt(9) + 1);
        for (int i = 1; i < length; i++) {
            phone.append(random.nextInt(10));
        }
        return phone.toString();
    }

    /**
     * Generates a 10-digit random phone number
     * @return Random phone number
     */
    public static String generatePhoneNumber() {
        return generatePhoneNumber(10);
    }
}

