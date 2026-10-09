package pages;

import java.math.BigDecimal;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasketPage {
    private final WebDriverWait wait;

    public BasketPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public BigDecimal getPrice(By locator) {
        String cleaned = getText(locator).replaceAll("[^0-9.]", "");
        return new BigDecimal(cleaned);
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void setQuantity(By quantityField, int quantity) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(quantityField));
        field.clear();
        field.sendKeys(String.valueOf(quantity));
    }
}
