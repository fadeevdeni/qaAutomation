package ru.calcus;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class AbstractWebDriver {

    public ChromeDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/webDrivers/chromedriver93.0.4577.63/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterTest
    public void close() {

        driver.quit();

    }
}
