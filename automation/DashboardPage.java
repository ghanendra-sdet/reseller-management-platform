package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    private final WebDriver driver;
    private final By dashboardSummary = By.id("resellerDashboardSummary");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDisplayed() {
        return driver.findElement(dashboardSummary).isDisplayed();
    }
}
