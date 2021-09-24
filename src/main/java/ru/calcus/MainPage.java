package ru.calcus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage extends AbstractWebDriver {

    @Test(groups = {"smokeTest", "regress"})
    public void AA0001() {
        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        //Получаем тайтл страницы в переменную
        String title = driver.getTitle();
        //Проверяем актуальный тайтл главной страницы с ожидаемым
        Assert.assertEquals(title, "Онлайн калькуляторы и справочники");

   }

   @Test(groups = {"smokeTest", "regress"}, dependsOnMethods = {"AA0001"})
   public void AA0002() {

       //Запрашиваем главную страницу
       driver.get("https://calcus.ru/");

       //Находим поле ввода логина
       WebElement login = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='email']"));
       //Проверяем что элемент найден
       Assert.assertNotNull(login);

       //Находим поле ввода пароля
       WebElement password = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='password']"));
       //Проверяем что элемент найден
       Assert.assertNotNull(password);
   }

    //После отладки установить зависимость dependsOnMethods = {"AA0001", "AA0002"}
    @Test(groups = {"smokeTest", "regress"})
    public void AA0003() {

        String loginTx = "ronhabb@yandex.ru";
        String passwordTx = "Qwerty123";


        //Запрашиваем главную страницу
        driver.get("https://calcus.ru/");

        //Находим ссылку для открытия формы авторизации и кликаем по ней
        WebElement loginModal = driver.findElement(By.xpath("//a[@href='#loginModal']"));
        //Ожидаем что ссылка на странице есть
        Assert.assertNotNull(loginModal);

        //Если предыдущее ожидание сработало кликаем по ссылке, открывается модальное окно
        loginModal.click();

        //Находим поле ввода логина и отправляем туда данные для авторизации
        WebElement login = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='email']"));
        login.sendKeys(loginTx);

        //Находим поле ввода пароля и отправляем туда данные для авторизации
        WebElement password = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='password']"));
        password.sendKeys(passwordTx);

        //Кликаем кнопку "Войти"
        driver.findElement(By.xpath("//div[@id='loginModal']//button[@type='submit']")).click();

        //Проверяем что на странице вместо ссылки "Вход" теперь прописан указанный имейл адрес
        WebElement loginText = driver.findElement(By.xpath("//div[@class='nav-item dropdown']//span[2]"));
        Assert.assertEquals(loginText.getText(), loginTx);

    }
}
