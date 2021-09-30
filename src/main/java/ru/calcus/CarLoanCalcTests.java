package ru.calcus;

import Utils.AbstractWebDriver;
import Utils.ApplicationManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Epics;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

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


    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка доступности страницы калькулятора")
    @Test(groups = {"smokeTest", "regress"})
    public void AB0001() {

        String expectedTitle = "Калькулятор автокредита";

        appManager.GetPage(driver, url);
        appManager.CheckTitle(driver, expectedTitle);
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка существования полей ввода калькулятора")
    @Test(groups = {"smokeTest", "regress"})
    public void AB0002() {

        String[] inputXpaths = new String[6];

        appManager.GetPage(driver, url);
        //Активируем второй вариант расчета кредита
        appManager.ClickElement(driver, bySumCalcXpath);
        //Проверяем существования полей ввода
        inputXpaths[0] = creditSumXpath;
        inputXpaths[1] = periodXpath;
        inputXpaths[2] = percentXpath;
        inputXpaths[3] = periodTypeSelectorXpath;
        inputXpaths[4] = annuitetRadioXpath;
        inputXpaths[5] = differentRadioXpath;
        appManager.CheckInputs(driver, inputXpaths);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Успешный расчет")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0003() {

        //Входные данные

        String creditSum = "4000000";
        String period = "60";
        String periodType = "M"; // (M)onth or (Y)ear
        String percent = "20";
        String paymentType = "annuitet"; //annuitet(type 1) or different(type 2)

        String[][] inputsData = {
                {creditSumXpath,creditSum},
                {periodXpath, period},
                {percentXpath, percent}
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, periodType}
        };

        //Ожидаемые данные
        String assertMonthlyPayment = "105 975,53";
        String assertOverPayment = "2 358 531,80";
        String assertTotalPay = "6 358 531,80";

        String[][] assertResults = {
                {monthlyPaymentXpath, assertMonthlyPayment},
                {overPaymentXpath, assertOverPayment},
                {totalPayXpath, assertTotalPay}
        };

        appManager.GetPage(driver, url);
        appManager.ClickElement(driver, bySumCalcXpath);
        appManager.FillInInputs(driver, inputsData);
        appManager.SelectElement(driver, selectPeriodType);
        appManager.ClickElement(driver, annuitetRadioXpath);
        appManager.ClickElement(driver, calcButton);
        appManager.WaitElements(driver, assertResults);
        appManager.AssertResults(driver, assertResults);

    }
}
