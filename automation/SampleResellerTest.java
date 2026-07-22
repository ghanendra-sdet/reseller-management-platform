package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

/**
 * Sample Selenium + TestNG regression test for the Reseller Dashboard.
 * All data below is DUMMY/SAMPLE data for portfolio demonstration only —
 * no real credentials or endpoints.
 */
public class SampleResellerTest {

    private WebDriver driver;

    private static final String DUMMY_RESELLER_A_USER = "DEMO-RESELLER-A";
    private static final String DUMMY_RESELLER_A_PASS = "Dummy@Passw0rd";
    private static final String DUMMY_RESELLER_B_USER = "DEMO-RESELLER-B";
    private static final String DUMMY_RESELLER_B_PASS = "Dummy@Passw0rd2";

    @BeforeMethod
    public void setUp() {
        // driver = new ChromeDriver(); // omitted: driver setup is environment-specific
    }

    @Test
    public void resellerCanLoginAndSeeDashboard() {
        LoginPage login = new LoginPage(driver);
        login.goTo();
        login.loginAs(DUMMY_RESELLER_A_USER, DUMMY_RESELLER_A_PASS);

        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isDisplayed(), "Reseller dashboard should load after login");
    }

    @Test
    public void merchantOnboardedUnderResellerAIsVisibleOnlyToResellerA() {
        LoginPage login = new LoginPage(driver);
        login.goTo();
        login.loginAs(DUMMY_RESELLER_A_USER, DUMMY_RESELLER_A_PASS);

        MerchantManagementPage merchants = new MerchantManagementPage(driver);
        merchants.goTo();
        merchants.onboardDummyMerchant("Demo Merchant A1");

        Assert.assertTrue(merchants.merchantListContains("Demo Merchant A1"));
    }

    @Test
    public void resellerBCannotSeeResellerAMerchants() {
        // Reseller A onboards a merchant
        LoginPage login = new LoginPage(driver);
        login.goTo();
        login.loginAs(DUMMY_RESELLER_A_USER, DUMMY_RESELLER_A_PASS);

        MerchantManagementPage merchantsAsA = new MerchantManagementPage(driver);
        merchantsAsA.goTo();
        merchantsAsA.onboardDummyMerchant("Demo Merchant A2");
        login.logout();

        // Reseller B must never see it — the core data-isolation assertion for this module
        login.loginAs(DUMMY_RESELLER_B_USER, DUMMY_RESELLER_B_PASS);
        MerchantManagementPage merchantsAsB = new MerchantManagementPage(driver);
        merchantsAsB.goTo();

        Assert.assertFalse(
            merchantsAsB.merchantListContains("Demo Merchant A2"),
            "Reseller B must never see a merchant onboarded under Reseller A"
        );
    }

    @Test
    public void revenueReportIsScopedToLoggedInReseller() {
        LoginPage login = new LoginPage(driver);
        login.goTo();
        login.loginAs(DUMMY_RESELLER_A_USER, DUMMY_RESELLER_A_PASS);

        RevenueReportPage revenue = new RevenueReportPage(driver);
        revenue.goTo();

        Assert.assertTrue(
            revenue.allRowsBelongToReseller(DUMMY_RESELLER_A_USER),
            "Every row in the revenue report must belong to the logged-in reseller only"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
