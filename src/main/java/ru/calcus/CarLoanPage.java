package ru.calcus;

import Utils.AppManager;
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

    @Test(groups = {"smokeTest", "regress"})
    public void AB0002() {

        //Запрашиваем страницу калькулятора расчет автокредита
        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        driver.findElement(By.xpath("//div[@class='calc-fright']//a[@data-value='2']")).click();

        WebElement creditSum = driver.findElement(By.xpath("//input[@name='credit_sum']"));
        Assert.assertNotNull(creditSum);

        WebElement period = driver.findElement(By.xpath("//input[@name='period']"));
        Assert.assertNotNull(period);

        WebElement periodType = driver.findElement(By.xpath("//select[@name='period_type']"));
        Assert.assertNotNull(periodType);

        WebElement percent = driver.findElement(By.xpath("//input[@name='percent']"));
        Assert.assertNotNull(percent);

        //Ищем на странице радио баттоны типа ежемесячного платежа
        WebElement annuitet = driver.findElement(By.id("payment-type-1"));
        Assert.assertNotNull(annuitet);
        WebElement different = driver.findElement(By.id("payment-type-2"));
        Assert.assertNotNull(different);

    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0003() {

        //Входные данные
        String creditSum = "4000000";
        String period = "60";
        String periodType = "M"; // (M)onth or (Y)ear
        String percent = "20";
        String paymentType = "annuitet"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "105 975,53";
        String assertOverPayment = "2 358 531,80";
        String assertTotalPaid = "6 358 531,80";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);

    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0004() {

        //Входные данные
        String creditSum = "4000000";
        String period = "60";
        String periodType = "M"; // (M)onth or (Y)ear
        String percent = "20";
        String paymentType = "different"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "133 333,33 … 67 777,78";
        String assertOverPayment = "2 033 333,33";
        String assertTotalPaid = "6 033 333,33";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0005() {

        //Входные данные
        String creditSum = "4000000";
        String period = "3";
        String periodType = "Y"; // (M)onth or (Y)ear
        String percent = "20";
        String paymentType = "annuitet"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "148 654,33";
        String assertOverPayment = "1 351 555,88";
        String assertTotalPaid = "5 351 555,88";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0006() {

        //Входные данные
        String creditSum = "4000000";
        String period = "3";
        String periodType = "Y"; // (M)onth or (Y)ear
        String percent = "20";
        String paymentType = "different"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "177 777,78 … 112 962,96";
        String assertOverPayment = "1 233 333,33";
        String assertTotalPaid = "5 233 333,33";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0007() {

        //Входные данные
        String creditSum = "0,01";
        String period = "1";
        String periodType = "Y"; // (M)onth or (Y)ear
        String percent = "0,01";
        String paymentType = "annuitet"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "0,00";
        String assertOverPayment = "-0,01";
        String assertTotalPaid = "0,00";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0008() {

        //Входные данные
        String creditSum = "0,01";
        String period = "1";
        String periodType = "Y"; // (M)onth or (Y)ear
        String percent = "0,01";
        String paymentType = "different"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "0,00 … 0,00";
        String assertOverPayment = "0,00";
        String assertTotalPaid = "0,01";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0009() {

        //Входные данные
        String creditSum = "0,01";
        String period = "1";
        String periodType = "M"; // (M)onth or (Y)ear
        String percent = "999,99";
        String paymentType = "annuitet"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "0,02";
        String assertOverPayment = "0,01";
        String assertTotalPaid = "0,02";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }

    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0010() {

        //Входные данные
        String creditSum = "0,01";
        String period = "1";
        String periodType = "M"; // (M)onth or (Y)ear
        String percent = "999,99";
        String paymentType = "different"; //annuitet or different

        //Ожидаемые данные
        String assertMonthlyPayment = "0,02 … 0,02";
        String assertOverPayment = "0,01";
        String assertTotalPaid = "0,02";

        driver.get("https://calcus.ru/kalkulyator-avtokredita");

        AppManager appMan = new AppManager();
        String[] actualData = appMan.carLoanCalculate(driver, creditSum, period, periodType, percent, paymentType);

        //Проверяем полученные данные с ожидаемыми
        Assert.assertEquals(actualData[0], assertMonthlyPayment);
        Assert.assertEquals(actualData[1], assertOverPayment);
        Assert.assertEquals(actualData[2], assertTotalPaid);
    }
}
