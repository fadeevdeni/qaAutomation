package ru.calcus;

import Utils.AbstractWebDriver;
import Utils.ApplicationManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Epics;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class AuthorizationFormTests extends AbstractWebDriver {

    private static final ApplicationManager appManager = new ApplicationManager();
    private static final String url = "https://calcus.ru/";
    private static final String authModalFormLinkXpath = "//a[@href='#loginModal']";
    private static final String loginInputXpath = "//div[@id='loginModal']//input[@type='email']";
    private static final String passwordInputXpath = "//div[@id='loginModal']//input[@type='password']";
    private static final String loginButtonXpath = "//div[@id='loginModal']//button[@type='submit']";
    private static final String loginOnPageXpath = "//div[@class='nav-item dropdown']//span[2]";
    private static final String authErrorMsgXpath = "//form[@class='js-auth-form']//div[@class='mb-3 alert alert-danger auth-error-placeholder']";

    private void SuccessLogin(String[][] inputData) {

        appManager.GetPage(driver, url);
        appManager.ClickElement(driver, authModalFormLinkXpath);
        appManager.FillInInputs(driver, inputData);
        appManager.ClickElement(driver, loginButtonXpath);
        appManager.WaitElement(driver, loginOnPageXpath);
        appManager.AssertResult(driver, loginOnPageXpath, inputData[0][1]);

    }

    private void AuthFormValidationConstruct(String[][] inputData, String[][] validationErrors) {

        appManager.GetPage(driver, url);
        appManager.ClickElement(driver, authModalFormLinkXpath);
        appManager.FillInInputs(driver, inputData);
        appManager.ClickElement(driver, loginButtonXpath);
        appManager.AssertValidationErrors(driver, validationErrors);

    }

    private void AuthFormErrorConstruct(String[][] inputData, String authErrorMsg) {

        appManager.GetPage(driver, url);
        appManager.ClickElement(driver, authModalFormLinkXpath);
        appManager.FillInInputs(driver, inputData);
        appManager.ClickElement(driver, loginButtonXpath);
        appManager.WaitElement(driver, authErrorMsgXpath);
        appManager.AssertResult(driver, authErrorMsgXpath, authErrorMsg);
    }


    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка доступности главой страницы")
    @Test(groups = {"smokeTest", "regress"})
    public void AA0001() {

        String expectedTitle = "Онлайн калькуляторы и справочники";

        appManager.GetPage(driver, url);
        appManager.CheckTitle(driver, expectedTitle);
    }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка существования модального окна и формы авторизации на странице")
    @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"AA0001"})
    public void AA0002() {

        String[] inputXpaths = {
                loginButtonXpath,
                passwordInputXpath,
                loginInputXpath
        };

        appManager.GetPage(driver, url);
        appManager.ClickElement(driver, authModalFormLinkXpath);
        appManager.CheckInputs(driver, inputXpaths);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Успешная авторизация")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0003() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@yandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        SuccessLogin(inputData);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пустое поле пароля")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0004() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@yandex.ru"},
                {passwordInputXpath, ""}
        };

        String[][] validationErrors = {
                {loginInputXpath, ""},
                {passwordInputXpath, "Заполните это поле."}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пустое поле логина")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0005() {

        String[][] inputData = {
                {loginInputXpath, ""},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Заполните это поле."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Неверный пароль")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0006() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@yandex.ru"},
                {passwordInputXpath, "321ytrewq"}
        };

        String authErrorMsg = "Неверный пароль";

        AuthFormErrorConstruct(inputData, authErrorMsg);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Неверный имейл")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0007() {

        String[][] inputData = {
                {loginInputXpath, "qwerty321@yandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        String authErrorMsg = "Пользователь с таким Email не зарегистрирован";

        AuthFormErrorConstruct(inputData, authErrorMsg);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пропущен знак \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0008() {

        String[][] inputData = {
                {loginInputXpath, "ronhabbyandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Адрес электронной почты должен содержать символ \"@\". " +
                        "В адресе \"ronhabbyandex.ru\" отсутствует символ \"@\"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пропущена часть адреса до знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0009() {

        String[][] inputData = {
                {loginInputXpath, "@yandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Введите часть адреса до символа \"@\". Адрес \"@yandex.ru\" неполный."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пропущена часть адреса после \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0010() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Введите часть адреса после символа \"@\". Адрес \"ronhabb@\" неполный."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел в начала логина")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0011() {

        String[][] inputData = {
                {loginInputXpath, " ronhabb@yandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        SuccessLogin(inputData);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел в конце логина")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0012() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@yandex.ru "},
                {passwordInputXpath, "Qwerty123"}
        };

        SuccessLogin(inputData);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел посередине адреса имейл до знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0013() {

        String[][] inputData = {
                {loginInputXpath, "ron habb@yandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Часть адреса до символа \"@\" не должна содержать символ \" \"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел в части адреса после знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0014() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@yan dex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Часть адреса после символа \"@\" не должна содержать символ \" \"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в имейл адресе")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0015() {

        String[][] inputData = {
                {loginInputXpath, "кщтфрии@нфтвучюкг"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Часть адреса до символа \"@\" не должна содержать символ \"к\"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в адресе имейл до знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0016() {

        String[][] inputData = {
                {loginInputXpath, "кщтрфии@yandex.ru"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Часть адреса до символа \"@\" не должна содержать символ \"к\"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    //Здесь умышленно допущена ошибка для демонстрации. На сайте неверно отрабатывает логика валидации.
    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в адресе имейл после знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0017() {

        String[][] inputData = {
                {loginInputXpath, "ronhabb@нфтвучюкг"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Часть адреса после символа \"@\" не должна содержать символ \"н\"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в адресе и пропущен знак \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0018() {

        String[][] inputData = {
                {loginInputXpath, "кщтрфиинфтвучюкг"},
                {passwordInputXpath, "Qwerty123"}
        };

        String[][] validationErrors = {
                {loginInputXpath, "Адрес электронной почты должен содержать символ \"@\". " +
                        "В адресе \"кщтрфиинфтвучюкг\" отсутствует символ \"@\"."},
                {passwordInputXpath, ""}
        };

        AuthFormValidationConstruct(inputData, validationErrors);

    }
}
