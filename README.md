# BlueMap Fusion resource models

This Java 21 source module holds the exact Fusion connection-mask selection
shared by independent BlueMap add-ons. Version `0.1.0-alpha.1` provides five
types in `io.github.janguenter.bluemap.resource.fusion.model`:

- `AxisVector` for bounded world-axis arithmetic;
- `FusionDirection` for the stable eight-bit mask order;
- `FusionTextureLayout` for the six supported layout names;
- `FusionTextureSelector` for exact tile and PIECED-corner selection; and
- `TextureOrientation` for BlueMap face orientation and neighbor remapping.

Profile parsers map their local layout enum by name. Wire names, sheet
dimensions, predicates, resource catalogs, installed-resource validation,
routes, activation, diagnostics, and mesh emission remain consumer-owned.

## Consumer model

BlueMap add-ons have separate classloaders and no dependable installed-library
version contract. Pin this repository at an exact commit as a Git submodule,
then compile `src/main/java` into the consumer's production source set:

```groovy
sourceSets {
    main.java.srcDir 'modules/bluemap-fusion-resource-models/src/main/java'
}
```

Do not install `bluemap-fusion-resource-models-*.jar` beside BlueMap and do not
nest it in an add-on. Consumers must verify the gitlink, checkout HEAD, and
cleanliness before compilation, and must admit each shared class exactly once
in their final archive.

## Dependencies

Four types are JDK-only. `TextureOrientation` compiles against BlueMap 5.22
core's `Direction`; the exact audited backport commit is enforced by the
composite build in `settings.gradle`. BlueMap is compile-only and is not
declared in the publication metadata or bundled in either archive.

## Build

Use Java 21, the exact BlueMap checkout, and Gradle 9.4.0 or 9.6.1:

```bash
gradle --no-daemon -PbluemapSourcePath=<exact-checkout> \
  clean check verifyPublication
```

The gate verifies source origins, all 256 masks for every consumer-supported
layout, all PIECED corner masks, every face orientation, Checkstyle, exact
archive boundaries, and dependency-free POM and Gradle module metadata.

## Licensing and provenance

The implementation is independently authored MIT code. Fusion 1.3.12 runtime
resources and its reference source are evidence only and are not redistributed.
BlueMap's MIT notice is retained because `TextureOrientation` deliberately
uses its exact 5.22 ABI. See `NOTICE.md`, `THIRD_PARTY.md`, and
`provenance/origins.json`.
