# Reseller Dashboard — Documentation Map

> New to this repo? Start here. This page answers the questions a tech-curious QA/SDET would
> actually ask, and points to exactly the doc that answers each one.

| Question | Answer |
|---|---|
| What is this, in plain terms? | [`business-overview.md`](./business-overview.md) sections 1–2 |
| Who's involved / stakeholders? | [`business-overview.md`](./business-overview.md) section 6 |
| What does it depend on? | [`business-overview.md`](./business-overview.md) section 7, [`shared-platform-services.md`](./shared-platform-services.md) |
| How does the reseller earn money — business flow? | [`business-overview.md`](./business-overview.md) section 5 (lifetime commission model) |
| What are the modules and submodules? | [`business-overview.md`](./business-overview.md) section 2 |
| How does onboarding, commission accrual, and attribution actually work — real sequence diagrams? | [`architecture-and-flow.md`](./architecture-and-flow.md) |
| What's the highest-risk testing theme? | [`business-overview.md`](./business-overview.md) section 4 (data isolation) |
| How does a real cross-tenant data leak actually happen under the hood? | [`architecture-and-flow.md`](./architecture-and-flow.md) section 4 |
| What tech was used, and what skills does this repo demonstrate? | [`tech-and-skills.md`](./tech-and-skills.md) |
| What does the UI need to get right, consistently? | [`ui-consistency.md`](./ui-consistency.md) |
| What's tested? | [`../regression-checklist.md`](../regression-checklist.md) |
| What's automated? | [`../automation/README.md`](../automation/README.md) |
| What does a real-looking defect report look like? | [`../sample-defect-report.md`](../sample-defect-report.md) |
| What does a regression execution report look like? | [`../regression-execution-summary.md`](../regression-execution-summary.md) |
| What does a Requirement Traceability Matrix (RTM) actually look like? | [`../sample-rtm.md`](../sample-rtm.md) |

## Business Flow vs. Tech Flow vs. User Flow

- **Business Flow** — how the reseller *earns*: onboard a merchant once, that merchant is
  permanently attributed to the reseller, and every transaction that merchant makes across
  Collection/Payout/Connected Banking/BBPS generates a commission for the reseller, for the life
  of the relationship. See [`business-overview.md`](./business-overview.md) section 5.
- **Tech Flow** — how attribution and commission are computed under the hood: the Commercial
  Engine reads each of the four services' transaction data, applies the reseller's commission
  rate, and aggregates it into the reseller-scoped Revenue view, all gated by the shared Role &
  Permission Service so no reseller ever sees another's numbers. See
  [`shared-platform-services.md`](./shared-platform-services.md) and
  [`architecture-and-flow.md`](./architecture-and-flow.md) for the full sequence diagrams.
- **User Flow** — what the reseller actually clicks through: Dashboard → Merchant Management
  (onboard) → Service Management (enable services) → Reports → Revenue. See the README's
  [How It Works](../README.md#-how-it-works--business-flow) section.

## Reading Order

```
README.md (repo root)
      │
      ▼
docs/business-overview.md      ← what this is, modules/submodules, lifetime commission model, stakeholders
      │
      ▼
docs/architecture-and-flow.md  ← real Mermaid sequence/flow diagrams: onboarding, commission accrual,
      │                            the Defect #1 defense-in-depth mechanism, lifetime attribution state model
      ▼
docs/tech-and-skills.md        ← full tech stack, skill → proof map, CI/CD shape, performance testing depth
      │
      ▼
docs/ui-consistency.md         ← cross-screen consistency for revenue/commission data
      │
      ▼
docs/shared-platform-services.md  ← company-wide services + direct product dependencies
      │
      ▼
regression-checklist.md        ← test cases, including lifetime-attribution and isolation scenarios
      │
      ▼
sample-defect-report.md → sample-rtm.md → regression-execution-summary.md → automation/README.md
```
