package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.HomePage;
import pages.ProductPage;
import utils.ConfigReader;
import utils.DriverFactory;

import java.util.Map;

public class BigBasketTest {
    private WebDriver driver;
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        homePage = new HomePage(DriverFactory.getDriver());
        productPage = new ProductPage(DriverFactory.getDriver());
    }

    @DataProvider(name = "products")
    public Object[][] products() {
        return new Object[][] { {"Milk"}, {"Biscuits"} };
    }

    @Test(groups = {"smoke"})
    public void verifyMilkSearchResults() {
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct("Milk");

        Assert.assertTrue(productPage.areProductsDisplayed("Milk"),
            "Milk search results should be displayed.");
    }

    @Test(dataProvider = "products", groups = {"smoke"})
    public void verifySearchResultsForProducts(String product) {
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct(product);

        Assert.assertTrue(productPage.areProductsDisplayed(product),
            product + " search results should be displayed.");
    }

    @Test(groups = {"regression"})
    public void applyBrandFilterAndValidateResult() {
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct("Milk");
        productPage.applyBrandFilter("Amul");
        Assert.assertTrue(productPage.areProductsDisplayed("Amul"),
                "brand filtered search results should be displayed.");

    }

    @Test(groups = {"regression"})
    public void validateQuantityAndBasketAmount(){
        homePage.open(ConfigReader.get("url"));
        homePage.searchProduct("Milk");
        productPage.areProductsDisplayed("Milk");
        productPage.selectFirstProduct();
        Map<String, Integer> product = productPage.CaptureProductnamePrice();
        productPage.addProductIncreaseQuantityAndValidate();

        homePage.searchProduct("Biscuits");
        productPage.areProductsDisplayed("Biscuits");
        productPage.selectFirstProduct();
        Map<String, Integer> product1 = productPage.CaptureProductnamePrice();
        productPage.addProductAndValidate();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
