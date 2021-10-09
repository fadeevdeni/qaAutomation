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

public class AuthorizationFormTests extends AbstractWebDriver {

    private static final ApplicationManager appManager = new ApplicationManager();
    private static final String URL = "https://calcus.ru/";
    private static final String authModalFormLinkXpath = "//a[@href='#loginModal']";
    private static final String loginInputXpath = "//div[@id='loginModal']//input[@type='email']";
    private static final String passwordInputXpath = "//div[@id='loginModal']//input[@type='password']";
    private static final String loginButtonXpath = "//div[@id='loginModal']//button[@type='submit']";
    private static final String loginOnPageXpath = "//div[@class='nav-item dropdown']//span[2]";
    private static final String authErrorMsgXpath = "//form[@class='js-auth-form']//div[@class='mb-3 alert alert-danger auth-error-placeholder']";
    private static final String PATH_TO_DATA_PROVIDER = "src/testDataProviders/AuthForm/";

    @DataProvider(name = "SuccessAuthDP")
    public Iterator<Object[]> SuccessAuthDP() throws IOException {
        return appManager.parseCsvData(PATH_TO_DATA_PROVIDER + "SuccessAuth.csv");
    }

    @DataProvider(name = "FailAuthDP")
    public Iterator<Object[]> FailAuthDP() throws IOException {
        return appManager.parseCsvData(PATH_TO_DATA_PROVIDER + "FailAuth.csv");
    }

    @DataProvider(name = "AuthFormValidationDP")
    public Iterator<Object[]> AuthFormValidationDP() throws IOException {
        return appManager.parseCsvData(PATH_TO_DATA_PROVIDER + "AuthFormValidation.csv");
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка доступности главой страницы")
    @Test(groups = {"smokeTest", "regress"})
    public void CheckPageAvailable() {

        String expectedTitle = "Онлайн калькуляторы и справочники";

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.CheckTitle(expectedTitle);
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка существования модального окна и формы авторизации на странице")
    @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"CheckPageAvailable"})
    public void CheckInputs() {

        String[] inputXpaths = {
                loginButtonXpath,
                passwordInputXpath,
                loginInputXpath
        };

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(authModalFormLinkXpath);
        appManager.CheckInputs(inputXpaths);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Успешная авторизация")
    @Test(dataProvider = "SuccessAuthDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void SuccessLogin(String login, String password) {

        String[][] inputData = {
                {loginInputXpath, login},
                {passwordInputXpath, password}
        };

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(authModalFormLinkXpath);
        appManager.FillInInputs(inputData);
        appManager.ClickElement(loginButtonXpath);
        appManager.WaitElement(loginOnPageXpath);
        appManager.AssertResult(loginOnPageXpath, login);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Не успешная авторизация")
    @Test(dataProvider = "FailAuthDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void FailLogin(String login, String password, String errorMessage) {

        String[][] inputData = {
                {loginInputXpath, login},
                {passwordInputXpath, password}
        };

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(authModalFormLinkXpath);
        appManager.FillInInputs(inputData);
        appManager.ClickElement(loginButtonXpath);
        appManager.WaitElement(authErrorMsgXpath);
        appManager.AssertResult(authErrorMsgXpath, errorMessage);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Валидация полей формы авторизации")
    @Test(dataProvider = "AuthFormValidationDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void AuthFormValidation(String login, String password, String loginInputError, String passwordInputError) {

        String[][] inputData = {
                {loginInputXpath, login},
                {passwordInputXpath, password}
        };

        String[][] validationErrors = {
                {loginInputXpath, loginInputError},
                {passwordInputXpath, passwordInputError}
        };

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(authModalFormLinkXpath);
        appManager.FillInInputs(inputData);
        appManager.ClickElement(loginButtonXpath);
        appManager.AssertValidationErrors(validationErrors);

    }
}
