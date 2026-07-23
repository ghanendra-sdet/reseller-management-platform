# Reseller Dashboard — Automation Framework

> Automated scenarios trace directly to [`../regression-checklist.md`](../regression-checklist.md)
> sections 1–3 (core flow, data isolation, lifetime commission attribution). See
> [`../docs/README.md`](../docs/README.md) for the full documentation map.

Automation for the Reseller Dashboard regression path, built with **Selenium WebDriver + Java +
TestNG**, using the **Page Object Model (POM)**.

## Why Selenium + Java + TestNG

- TestNG's data-provider pattern is a natural fit for this module's core testing theme:
  running the same scoping assertion across multiple dummy reseller accounts to verify
  cross-tenant isolation
- Mature parallel execution support in TestNG for running isolation checks across simulated
  reseller sessions concurrently

## Suggested Project Structure

```
automation/
├── README.md
├── pom.xml
├── src/test/java/
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── DashboardPage.java
│   │   ├── MerchantManagementPage.java
│   │   ├── ServiceManagementPage.java
│   │   └── RevenueReportPage.java
│   ├── fixtures/
│   │   └── DummyResellerData.java
│   └── tests/
│       └── SampleResellerTest.java
└── testng.xml
```

> This repo currently includes one representative sample (`SampleResellerTest.java`) rather than
> the full framework, to keep the portfolio focused.

## Test Data Policy

All automation uses **dummy data only**: dummy reseller accounts (e.g. `DEMO-RESELLER-A`,
`DEMO-RESELLER-B`) and dummy merchants, specifically designed in pairs to make cross-tenant
isolation easy to assert against.

## Priority Automated Scenarios

1. Reseller login & dashboard summary accuracy
2. Merchant onboarding under a reseller
3. Service enablement per merchant
4. Cross-reseller data isolation checks (critical negative-test category)
5. Revenue report accuracy against underlying commercial data
