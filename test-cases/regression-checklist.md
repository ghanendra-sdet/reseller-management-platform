# Reseller Dashboard — Regression Checklist & Test Cases

> Sample regression suite structure with dummy data. Format: ID | Scenario | Steps | Expected Result

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

## 3. Negative & Edge Cases

| ID | Scenario | Steps | Expected Result |
|---|---|---|---|
| TC-013 | Enable service for non-existent merchant | 1. Attempt to enable a service for an invalid/deleted merchant ID | Clear error, no service enabled |
| TC-014 | Duplicate merchant onboarding | 1. Onboard the same dummy merchant twice under the same reseller | Duplicate rejected with a clear message |
| TC-015 | Unauthorized admin action | 1. As a reseller-role user, attempt an admin-only action (e.g. approve another reseller) | Access denied |

## 4. Full Regression Checklist

- [ ] Reseller Login & Dashboard
- [ ] Merchant Management (Onboard / Update)
- [ ] Service Management (enable/disable per merchant)
- [ ] Reports (scoped correctly to reseller)
- [ ] Revenue Calculation
- [ ] Commercials / Revenue-Share Configuration
- [ ] Cross-Reseller Data Isolation
- [ ] Permissions / Role-Based Access

## 5. Priority Automation Candidates

1. Reseller login & dashboard summary accuracy
2. Merchant onboarding under a reseller
3. Service enablement per merchant
4. Cross-reseller data isolation checks
5. Revenue report accuracy

See [`automation/`](../automation) for the Selenium/TestNG implementation.
