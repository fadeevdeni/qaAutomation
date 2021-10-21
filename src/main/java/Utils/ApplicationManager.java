package Utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

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

    @Step(value = "Получить значение тэга <title>")
    public String GetTitle() {

        return driver.getTitle();

    }

    @Step(value = "Находим элемент")
    public WebElement GetElementByXpath(String inputXpath) {

        return driver.findElement(By.xpath(inputXpath));

    }

    @Step(value = "Кликаем по элементу")
    public void ClickElement(String clickXpath) {

        driver.findElement(By.xpath(clickXpath)).click();

    }

    @Step(value = "Выбираем селектор")
    public void SelectElementByXpath(String selectorXpath, String selectorValue) {

        Select selector = new Select(driver.findElement(By.xpath(selectorXpath)));
        selector.selectByValue(selectorValue);
    }

    @Step(value = "Ожидаем элемент")
    public void WaitElementByXpath(String waitElementXpath) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(30));
        wait.pollingEvery(Duration.ofSeconds(1));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitElementXpath)));

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
