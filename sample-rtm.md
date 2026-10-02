# Sample Requirement Traceability Matrix — Reseller Dashboard

> Worked example using dummy data. An RTM is referenced throughout this repo as a core
> responsibility — this is what that artifact actually looks like, not just a claim that it
> exists. See [`docs/README.md`](./docs/README.md) for the full documentation map.

## What an RTM Is Actually For

A regression checklist (see [`regression-checklist.md`](./regression-checklist.md)) answers
"what do we test." An RTM answers a different, equally important question: **"does every
business requirement have test coverage, and is that coverage actually sufficient?"** The two
documents look similar but serve different purposes — a checklist is organized by test area; an
RTM is organized by *requirement*, which is what makes it the tool that actually catches a
requirement with **no** test coverage at all, not just a weakly-tested one.

## The Matrix

| Req ID | Requirement (from a sample sprint story) | Linked Test Case(s) | Automation Status | Coverage Status |
|---|---|---|---|---|
| REQ-201 | Reseller can log in and see their own dashboard summary | TC-001, TC-002 | Automated | ✅ Covered |
| REQ-202 | Reseller can onboard a merchant under their own account | TC-003 | Automated | ✅ Covered |
| REQ-203 | Reseller can enable/disable a resold service per merchant | TC-004, TC-005 | Manual | ✅ Covered |
| REQ-204 | Revenue displayed matches underlying commercial/settlement data exactly | TC-006 | Automated | ✅ Covered |
| REQ-205 | A reseller never sees another reseller's merchants, in any list view | TC-008 | Automated | ✅ Covered |
| REQ-206 | A reseller's search can never surface another reseller's merchant, under any search term | TC-009 | Automated | ✅ Covered — closed the gap behind `BUG-RSL-4021` |
| REQ-207 | Direct object/URL access to another reseller's merchant record is denied | TC-010 | Manual | ✅ Covered |
| REQ-208 | Pagination never leaks cross-reseller data on later pages | TC-012 | Manual | ✅ Covered |
| REQ-209 | Commission accrues correctly across all four resold services (Collection/Payout/Connected Banking/BBPS) | TC-016, TC-021 | Automated | ✅ Covered |
| REQ-210 | Merchant-reseller attribution survives any length of merchant inactivity, with zero auto-expiry | TC-018 | Manual | ✅ Covered — this is the requirement `BUG-RSL-4088` violated |
| REQ-211 | Attribution changes **only** via an explicit admin reassignment action | TC-019 | Manual | ✅ Covered |
| REQ-212 | Every admin reassignment action is written to an immutable audit log, attributable to the acting admin | — | — | ❌ **Gap — no test case exists yet** |
| REQ-213 | Tenant-isolation scoping holds under concurrent, multi-reseller load (not just sequential, one-reseller-at-a-time testing) | — | — | ❌ **Gap — identified when performance testing was added to this suite; see `docs/tech-and-skills.md` section 5** |
| REQ-214 | Dashboard total commission equals the sum of Revenue's per-service breakdown | TC-020 | Manual | ✅ Covered |

## What the Gaps Actually Caught

This is the part a checklist alone wouldn't surface, because a checklist only tells you about the
tests that already exist:

- **REQ-212** came directly out of reading [`shared-platform-services.md`](./docs/shared-platform-services.md),
  which lists Audit Logs as a shared platform service this product depends on, and
  [`architecture-and-flow.md`](./docs/architecture-and-flow.md) section 5, which states
  reassignment must be "explicit, audited." `TC-019` confirms the *commission effect* of a
  reassignment is correct — it never actually asserts an audit log entry was written at all. For
  a platform whose core value proposition is trustworthy tenant/ownership guarantees, "we changed
  who owns this merchant" with no verified audit trail is a real, not cosmetic, gap. Raised as a
  new story (illustrative ID `RSL-3102`).
- **REQ-213** is a gap this RTM only caught because performance testing was added to this suite's
  scope at all (see [`docs/tech-and-skills.md`](./docs/tech-and-skills.md) section 5) — every
  existing isolation test case (`TC-008`–`TC-012`) runs against one reseller session at a time,
  sequentially. None of them actually prove isolation holds when many resellers hit the system
  simultaneously, which is precisely the scenario a connection-pooling or caching bug would need
  to manifest under. This is a textbook example of why performance and security testing overlap
  more than their separate names suggest.

**The general pattern:** an RTM's value isn't the rows that say "Covered" — those just confirm
existing test design. Its value is specifically the rows that say "Gap," because those are the
requirements a test-case-first workflow (write tests, forget to check them against the original
requirement list) would never have surfaced on its own.
