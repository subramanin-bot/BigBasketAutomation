package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean areProductsDisplayed(String productName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(.,'"+productName+"')]")));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void applyBrandFilter(String brand) {
        wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[normalize-space()='"+brand+"']/preceding-sibling::input[@type='checkbox']"))).click();
    }



}