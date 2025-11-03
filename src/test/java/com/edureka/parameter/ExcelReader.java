package com.edureka.parameter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ExcelReader - Utility to read test data from Excel files
 * Supports .xlsx format using Apache POI
 */
public class ExcelReader {
    
    private static final String EXCEL_FILE_PATH = "src/test/resources/ExcelData/Data.xlsx";
    
    /**
     * Get test data from Excel sheet as Object[][]
     * Used with TestNG @DataProvider
     * 
     * @param sheetName - Name of the Excel sheet
     * @return Object[][] - 2D array of test data
     */
    public static Object[][] getTestData(String sheetName) {
        Object[][] data = null;
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                System.err.println("✗ Sheet '" + sheetName + "' not found in Excel file");
                return new Object[0][0];
            }
            
            // Get total rows (excluding header)
            int totalRows = sheet.getLastRowNum();
            
            // Get total columns from header row
            Row headerRow = sheet.getRow(0);
            int totalCols = headerRow.getLastCellNum();
            
            // Initialize data array
            data = new Object[totalRows][totalCols];
            
            // Read data (skip header row - start from row 1)
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                
                if (row != null) {
                    for (int j = 0; j < totalCols; j++) {
                        Cell cell = row.getCell(j);
                        data[i-1][j] = getCellValue(cell);
                    }
                }
            }
            
            file.close();
            System.out.println("✓ Test data loaded from sheet: " + sheetName + 
                             " (Rows: " + totalRows + ", Columns: " + totalCols + ")");
            
        } catch (IOException e) {
            System.err.println("✗ Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return data;
    }
    
    /**
     * Get test data with custom file path
     */
    public static Object[][] getTestData(String filePath, String sheetName) {
        Object[][] data = null;
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                System.err.println("✗ Sheet '" + sheetName + "' not found");
                return new Object[0][0];
            }
            
            int totalRows = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(0);
            int totalCols = headerRow.getLastCellNum();
            
            data = new Object[totalRows][totalCols];
            
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < totalCols; j++) {
                        Cell cell = row.getCell(j);
                        data[i-1][j] = getCellValue(cell);
                    }
                }
            }
            
            file.close();
            System.out.println("✓ Data loaded: " + sheetName + " (" + totalRows + " rows)");
            
        } catch (IOException e) {
            System.err.println("✗ Error reading file: " + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return data;
    }
    
    /**
     * Get cell value as String regardless of cell type
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
                
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                // Return as integer if no decimal part
                double numValue = cell.getNumericCellValue();
                if (numValue == Math.floor(numValue)) {
                    return String.valueOf((int) numValue);
                }
                return String.valueOf(numValue);
                
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
                
            case FORMULA:
                return cell.getCellFormula();
                
            case BLANK:
                return "";
                
            default:
                return "";
        }
    }
    
    /**
     * Get specific column data from sheet
     */
    public static List<String> getColumnData(String sheetName, String columnName) {
        List<String> columnData = new ArrayList<>();
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                System.err.println("✗ Sheet not found: " + sheetName);
                return columnData;
            }
            
            // Find column index
            Row headerRow = sheet.getRow(0);
            int columnIndex = -1;
            
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && getCellValue(cell).equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }
            
            if (columnIndex == -1) {
                System.err.println("✗ Column not found: " + columnName);
                return columnData;
            }
            
            // Read column data
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(columnIndex);
                    columnData.add(getCellValue(cell));
                }
            }
            
            file.close();
            System.out.println("✓ Column data loaded: " + columnName + " (" + columnData.size() + " values)");
            
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return columnData;
    }
    
    /**
     * Get row count from sheet (excluding header)
     */
    public static int getRowCount(String sheetName) {
        int rowCount = 0;
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet != null) {
                rowCount = sheet.getLastRowNum();
            }
            
            file.close();
            
        } catch (IOException e) {
            System.err.println("✗ Error getting row count: " + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return rowCount;
    }
    
    /**
     * Get column count from sheet
     */
    public static int getColumnCount(String sheetName) {
        int colCount = 0;
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet != null) {
                Row headerRow = sheet.getRow(0);
                if (headerRow != null) {
                    colCount = headerRow.getLastCellNum();
                }
            }
            
            file.close();
            
        } catch (IOException e) {
            System.err.println("✗ Error getting column count: " + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return colCount;
    }
    
    /**
     * Get all sheet names from Excel file
     */
    public static List<String> getAllSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            workbook = new XSSFWorkbook(file);
            
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
            
            file.close();
            System.out.println("✓ Total sheets: " + numberOfSheets);
            
        } catch (IOException e) {
            System.err.println("✗ Error getting sheet names: " + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return sheetNames;
    }
    
    /**
     * Print all data from a sheet (for debugging)
     */
    public static void printSheetData(String sheetName) {
        Workbook workbook = null;
        
        try {
            FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                System.err.println("✗ Sheet not found: " + sheetName);
                return;
            }
            
            System.out.println("\n========== Data from Sheet: " + sheetName + " ==========");
            
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    System.out.print(getCellValue(cell) + "\t");
                }
                System.out.println();
            }
            
            System.out.println("========================================\n");
            file.close();
            
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}