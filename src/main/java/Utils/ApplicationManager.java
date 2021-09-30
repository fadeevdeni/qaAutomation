package Utils;

import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

            WebElement element = driver.findElement(By.xpath(inputXpath));
            Assert.assertNotNull(element);

        }

    }

    @Step(value = "Кликаем по элементу")
    public void ClickElement(@NotNull WebDriver driver, String clickXpath) {

        driver.findElement(By.xpath(clickXpath)).click();

    }

    @Step(value = "Заполняем поля формы")
    public void FillInInputs(@NotNull WebDriver driver, String[][] inputs) {

        for (String[] items : inputs) {
            WebElement input = driver.findElement(By.xpath(items[0]));
            input.sendKeys(items[1]);

        }

    }

    @Step(value = "Выбираем селектор")
    public void SelectElement(@NotNull WebDriver driver, String[][] selectors) {

        for (String[] select : selectors) {
            WebElement selectElement = driver.findElement(By.xpath(select[0]));
            Select selector = new Select(selectElement);
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

    @Step(value = "Сравниваем полученные результаты")
    public void AssertResults(@NotNull WebDriver driver, String[][] assertElements) {

        for (String[] assertElement : assertElements) {

            WebElement element = driver.findElement(By.xpath(assertElement[0]));
            Assert.assertEquals(element.getText(), assertElement[1]);

        }
    }
}
