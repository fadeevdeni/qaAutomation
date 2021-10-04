package Utils;

import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ApplicationManager {

    @Step(value = "Открыть страницу")
    public void GetPage(@NotNull WebDriver driver, String url) {

        driver.get(url);

    }

    public void Login(@NotNull WebDriver driver,String login, String password) {

    }

    @Step(value = "Сравнить ожидаемое значение тэга <title> с актуальным")
    public void CheckTitle(@NotNull WebDriver driver, String expectedTitle) {

        Assert.assertEquals(driver.getTitle(), expectedTitle);

    }
    @Step(value = "Проверяем существование элементов на странице")
    public void CheckInputs(@NotNull WebDriver driver, String[] inputXpaths ) {

        for (String inputXpath : inputXpaths) {

            Assert.assertNotNull(By.xpath(inputXpath).findElement(driver));

        }

    }

    @Step(value = "Кликаем по элементу")
    public void ClickElement(@NotNull WebDriver driver, String clickXpath) {

        driver.findElement(By.xpath(clickXpath)).click();

    }

    @Step(value = "Заполняем поля формы")
    public void FillInInputs(@NotNull WebDriver driver, String[][] inputs) {

        for (String[] items : inputs) {
            driver.findElement(By.xpath(items[0])).sendKeys(items[1]);

        }

    }

    @Step(value = "Выбираем селектор")
    public void SelectElement(@NotNull WebDriver driver, String[][] selectors) {

        for (String[] select : selectors) {
            Select selector = new Select(driver.findElement(By.xpath(select[0])));
            selector.selectByValue(select[1]);

        }
    }

    @Step(value = "Ожидаем результат")
    public void WaitElements(@NotNull WebDriver driver, String[][] waitElements) {

        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] waitElement : waitElements) {

            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitElement[0])));

        }
    }

    @Step(value = "Ожидаем результат")
    public void WaitElement(@NotNull WebDriver driver, String waitElementXpath) {

        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(10));

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitElementXpath)));

    }

    @Step(value = "Сравниваем полученные результаты")
    public void AssertResults(@NotNull WebDriver driver, String[][] assertElements) {

        for (String[] assertElement : assertElements) {

            Assert.assertEquals(driver.findElement(By.xpath(assertElement[0])).getText(), assertElement[1].trim());

        }
    }

    @Step(value = "Сравниваем полученный результат")
    public void AssertResult(@NotNull WebDriver driver, String assertElementXpath, String assertElement) {

            Assert.assertEquals(driver.findElement(By.xpath(assertElementXpath)).getText(), assertElement.trim());
    }

    @Step(value = "Проверяем ошибки валидации")
    public void AssertValidationErrors(@NotNull WebDriver driver, String[][] validationErrors) {

        for (String[] validationError : validationErrors) {

            Assert.assertEquals(driver.findElement(
                    By.xpath(validationError[0])).getAttribute("validationMessage"), validationError[1]);

        }
    }
}
