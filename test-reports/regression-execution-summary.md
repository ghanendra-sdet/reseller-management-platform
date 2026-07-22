# Reseller Dashboard — Regression Execution Summary (Sample)

> Representative regression execution report for portfolio purposes.

## Execution Overview

| Metric | Value |
|---|---|
| Test Cycle | Sample Release Regression |
| Total Test Cases Executed | 51 |
| Passed | 47 |
| Failed | 3 |
| Blocked | 1 |
| Pass Rate | 92.2% |

## Results by Area

| Area | Test Cases | Passed | Failed | Notes |
|---|---|---|---|---|
| Login & Dashboard | 6 | 6 | 0 | — |
| Merchant Management | 10 | 9 | 1 | Duplicate onboarding edge case failed |
| Service Management | 8 | 8 | 0 | — |
| Reports | 6 | 5 | 1 | 1 blocked — test data for a second reseller not seeded |
| Revenue Calculation | 6 | 6 | 0 | — |
| **Cross-Reseller Data Isolation** | 15 | 13 | 2 | **Critical**: search leakage across resellers (see BUG-RSL-4021-style defect) |

## Defect Summary

| Severity | Count |
|---|---|
| Critical | 1 |
| Major | 1 |
| Minor | 1 |

## Conclusion

The regression cycle's highest-value finding was in the data-isolation test category — exactly
where this module's QA strategy places the most weight. A critical cross-reseller search leakage
defect was caught before release, reinforcing why isolation testing is treated as a first-class
regression area rather than a secondary concern for this product.
