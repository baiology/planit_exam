import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class TestCases extends Services {
    public WebDriver driver;
    public String filepath;
    public String sheetname;
    public String url;


    public TestCases(String url, String filepath, String sheetname) {
        this.driver = DriverFactory.getDriver();
        this.url = url;
        this.filepath = filepath;
        this.sheetname = sheetname;
    }
    public void runTest() {
        driver.get(this.url);
        ExcelUtils excel = new ExcelUtils(this.filepath, this.sheetname);
        excel.setExcelFileSheet();
        ArrayList<ArrayList<String>> steps = excel.getAllData();
        for (ArrayList<String> step:steps) {
            Integer row_status = steps.indexOf(step) + 1;
//            System.out.println(steps.indexOf(step));
            String locType = step.get(2);
            String locator = step.get(3);
            String inputData = step.get(4);
            String actionEvent = step.get(5);
            String sfreq = step.get(6);
            Integer freq;
            if (sfreq.toUpperCase().equals("N/A") || sfreq == null) {
                freq = 1;
            } else {
                freq = new Double(sfreq).intValue();
            }
            if (actionEvent.toUpperCase().equals("CLICK")) {
                click(driver, locator, excel, row_status, freq);
            } else if (actionEvent.toUpperCase().equals("SET")) {
                type(driver, locator, inputData, excel, row_status);
            } else if (actionEvent.toUpperCase().equals("VERIFYPRESENCE")) {
                isElementPresent(driver, locator, excel, row_status);
            } else if (actionEvent.toUpperCase().equals("VERIFYABSENCE")) {
                isElementNotPresent(driver, locator, excel, row_status);
            } else if (actionEvent.toUpperCase().equals("WAITVERIFYPRESENCE")) {
                waitForElementVisible(driver, locator, excel, row_status);
            } else if (actionEvent.toUpperCase().equals("VERIFYITEMS")) {
                verifyItems(driver, locator, excel, row_status, freq);
            }
        }
        driver.quit();
    }
}