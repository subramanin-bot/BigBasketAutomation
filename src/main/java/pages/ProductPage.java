package pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.CommonMethods;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private CommonMethods common;


    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.common = new CommonMethods(driver);
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
        By brandLocator = By.xpath(
                "//label[normalize-space()='" + brand +
                        "']/preceding-sibling::input[@type='checkbox']"
        );

        common.scrollToView(brandLocator);
        common.waitForClickable(brandLocator);
        common.jsClick(brandLocator);
    }

    public void selectFirstProduct(){

        By firstProduct = By.xpath("(//button[text()='Add']/ancestor::li)[1]");

        common.waitForVisible(firstProduct);
        common.waitForClickable(firstProduct);
        common.click(firstProduct);
    }

    public Map<String, Integer> CaptureProductnamePrice() {

        Map<String, Integer> productDetails = new HashMap<>();
        By productNameLocator  = By.xpath("//h1[@class='sc-dtInlm WrSqK']");
        By priceTextLocator = By.xpath("//td[contains(normalize-space(.),'Price:')]");

        common.switchToNewTab(driver);
        common.waitForVisible(productNameLocator);
        String productName = common.getText(productNameLocator);
        String priceText = common.getText(priceTextLocator);
        int price = common.extractInteger(priceText);
        productDetails.put(productName, price);
        return productDetails;
    }

    public void addProductAndValidate() {

        By addToBasket = By.xpath("(//button[text()='Add to basket'])[1]");
        By successMessage = By.xpath("//*[text()='An item has been added to your basket successfully']");
        By quantityLocator = By.xpath("((//div[@id='add-button'])[1]/div)[1]");

        common.click(addToBasket);
        common.waitForVisible(successMessage);
        String message = common.getText(successMessage);
        Assert.assertEquals(message, "An item has been added to your basket successfully");
        common.waitForVisible(By.xpath("(//div[@id='add-button'])[1]/div[text()='1']"));

        int actualQuantity = common.extractInteger(common.getText(quantityLocator));
        Assert.assertEquals(actualQuantity, 1);
    }



    public void addProductIncreaseQuantityAndValidate() {

        By addToBasket = By.xpath("(//button[text()='Add to basket'])[1]");
        By successMessage = By.xpath("//*[text()='An item has been added to your basket successfully']");
        By plusButton = By.xpath("((//div[@id='add-button'])[1]/button)[2]");
        By quantityLocator = By.xpath("((//div[@id='add-button'])[1]/div)[1]");

        common.click(addToBasket);
        common.waitForVisible(successMessage);
        String message = common.getText(successMessage);
        Assert.assertEquals(message, "An item has been added to your basket successfully");

        common.click(plusButton);
        common.waitForVisible(successMessage);
        String message1 = common.getText(successMessage);
        Assert.assertEquals(message1, "An item has been added to your basket successfully");
        common.waitForVisible(By.xpath("(//div[@id='add-button'])[1]/div[text()='2']"));

        int actualQuantity = common.extractInteger(common.getText(quantityLocator));
        Assert.assertEquals(actualQuantity, 2);
    }

    }