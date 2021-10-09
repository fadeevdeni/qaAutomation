package Utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApplicationManager {

    private WebDriver driver;

    public void SetUpDriver(WebDriver driver) {
        this.driver = driver;
    }

    @Step(value = "Открыть страницу")
    public void GetPage(String url) {

        driver.get(url);

    }

    @Step(value = "Сравнить ожидаемое значение тэга <title> с актуальным")
    public void CheckTitle(String expectedTitle) {

        Assert.assertEquals(driver.getTitle(), expectedTitle);

    }
    @Step(value = "Проверяем существование элементов на странице")
    public void CheckInputs(String[] inputXpaths ) {

        for (String inputXpath : inputXpaths) {

            Assert.assertNotNull(By.xpath(inputXpath).findElement(driver));

        }

    }

    @Step(value = "Кликаем по элементу")
    public void ClickElement(String clickXpath) {

        driver.findElement(By.xpath(clickXpath)).click();

    }

    @Step(value = "Заполняем поля формы")
    public void FillInInputs(String[][] inputs) {

        for (String[] items : inputs) {
            driver.findElement(By.xpath(items[0])).sendKeys(items[1]);

        }

    }

    @Step(value = "Выбираем селектор")
    public void SelectElement(String[][] selectors) {

        for (String[] select : selectors) {
            Select selector = new Select(driver.findElement(By.xpath(select[0])));
            selector.selectByValue(select[1]);

        }
    }

    @Step(value = "Ожидаем результат")
    public void WaitElements(String[][] waitElements) {

        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] waitElement : waitElements) {

            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitElement[0])));

        }
    }

    @Step(value = "Ожидаем результат")
    public void WaitElement(String waitElementXpath) {

        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(10));

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitElementXpath)));

    }

    @Step(value = "Сравниваем полученные результаты")
    public void AssertResults(String[][] assertElements) {

        for (String[] assertElement : assertElements) {

            Assert.assertEquals(driver.findElement(By.xpath(assertElement[0])).getText(), assertElement[1].trim());

        }
    }

    @Step(value = "Сравниваем полученный результат")
    public void AssertResult(String assertElementXpath, String assertElement) {

            Assert.assertEquals(driver.findElement(By.xpath(assertElementXpath)).getText(), assertElement.trim());
    }

    @Step(value = "Проверяем ошибки валидации")
    public void AssertValidationErrors(String[][] validationErrors) {

        for (String[] validationError : validationErrors) {

            Assert.assertEquals(driver.findElement(
                    By.xpath(validationError[0])).getAttribute("validationMessage"), validationError[1].trim());

        }
    }

    //Парсинг CSV файла с данными.
    public Iterator<Object[]> parseCsvData(String fileName) throws IOException {

        List<Object []> testCases = new ArrayList<>();
        String[] data;

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            // use comma as separator
            data= line.split(";");
            testCases.add(data);
        }

        return testCases.iterator();

    }
}
