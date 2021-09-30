package ru.calcus;

import Utils.AbstractWebDriver;
import Utils.AppManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Epics;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage extends AbstractWebDriver {

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка доступности главой страницы")
    @Test(groups = {"smokeTest", "regress"})
    public void AA0001() {
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        //Получаем тайтл страницы в переменную
        String title = driver.getTitle();
        //Проверяем актуальный тайтл главной страницы с ожидаемым
        Assert.assertEquals(title, "Онлайн калькуляторы и справочники");

   }

    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка существования модального окна и формы авторизации на странице")
    @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"AA0001"})
    public void AA0002() {

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        //Находим ссылку для открытия формы авторизации и кликаем по ней
           WebElement loginModal = driver.findElement(By.xpath("//a[@href='#loginModal']"));
           //Ожидаем что ссылка на странице есть
           Assert.assertNotNull(loginModal);

           //Находим поле ввода логина
           WebElement login = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='email']"));
           //Проверяем что элемент найден
           Assert.assertNotNull(login);

           //Находим поле ввода пароля
           WebElement password = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='password']"));
           //Проверяем что элемент найден
           Assert.assertNotNull(password);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Успешная авторизация")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0003() {

        String loginTx = "ronhabb@yandex.ru";
        String passwordTx = "Qwerty123";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        lf.login(driver,loginTx, passwordTx);

        //Проверяем что на странице вместо ссылки "Вход" теперь прописан указанный имейл адрес
        WebElement loginText = driver.findElement(By.xpath("//div[@class='nav-item dropdown']//span[2]"));
        Assert.assertEquals(loginText.getText(), loginTx);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пустое поле пароля")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0004() {

        String loginTx = "ronhabb@yandex.ru";
        String passwordTx = "";
        String PasswordInputError = "Заполните это поле.";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(validationErrors[1], PasswordInputError);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пустое поле логина")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0005() {

        String loginTx = "";
        String passwordTx = "Qwerty123";
        String loginInputError = "Заполните это поле.";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Неверный пароль")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0006() {

        String loginTx = "ronhabb@yandex.ru";
        String passwordTx = "321ytrewq";
        String authorizationMessage = "Неверный пароль";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager appMan = new AppManager();
        appMan.login(driver, loginTx, passwordTx);

        WebElement authorizationError = driver.findElement(By
                .xpath("//form[@class='js-auth-form']//div[@class='mb-3 alert alert-danger auth-error-placeholder']"));

        Assert.assertEquals(authorizationError.getAttribute("style"), "display: block;");
        Assert.assertEquals(authorizationError.getText(), authorizationMessage);


    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Неверный имейл")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0007() {

        String loginTx = "qwerty321@yandex.ru";
        String passwordTx = "Qwerty123";
        String authorizationMessage = "Пользователь с таким Email не зарегистрирован";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        lf.login(driver, loginTx, passwordTx);

        WebElement authorizationError = driver.findElement(By
                .xpath("//form[@class='js-auth-form']//div[@class='mb-3 alert alert-danger auth-error-placeholder']"));

        Assert.assertEquals(authorizationError.getAttribute("style"), "display: block;");
        Assert.assertEquals(authorizationError.getText(), authorizationMessage);

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пропущен знак \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0008() {

        String loginTx = "ronhabbyandex.ru";
        String passwordTx = "Qwerty123";
        String loginInputError = "Адрес электронной почты должен содержать символ \"@\". " +
                "В адресе \"ronhabbyandex.ru\" отсутствует символ \"@\".";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пропущена часть адреса до знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0009() {

        String loginTx = "@yandex.ru";
        String passwordTx = "Qwerty123";
        String loginInputError = "Введите часть адреса до символа \"@\". Адрес \"@yandex.ru\" неполный.";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пропущена часть адреса после \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0010() {

        String loginTx = "ronhabb@";
        String passwordTx = "Qwerty123";
        String loginInputError = "Введите часть адреса после символа \"@\". Адрес \"ronhabb@\" неполный.";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        //System.out.println(validationErrors[0]);
        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел в начала логина")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0011() {

        String loginTx = " ronhabb@yandex.ru";
        String passwordTx = "Qwerty123";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        lf.login(driver,loginTx, passwordTx);

        //Проверяем что на странице вместо ссылки "Вход" теперь прописан указанный имейл адрес
        WebElement loginText = driver.findElement(By.xpath("//div[@class='nav-item dropdown']//span[2]"));
        Assert.assertEquals(loginText.getText(), loginTx.replaceAll("\\s+",""));

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел в конце логина")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0012() {

        String loginTx = "ronhabb@yandex.ru ";
        String passwordTx = "Qwerty123";

        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        lf.login(driver,loginTx, passwordTx);

        //Проверяем что на странице вместо ссылки "Вход" теперь прописан указанный имейл адрес
        WebElement loginText = driver.findElement(By.xpath("//div[@class='nav-item dropdown']//span[2]"));
        Assert.assertEquals(loginText.getText(), loginTx.replaceAll("\\s+",""));

    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел посередине адреса имейл до знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0013() {

        String loginTx = "ron habb@yandex.ru";
        String passwordTx = "Qwerty123";
        String loginInputError = "Часть адреса до символа \"@\" не должна содержать символ \" \".";
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Пробел в части адреса после знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0014() {

        String loginTx = "ronhabb@yan dex.ru";
        String passwordTx = "Qwerty123";
        String loginInputError = "Часть адреса после символа \"@\" не должна содержать символ \" \".";
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в имейл адресе")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0015() {

        String loginTx = "кщтфрии@нфтвучюкг";
        String passwordTx = "Qwerty123";
        String loginInputError = "Часть адреса до символа \"@\" не должна содержать символ \"к\".";
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в адресе имейл до знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0016() {

        String loginTx = "кщтрфии@yandex.ru";
        String passwordTx = "Qwerty123";
        String loginInputError = "Часть адреса до символа \"@\" не должна содержать символ \"к\".";
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    //Здесь умышленно допущена ошибка для демонстрации. На сайте неверно отрабатывает логика валидации.
    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в адресе имейл после знака \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0017() {

        String loginTx = "ronhabb@нфтвучюкг";
        String passwordTx = "Qwerty123";
        String loginInputError = "Часть адреса после символа \"@\" не должна содержать символ \"к\".";
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }

    @Epic(value = "Регресс")
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Кириллица в адресе и пропущен знак \"@\"")
    @Test(groups = {"regress"}, dependsOnMethods = {"AA0001", "AA0002"})
    public void AA0018() {

        String loginTx = "кщтрфиинфтвучюкг";
        String passwordTx = "Qwerty123";
        String loginInputError = "Адрес электронной почты должен содержать символ \"@\". " +
                "В адресе \"кщтрфиинфтвучюкг\" отсутствует символ \"@\".";
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        AppManager lf = new AppManager();
        String[] validationErrors = lf.loginFail(driver, loginTx, passwordTx);

        Assert.assertEquals(loginInputError, validationErrors[0]);
    }
}
