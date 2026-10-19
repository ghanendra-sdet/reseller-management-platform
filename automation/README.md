# Reseller Dashboard — Automation Framework

> Automated scenarios trace directly to [`../regression-checklist.md`](../regression-checklist.md)
> sections 1–3 (core flow, data isolation, lifetime commission attribution). See
> [`../docs/README.md`](../docs/README.md) for the full documentation map,
> [`../docs/architecture-and-flow.md`](../docs/architecture-and-flow.md) for the sequence diagrams
> each scenario below is built to validate, and
> [`../docs/tech-and-skills.md`](../docs/tech-and-skills.md) section 5 for a worked, illustrative
> JMeter concurrent-isolation scenario plus the full performance testing approach for this domain.

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
├── testng.xml
└── jmeter/
    └── concurrent-isolation-load.jmx   ← tenant-isolation scoping under concurrent multi-reseller load
```

> This repo currently includes one representative sample flow (`SampleResellerTest.java` plus the
> page objects it depends on: `LoginPage`, `DashboardPage`, `MerchantManagementPage`, and
> `RevenueReportPage`) rather than the full framework above, to keep the portfolio focused.
> `ServiceManagementPage` and the fixtures/JMeter layers are part of the suggested shape, not
> included yet.

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
6. Tenant-isolation scoping under concurrent multi-reseller load (JMeter — see
   [`../docs/tech-and-skills.md`](../docs/tech-and-skills.md) section 5)
