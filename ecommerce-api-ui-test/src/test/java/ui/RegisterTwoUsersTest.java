package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;

public class RegisterTwoUsersTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][]{
                {"M", "Ayan", "Dash", "ayan.dash" + System.currentTimeMillis() + "@gmail.com", "Test@123"},
                {"F", "Priya", "Patel", "priya.patel" + System.currentTimeMillis() + "@gmail.com", "Test@123"}
        };
    }

    @Test(dataProvider = "userData")
    public void registerUsers(String gender, String firstName, String lastName, String email, String password) {
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        if (gender.equalsIgnoreCase("M")) {
            driver.findElement(By.id("gender-male")).click();
        } else {
            driver.findElement(By.id("gender-female")).click();
        }

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
        driver.findElement(By.id("Email")).sendKeys(email);

        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        day.selectByVisibleText("13");
        month.selectByVisibleText("November");
        year.selectByVisibleText("1999");

        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

        driver.findElement(By.id("register-button")).click();

        WebElement successMsg = driver.findElement(By.cssSelector("div.result"));
        Assert.assertTrue(successMsg.getText().contains("Your registration completed"),
                "Registration failed for user: " + email);

        driver.findElement(By.cssSelector("a.button-1.register-continue-button")).click();

        driver.findElement(By.cssSelector("a.ico-logout")).click();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
