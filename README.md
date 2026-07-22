# 🤝 Reseller Management Platform

**A partner/reseller management dashboard for a fintech platform — QA & Automation Portfolio Project**

> This repository documents the QA strategy, test automation, and testing approach applied to a
> **Reseller Dashboard** — the interface resellers/partners use to onboard and manage their own
> book of merchants under a fintech platform, and track the revenue they earn from doing so.
>
> All content here uses **generic/sample data only**. No client names, company names, or
> confidential/production information are included. Dates and timelines are placeholders —
> update `[Timeline]` before publishing.

---

## 📖 Table of Contents

1. [What is a Reseller Dashboard?](#-what-is-a-reseller-dashboard)
2. [My Role](#-my-role)
3. [Tech Stack & Tools Used](#-tech-stack--tools-used)
4. [Types of Testing Performed](#-types-of-testing-performed)
5. [How It Works — Business Flow](#-how-it-works--business-flow)
6. [Key Achievements](#-key-achievements)
7. [Automation Approach](#-automation-approach)
8. [Regression Checklist](#-regression-checklist)
9. [Screenshots & Reports](#-screenshots--reports)
10. [Repository Structure](#-repository-structure)

---

## 💡 What is a Reseller Dashboard?

A **Reseller Dashboard** lets a partner organization (a "reseller") onboard and manage their own
portfolio of merchants under a larger fintech platform, in exchange for a share of the revenue
those merchants generate.

If you're new to fintech QA, HR, or any non-technical role: think of it as a franchise model
applied to a payments platform. The platform provides the underlying infrastructure (collection,
payout, banking); the reseller brings merchants, manages the relationship, and earns a
commission — so this module is really a **layer of indirection on top of merchant management and
commercials**, with its own dashboard, reporting, and revenue-tracking needs.

### Core Areas

- **Dashboard** — the reseller's summary view: merchants managed, transaction volume, revenue
- **Merchant Management** — onboarding and managing the reseller's own merchant portfolio
- **Service Management** — which platform services (Collection, Payout, Connected Banking,
  BBPS) are enabled for each merchant under this reseller
- **Reports** — activity and performance reporting scoped to the reseller's own merchants only
- **Revenue** — the reseller's earned share of platform commercials
- **Commercials** — the rules governing how that revenue share is calculated

### Who typically interacts with it?

| Role | What they do |
|---|---|
| **Reseller** | Onboards merchants under their own account, manages services, views revenue and reports |
| **Merchant (under a reseller)** | Uses the platform normally — largely unaware of the reseller layer |
| **Platform Admin/Ops** | Approves resellers, configures revenue-share commercials, monitors reseller-level activity |

---

## 👤 My Role

QA Engineer / SDET responsible for the Reseller Dashboard module, owning manual and automated
test coverage across merchant scoping, service management, and revenue reporting accuracy.

- Owned QA strategy for the reseller dashboard → merchant management → service management →
  reports → revenue journey
- Designed and executed automation covering merchant-scoping correctness — the area with the
  highest data-isolation risk in this module
- Performed **API testing** validating that reseller-scoped endpoints never leak data belonging
  to another reseller's merchants
- Validated revenue calculation accuracy against the underlying commercial/settlement data
- Logged, triaged, and tracked defects through their full lifecycle

**Timeline:** `[Add Duration]`

---

## 🛠 Tech Stack & Tools Used

| Category | Tools |
|---|---|
| **UI Automation** | Selenium WebDriver, Java, TestNG |
| **Build Tool** | Maven |
| **API Testing** | REST Assured, Postman |
| **CI/CD** | Jenkins |
| **Bug Tracking** | JIRA |
| **Version Control** | Git, GitHub |

---

## 🧪 Types of Testing Performed

- **Functional Testing** — reseller dashboard, merchant management, service toggling
- **Regression Testing** — full onboarding-to-revenue-reporting suite before every release
- **API Testing** — reseller-scoped endpoints, revenue calculation endpoints
- **Data Isolation / Multi-Tenancy Testing** — confirming one reseller can never see another
  reseller's merchants, transactions, or revenue
- **Negative Testing** — invalid merchant assignment, service enablement for a non-existent
  merchant, unauthorized cross-reseller access attempts
- **Smoke & Sanity Testing** — post-deployment health checks

---

## 🔄 How It Works — Business Flow

### Reseller Regression Flow

```
Reseller Dashboard (summary)
      │
      ▼
Merchant Management (onboard / manage merchants under this reseller)
      │
      ▼
Service Management (enable Collection / Payout / Connected Banking / BBPS per merchant)
      │
      ▼
Reports (activity, scoped strictly to this reseller's merchants)
      │
      ▼
Revenue (reseller's earned commission, derived from merchant commercial activity)
```

### Why Data Isolation Is the Highest-Risk Area

Because a Reseller Dashboard is a **multi-tenant view over a shared merchant base**, the single
most damaging class of defect is a reseller seeing (or worse, managing) another reseller's
merchants, transactions, or revenue. Every list, search, and report in this module must be
tested not just for correctness, but for **scoping** — filtered strictly to the logged-in
reseller's own portfolio.

### Admin Functions

- Reseller onboarding & approval
- Revenue-share commercial configuration
- Reseller-level activity monitoring
- Audit logs

---

## 🏆 Key Achievements

- Designed a multi-tenancy-focused test strategy treating merchant-scoping and data isolation as
  the top-priority regression area, ahead of standard functional coverage
- Validated revenue calculation accuracy end-to-end against underlying commercial/settlement data
- Built automated regression coverage for the reseller-to-revenue-reporting journey
- Logged and tracked defects across scoping, service-management, and revenue-calculation themes

---

## 🤖 Automation Approach

Automation is built with **Selenium WebDriver + Java + TestNG**, using the **Page Object Model**.

### Priority Automated Scenarios

1. Reseller login & dashboard summary accuracy
2. Merchant onboarding under a reseller
3. Service enablement per merchant
4. Cross-reseller data isolation checks (a critical negative-test category)
5. Revenue report accuracy against underlying commercial data

See [`automation/`](./automation) for the framework README and a sample spec file using dummy
data.

---

## ✅ Regression Checklist

- [ ] Reseller Login & Dashboard
- [ ] Merchant Management (Onboard / Update)
- [ ] Service Management (enable/disable per merchant)
- [ ] Reports (scoped correctly to reseller)
- [ ] Revenue Calculation
- [ ] Commercials / Revenue-Share Configuration
- [ ] Cross-Reseller Data Isolation
- [ ] Permissions / Role-Based Access

Full checklist with edge cases available in [`test-cases/`](./test-cases).

---

## 📸 Screenshots & Reports

Sample test execution reports and defect report templates are available under
[`test-reports/`](./test-reports) and [`bug-reports/`](./bug-reports).

---

## 📁 Repository Structure

```
reseller-management-platform/
├── README.md
├── docs/
│   ├── business-overview.md      → What a Reseller Dashboard is, glossary, multi-tenancy risk model
│   └── shared-platform-services.md → Company-wide services this product depends on (Auth, Role & Permission Service, etc.)
├── test-cases/
│   └── regression-checklist.md   → Full regression suite + edge cases
├── automation/
│   ├── README.md                 → Framework setup & structure
│   └── SampleResellerTest.java   → Sample Selenium + TestNG test (dummy data)
├── bug-reports/
│   └── sample-defect-report.md   → Defect report template with dummy example
└── test-reports/
    └── regression-execution-summary.md → Sample regression test execution report
```
