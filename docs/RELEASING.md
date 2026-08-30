# Releasing

Releases require a clean reviewed commit and an annotated tag named exactly
`v<module_version>`.

1. Run `clean check verifyPublication` with Gradle 9.4.0 and 9.6.1 on Java 21
   against the exact BlueMap and API commits enforced by `settings.gradle`.
2. Rebuild with Gradle 9.6.1 from clean state twice and compare the production
   JAR, sources JAR, POM, module metadata, and `SHA256SUMS` byte for byte.
3. Confirm origin, differential, Checkstyle, archive, and publication gates.
4. Inspect both archives for the exact class/source roster, notices, and
   absence of descriptors, nested JARs, BlueMap/Fusion classes, or assets.
5. Merge the reviewed version commit and create the annotated tag there.
6. Let the release workflow validate both Gradle versions, publish a draft,
   attest the exact files, publish Maven metadata, re-download every asset,
   and only then publish the prerelease or release.

A module release does not authorize a consumer update or server deployment.
