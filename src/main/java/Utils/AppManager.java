package Utils;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppManager {

    public void login(@NotNull WebDriver driver, String login, String password) {

        //Находим ссылку для открытия формы авторизации и кликаем по ней
        WebElement loginModal = driver.findElement(By.xpath("//a[@href='#loginModal']"));
        loginModal.click();

        //Находим поле ввода логина и отправляем туда данные для авторизации
        WebElement loginInput = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='email']"));
        loginInput.sendKeys(login);

        //Находим поле ввода пароля и отправляем туда данные для авторизации
        WebElement passwordInput = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='password']"));
        passwordInput.sendKeys(password);

        //Кликаем кнопку "Войти"
        driver.findElement(By.xpath("//div[@id='loginModal']//button[@type='submit']")).click();

    }

    //Возвращает двумерный массив с текстом ошибок
    //Ошибки для поля логина с индексом [0]
    //Ошибки для поля пароля с индексом [1]
    public String[] loginFail(@NotNull WebDriver driver, String login, String password) {

        String[] validationErrors = new String[2];

        //Находим ссылку для открытия формы авторизации и кликаем по ней
        WebElement loginModal = driver.findElement(By.xpath("//a[@href='#loginModal']"));
        loginModal.click();

        //Находим поле ввода логина и отправляем туда данные для авторизации
        WebElement loginInput = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='email']"));
        loginInput.sendKeys(login);
        validationErrors[0] = loginInput.getAttribute("validationMessage");

        //Находим поле ввода пароля и отправляем туда данные для авторизации
        WebElement passwordInput = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='password']"));
        passwordInput.sendKeys(password);
        validationErrors[1] = passwordInput.getAttribute("validationMessage");

        //Кликаем кнопку "Войти"
        driver.findElement(By.xpath("//div[@id='loginModal']//button[@type='submit']")).click();

        return validationErrors;
    }

    public void CarLoanCalculate(@NotNull WebDriver driver, String creditSum,
                                 String period, String periodType, String percent, String paymentType) {

        WebElement creditSumInput = driver.findElement(By.xpath("//input[@name='credit_sum']"));
        creditSumInput.sendKeys(creditSum);

        WebElement periodInput = driver.findElement(By.xpath("//input[@name='period']\""));
        periodInput.sendKeys(period);

        WebElement percentInput = driver.findElement(By.xpath("//input[@name='percent']"));
        percentInput.sendKeys(percent);


    }
}
