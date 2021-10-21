package ru.calcus;

import Utils.AbstractWebDriver;
import Utils.ApplicationManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Epics;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;

public class CarLoanCalcTests extends AbstractWebDriver {

    private static final ApplicationManager appManager = new ApplicationManager();
    private static final String URL = "https://calcus.ru/kalkulyator-avtokredita";
    private static final String BY_SUM_CALC_XPATH = "//div[@class='calc-fright']//a[@data-value='2']";
    private static final String CREDIT_SUM_XPATH = "//input[@name='credit_sum']";
    private static final String PERIOD_XPATH = "//input[@name='period']";
    private static final String PERCENT_XPATH = "//input[@name='percent']";
    private static final String PERIOD_TYPE_SELECTOR_XPATH = "//select[@name='period_type']";
    private static final String ANNUITET_RADIO_XPATH = "//div[@class='calc-fright']//label[@for='payment-type-1']";
    private static final String DIFFERENT_RADIO_XPATH = "//div[@class='calc-fright']//label[@for='payment-type-2']";
    private static final String CALC_BUTTON = "//div[@class='calc-frow button-row']//button[@type='submit']";
    private static final String MONTHLY_PAYMENT_XPATH = "//div[@class='calc-result-value result-placeholder-monthlyPayment']";
    private static final String OVER_PAYMENT_XPATH = "//div[@class='calc-result-value result-placeholder-overPayment']";
    private static final String TOTAL_PAY_XPATH = "//div[@class='calc-result-value result-placeholder-totalPaid']";
    private static final String PATH_TO_DATA_PROVIDER = "src/testDataProviders/CarLoanCalc/";

    @DataProvider(name = "TestDataDP")
    public Iterator<Object[]> TestData() throws IOException {
        return appManager.parseCsvData(PATH_TO_DATA_PROVIDER + "TestData.csv");
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка доступности страницы калькулятора")
    @Test(groups = {"smokeTest", "regress"})
    public void CheckPageAvailable() {

        String expectedTitle = "Калькулятор автокредита";

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);

        Assert.assertEquals(appManager.GetTitle(), expectedTitle);
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка существования полей ввода калькулятора")
    @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"CheckPageAvailable"})
    public void CheckInputs() {

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        //Активируем второй вариант расчета кредита
        appManager.ClickElement(BY_SUM_CALC_XPATH);

        Assert.assertNotNull(appManager.GetElementByXpath(CREDIT_SUM_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(PERIOD_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(PERCENT_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(PERIOD_TYPE_SELECTOR_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(ANNUITET_RADIO_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(DIFFERENT_RADIO_XPATH));

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Успешный расчет")
    @Test(dataProvider = "TestDataDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void CarLoanCalculator(String creditSum, String period, String periodType,
                                  String percent, String paymentType,
                                  String monthlyPayment, String overPayment, String totalPay) {

        appManager.SetUpDriver(driver);

        appManager.GetPage(URL);

        appManager.ClickElement(BY_SUM_CALC_XPATH);

        appManager.GetElementByXpath(CREDIT_SUM_XPATH).sendKeys(creditSum);
        appManager.GetElementByXpath(PERIOD_XPATH).sendKeys(period);
        appManager.GetElementByXpath(PERCENT_XPATH).sendKeys(percent);

        appManager.SelectElementByXpath(PERIOD_TYPE_SELECTOR_XPATH, periodType);

        if(paymentType.equals("annuitet")) {
            appManager.ClickElement(ANNUITET_RADIO_XPATH);
        }
        else if(paymentType.equals("different")) {
            appManager.ClickElement(DIFFERENT_RADIO_XPATH);
        }

        appManager.ClickElement(CALC_BUTTON);

        appManager.WaitElementByXpath(MONTHLY_PAYMENT_XPATH);
        appManager.WaitElementByXpath(OVER_PAYMENT_XPATH);
        appManager.WaitElementByXpath(TOTAL_PAY_XPATH);

        Assert.assertEquals(appManager.GetElementByXpath(MONTHLY_PAYMENT_XPATH).getText(), monthlyPayment);
        Assert.assertEquals(appManager.GetElementByXpath(OVER_PAYMENT_XPATH).getText(), overPayment);
        Assert.assertEquals(appManager.GetElementByXpath(TOTAL_PAY_XPATH).getText(), totalPay);

    }
}
