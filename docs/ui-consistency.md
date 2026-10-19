# Reseller Dashboard — UI Consistency

> Merchant, revenue, and commission data surfaces across Dashboard, Merchant Management, Service
> Management, Reports, and Revenue (see [`business-overview.md`](./business-overview.md)). This
> document covers whether it's represented **consistently** across all of them.

## Why a UI Mismatch Here Isn't Just Cosmetic

This module's two central risks — cross-reseller data leakage (section 4) and lifetime commission
attribution (section 5) of [`business-overview.md`](./business-overview.md) — are both silent
failure modes. A reseller who sees mismatched revenue figures between the Dashboard summary and a
Revenue report can't tell whether that's a rendering bug or an actual commission-calculation
defect, and will, correctly, escalate either way.

## 1. Status Representation Consistency

| Status | Expected Label | Expected Color (convention) |
|---|---|---|
| Merchant active under reseller | "Active" | Green |
| Merchant service disabled | "Disabled" | Grey/neutral, not Red (not a failure state) |
| Merchant onboarding pending | "Pending" | Amber |
| Merchant attribution reassigned/removed | "Reassigned" / "Unattributed" | Distinct from "Disabled" — this is an attribution-record change, not a service-state change |

## 2. Currency & Commission Formatting Consistency

| Element | Convention to Verify |
|---|---|
| Currency symbol | Consistent ₹ placement across Dashboard summary, Revenue module, and exported Reports |
| Decimal places | Commission figures always shown to 2 decimal places, never rounded differently on different screens |
| Per-service breakdown | If Revenue shows a per-service (Collection/Payout/Connected Banking/BBPS) commission breakdown, the four service labels must exactly match the labels used in Service Management |

## 3. Terminology Consistency

Per the glossary in [`business-overview.md`](./business-overview.md), watch for drift on:

- "Revenue Share" vs. "Commission" vs. "Earnings" used interchangeably for the same figure
- "Merchant Scoping" vs. "Tenant Isolation" as different labels for the same guarantee
- "Attribution" — must be labeled identically wherever a merchant's owning reseller is displayed
  (Merchant Management, Reports, Revenue, and any admin reassignment screen)

## 4. Lifetime Commission Display Consistency

Per [`business-overview.md`](./business-overview.md) section 5, commission accrues across four
services for the life of the merchant relationship. This introduces its own consistency
requirements:

- Total commission shown on the Dashboard summary must equal the sum of the per-service
  commission breakdown shown in Revenue — any mismatch is a calculation or aggregation defect,
  not a UI nuance
- A merchant's attribution status (still attributed / reassigned / removed) must be labeled and
  colored identically everywhere it appears, since it directly explains why commission is or
  isn't accruing
- If a report shows commission trending to zero for a previously active merchant, the UI must
  make it discoverable *why* — service disabled, attribution changed, or merchant churned — never
  an unexplained drop

## 5. Empty States & Error Messages

- Does Merchant Management, Reports, and Revenue each show a deliberate empty state for a brand
  new reseller with zero onboarded merchants, distinct from an error state?
- Is the "access denied" message shown on a cross-reseller access attempt (see
  [`regression-checklist.md`](../regression-checklist.md) section 2) worded consistently
  regardless of which screen triggered it?

## 6. Cross-Browser & Responsive Consistency

- Do merchant lists, revenue tables, and status badges render identically across Chrome, Firefox,
  and Safari/WebKit?
- Does the Dashboard summary degrade gracefully on smaller viewports without truncating
  commission figures?

## 7. Accessibility Consistency

- Are Active/Disabled/Pending/Reassigned states distinguishable by more than color alone?

---

## Coverage Mapping

See [`../regression-checklist.md`](../regression-checklist.md) section 4 for the UI consistency
test cases derived from this document.
