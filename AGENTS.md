# Agent guide for BlueMap Fusion resource models

Read this file, `README.md`, `docs/ARCHITECTURE.md`, and
`provenance/origins.json` before changing production code.

## Scope

Version `0.1.0-alpha.1` contains only `AxisVector`, `FusionDirection`,
`FusionTextureLayout`, `FusionTextureSelector`, and `TextureOrientation` in
the neutral `io.github.janguenter.bluemap.resource.fusion.model` package.

Keep predicates, block-state policy, JSON parsing, resource catalogs,
installed-resource admission, routes, diagnostics, activation, and mesh
emission in consumers. Do not generalize this Fusion contract to Athena or CTM.

## Dependency and packaging contract

`AxisVector`, `FusionDirection`, `FusionTextureLayout`, and
`FusionTextureSelector` are JDK-only. `TextureOrientation` uses BlueMap 5.22's
`Direction` ABI. The build must use the exact BlueMap backport and API commits
in `settings.gradle`; no BlueMap class is bundled.

Consumers pin this repository as a Git submodule and compile its production
sources into their own add-on JAR. The standalone module JAR is not a server
component. Do not add an entrypoint, descriptor, service registration,
`module-info`, nested JAR, mod metadata, installed provider, or runtime state.

## Origin contract

The three stable helpers are exact Rechiseled copies after package
normalization. The selector changes only its package, parameter type name, and
corrected Javadoc. `FusionTextureLayout` projects the six Rechiseled enum names
without importing consumer parsing or sheet dimensions. Frozen consumer
oracles and hashes are recorded in `provenance/origins.json`.

Any behavior change requires a new version, focused differential evidence,
and a consumer impact review. Never copy or redistribute Fusion code or assets.

## Required gates

Serialize Gradle with `/tmp/bluemap-gradle.lock` and run:

```bash
flock /tmp/bluemap-gradle.lock gradle-9.4.0 --no-daemon \
  -PbluemapSourcePath=<exact-checkout> clean check verifyPublication
flock /tmp/bluemap-gradle.lock gradle-9.6.1 --no-daemon \
  -PbluemapSourcePath=<exact-checkout> clean check verifyPublication
```

Before release, reproduce every publication file twice with Gradle 9.6.1 and
compare bytes. Inspect both archives and run `actionlint` after workflow edits.
A version increase and release require a reviewed pull request and an annotated
`v<module_version>` tag.
