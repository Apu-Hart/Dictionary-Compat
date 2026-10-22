Corresponding GPL-3.0-only source for Dictionary Compat 0.1.0-candidate.1.
Author: Apu-Hart. Binary SHA-256: 7a3b191c6567e46fc82b3d330cb01a9c8115620bc7085af8bdee177b522dae52.
Original source, LICENSE, NOTICE and build controls are retained unchanged.
Independent build: install Java 21 and Gradle 8.12, then run gradle -p public-build --no-daemon clean build.
public-build declares public NeoForge/Mixin dependencies; it needs no Quest Games source or private classpath.
First build needs access to public Maven/Minecraft servers. The original local-cache build controls remain as historical records.
Output: public-build/build/libs/dictionarycompat-0.1.0-candidate.1.jar. The abiCheck task runs seven policy checks.
No third-party JARs, player files, credentials, or private Quest Games implementation are included.
