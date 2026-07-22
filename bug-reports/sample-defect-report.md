# Sample Defect Report — Reseller Dashboard

> Template + worked example using dummy data. Reflects the data-isolation defect theme that is
> the primary risk area for this module.

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

