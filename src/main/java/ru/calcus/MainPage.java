package ru.calcus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage extends AbstractWebDriver {

    @Test(groups = {"smokeTest", "regress"})
    public void AA0001() {
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        //Получаем тайтл страницы в переменную
        String title = driver.getTitle();
        //Проверяем актуальный тайтл главной страницы с ожидаемым
        Assert.assertEquals(title, "Онлайн калькуляторы и справочники");

   }

   @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"AA0001"})
   public void AA0002() {

       //Запрашиваем главную страницу
       driver.get("https://calcus.ru/");

       WebElement login = driver.findElement(By.xpath("//input[@type='email']"));
       Assert.assertNotNull(login);

       WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
       Assert.assertNotNull(password);
   }
}
