package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class AbstractWebDriver {

    public WebDriver driver;

    //Устанавливаем время ожидания ответа страницы
    private static final Integer webDriverWaitTime = 10;

    private static final String webDriversPath = "src/webdrivers/";

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {

        if (browser == null) {
            browser = "chrome";
        }

        if (browser.equals("chrome")) {

            System.setProperty("webdriver.chrome.driver", "" + webDriversPath + "chromedriver.exe");
            driver = new ChromeDriver();
        }

        else if (browser.equals("edge")) {

            System.setProperty("webdriver.edge.driver", "" + webDriversPath + "msedgedriver.exe");
            driver = new EdgeDriver();
        }

        //Устанавливаем неявное время ожидания ответа страницы для драйвера
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(webDriverWaitTime));

        System.out.println(browser);

    }


    @AfterMethod(alwaysRun = true)
    public void close() {

        if (driver != null)
            driver.quit();

    }
}
