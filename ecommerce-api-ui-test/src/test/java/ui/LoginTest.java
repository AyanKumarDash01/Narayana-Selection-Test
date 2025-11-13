package ui;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    String userEmail = "ayan.dash@gmail.com"; 
    String userPassword = "Test@123";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void loginUserA() {
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");

        driver.findElement(By.id("Email")).sendKeys(userEmail);
        driver.findElement(By.id("Password")).sendKeys(userPassword);
        driver.findElement(By.cssSelector("button.button-1.login-button")).click();

        WebElement logoutLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.ico-logout"))
        );
        Assert.assertTrue(logoutLink.isDisplayed(), "Login failed — Logout link not found!");

        System.out.println("Login successful for: " + userEmail);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
