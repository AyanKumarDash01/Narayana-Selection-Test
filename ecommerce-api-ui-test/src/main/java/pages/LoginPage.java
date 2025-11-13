package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    By emailField = By.id("Email");
    By passwordField = By.id("Password");
    By loginBtn = By.cssSelector("button.button-1.login-button");
    By logoutLink = By.cssSelector("a.ico-logout");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
    }

    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();
    }

    public boolean isLogoutVisible() {
        WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
        return logout.isDisplayed();
    }
}
