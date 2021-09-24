package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.calcus.AbstractWebDriver;

public class LoginForm {

    public void login(WebDriver driver, String loginTx, String passwordTx) {

        //Находим поле ввода логина и отправляем туда данные для авторизации
        WebElement login = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='email']"));
        login.sendKeys(loginTx);

        //Находим поле ввода пароля и отправляем туда данные для авторизации
        WebElement password = driver.findElement(By.xpath("//div[@id='loginModal']//input[@type='password']"));
        password.sendKeys(passwordTx);

        //Кликаем кнопку "Войти"
        driver.findElement(By.xpath("//div[@id='loginModal']//button[@type='submit']")).click();
    }
}
