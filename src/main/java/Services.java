import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Services {
    public Integer itemCount = 0;
    public String funny_cow_xpath = "//*[@id=\"product-6\"]/div/p/a";
    public String fluffy_bunny_xpath = "//*[@id=\"product-4\"]/div/p/a";

    public void waitForElement(WebDriver driver, String locator) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    protected void click(WebDriver driver, String locator, ExcelUtils excel, Integer writeRow, Integer freq) {
        try {
            if (locator.equals(funny_cow_xpath) || locator.equals(fluffy_bunny_xpath)) {
                itemCount += freq;
            }
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            for (Integer ctr = 1; ctr <= freq; ctr++) {
                driver.findElement(By.xpath(locator)).click();
            }
            excel.setCellData("Passed", writeRow, 7);
        } catch (NoSuchElementException e) {
            excel.setCellData("Failed", writeRow, 7);
        }
    }

    protected void clickViaCss(WebDriver driver, String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }

    protected void type(WebDriver driver, String locator, String text, ExcelUtils excel, Integer writeRow) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            driver.findElement(By.xpath(locator)).sendKeys(text);
            excel.setCellData("Passed", writeRow, 7);
        } catch (NoSuchElementException e) {
            excel.setCellData("Failed", writeRow, 7);
        }
    }

    protected void typeViaCss(WebDriver driver, String locator, String text) {
        driver.findElement(By.cssSelector(locator)).sendKeys(text);
    }

//    protected void type(WebDriver driver, String method, String locator, String text) {
//        if (method.equalsIgnoreCase("xpath"))
//            driver.findElement(By.xpath(locator)).sendKeys(text);
//        else if (method.equalsIgnoreCase("css"))
//            driver.findElement(By.cssSelector(locator)).sendKeys(text);
//        else
//            driver.findElement(By.id(locator)).sendKeys(text);
//    }

    protected boolean isElementPresent(WebDriver driver, String locator, ExcelUtils excel, Integer writeRow) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            driver.findElement(By.xpath(locator));
            excel.setCellData("Passed", writeRow, 7);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            excel.setCellData("Failed", writeRow, 7);
            return false;
        }
    }

    protected boolean isElementNotPresent(WebDriver driver, String locator, ExcelUtils excel, Integer writeRow) {
        try {
            //WebDriverWait wait = new WebDriverWait(driver, 15);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            driver.findElement(By.xpath(locator));
            excel.setCellData("Failed", writeRow, 7);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            excel.setCellData("Passed", writeRow, 7);
            return false;
        }
    }

    private boolean isElementVisible(WebDriver driver, String locator) {
        try {
            return driver.findElement(By.xpath(locator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForElementVisible(WebDriver driver, String locator, ExcelUtils excel, Integer writeRow) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            excel.setCellData("Passed", writeRow, 7);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            excel.setCellData("Failed", writeRow, 7);
        }
    }
    protected void waitForElementInVisible(WebDriver driver, String locator) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
    }

    protected WebElement getWebElement(WebDriver driver, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public String getText(WebDriver driver, String xpath, Boolean isAttrib) {
        if (isAttrib) {
            return driver.findElement(By.xpath(xpath)).getAttribute("value");
        } else {
            return driver.findElement(By.xpath(xpath)).getText();
        }
    }

    public void verifyItems(WebDriver driver, String locator, ExcelUtils excel, Integer writeRow, Integer freq) {
        //System.out.println(itemCount);
        Integer totalToys = 2;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
//            String sActCount = driver.findElement(By.xpath(locator)).getText();
//            Integer actCount = Integer.parseInt(sActCount);

            String start_xpath = "/html/body/div[2]/div/form/table/tbody/tr";
            Integer total_quantity = 0;
            for (Integer ctr = 1; ctr <= totalToys; ctr++) {
                String xp = start_xpath + "[" + ctr + "]/td[3]/input";
                String squantity = getText(driver, xp, Boolean.TRUE);
                if (squantity != null || squantity.equals("")) {
                    Integer quantity = new Double(squantity).intValue();
                    total_quantity += quantity;
                }
            }
            if (total_quantity.equals(itemCount)) {
                excel.setCellData("Passed", writeRow, 7);
            } else {
                excel.setCellData("Failed", writeRow, 7);
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            excel.setCellData("Failed", writeRow, 7);
        }
    }

//    public void verifyItems(WebDriver driver, String locator, ExcelUtils excel, Integer writeRow, Integer freq) {
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, 10);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
//            String sActCount = driver.findElement(By.xpath(locator)).getText();
//            Integer actCount = Integer.parseInt(sActCount);
//            if (itemCount.equals(actCount)) {
//                excel.setCellData("Passed", writeRow, 7);
//            } else {
//                excel.setCellData("Failed", writeRow, 7);
//            }
//        } catch (org.openqa.selenium.NoSuchElementException e) {
//            excel.setCellData("Failed", writeRow, 7);
//        }
//    }
}
