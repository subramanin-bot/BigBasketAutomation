package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.ProductPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class BigBasketTest {
    private WebDriver driver;
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
    }

    @DataProvider(name = "products")
    public Object[][] products() {
        return new Object[][] { {"Milk"}, {"Biscuits"} };
    }

    @Test
    public void verifyMilkSearchResults() {
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct("Milk");

        Assert.assertTrue(productPage.areProductsDisplayed("Milk"),
            "Milk search results should be displayed.");
    }

    @Test(dataProvider = "products")
    public void verifySearchResultsForProducts(String product) {
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct(product);

        Assert.assertTrue(productPage.areProductsDisplayed(product),
            product + " search results should be displayed.");
    }

    @Test
    public void applyBrandFilterAndValidateResult() {
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct("Milk");
        productPage.applyBrandFilter("Amul");
        Assert.assertTrue(productPage.areProductsDisplayed("Amul"),
                "brand filtered search results should be displayed.");

    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
