package ru.calcus;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

public class AbstractWebDriver {

    public static WebDriver driver;

    private static final String webDriversPath = "src/webdrivers/";

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional String browser) {

        if (browser == null) {
            browser = "test";
        }

        if (browser.equals("chrome")) {

            System.setProperty("webdriver.chrome.driver", "" + webDriversPath + "chromedriver.exe");
            driver = new ChromeDriver();
            System.out.println(browser);
        }

        if (browser.equals("edge")) {

            System.setProperty("webdriver.edge.driver", "" + webDriversPath + "msedgedriver.exe");
            driver = new EdgeDriver();
            System.out.println(browser);

        }

    }


    @AfterClass(alwaysRun = true)
    public void close() {

        if (driver != null)
            driver.quit();

    }
}
