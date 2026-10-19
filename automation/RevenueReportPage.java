package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RevenueReportPage {
    private final WebDriver driver;
    private final By revenueRows = By.cssSelector("[data-testid='revenue-row']");
    private final By revenueRowResellerId = By.cssSelector("[data-testid='revenue-row-reseller-id']");

    public RevenueReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("/revenue");
    }

    public boolean allRowsBelongToReseller(String resellerId) {
        List<WebElement> rows = driver.findElements(revenueRows);
        return rows.stream().allMatch(row ->
            row.findElement(revenueRowResellerId).getText().equals(resellerId));
    }
}
