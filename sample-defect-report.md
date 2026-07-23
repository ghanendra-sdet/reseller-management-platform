# Sample Defect Report — Reseller Dashboard

> Template + worked example using dummy data. Reflects the data-isolation and lifetime-commission
> defect themes that are the primary risk areas for this module — see
> [`docs/business-overview.md`](./docs/business-overview.md) sections 4–5 for why, and
> [`docs/README.md`](./docs/README.md) for the full documentation map.

---

## Defect #1

| Field | Value |
|---|---|
| **ID** | BUG-RSL-4021 (sample) |
| **Title** | Merchant search returns results from another reseller's portfolio |
| **Severity** | Critical |
| **Module** | Reseller Dashboard → Merchant Management |
| **Environment** | UAT (dummy data) |

**Steps to Reproduce**
1. Log in as dummy Reseller A
2. Onboard a dummy merchant named "Demo Merchant A1"
3. Log out, log in as dummy Reseller B (a different reseller account)
4. In Merchant Management, search for "Demo Merchant"

**Expected Result**
Reseller B's search should return zero results — "Demo Merchant A1" belongs to Reseller A and
must never be visible to Reseller B under any search term.

**Actual Result**
The search returns "Demo Merchant A1" in Reseller B's results. The search endpoint filters by
search term but does not additionally filter by the logged-in reseller's ID.

**Impact**
A direct multi-tenancy data leak — one reseller can discover another reseller's merchant
portfolio, which is a serious confidentiality and competitive-harm issue.

**Suggested Fix**
Enforce reseller-ID scoping at the query level for every merchant-related endpoint, not only in
the default (unfiltered) list view — search, filters, and pagination must all inherit the same
scoping constraint.

---

## Defect #2

| Field | Value |
|---|---|
| **ID** | BUG-RSL-4088 (sample) |
| **Title** | Merchant's commission stops accruing to reseller after an extended inactivity period, despite no admin reassignment |
| **Severity** | Major |
| **Module** | Reseller Dashboard → Revenue / Commission Attribution |
| **Environment** | UAT (dummy data) |

**Steps to Reproduce**
1. As dummy Reseller A, onboard a dummy merchant and confirm commission accrues from an initial
   Collection transaction
2. Simulate the merchant having zero transactions for an extended period (dummy test data)
3. Resume transactions on the same merchant (e.g. a new BBPS bill payment)
4. Check Reseller A's Revenue module for the new commission

**Expected Result**
Per [`docs/business-overview.md`](./docs/business-overview.md) section 5, attribution — and
therefore commission eligibility — persists for the **lifetime** of the merchant relationship
unless an admin explicitly reassigns it. The new transaction's commission should accrue to
Reseller A automatically.

**Actual Result**
The new transaction's commission does not appear in Reseller A's Revenue module. Investigation
shows an internal batch job treats merchants with no transactions past a fixed window as
"dormant" and clears their attribution flag, silently detaching them from their reseller with no
admin action and no notification.

**Impact**
Directly contradicts the platform's lifetime-commission commercial model — the reseller loses
earned revenue on a merchant they legitimately still own, through no fault of the merchant or the
reseller. A significant trust and commercial-accuracy issue if it reached production.

**Suggested Fix**
Remove any time-based auto-expiry of merchant-reseller attribution. Attribution should only ever
change via an explicit, audited admin reassignment action (see
[`regression-checklist.md`](./regression-checklist.md) TC-018–019).

---

## Defect Reporting Template (blank)

| Field | Value |
|---|---|
| **ID** | |
| **Title** | |
| **Severity** | Minor / Major / Critical / Blocker |
| **Module** | |
| **Environment** | |

**Steps to Reproduce**
1.
2.
3.

**Expected Result**


**Actual Result**


**Impact**


**Suggested Fix**

