package ru.calcus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CarLoanPage extends AbstractWebDriver {

    @Test(groups = {"smokeTest", "regress"})
    public void AB0001() {

        //Запрашиваем страницу калькулятора расчета автокредита
        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        //Получаем тайтл страницы
        String title = driver.getTitle();
        //Проверяем актуальный тайтл страницы с ожидаемым
        Assert.assertEquals(title, "Калькулятор автокредита");

    }

    @Test(groups = {"smokeTest, regress"}, dependsOnMethods = {"AB0001"})
    public void AB0002() {

        //Запрашиваем страницу калькулятора расчет автокредита
        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        //Ищем на странице поле ввода стоимости автомобиля
        WebElement carCost = driver.findElement(By.xpath("//input[@name='cost']"));
        Assert.assertNotNull(carCost);

        //Ищем на странице поле ввода суммы первоначального взноса
        WebElement firstSum = driver.findElement(By.xpath("//input[@name='start_sum']"));
        Assert.assertNotNull(firstSum);

        //Ищем на странице поле ввода срока кредита
        WebElement period = driver.findElement(By.xpath("//input[@name='period']"));
        Assert.assertNotNull(period);

        //Ищем на странице поле ввода процентной ставки
        WebElement percent = driver.findElement(By.xpath("//input[@name='percent']"));
        Assert.assertNotNull(percent);

        //Ищем на странице радио баттоны типа ежемесячного платежа
        WebElement annuitet = driver.findElement(By.id("payment-type-1"));
        Assert.assertNotNull(annuitet);
        WebElement different = driver.findElement(By.id("payment-type-2"));
        Assert.assertNotNull(different);

        //Ищем на странице поле ввода суммы кредита
        WebElement creditSum = driver.findElement(By.xpath("//input[@name='credit_sum']"));
        Assert.assertNotNull(creditSum);
    }
}
