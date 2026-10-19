package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("loginButton");
    private final By logoutButton = By.id("logoutButton");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("/login");
    }

    public void loginAs(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public void logout() {
        driver.findElement(logoutButton).click();
    }
}
