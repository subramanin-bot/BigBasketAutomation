package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchBox = By.xpath("//input[@placeholder='Search for Products...' and not(ancestor::header[contains(@class,'sm:hidden fixed ')])]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void open(String url) {
        driver.get(url);
    }

    public void searchProduct(String product) {
        WebElement search = wait.until(
            ExpectedConditions.elementToBeClickable(searchBox));
        search.clear();
        search.sendKeys(product);
        search.sendKeys(Keys.ENTER);
    }
}
