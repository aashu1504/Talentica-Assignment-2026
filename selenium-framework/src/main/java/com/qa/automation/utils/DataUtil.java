package com.qa.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataUtil class provides methods to read test data from JSON and Excel files
 */
public class DataUtil {

    private static final Logger logger = LogManager.getLogger(DataUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads data from a JSON file and returns as JsonNode
     * @param filePath Path to the JSON file
     * @return JsonNode containing the JSON data, or null if failed
     */
    public static JsonNode readJsonFile(String filePath) {
        try {
            logger.info("Reading JSON file: {}", filePath);
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("JSON file not found: {}", filePath);
                return null;
            }
            JsonNode jsonNode = objectMapper.readTree(file);
            logger.info("Successfully read JSON file: {}", filePath);
            return jsonNode;
        } catch (IOException e) {
            logger.error("Failed to read JSON file: {}. Error: {}", filePath, e.getMessage());
            return null;
        }
    }

    /**
     * Gets a value from JSON file using a JSON path (e.g., "user.name")
     * @param filePath Path to the JSON file
     * @param jsonPath Path to the value in JSON (dot-separated)
     * @return Value as String, or null if not found
     */
    public static String getJsonValue(String filePath, String jsonPath) {
        try {
            JsonNode jsonNode = readJsonFile(filePath);
            if (jsonNode == null) {
                return null;
            }

            String[] pathParts = jsonPath.split("\\.");
            JsonNode currentNode = jsonNode;

            for (String part : pathParts) {
                if (currentNode.has(part)) {
                    currentNode = currentNode.get(part);
                } else {
                    logger.warn("JSON path not found: {} in file: {}", jsonPath, filePath);
                    return null;
                }
            }

            String value = currentNode.asText();
            logger.info("Retrieved JSON value: {} = {}", jsonPath, value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get JSON value: {} from file: {}. Error: {}", 
                    jsonPath, filePath, e.getMessage());
            return null;
        }
    }

    /**
     * Gets all data from a JSON file as a Map
     * @param filePath Path to the JSON file
     * @return Map containing all key-value pairs, or null if failed
     */
    public static Map<String, Object> getJsonDataAsMap(String filePath) {
        try {
            logger.info("Reading JSON file as Map: {}", filePath);
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("JSON file not found: {}", filePath);
                return null;
            }
            Map<String, Object> dataMap = objectMapper.readValue(file, Map.class);
            logger.info("Successfully read JSON file as Map: {}", filePath);
            return dataMap;
        } catch (IOException e) {
            logger.error("Failed to read JSON file as Map: {}. Error: {}", filePath, e.getMessage());
            return null;
        }
    }

    /**
     * Reads data from an Excel file and returns as List of Maps
     * Each row becomes a Map with column headers as keys
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to read
     * @return List of Maps containing row data, or empty list if failed
     */
    public static List<Map<String, String>> readExcelFile(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        
        try {
            logger.info("Reading Excel file: {} from sheet: {}", filePath, sheetName);
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                logger.error("Sheet not found: {} in file: {}", sheetName, filePath);
                workbook.close();
                fis.close();
                return dataList;
            }

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                logger.error("Header row not found in sheet: {}", sheetName);
                workbook.close();
                fis.close();
                return dataList;
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValueAsString(cell);
                    rowData.put(headers.get(j), cellValue);
                }
                dataList.add(rowData);
            }

            workbook.close();
            fis.close();
            logger.info("Successfully read {} rows from Excel file: {}", dataList.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to read Excel file: {}. Error: {}", filePath, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while reading Excel file: {}. Error: {}", filePath, e.getMessage());
        }

        return dataList;
    }

    /**
     * Reads data from an Excel file using the first sheet
     * @param filePath Path to the Excel file
     * @return List of Maps containing row data, or empty list if failed
     */
    public static List<Map<String, String>> readExcelFile(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            String firstSheetName = workbook.getSheetAt(0).getSheetName();
            workbook.close();
            fis.close();
            return readExcelFile(filePath, firstSheetName);
        } catch (Exception e) {
            logger.error("Failed to read Excel file: {}. Error: {}", filePath, e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Gets a specific row from Excel file
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet
     * @param rowIndex Index of the row (0-based, excluding header)
     * @return Map containing row data, or null if not found
     */
    public static Map<String, String> getExcelRow(String filePath, String sheetName, int rowIndex) {
        try {
            List<Map<String, String>> dataList = readExcelFile(filePath, sheetName);
            if (rowIndex >= 0 && rowIndex < dataList.size()) {
                logger.info("Retrieved row {} from Excel file: {}", rowIndex, filePath);
                return dataList.get(rowIndex);
            } else {
                logger.warn("Row index {} out of bounds in Excel file: {}", rowIndex, filePath);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get Excel row: {} from file: {}. Error: {}", 
                    rowIndex, filePath, e.getMessage());
            return null;
        }
    }

    /**
     * Gets a specific cell value from Excel file
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet
     * @param rowIndex Index of the row (0-based, excluding header)
     * @param columnName Name of the column (header)
     * @return Cell value as String, or null if not found
     */
    public static String getExcelCellValue(String filePath, String sheetName, int rowIndex, String columnName) {
        try {
            Map<String, String> rowData = getExcelRow(filePath, sheetName, rowIndex);
            if (rowData != null && rowData.containsKey(columnName)) {
                String value = rowData.get(columnName);
                logger.info("Retrieved Excel cell value: {} = {}", columnName, value);
                return value;
            } else {
                logger.warn("Column not found: {} in row {} of Excel file: {}", columnName, rowIndex, filePath);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get Excel cell value: {} from file: {}. Error: {}", 
                    columnName, filePath, e.getMessage());
            return null;
        }
    }

    /**
     * Converts a cell value to String regardless of cell type
     * @param cell Cell object
     * @return String value of the cell
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        // Remove decimal if it's a whole number
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == (long) numericValue) {
                            return String.valueOf((long) numericValue);
                        } else {
                            return String.valueOf(numericValue);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                case BLANK:
                    return "";
                default:
                    return "";
            }
        } catch (Exception e) {
            logger.warn("Error converting cell value to string. Error: {}", e.getMessage());
            return "";
        }
    }
}

