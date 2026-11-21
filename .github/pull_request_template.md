## Summary

<!-- What changed? Keep this short and reviewer-friendly. -->

## Why

<!-- What problem or learning goal does this solve? -->

## Verification

<!-- State what you actually ran or checked. -->

- [ ] `./gradlew test`
- [ ] Docker build/run verified, if this change affects a service or container
- [ ] Manually verified the changed behavior, where applicable

## Infrastructure / data impact

<!-- Write None, or describe Azure, database, migration, configuration, or secret changes. -->

## Risk and rollback

<!-- What could fail? How would we safely revert this change? -->

## Reviewer checklist

- [ ] No secrets, credentials, or local `.env` files are committed
- [ ] Tests and documentation were updated where needed
- [ ] Database migrations are backward-compatible, if applicable
- [ ] CI/CD or deployment impact has been considered
