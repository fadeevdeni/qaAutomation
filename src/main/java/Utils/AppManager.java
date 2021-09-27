package Utils;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public String[] carLoanCalculate(@NotNull WebDriver driver, String creditSum,
                                 String period, String periodType, String percent, String paymentType) {

        //создаем массив для возврата результатов
        String[] calcResults = new String[3];

        //Включаем второй вариант расчета по сумме кредита
        driver.findElement(By.xpath("//div[@class='calc-fright']//a[@data-value='2']")).click();

        //Находим поле суммы кредиты и передаем в него значение
        WebElement creditSumInput = driver.findElement(By.xpath("//input[@name='credit_sum']"));
        creditSumInput.sendKeys(creditSum);

        //Находим поле срока кредита и передаем в него значение
        WebElement periodInput = driver.findElement(By.xpath("//input[@name='period']"));
        periodInput.sendKeys(period);

        //Находим селектор выбора типа срока, Года или месяцы
        WebElement periodTypeSelect = driver.findElement(By.xpath("//select[@name='period_type']"));
        Select periodTypeSelected = new Select(periodTypeSelect);
        periodTypeSelected.selectByValue(periodType);

        //Находим поле ввода процентов и передаем в него значение
        WebElement percentInput = driver.findElement(By.xpath("//input[@name='percent']"));
        percentInput.sendKeys(percent);

        //Выбираем тип расчета платежей, аннуитетные или дифференцированные
        if (paymentType.equals("annuitet")) {

            driver.findElement(By.xpath("//div[@class='calc-fright']//label[@for='payment-type-1']")).click();
        }
        else if (paymentType.equals("different")) {

            driver.findElement(By.xpath("//div[@class='calc-fright']//label[@for='payment-type-2']")).click();
        }

        //Нажимаем кнопку расчета
        driver.findElement(By.xpath("//div[@class='calc-frow button-row']//button[@type='submit']")).click();

        //Сохраняем XPath для поиска результатов расчета
        String monthlyPaymentXPath = "//div[@class='calc-result-value result-placeholder-monthlyPayment']";
        //Создаем ожидание
        WebDriverWait monthlyPaymentWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Задаем ожидание появления результатов расчета на странице
        monthlyPaymentWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(monthlyPaymentXPath)));
        //Получаем рассчитанный ежемесячный платеж и записываем в массив с индексом [0]
        WebElement monthlyPayment = driver.findElement(By.xpath(monthlyPaymentXPath));
        calcResults[0] = monthlyPayment.getText();

        //Получаем рассчитанные начисленные проценты и записываем в массив с индексом [1]
        WebElement overPayment = driver.findElement(
                By.xpath("//div[@class='calc-result-value result-placeholder-overPayment']"));
        calcResults[1] = overPayment.getText();

        //Получаем общую стоимость кредита и записываем в массив с индексом [2]
        WebElement totalPaid = driver.findElement(
                By.xpath("//div[@class='calc-result-value result-placeholder-totalPaid']"));
        calcResults[2] = totalPaid.getText();

        //Возвращаем результаты расчета
        //Ежемесячный платеж индекс 0
        //Начисленные проценты индекс 1
        //Стоимость кредита индекс 2
        return calcResults;

    }
}
