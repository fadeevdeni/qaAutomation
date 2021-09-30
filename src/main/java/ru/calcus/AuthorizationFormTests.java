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


    @Epics(value = {@Epic(value = "Smoke Test"), @Epic(value = "Регресс")})
    @Feature(value = "Проверка авторизации на главной странице")
    @Story(value = "Проверка доступности главой страницы")
    @Test(groups = {"smokeTest", "regress"})
    public void AA0001() {

        String url = "https://calcus.ru/";
        String expectedTitle = "Онлайн калькуляторы и справочники";

        appManager.GetPage(driver, url);
        appManager.CheckTitle(driver, expectedTitle);
    }
}
