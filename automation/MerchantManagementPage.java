package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MerchantManagementPage {
    private final WebDriver driver;
    private final By addMerchantButton = By.id("addMerchantButton");
    private final By merchantNameField = By.id("merchantName");
    private final By saveMerchantButton = By.id("saveMerchantButton");
    private final By merchantListItems = By.cssSelector("[data-testid='merchant-list-item']");

    public MerchantManagementPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("/merchants");
    }

    public void onboardDummyMerchant(String merchantName) {
        driver.findElement(addMerchantButton).click();
        driver.findElement(merchantNameField).sendKeys(merchantName);
        driver.findElement(saveMerchantButton).click();
    }

    public boolean merchantListContains(String merchantName) {
        List<WebElement> items = driver.findElements(merchantListItems);
        return items.stream().anyMatch(item -> item.getText().contains(merchantName));
    }
}
