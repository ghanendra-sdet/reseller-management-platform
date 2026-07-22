# Reseller Dashboard — Business Overview

> **Start here if you're new to fintech QA, in HR, or from a non-QA technical role.** This
> document explains what a Reseller Dashboard does and why it matters, before you look at any
> test case or code.

## 1. What problem does it solve?

A fintech platform can grow faster by letting partners ("resellers") bring their own merchants
onto the platform, in exchange for a revenue share — rather than the platform having to acquire
every merchant directly. The Reseller Dashboard is what makes that partnership operable: it lets
a reseller manage their own merchant portfolio, control which services are active for each, and
see what they've earned.

## 2. Core Modules

- **Dashboard** — reseller's summary: merchants managed, transaction volume, revenue
- **Merchant Management** — onboarding and managing merchants under this reseller
- **Service Management** — enabling Collection / Payout / Connected Banking / BBPS per merchant
- **Reports** — activity reporting, strictly scoped to this reseller's own merchants
- **Revenue** — the reseller's earned share of platform commercials
- **Commercials** — the configuration governing how that revenue share is calculated

## 3. Glossary

| Term | Meaning |
|---|---|
| **Reseller** | A partner organization that onboards and manages its own merchants under the platform |
| **Merchant Scoping** | Ensuring a reseller only ever sees/manages merchants that belong to them |
| **Revenue Share** | The commission a reseller earns from their merchants' commercial activity |
| **Multi-Tenancy** | An architecture where multiple resellers share the same platform but must never see each other's data |

## 4. Why Data Isolation Is the Central Testing Theme

This module is fundamentally a **multi-tenant view over a shared merchant base**. The single
most damaging class of defect is a reseller seeing or managing another reseller's merchants,
transactions, or revenue — a data-isolation failure, not just a functional bug.

Every list, search, filter, and report in this module needs a **scoping test**, not just a
correctness test: does this feature return only the logged-in reseller's own data, under every
combination of filters, pagination, and search terms?

## 5. Cross-Module Dependencies

- **Merchant Management (platform-wide)** — resellers manage a scoped subset of the same
  underlying merchant records
- **Commercial Engine** — computes both the merchant's fees and the reseller's revenue share
  from the same transaction data
- **Collection / Payout / Connected Banking / BBPS** — the actual services being toggled on/off
  per merchant

*(A dedicated `architecture-and-flow.md` with a scoping/multi-tenancy diagram is a good candidate
for the next iteration of this repo.)*
