# Pilot contract

The bounded pilot is Rechiseled and Rechiseled Create. They share the exact
superset selector while exercising different resource catalogs and rendering
routes. Connected Glass remains the independent primary control; Glassential
remains a second non-migrated control. Crystalix is excluded from v0.1 because
its axis guard, direction API, orientation frame, resource admission, and test
coverage differ.

## Frozen source baselines

| Consumer | Immutable source commit | Layout subset |
| --- | --- | --- |
| Connected Glass | `4a4eb5030d18f1e54cd5a8ad1c2dc093a187ac06` | `PLAIN`, `PIECED` |
| Glassential | `13fd12412573c62847700d1cfc8e4aa9d2bb5ea1` | `PLAIN`, `SIMPLE`, `FULL`, `PIECED` |
| Rechiseled | `8588d99388c213b938d79931dd6d9e9ef8e4099c` | all six |
| Rechiseled Create | `e1fd8afc1816154a95e224815ca4015528fd0e2e` | all six |

The current main commits recorded in `provenance/origins.json` have identical
production and test sources at this boundary.

## Migration gate

Each pilot must pin the reviewed module commit as a source submodule, map its
consumer-local layout enum by name, remove the five displaced local types, and
retain all profile parsers and runtime policy. A migration must then prove:

- exact shared source and normalized bytecode parity;
- unchanged profile, generated-resource, gallery, and acceptance bytes;
- a fully accounted production and sources archive diff;
- the complete isolated consumer build and tests;
- exact artifact activation and gallery assertions in the combined 51-add-on
  ATMons 1.2.0 server on first boot and after a container replacement; and
- no duplicate-class, linkage, registration, or inactive-route diagnostics.

No consumer release is authorized by the module build alone.
