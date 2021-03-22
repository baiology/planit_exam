import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    public static WebDriver getDriver() {
        String curr_dir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", curr_dir + "\\chromedriver.exe");
//        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}