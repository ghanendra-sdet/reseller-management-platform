# Reseller Dashboard — Regression Checklist & Test Cases

> Sample regression suite structure with dummy data. Format: ID | Scenario | Steps | Expected Result.
> See [`docs/business-overview.md`](./docs/business-overview.md) for why data isolation (section
> 2) and lifetime commission attribution (section 3) are treated as first-class scenarios here,
> and [`docs/README.md`](./docs/README.md) for the full documentation map.

## 1. Core Flow

| ID | Scenario | Steps | Expected Result |
|---|---|---|---|
| TC-001 | Reseller login | 1. Login with valid dummy reseller credentials | Redirected to Reseller Dashboard |
| TC-002 | Dashboard summary accuracy | 1. Compare dashboard merchant count/revenue to underlying data | Totals match exactly |
| TC-003 | Onboard a new merchant | 1. Add a dummy merchant under this reseller | Merchant appears in this reseller's Merchant Management list only |
| TC-004 | Enable a service for a merchant | 1. Enable "Collection" for a dummy merchant | Service becomes active for that merchant only |
| TC-005 | Disable a service for a merchant | 1. Disable a previously enabled service | Merchant loses access to that service immediately |
| TC-006 | Revenue calculation accuracy | 1. Compare displayed revenue to a manual calculation from underlying commercial data | Figures match exactly |
| TC-007 | Reports scoped to reseller | 1. Generate a report as Reseller A | Only Reseller A's merchant data appears |

## 2. Data Isolation / Multi-Tenancy (Highest Priority)

| ID | Scenario | Steps | Expected Result |
|---|---|---|---|
| TC-008 | Cross-reseller merchant list isolation | 1. Login as Reseller A 2. View Merchant Management | Only Reseller A's merchants are listed — none from Reseller B |
| TC-009 | Cross-reseller search leakage | 1. As Reseller A, search using a term that would match a Reseller B merchant | No Reseller B data returned |
| TC-010 | Direct URL/ID access attempt | 1. As Reseller A, attempt to access a merchant record ID known to belong to Reseller B directly via URL/API | Access denied |
| TC-011 | Cross-reseller revenue report isolation | 1. As Reseller A, generate a revenue report | Report contains zero data attributable to Reseller B's merchants |
| TC-012 | Pagination doesn't leak across resellers | 1. As Reseller A, page through merchant list to the last page | All results remain scoped to Reseller A across every page |

## 3. Lifetime Commission Attribution

> Derived from [`docs/business-overview.md`](./docs/business-overview.md) section 5 — a
> merchant's commission attribution to the reseller who onboarded them must persist for the life
> of the relationship across all four resold services (Collection, Payout, Connected Banking,
> BBPS).

| ID | Scenario | Steps | Expected Result |
|---|---|---|---|
| TC-016 | Commission accrues across all four services | 1. Onboard a dummy merchant 2. Enable Collection, Payout, Connected Banking, and BBPS for that merchant 3. Simulate one transaction on each service | Reseller's Revenue module shows commission accrued from all four, correctly attributed |
| TC-017 | Attribution survives a service being disabled and re-enabled | 1. Disable then re-enable one service (e.g. BBPS) for a merchant | Commission attribution and historical earnings for that service are unaffected; new transactions resume accruing correctly |
| TC-018 | Attribution survives merchant inactivity | 1. Simulate a merchant with no transactions for an extended period 2. Resume transactions | Merchant remains attributed to the same reseller; commission resumes accruing with no re-onboarding required |
| TC-019 | Admin reassignment updates attribution correctly | 1. As Platform Admin, reassign a merchant from Reseller A to Reseller B 2. Simulate a new transaction | New commission accrues to Reseller B only; Reseller A's historical (pre-reassignment) commission remains unchanged |
| TC-020 | Dashboard total matches Revenue per-service breakdown | 1. Compare the Dashboard summary's total commission figure to the sum of the per-service breakdown in Revenue | Figures match exactly |
| TC-021 | BBPS internal-rail transactions still generate commission | 1. As a merchant using BBPS's internal Payout/Connected Banking rail (lower fee), complete a bill payment | Reseller commission accrues correctly despite the transaction routing through BBPS's internal rail rather than the external gateway |

## 4. UI Consistency

> Derived from [`docs/ui-consistency.md`](./docs/ui-consistency.md) — cross-screen consistency,
> not single-screen correctness.

| ID | Scenario | Steps | Expected Result |
|---|---|---|---|
| TC-022 | Merchant status labeling consistency | 1. Compare "Active"/"Disabled"/"Pending" labels across Dashboard, Merchant Management, and Reports | Identical labels and colors everywhere |
| TC-023 | Currency/commission formatting consistency | 1. View the same commission figure on Dashboard, Revenue, and an exported Report | Decimal places, thousands separator, and ₹ placement match exactly |
| TC-024 | Per-service commission label consistency | 1. Compare the four service names (Collection/Payout/Connected Banking/BBPS) as shown in Service Management vs. the Revenue breakdown | Identical naming used everywhere |
| TC-025 | Attribution status distinguishable without color | 1. View Active/Disabled/Pending/Reassigned states with color/grayscale rendering simulated | Each remains distinguishable via icon/text label alone |

## 5. Negative & Edge Cases

| ID | Scenario | Steps | Expected Result |
|---|---|---|---|
| TC-013 | Enable service for non-existent merchant | 1. Attempt to enable a service for an invalid/deleted merchant ID | Clear error, no service enabled |
| TC-014 | Duplicate merchant onboarding | 1. Onboard the same dummy merchant twice under the same reseller | Duplicate rejected with a clear message |
| TC-015 | Unauthorized admin action | 1. As a reseller-role user, attempt an admin-only action (e.g. approve another reseller) | Access denied |

## 6. Full Regression Checklist

- [ ] Reseller Login & Dashboard
- [ ] Merchant Management (Onboard / Update)
- [ ] Service Management (enable/disable per merchant)
- [ ] Reports (scoped correctly to reseller)
- [ ] Revenue Calculation
- [ ] Lifetime Commission Attribution (across Collection/Payout/Connected Banking/BBPS)
- [ ] Commercials / Revenue-Share Configuration
- [ ] Cross-Reseller Data Isolation
- [ ] Permissions / Role-Based Access
- [ ] UI Consistency (status labeling, formatting, terminology, accessibility)

## 7. Priority Automation Candidates

1. Reseller login & dashboard summary accuracy
2. Merchant onboarding under a reseller
3. Service enablement per merchant
4. Cross-reseller data isolation checks
5. Revenue report accuracy
6. Lifetime commission attribution across all four services

See [`automation/`](./automation) for the Selenium/TestNG implementation.
