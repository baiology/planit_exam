import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelUtils {
    public static String sheetName;
    public static String filepath;

    //Main Directory of the project

    //Location of Test data excel file
    public static String testDataExcelPath = null;

    //Excel WorkBook
    private static XSSFWorkbook excelWBook;

    //Excel Sheet
    private static XSSFSheet excelWSheet;

    //Excel cell
    private static XSSFCell cell;

    //Excel row
    private static XSSFRow row;

    //Row Number
    public static int rowNumber;

    //Last row
    public static int lastrow;

    //Last col
    public static int lastcol;

    //Column Number
    public static int columnNumber;

    //Setter and Getters of row and columns
    public static void setRowNumber(int pRowNumber) {
        rowNumber = pRowNumber;
    }

    public static int getRowNumber() {
        return rowNumber;
    }

    public static void setColumnNumber(int pColumnNumber) {
        columnNumber = pColumnNumber;
    }

    public static int getColumnNumber() {
        return columnNumber;
    }

    public ExcelUtils(String filepath, String sheetname) {
        this.filepath = filepath;
        this.sheetName = sheetname;
    }

    // This method has two parameters: "Test data excel file name" and "Excel sheet name"
    // It creates FileInputStream and set excel file and excel sheet to excelWBook and excelWSheet variables.
    public static void setExcelFileSheet() {
        testDataExcelPath = filepath;
        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(testDataExcelPath);
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //This method reads the test data from the Excel cell.
    //We are passing row number and column number as parameters.
    public static String getCellData(int RowNum, int ColNum) {
        try {
            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        } catch (Exception e) {
            throw (e);
        }
    }

    //This method takes row number as a parameter and returns the data of given row number.
    public static XSSFRow getRowData(int RowNum) {
        try {
            row = excelWSheet.getRow(RowNum);
            return row;
        } catch (Exception e) {
            throw (e);
        }
    }

    //This method gets last row.
    public static int getLastRow() {
        try {
            lastrow = excelWSheet.getLastRowNum();
            return lastrow;
        } catch (Exception e) {
            throw (e);
        }
    }

    //This method gets last row.
    public static int getLastCol() {
        int columnIndex = -1;
        for (int i = excelWSheet.getRow(0).getLastCellNum() - 1; i >= 0; i--) {
            Cell cell = excelWSheet.getRow(0).getCell(i);

            if (cell == null || CellType.BLANK.equals(cell.getCellType()) || StringUtils.isBlank(cell.getStringCellValue())) {
                continue;
            } else {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        return columnIndex;    }

    public ArrayList<XSSFCell> getCollData(String colletter){
        int colIdx = CellReference.convertColStringToIndex(colletter);
        ArrayList<XSSFCell> arr_coldata = new ArrayList<>();
        for (int i = 1; i <= getLastRow(); i++) {
            Cell cell = excelWSheet.getRow(i).getCell(colIdx);
            if (cell != null) {
                arr_coldata.add(excelWSheet.getRow(i).getCell(colIdx));
            }
        }
        return arr_coldata;
    }

    public ArrayList<ArrayList<String>> getAllData(){
        String rowval;
        ArrayList<ArrayList<String>> arr_data = new ArrayList<ArrayList<String>>();
        for (int i = 1; i <= getLastRow(); i++) { //start row at index 1
            arr_data.add(new ArrayList<String>());
            for (int j = 0; j <= getLastCol(); j++) {
                rowval = String.valueOf(excelWSheet.getRow(i).getCell(j));
                if (rowval == null) {
                    rowval = "n/a";
                } else {
                    rowval = String.valueOf(excelWSheet.getRow(i).getCell(j));
                }
                arr_data.get(i-1).add(rowval);
            }
        }
        return arr_data;
    }
        //This method gets excel file, row and column number and set a value to the that cell.
    public static void setCellData(String value, int RowNum, int ColNum) {
        try {
            row = excelWSheet.getRow(RowNum);
            cell = row.getCell(ColNum);
            if (cell == null) {
                cell = row.createCell(ColNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(testDataExcelPath);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}