package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonMethods {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public CommonMethods(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public void sendKeys(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    public boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void scrollToView(WebElement element) {
        js.executeScript(
                "arguments[0].scrollIntoView(" +
                        "{behavior:'smooth', block:'center'});",
                element);
    }

    public void scrollToView(By locator) {
        WebElement element = waitForVisible(locator);
        scrollToView(element);
    }

    public void scrollToBottom() {
        js.executeScript(
                "window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void scrollByPixels(int pixels) {
        js.executeScript("window.scrollBy(0, arguments[0]);", pixels);
    }

    public void jsClick(By locator) {
        WebElement element = waitForVisible(locator);
        js.executeScript("arguments[0].click();", element);
    }

    public void selectByVisibleText(By locator, String text) {
        WebElement element = waitForVisible(locator);
        new Select(element).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        WebElement element = waitForVisible(locator);
        new Select(element).selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        WebElement element = waitForVisible(locator);
        new Select(element).selectByIndex(index);
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public boolean waitUntilInvisible(By locator) {
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public String takeScreenshot(String fileName) {
        try {
            Path directory = Paths.get("screenshots");
            Files.createDirectories(directory);

            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path destination = directory.resolve(fileName + ".png");
            Files.copy(source.toPath(), destination);

            return destination.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save screenshot: " + fileName, e);
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void switchToNewTab(WebDriver driver) {
        String parentWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public int extractInteger(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Input text cannot be null or empty"
            );
        }

        String numericValue = text.replaceAll("[^0-9]", "");

        if (numericValue.isEmpty()) {
            throw new IllegalArgumentException(
                    "No numeric value found in text: " + text
            );
        }

        return Integer.parseInt(numericValue);
    }

}