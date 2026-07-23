# Reseller Dashboard — Shared Platform Services

> The Reseller Dashboard doesn't run in isolation — it's one of several products (Collection,
> Payout, Connected Banking, BBPS, Reseller, and others) built on top of a **common company-wide
> platform layer**. This document lists the shared services this product depends on, distinct
> from Reseller-specific modules like Merchant Scoping and Revenue Calculation (see
> [`business-overview.md`](./business-overview.md)).

## Why "Shared Platform" Matters for Testing

A defect in a shared service doesn't stay contained to one product. A bug in the company-wide
**Role & Permission Service**, for example, is especially dangerous for this product — the
Reseller Dashboard's entire multi-tenancy safety model (see
[`regression-checklist.md`](../regression-checklist.md) section 2) depends
on that shared service correctly scoping access, not on Reseller-specific code alone.

## Shared Platform Services (Company-Wide)

### Identity & Access
- Authentication
- Authorization
- OTP Service
- User Management
- Role & Permission Service

### Merchant Lifecycle
- Merchant Management
- Merchant Onboarding
- Merchant Activation
- Merchant Profile

### Financial Engines
- Commercial Engine
- GST Engine
- Ledger Engine
- Settlement Engine
- Reconciliation Engine

### Reporting & Data Export
- Report Engine
- Export Engine
- Download Engine
- Dashboard Service
- Search Engine
- Filter Engine

### Platform Infrastructure
- Audit Logs
- Activity Logs
- Notification Service
- API Gateway
- Validation Service
- File Upload Service
- File Download Service
- Scheduler / Background Workers

## How the Reseller Dashboard Depends on These

- **Role & Permission Service / Authorization** — this is the single most load-bearing shared
  dependency for this product. Every cross-reseller data-isolation guarantee documented in
  [`docs/business-overview.md`](./business-overview.md) ultimately rests on this shared service
  correctly scoping every query to the logged-in reseller — a regression here is the exact
  failure mode behind the sample defect in [`sample-defect-report.md`](../sample-defect-report.md)
- **Merchant Management** — a reseller's "own" merchants are a scoped view over the same shared
  Merchant Management service every other product's merchant records live in, not a
  Reseller-only merchant table
- **Commercial Engine** — revenue-share calculation (a reseller's earnings) is computed from the
  same shared commercial data that produces each merchant's own fees
- **Report Engine / Export Engine** — reseller-scoped reports must inherit the same reseller-ID
  filtering as every other data view; a shared Report Engine defect that ignores tenant scoping
  would be a critical, not cosmetic, issue for this product specifically

## Direct Product Dependency: Collection, Payout, Connected Banking & BBPS (Not "Shared Platform," But Related)

Distinct from the shared services above, the Reseller Dashboard has a **direct, revenue-bearing
dependency** on four other products — see
[`business-overview.md`](./business-overview.md) section 7 for the full explanation. In short:
the reseller doesn't just reuse these products' infrastructure, it resells access to them to its
onboarded merchants and earns **lifetime commission** on their transaction volume across all
four. A transaction-logging or status-reporting regression in Collection, Payout, Connected
Banking, or BBPS therefore has a direct financial blast radius for this product specifically,
even though the bug itself lives elsewhere.

## Support Escalation: AI Dispute Resolution Engine

Reseller commission disputes and merchant issues arising from any of the four resold services are
ultimately handled by the shared
[AI Dispute Resolution Engine](https://github.com/ghanendra-sdet/ai-dispute-resolution-engine) —
the platform's single AI-powered support layer common across Collection, Payout, Connected
Banking, BBPS, Reseller, and YOBO.

## Platform Summary (Company-Wide Context)

| Product | Approx. Services |
|---|---|
| Collection | 38 |
| Payout | 35 |
| Connected Banking | 28 |
| Shared Platform | 28 |

**~70–80 unique logical services** across the platform in total — many shared rather than
independently reimplemented per product. The Reseller Dashboard does not have its own
separately-documented internal service breakdown in this portfolio; its module-level view is in
[`business-overview.md`](./business-overview.md).

## Testing Implication: Blast Radius

When scoping regression for a change to any shared service, ask: *which other products also
depend on this service?* For the Reseller Dashboard specifically, **any** change to the shared
Role & Permission Service warrants a full cross-reseller data-isolation smoke pass before
release — this product has the least tolerance in the entire platform for a permission-scoping
regression, given that its core value proposition is tenant isolation itself.
