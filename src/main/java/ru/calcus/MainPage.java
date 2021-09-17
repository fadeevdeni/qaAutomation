package ru.calcus;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage extends AbstractWebDriver {

   @Test
    public void AT0001() {

       driver.get("https://calcus.ru/");

       String title = driver.getTitle();
       Assert.assertEquals(title, "Онлайн калькуляторы и справочники");

   }
}
