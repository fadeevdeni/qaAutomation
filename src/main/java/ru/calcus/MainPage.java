package ru.calcus;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage {

   @Test
    public void AT0001() {

       System.setProperty("webdriver.chrome.driver", "src/webDrivers/chromedriver93.0.4577.63/chromedriver.exe");
       ChromeDriver driver = new ChromeDriver();

       driver.get("https://calcus.ru/");

       String title = driver.getTitle();
       Assert.assertEquals(title, "Онлайн калькуляторы и справочники");

       driver.quit();
   }
}
