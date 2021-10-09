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

    private void CalcTestConstructor(String[][] inputsData, String[][] selectPeriodType,
                                     String paymentType, String[][] assertResults) {

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


    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка доступности страницы калькулятора")
    @Test(groups = {"smokeTest", "regress"})
    public void AB0001() {

        String expectedTitle = "Калькулятор автокредита";

        appManager.GetPage(url);
        appManager.CheckTitle(expectedTitle);
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка существования полей ввода калькулятора")
    @Test(groups = {"smokeTest", "regress"})
    public void AB0002() {

        String[] inputXpaths = {
                creditSumXpath,
                periodXpath,
                percentXpath,
                periodTypeSelectorXpath,
                annuitetRadioXpath,
                differentRadioXpath
        };

        appManager.GetPage(url);
        //Активируем второй вариант расчета кредита
        appManager.ClickElement(bySumCalcXpath);

        appManager.CheckInputs(inputXpaths);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Успешный расчет")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0003() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"4000000"}, // Сумма кредита
                {periodXpath, "60"}, // Период кредитования
                {percentXpath, "20"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "M"} // (M)onth or (Y)ear
        };

        String paymentType = "annuitet"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "105 975,53"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "2 358 531,80"}, // Сумма начисленных процентов
                {totalPayXpath, "6 358 531,80"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Успешный расчет")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0004() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"4000000"}, // Сумма кредита
                {periodXpath, "60"}, // Период кредитования
                {percentXpath, "20"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "M"} // (M)onth or (Y)ear
        };

        String paymentType = "different"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "133 333,33 … 67 777,78"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "2 033 333,33"}, // Сумма начисленных процентов
                {totalPayXpath, "6 033 333,33"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Успешный расчет")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0005() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"4000000"}, // Сумма кредита
                {periodXpath, "3"}, // Период кредитования
                {percentXpath, "20"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "Y"} // (M)onth or (Y)ear
        };

        String paymentType = "annuitet"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "148 654,33"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "1 351 555,88"}, // Сумма начисленных процентов
                {totalPayXpath, "5 351 555,88"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Успешный расчет")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0006() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"4000000"}, // Сумма кредита
                {periodXpath, "3"}, // Период кредитования
                {percentXpath, "20"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "Y"} // (M)onth or (Y)ear
        };

        String paymentType = "different"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "177 777,78 … 112 962,96"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "1 233 333,33"}, // Сумма начисленных процентов
                {totalPayXpath, "5 233 333,33"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка граничных значений")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0007() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"0,01"}, // Сумма кредита
                {periodXpath, "1"}, // Период кредитования
                {percentXpath, "0,01"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "Y"} // (M)onth or (Y)ear
        };

        String paymentType = "annuitet"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "0,00"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "-0,01"}, // Сумма начисленных процентов
                {totalPayXpath, "0,00"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка граничных значений")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0008() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"0,01"}, // Сумма кредита
                {periodXpath, "1"}, // Период кредитования
                {percentXpath, "0,01"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "Y"} // (M)onth or (Y)ear
        };

        String paymentType = "different"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "0,00 … 0,00"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "0,00"}, // Сумма начисленных процентов
                {totalPayXpath, "0,01"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка граничных значений")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0009() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"0,01"}, // Сумма кредита
                {periodXpath, "1"}, // Период кредитования
                {percentXpath, "999,99"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "M"} // (M)onth or (Y)ear
        };

        String paymentType = "annuitet"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "0,02"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "0,01"}, // Сумма начисленных процентов
                {totalPayXpath, "0,02"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка калькулятора расчета автокредита")
    @Story(value = "Проверка граничных значений")
    @Test(groups = {"regress"}, dependsOnMethods = {"AB0001", "AB0002"})
    public void AB0010() {

        //Входные данные
        String[][] inputsData = {
                {creditSumXpath,"0,01"}, // Сумма кредита
                {periodXpath, "1"}, // Период кредитования
                {percentXpath, "999,99"} // Процентная ставка
        };

        String[][] selectPeriodType = {
                {periodTypeSelectorXpath, "M"} // (M)onth or (Y)ear
        };

        String paymentType = "different"; //annuitet(type 1) or different(type 2)

        String[][] assertResults = {
                {monthlyPaymentXpath, "0,02 … 0,02"}, // Сумма ежемесячного платежа
                {overPaymentXpath, "0,01"}, // Сумма начисленных процентов
                {totalPayXpath, "0,02"} // Общая стоимость кредита
        };

        CalcTestConstructor(inputsData, selectPeriodType, paymentType, assertResults);
    }
}
