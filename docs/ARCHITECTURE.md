# Architecture

## Boundary

The module contains two layers in one source-bundled archive:

1. `AxisVector`, `FusionDirection`, `FusionTextureLayout`, and
   `FusionTextureSelector` use only Java 21.
2. `TextureOrientation` uses the exact BlueMap 5.22 `Direction` ABI.

This split is explicit in source and provenance. Replacing `Direction` with a
new facade would add an abstraction unsupported by the four exact consumers.

`FusionTextureLayout` deliberately contains only `PLAIN`, `PIECED`, `FULL`,
`HORIZONTAL`, `VERTICAL`, and `SIMPLE`. Consumers retain wire parsing and sheet
dimensions, then map their local enum to this enum by the exact constant name.
The selector rejects masks outside `0..255`; PIECED corner selection rejects
non-corner directions. These failures match the frozen implementations.

## Packaging

Each add-on compiles the five shared sources into its own JAR. The standalone
JAR, sources JAR, POM, and Gradle module metadata are review artifacts, not a
server dependency. They contain no descriptor, entrypoint, service, mod
metadata, nested JAR, Fusion asset, or BlueMap class.

## Deliberate exclusions

The module does not own:

- Fusion JSON parsing or format-version profiles;
- block predicates or BlueMap `BlockState` coupling;
- resource roots, first-winner rules, structural closure, or collision policy;
- block allowlists, routes, registration, diagnostics, or activation;
- geometry, UV, lighting, ambient occlusion, culling, or map-color emission;
- Athena, CTM, or a generic connected-texture strategy API.

Those contracts differ materially across consumers. Moving them would require
policy callbacks or resource abstractions that the current code does not prove.

## Verification

Frozen MIT sources from Connected Glass, Glassential, Rechiseled, and
Rechiseled Create compile only in the test source set. Differential tests run
every byte mask across each consumer's admitted layout subset and every byte
mask across every PIECED corner. Source-origin hashes and package-normalized
parity are checked independently by Gradle.
