package ru.calcus;

import Utils.AbstractWebDriver;
import Utils.ApplicationManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Epics;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;

public class CarLoanCalcTests extends AbstractWebDriver {

    private static final ApplicationManager appManager = new ApplicationManager();
    private static final String url = "https://calcus.ru/kalkulyator-avtokredita";
    private static final String bySumCalcXpath = "//div[@class='calc-fright']//a[@data-value='2']";
    private static final String creditSumXpath = "//input[@name='credit_sum']";
    private static final String periodXpath = "//input[@name='period']";
    private static final String percentXpath = "//input[@name='percent']";
    private static final String periodTypeSelectorXpath = "//select[@name='period_type']";
    private static final String annuitetRadioXpath = "//div[@class='calc-fright']//label[@for='payment-type-1']";
    private static final String differentRadioXpath = "//div[@class='calc-fright']//label[@for='payment-type-2']";
    private static final String calcButton = "//div[@class='calc-frow button-row']//button[@type='submit']";
    private static final String monthlyPaymentXpath = "//div[@class='calc-result-value result-placeholder-monthlyPayment']";
    private static final String overPaymentXpath = "//div[@class='calc-result-value result-placeholder-overPayment']";
    private static final String totalPayXpath = "//div[@class='calc-result-value result-placeholder-totalPaid']";
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
        appManager.GetPage(url);
        appManager.CheckTitle(expectedTitle);
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка существования полей ввода калькулятора")
    @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"CheckPageAvailable"})
    public void CheckInputs() {

        String[] inputXpaths = {
                creditSumXpath,
                periodXpath,
                percentXpath,
                periodTypeSelectorXpath,
                annuitetRadioXpath,
                differentRadioXpath
        };

        appManager.SetUpDriver(driver);
        appManager.GetPage(url);
        //Активируем второй вариант расчета кредита
        appManager.ClickElement(bySumCalcXpath);

        appManager.CheckInputs(inputXpaths);

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

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath, creditSum}, // Сумма кредита
                {periodXpath, period}, // Период кредитования
                {percentXpath, percent} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, periodType} // (M)onth or (Y)ear
        };

        String[][] assertResults = {
                {monthlyPaymentXpath, monthlyPayment}, // Сумма ежемесячного платежа
                {overPaymentXpath, overPayment}, // Сумма начисленных процентов
                {totalPayXpath, totalPay} // Общая стоимость кредита
        };

        appManager.SetUpDriver(driver);

        appManager.GetPage(url);

        appManager.ClickElement(bySumCalcXpath);

        appManager.FillInInputs(inputsData);

        appManager.SelectElement(selectPeriodType);

        if(paymentType.equals("annuitet")) {
            appManager.ClickElement(annuitetRadioXpath);
        }
        else if(paymentType.equals("different")) {
            appManager.ClickElement(differentRadioXpath);
        }

        appManager.ClickElement(calcButton);

        appManager.WaitElements(assertResults);

        appManager.AssertResults(assertResults);

    }
}
