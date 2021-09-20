package ru.calcus;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage extends AbstractWebDriver {

    @Test(groups = {"smokeTest"})
    public void AT0001() {
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        //Получаем тайтл страницы в переменную
        String title = driver.getTitle();
        //Проверяем актуальный тайтл главной страницы с ожидаемым
        Assert.assertEquals(title, "Онлайн калькуляторы и справочники");

   }
}
