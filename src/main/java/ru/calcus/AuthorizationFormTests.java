package ru.calcus;

import Utils.AbstractWebDriver;
import Utils.ApplicationManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.testng.Assert.assertEquals;

public class AuthorizationFormTests extends AbstractWebDriver {

    private static final ApplicationManager appManager = new ApplicationManager();
    private static final String URL = "https://calcus.ru/";
    private static final String AUTH_MODAL_FORM_LINK_XPATH = "//a[@href='#loginModal']";
    private static final String LOGIN_INPUT_XPATH = "//div[@id='loginModal']//input[@type='email']";
    private static final String PASSWORD_INPUT_XPATH = "//div[@id='loginModal']//input[@type='password']";
    private static final String LOGIN_BUTTON_XPATH = "//div[@id='loginModal']//button[@type='submit']";
    private static final String LOGIN_ON_PAGE_XPATH = "//div[@class='nav-item dropdown']//span[2]";
    private static final String AUTH_ERROR_MSG_XPATH = "//form[@class='js-auth-form']//div[@class='mb-3 alert alert-danger auth-error-placeholder']";
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

        Assert.assertEquals(appManager.GetTitle(), expectedTitle);

    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка существования модального окна и формы авторизации на странице")
    @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"CheckPageAvailable"})
    public void CheckInputs() {

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(AUTH_MODAL_FORM_LINK_XPATH);

        Assert.assertNotNull(appManager.GetElementByXpath(LOGIN_INPUT_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(PASSWORD_INPUT_XPATH));
        Assert.assertNotNull(appManager.GetElementByXpath(LOGIN_INPUT_XPATH));
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Успешная авторизация")
    @Test(dataProvider = "SuccessAuthDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void SuccessLogin(String login, String password) {

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(AUTH_MODAL_FORM_LINK_XPATH);

        appManager.GetElementByXpath(LOGIN_INPUT_XPATH).sendKeys(login);
        appManager.GetElementByXpath(PASSWORD_INPUT_XPATH).sendKeys(password);

        appManager.ClickElement(LOGIN_BUTTON_XPATH);

        appManager.WaitElementByXpath(LOGIN_ON_PAGE_XPATH);

        Assert.assertEquals(appManager.GetElementByXpath(LOGIN_ON_PAGE_XPATH).getText(), login.trim());

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Не успешная авторизация")
    @Test(dataProvider = "FailAuthDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void FailLogin(String login, String password, String errorMessage) {

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(AUTH_MODAL_FORM_LINK_XPATH);

        appManager.GetElementByXpath(LOGIN_INPUT_XPATH).sendKeys(login);
        appManager.GetElementByXpath(PASSWORD_INPUT_XPATH).sendKeys(password);

        appManager.ClickElement(LOGIN_BUTTON_XPATH);

        appManager.WaitElementByXpath(AUTH_ERROR_MSG_XPATH);

        Assert.assertEquals(appManager.GetElementByXpath(AUTH_ERROR_MSG_XPATH).getText(), errorMessage.trim());

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Валидация полей формы авторизации")
    @Test(dataProvider = "AuthFormValidationDP",
            groups = {"regress"},
            dependsOnMethods = {"CheckPageAvailable", "CheckInputs"})
    public void AuthFormValidation(String login, String password, String loginInputError, String passwordInputError) {

        appManager.SetUpDriver(driver);
        appManager.GetPage(URL);
        appManager.ClickElement(AUTH_MODAL_FORM_LINK_XPATH);

        appManager.GetElementByXpath(LOGIN_INPUT_XPATH).sendKeys(login);
        appManager.GetElementByXpath(PASSWORD_INPUT_XPATH).sendKeys(password);

        appManager.ClickElement(LOGIN_BUTTON_XPATH);

        Assert.assertEquals(
                appManager.GetElementByXpath(LOGIN_INPUT_XPATH).getAttribute("validationMessage"),
                loginInputError.trim());

        Assert.assertEquals(
                appManager.GetElementByXpath(PASSWORD_INPUT_XPATH).getAttribute("validationMessage"),
                passwordInputError.trim()
        );
    }
}
