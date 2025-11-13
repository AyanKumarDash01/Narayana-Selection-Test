package ui;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SearchAndCheckoutTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
        driver.findElement(By.id("Email")).sendKeys("ayan.dash@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Test@123");
        driver.findElement(By.cssSelector("button.button-1.login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.ico-logout")));
    }

    @Test(priority = 1)
    public void searchProduct() {
        // Search for MacBook
        driver.findElement(By.id("small-searchterms")).sendKeys("Apple MacBook Pro 13-inch");
        driver.findElement(By.cssSelector("button.search-box-button")).click();

        // Retry if no product found
        if (driver.getPageSource().contains("No products were found")) {
            driver.findElement(By.id("q")).clear();
            driver.findElement(By.id("q")).sendKeys("Apple MacBook");
            driver.findElement(By.cssSelector("button.search-button")).click();
        }

        WebElement macbookLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Apple MacBook Pro")));
        Assert.assertTrue(macbookLink.isDisplayed(), "Apple MacBook Pro not visible.");
        macbookLink.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("apple-macbook-pro"),
                "Not navigated to product detail page.");
    }

    @Test(priority = 2, dependsOnMethods = "searchProduct")
    public void hoverAndCheckout() {
        driver.get("https://demo.nopcommerce.com/");

        Actions actions = new Actions(driver);
        WebElement computersMenu = driver.findElement(By.linkText("Computers"));
        WebElement notebooksMenu = driver.findElement(By.linkText("Notebooks"));
        actions.moveToElement(computersMenu).pause(1000).click(notebooksMenu).perform();

        WebElement pageTitle = driver.findElement(By.cssSelector("div.page-title h1"));
        Assert.assertEquals(pageTitle.getText(), "Notebooks", "Notebooks page not opened.");

        WebElement addBtn = driver.findElement(By.xpath("//button[contains(text(),'Add to cart')]"));
        addBtn.click();

        WebElement successBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.content")));
        Assert.assertTrue(successBar.getText().contains("The product has been added"),
                "Success message not shown.");

        driver.get("https://demo.nopcommerce.com/cart");

        WebElement terms = driver.findElement(By.id("termsofservice"));
        if (!terms.isSelected()) {
            terms.click();
        }

        driver.findElement(By.id("checkout")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout") || driver.getCurrentUrl().contains("login"),
                "Checkout not started.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
